package cc.i9mc.pluginchannel.listener;

import com.google.common.io.ByteStreams;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import cc.i9mc.pluginchannel.events.BukkitCommandEvent;
import cc.i9mc.pluginchannel.message.MessageBuilder;
import cc.i9mc.pluginchannel.message.ReadResult;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.Arrays;
import java.util.UUID;


public class ListenerBukkitMessage implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (channel.equalsIgnoreCase("pluginchannel:out")) {
            try {
                ReadResult readResult = MessageBuilder.readMessage(ByteStreams.newDataInput(message).readUTF());
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
                Bukkit.getPluginManager().callEvent(new BukkitCommandEvent(player, uuid, packet));
                MessageBuilder.MESSAGE_CACHES.remove(uuid.toString());
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
