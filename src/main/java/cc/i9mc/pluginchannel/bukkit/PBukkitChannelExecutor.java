package cc.i9mc.pluginchannel.bukkit;

import cc.i9mc.pluginchannel.runable.PChannelResult;
import org.bukkit.entity.Player;


public class PBukkitChannelExecutor {
	
	private PBukkitChannel channel;
	
	public PBukkitChannelExecutor(PBukkitChannel channel) {
		this.channel = channel;
	}
	
	public void connect(Player target, String server) {
		PBukkitChannelTask.createTask()
			.channel(channel)
			.sender(target)
			.command("BungeeCord", "ConnectOther", target.getName(), server)
			.run();
	}
	
	public void connectOther(Player sender, String target, String server) {
		PBukkitChannelTask.createTask()
			.channel(channel)
			.sender(sender)
			.command("BungeeCord", "ConnectOther", target, server)
			.run();
	}
	
	public void message(Player sender, String target, String message) {
		PBukkitChannelTask.createTask()
			.channel(channel)
			.sender(sender)
			.command("BungeeCord", "Message", target, message)
			.run();
	}
	
	public void broadcast(Player sender, String target, String message) {
		PBukkitChannelTask.createTask()
			.channel(channel)
			.sender(sender)
			.command("BungeeCord", "Broadcast", target, message)
			.run();
	}
	
	public void kickPlayer(Player sender, String target, String reason) {
		PBukkitChannelTask.createTask()
			.channel(channel)
			.sender(sender)
			.command("BungeeCord", "KickPlayer", target, reason)
			.run();
	}
	
	public void whois(Player sender, String target, PChannelResult runnable) {
		PBukkitChannelTask.createTask()
			.channel(channel)
			.sender(sender)
			.command("BungeeCord", "Whois", target)
			.result(runnable)
			.run();
	}
	
	public void playerCount(Player sender, String target, PChannelResult runnable) {
		PBukkitChannelTask.createTask()
			.channel(channel)
			.sender(sender)
			.command("BungeeCord", "PlayerCount", target)
			.result(runnable)
			.run();
	}
	
	public void playerList(Player sender, String target, PChannelResult runnable) {
		PBukkitChannelTask.createTask()
			.channel(channel)
			.sender(sender)
			.command("BungeeCord", "PlayerList", target)
			.result(runnable)
			.run();
	}
	
	public void serverList(Player sender, PChannelResult runnable) {
		PBukkitChannelTask.createTask()
			.channel(channel)
			.sender(sender)
			.command("BungeeCord", "ServerList")
			.result(runnable)
			.run();
	}

	public PBukkitChannel getChannel() {
		return this.channel;
	}
}
