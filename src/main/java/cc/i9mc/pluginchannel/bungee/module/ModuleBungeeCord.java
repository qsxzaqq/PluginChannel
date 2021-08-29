package cc.i9mc.pluginchannel.bungee.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cc.i9mc.pluginchannel.BungeeChannel;
import cc.i9mc.pluginchannel.events.BungeeCommandEvent;
import cc.i9mc.pluginchannel.logger.PLogger;
import cc.i9mc.pluginchannel.util.ArrayUtils;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;


public class ModuleBungeeCord implements Listener {
	
	@EventHandler
	public void onBungeeCommand(BungeeCommandEvent e) {
		if (e.isCancelled() || !e.getArgs()[0].equals("BungeeCord")) {
			return;
		}
		try {
			if (e.getArgs()[1].equalsIgnoreCase("ConnectOther")) {
				ProxiedPlayer player = BungeeChannel.getInstance().getProxy().getPlayer(e.getArgs()[2]);
				if (player == null) {
					PLogger.error("Invalid Player: &c" + e.getArgs()[2]);
					return;
				}
				ServerInfo serverInfo = BungeeChannel.getInstance().getProxy().getServerInfo(e.getArgs()[3]);
				if (serverInfo == null) {
					PLogger.error("Invalid Server: &c" + e.getArgs()[3]);
					return;
				}
				player.connect(serverInfo);
			}
			else if (e.getArgs()[1].equalsIgnoreCase("Message")) {
				ProxiedPlayer player = BungeeChannel.getInstance().getProxy().getPlayer(e.getArgs()[2]);
				if (player == null) {
					PLogger.error("Invalid Player: &c" + e.getArgs()[2]);
					return;
				}
				player.sendMessage(new TextComponent(ArrayUtils.arrayJoin(e.getArgs(), 3)));
			}
			else if (e.getArgs()[1].equalsIgnoreCase("Broadcast")) {
				List<ProxiedPlayer> players = new ArrayList<>();
				if (e.getArgs()[2].equalsIgnoreCase("all")) {
					players.addAll(BungeeChannel.getInstance().getProxy().getPlayers());
				} else {
					ServerInfo serverInfo = BungeeChannel.getInstance().getProxy().getServerInfo(e.getArgs()[2]);
					if (serverInfo == null) {
						PLogger.error("Invalid Server: &c" + e.getArgs()[2]);
						return;
					} else {
						players.addAll(serverInfo.getPlayers());
					}
				}
				TextComponent message = new TextComponent(ArrayUtils.arrayJoin(e.getArgs(), 3));
				players.forEach(x -> x.sendMessage(message));
			}
			else if (e.getArgs()[1].equalsIgnoreCase("KickPlayer")) {
				ProxiedPlayer player = BungeeChannel.getInstance().getProxy().getPlayer(e.getArgs()[2]);
				if (player == null) {
					PLogger.error("Invalid Player: &c" + e.getArgs()[2]);
					return;
				}
				player.disconnect(new TextComponent(ArrayUtils.arrayJoin(e.getArgs(), 3)));
			}
			else if (e.getArgs()[1].equalsIgnoreCase("Whois")) {
				ProxiedPlayer player = BungeeChannel.getInstance().getProxy().getPlayer(e.getArgs()[2]);
				if (player == null) {
					e.response("-");
				} else {
					e.response(player.getAddress().toString(), String.valueOf(player.getPing()), player.getServer().getInfo().getName());
				}
			}
			else if (e.getArgs()[1].equalsIgnoreCase("PlayerCount")) {
				if (e.getArgs()[2].equalsIgnoreCase("all")) {
					e.response(String.valueOf(BungeeChannel.getInstance().getProxy().getPlayers().size()));
				} else {
					ServerInfo serverInfo = BungeeChannel.getInstance().getProxy().getServerInfo(e.getArgs()[2]);
					if (serverInfo == null) {
						e.response("-");
					} else {
						e.response(String.valueOf(serverInfo.getPlayers().size()));
					}
				}
			}
			else if (e.getArgs()[1].equalsIgnoreCase("PlayerList")) {
				List<String> players = new ArrayList<>();
				if (e.getArgs()[2].equalsIgnoreCase("all")) {
					BungeeChannel.getInstance().getProxy().getPlayers().forEach(x -> players.add(x.getName()));
				} else {
					ServerInfo serverInfo = BungeeChannel.getInstance().getProxy().getServerInfo(e.getArgs()[2]);
					if (serverInfo == null) {
						e.response("-");
					} else {
						serverInfo.getPlayers().forEach(x -> players.add(x.getName()));
					}
				}
				e.response(players.toArray(new String[0]));
			}
			else if (e.getArgs()[1].equalsIgnoreCase("ServerList")) {
				List<String> servers = new ArrayList<>(BungeeChannel.getInstance().getProxy().getServers().keySet());
				e.response(servers.toArray(new String[0]));
			}
		} catch (Exception err) {
			PLogger.error("Invalid BungeeCommand: &c" + Arrays.asList(e.getArgs()));
			PLogger.error("Exception: &c" + err.getMessage());
		}
	}
}
