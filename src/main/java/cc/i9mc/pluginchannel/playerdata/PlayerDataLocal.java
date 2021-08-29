package cc.i9mc.pluginchannel.playerdata;

import cc.i9mc.pluginchannel.BukkitChannel;
import cc.i9mc.pluginchannel.runable.PChannelResult;
import org.bukkit.entity.Player;


public class PlayerDataLocal {
	
	private String username;
	private String ip = "NULL";
	private String server = "NULL";
	
	public PlayerDataLocal(Player player) {
		this.username = player.getName();
		
		BukkitChannel.getInst().getBukkitChannelExecutor().whois(player, username, result -> {
			ip = result[0];
			if (!ip.equals("-")) {
				server = result[2];
			}
		});
	}

	public String getUsername() {
		return this.username;
	}

	public String getIp() {
		return this.ip;
	}

	public String getServer() {
		return this.server;
	}
}
