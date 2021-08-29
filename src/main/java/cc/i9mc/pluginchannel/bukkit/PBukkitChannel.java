package cc.i9mc.pluginchannel.bukkit;

import cc.i9mc.pluginchannel.events.BukkitCommandEvent;
import cc.i9mc.pluginchannel.message.MessageBuilder;
import cc.i9mc.pluginchannel.timeable.Timeable;
import cc.i9mc.pluginchannel.util.ByteUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class PBukkitChannel implements Listener {

    private Plugin plugin;
    private List<PBukkitChannelTask> tasks = new CopyOnWriteArrayList<>();

    public PBukkitChannel(Plugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (PBukkitChannelTask task : tasks) {
                if (task instanceof Timeable && task.isTimeLess()) {
                    tasks.remove(task);
                    if (task.getRunnableTimeless() != null) {
                        task.getRunnableTimeless().run();
                    }
                }
            }
        }, 20, 20);
    }

    @EventHandler
    public void onMessage(BukkitCommandEvent e) {
        if (e.isCancelled()) {
            return;
        }
        for (PBukkitChannelTask task : tasks) {
            if (task.getUid().equals(e.getUid())) {
                try {
                    task.getRunnable().run(e.getArgs());
                } catch (Exception ignored) {
                }
                tasks.remove(task);
            }
        }
    }

    public void sendBukkitMessage(Player player, String... args) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
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
                player.sendPluginMessage(plugin, "pluginchannel:in", byteArrayOutputStream.toByteArray());
            }
        });
    }

    public Player getOnlinePlayer() {
        return Bukkit.getOnlinePlayers().size() == 0 ? null : Bukkit.getOnlinePlayers().iterator().next();
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public List<PBukkitChannelTask> getTasks() {
        return this.tasks;
    }
}
