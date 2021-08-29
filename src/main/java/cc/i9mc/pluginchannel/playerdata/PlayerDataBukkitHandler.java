package cc.i9mc.pluginchannel.playerdata;

import cc.i9mc.pluginchannel.bukkit.PBukkitChannel;
import cc.i9mc.pluginchannel.bukkit.PBukkitChannelTask;
import cc.i9mc.pluginchannel.runable.PChannelResult;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;


public class PlayerDataBukkitHandler {
	
	private PBukkitChannel channel;
	private HashMap<String, PlayerDataLocal> playerData = new HashMap<>();
	
	public PlayerDataBukkitHandler(PBukkitChannel channel) {
		this.channel = channel;
		new BukkitRunnable() {
			
			@Override
			public void run() {
				Bukkit.getOnlinePlayers().forEach(x -> loadPlayerDataLocal(x));
			}
		}.runTaskTimerAsynchronously(channel.getPlugin(), 0, 20 * 30);
	}
	
	public void setPlayerData(String target, String key, String value) {
		PBukkitChannelTask.createTask()
			.channel(channel)
			.sender(channel.getOnlinePlayer())
			.command("PlayerData", "Set", target, key, value)
			.run();
	}
	
	public void removePlayerData(String target, String key) {
		PBukkitChannelTask.createTask()
			.channel(channel)
			.sender(channel.getOnlinePlayer())
			.command("PlayerData", "Remove", target, key)
			.run();
	}
	
	public void getPlayerData(String target, String key, PChannelResult runnable) {
		PBukkitChannelTask.createTask()
			.channel(channel)
			.sender(channel.getOnlinePlayer())
			.command("PlayerData", "Get", target, key)
			.result(runnable)
			.run();
	}
	
	public PlayerDataLocal getPlayerDataLocal(Player player) {
		return playerData.get(player.getName());
	}
	
	public void loadPlayerDataLocal(Player player) {
		playerData.put(player.getName(), new PlayerDataLocal(player));
	}
	
	public void unloadPlayerDataLocal(Player player) {
		playerData.remove(player.getName());
	}

	public PBukkitChannel getChannel() {
		return this.channel;
	}

	public HashMap<String, PlayerDataLocal> getPlayerData() {
		return this.playerData;
	}
}
