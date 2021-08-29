package cc.i9mc.pluginchannel.logger;

import cc.i9mc.pluginchannel.BungeeChannel;
import cc.i9mc.pluginchannel.enums.ServerType;

public class PLogger {

	private static String pattern = "§8[§3§lPluginChannel§8][§r{1}§8] §f{2}";
	private static String patternBukkit = "§8[§3§lPluginChannel§8] §f{1}";

	public static void send(net.md_5.bungee.api.CommandSender sender, String message) {
		sender.sendMessage(new net.md_5.bungee.api.chat.TextComponent(patternBukkit
				.replace("{1}", "§7" + message.replace("&", "§"))));
	}

	public static void send(org.bukkit.command.CommandSender sender, String message) {
		sender.sendMessage(patternBukkit
				.replace("{1}", "§7" + message.replace("&", "§")));
	}

	public static void info(String message) {
		if (ServerType.getServerType() == ServerType.BUNGEECORD) {
			BungeeChannel.getInstance().getProxy().getConsole().sendMessage(new net.md_5.bungee.api.chat.TextComponent(pattern
					.replace("{1}", "§b信息")
					.replace("{2}", "§f" + message.replace("&", "§"))));
		} else {
			org.bukkit.Bukkit.getConsoleSender().sendMessage(pattern
					.replace("{1}", "§b信息")
					.replace("{2}", "§f" + message.replace("&", "§")));
		}
	}

	public static void warn(String message) {
		if (ServerType.getServerType() == ServerType.BUNGEECORD) {
			BungeeChannel.getInstance().getProxy().getConsole().sendMessage(new net.md_5.bungee.api.chat.TextComponent(pattern
					.replace("{1}", "§6警告")
					.replace("{2}", "§6" + message.replace("&", "§"))));
		} else {
			org.bukkit.Bukkit.getConsoleSender().sendMessage(pattern
					.replace("{1}", "§6警告")
					.replace("{2}", "§6" + message.replace("&", "§")));
		}
	}

	public static void error(String message) {
		if (ServerType.getServerType() == ServerType.BUNGEECORD) {
			BungeeChannel.getInstance().getProxy().getConsole().sendMessage(new net.md_5.bungee.api.chat.TextComponent(pattern
					.replace("{1}", "§4错误")
					.replace("{2}", "§4" + message.replace("&", "§"))));
		} else {
			org.bukkit.Bukkit.getConsoleSender().sendMessage(pattern
					.replace("{1}", "§4错误")
					.replace("{2}", "§4" + message.replace("&", "§")));
		}
	}
}
