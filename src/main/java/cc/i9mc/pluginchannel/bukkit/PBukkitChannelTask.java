package cc.i9mc.pluginchannel.bukkit;

import cc.i9mc.pluginchannel.logger.PLogger;
import cc.i9mc.pluginchannel.runable.PChannelResult;
import cc.i9mc.pluginchannel.timeable.Timeable;
import cc.i9mc.pluginchannel.util.ArrayUtils;
import org.bukkit.entity.Player;

import java.util.UUID;


public class PBukkitChannelTask extends Timeable {
	
	private PChannelResult runnable;
	private Runnable runnableTimeless;
	private PBukkitChannel channel;
	private Player sender;
	private String[] commands;
	private UUID uid = UUID.randomUUID();
	
	private PBukkitChannelTask(long effective) {
		super(effective);
	}
	
	public static PBukkitChannelTask createTask() {
		return new PBukkitChannelTask(5 * 1000L);
	}
	
	public static PBukkitChannelTask createTask(long effective) {
		return new PBukkitChannelTask(effective);
	}
	
	public PBukkitChannelTask channel(PBukkitChannel channel) {
		this.channel = channel;
		return this;
	}
	
	public PBukkitChannelTask sender(Player sender) {
		this.sender = sender;
		return this;
	}
	
	public PBukkitChannelTask result(PChannelResult runnable) {
		this.runnable = runnable;
		return this;
	}
	
	public PBukkitChannelTask timeless(Runnable runnable) {
		this.runnableTimeless = runnable;
		return this;
	}
	
	public PBukkitChannelTask command(String... commands) {
		this.commands = ArrayUtils.addFirst(commands, uid.toString());
		return this;
	}
	
	public void run() {
		if (sender == null || channel == null || commands == null) {
			PLogger.error("Invalid PluginCommand: &cMissing parameters");
			PLogger.error("Sender: &c" + (sender != null));
			PLogger.error("Channel: &c" + (channel != null));
			PLogger.error("Commands: &c" + (commands != null));
			return;
		}
		channel.sendBukkitMessage(sender, commands);
		if (runnable != null) {
			channel.getTasks().add(this);
		}
	}

	public PChannelResult getRunnable() {
		return this.runnable;
	}

	public Runnable getRunnableTimeless() {
		return this.runnableTimeless;
	}

	public PBukkitChannel getChannel() {
		return this.channel;
	}

	public Player getSender() {
		return this.sender;
	}

	public String[] getCommands() {
		return this.commands;
	}

	public UUID getUid() {
		return this.uid;
	}
}
