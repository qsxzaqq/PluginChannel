package cc.i9mc.pluginchannel;

import cc.i9mc.pluginchannel.bungee.PBungeeChannel;
import cc.i9mc.pluginchannel.bungee.module.ModuleBungeeCord;
import cc.i9mc.pluginchannel.enums.ServerType;
import cc.i9mc.pluginchannel.listener.ListenerBungeeMessage;
import cc.i9mc.pluginchannel.logger.PLogger;
import cc.i9mc.pluginchannel.playerdata.PlayerDataBungeeHandler;
import cc.i9mc.pluginchannel.util.TagUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;


public class BungeeChannel extends Plugin {

	private static BungeeChannel instance;
	private ProxyServer proxyServer;
	private PBungeeChannel bungeeChannel;
	private PlayerDataBungeeHandler playerDataHandler;

	public static BungeeChannel getInstance() {
		return BungeeChannel.instance;
	}

	@Override
	public void onLoad() {
		ServerType.setServerType(ServerType.BUNGEECORD);
	}

	@Override
	public void onEnable() {
		instance = this;
		proxyServer = ProxyServer.getInstance();
		bungeeChannel = new PBungeeChannel(this);
		playerDataHandler = new PlayerDataBungeeHandler(this);

		TagUtils.getInst();

		proxyServer.registerChannel("pluginchannel:in");
		proxyServer.registerChannel("pluginchannel:out");

		getProxy().getPluginManager().registerListener(this, new ListenerBungeeMessage());
		getProxy().getPluginManager().registerListener(this, new ModuleBungeeCord());

		PLogger.info("插件已载入");
		PLogger.info("框架: &8" + ServerType.getServerType());
	}

	@Override
	public void onDisable() {
		proxyServer.getScheduler().cancel(this);
	}

	public ProxyServer getProxyServer() {
		return this.proxyServer;
	}

	public PBungeeChannel getBungeeChannel() {
		return this.bungeeChannel;
	}

	public PlayerDataBungeeHandler getPlayerDataHandler() {
		return this.playerDataHandler;
	}
}
