package cc.i9mc.pluginchannel;

import cc.i9mc.pluginchannel.bukkit.PBukkitChannel;
import cc.i9mc.pluginchannel.bukkit.PBukkitChannelExecutor;
import cc.i9mc.pluginchannel.enums.ServerType;
import cc.i9mc.pluginchannel.listener.ListenerBukkitMessage;
import cc.i9mc.pluginchannel.listener.ListenerPlayer;
import cc.i9mc.pluginchannel.logger.PLogger;
import cc.i9mc.pluginchannel.playerdata.PlayerDataBukkitHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public class BukkitChannel extends JavaPlugin {
	private static BukkitChannel inst;
	private PBukkitChannel bukkitChannel;
	private PBukkitChannelExecutor bukkitChannelExecutor;
	private PlayerDataBukkitHandler playerDataHandler;

	public static BukkitChannel getInst() {
		return BukkitChannel.inst;
	}

	@Override
	public void onLoad() {
		ServerType.setServerType(ServerType.BUKKIT);
	}

	@Override
	public void onEnable() {
		inst = this;
		bukkitChannel = new PBukkitChannel(this);
		bukkitChannelExecutor = new PBukkitChannelExecutor(bukkitChannel);
		playerDataHandler = new PlayerDataBukkitHandler(bukkitChannel);

		Bukkit.getMessenger().registerIncomingPluginChannel(this, "pluginchannel:out", new ListenerBukkitMessage());
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "pluginchannel:in");

		Bukkit.getPluginManager().registerEvents(new ListenerPlayer(), this);

		PLogger.info("插件已载入");
		PLogger.info("框架: &8" + ServerType.getServerType());
	}

	@Override
	public void onDisable() {
		Bukkit.getMessenger().unregisterIncomingPluginChannel(this);
		Bukkit.getMessenger().unregisterOutgoingPluginChannel(this);
	}

	public PBukkitChannel getBukkitChannel() {
		return this.bukkitChannel;
	}

	public PBukkitChannelExecutor getBukkitChannelExecutor() {
		return this.bukkitChannelExecutor;
	}

	public PlayerDataBukkitHandler getPlayerDataHandler() {
		return this.playerDataHandler;
	}

}
