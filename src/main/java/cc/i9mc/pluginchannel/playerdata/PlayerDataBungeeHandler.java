package cc.i9mc.pluginchannel.playerdata;

import java.util.Arrays;

import cc.i9mc.pluginchannel.events.BungeeCommandEvent;
import cc.i9mc.pluginchannel.logger.PLogger;
import cc.i9mc.pluginchannel.util.ArrayUtils;
import cc.i9mc.pluginchannel.util.TagUtils;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;


public class PlayerDataBungeeHandler implements Listener {
	
	public PlayerDataBungeeHandler(Plugin plugin) {
		plugin.getProxy().getPluginManager().registerListener(plugin, this);
	}
	
	@EventHandler
	public void onCommand(BungeeCommandEvent e) {
		if (e.isCancelled() || !e.getArgs()[0].equals("PlayerData")) {
			return;
		}
		try {
			if (e.getArgs()[1].equalsIgnoreCase("Set")) {
				TagUtils.getInst().set(e.getArgs()[3], ArrayUtils.arrayJoin(e.getArgs(), 4), e.getArgs()[2]);
			}
			else if (e.getArgs()[1].equalsIgnoreCase("Remove")) {
				TagUtils.getInst().remove(e.getArgs()[3], e.getArgs()[2]);
			}
			else if (e.getArgs()[1].equalsIgnoreCase("Get")) {
				String result = TagUtils.getInst().getString(e.getArgs()[3], e.getArgs()[2]);
				e.response(result == null ? "null" : result);
			}
		} catch (Exception err) {
			PLogger.error("Invalid BungeeCommand: &c" + Arrays.asList(e.getArgs()));
			PLogger.error("Exception: &c" + err.getMessage());
		}
	}
}
