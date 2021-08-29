package cc.i9mc.pluginchannel.bungee;

import cc.i9mc.pluginchannel.BungeeChannel;
import cc.i9mc.pluginchannel.events.BungeeCommandEvent;
import cc.i9mc.pluginchannel.message.MessageBuilder;
import cc.i9mc.pluginchannel.timeable.Timeable;
import cc.i9mc.pluginchannel.util.ByteUtils;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;


public class PBungeeChannel implements Listener {

    private List<PBungeeChannelTask> tasks = new CopyOnWriteArrayList<>();

    public PBungeeChannel(Plugin plugin) {
        BungeeChannel.getInstance().getProxy().getPluginManager().registerListener(plugin, this);
        plugin.getProxy().getScheduler().schedule(plugin, () -> {
            for (PBungeeChannelTask task : tasks) {
                if (task instanceof Timeable && task.isTimeLess()) {
                    tasks.remove(task);
                    if (task.getRunnableTimeless() != null) {
                        task.getRunnableTimeless().run();
                    }
                }
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    @EventHandler
    public void onCommand(BungeeCommandEvent e) {
        if (e.isCancelled()) {
            return;
        }
        for (PBungeeChannelTask task : tasks) {
            if (task.getUid().equals(e.getUid())) {
                try {
                    task.getRunnable().run(e.getArgs());
                } catch (Exception ignored) {
                }
                tasks.remove(task);
            }
        }
    }

    public void sendBungeeMessage(ProxiedPlayer player, String... args) {
        sendBungeeMessage(player.getServer(), args);
    }

    public void sendBungeeMessage(Server server, String... args) {
        BungeeChannel.getInstance().getProxy().getScheduler().runAsync(BungeeChannel.getInstance(), () -> {
            for (String data : MessageBuilder.createMessage(args)) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                try {
                    dataOutputStream.writeUTF(data);
                } catch (Exception ignored) {
                } finally {
                    ByteUtils.close(dataOutputStream);
                    ByteUtils.close(byteArrayOutputStream);
                }
                server.sendData("pluginchannel:out", byteArrayOutputStream.toByteArray());
            }
        });
    }

    public List<PBungeeChannelTask> getTasks() {
        return this.tasks;
    }
}
