package cc.i9mc.pluginchannel.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import cc.i9mc.pluginchannel.BukkitChannel;


public class ListenerPlayer implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Bukkit.getScheduler().runTaskLaterAsynchronously(BukkitChannel.getInst(), () -> BukkitChannel.getInst().getPlayerDataHandler().loadPlayerDataLocal(e.getPlayer()), 5);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		BukkitChannel.getInst().getPlayerDataHandler().unloadPlayerDataLocal(e.getPlayer());
	}

}
