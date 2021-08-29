package cc.i9mc.pluginchannel.listener;

import cc.i9mc.pluginchannel.BungeeChannel;
import com.google.common.io.ByteStreams;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import cc.i9mc.pluginchannel.events.BungeeCommandEvent;
import cc.i9mc.pluginchannel.message.MessageBuilder;
import cc.i9mc.pluginchannel.message.ReadResult;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;


public class ListenerBungeeMessage implements Listener {

    @EventHandler
    public void onMessage(PluginMessageEvent e) {
        if (!e.isCancelled() && e.getSender() instanceof Server && e.getTag().equalsIgnoreCase("pluginchannel:in")) {
            try {
                ReadResult readResult = MessageBuilder.readMessage(ByteStreams.newDataInput(e.getData()).readUTF());
                if (!readResult.isFull()) {
                    return;
                }
                UUID uuid = UUID.fromString(readResult.getCurrentMessages().get(0).getUid());
                JsonArray array = (JsonArray) new JsonParser().parse(readResult.build());
                String[] packet = new String[array.size()];
                int bound = array.size();
                for (int i = 0; i < bound; i++) {
                    packet[i] = array.get(i).getAsString();
                }
                BungeeChannel.getInstance().getProxy().getPluginManager().callEvent(new BungeeCommandEvent((Server) e.getSender(), uuid, packet));
                MessageBuilder.MESSAGE_CACHES.remove(uuid.toString());
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
