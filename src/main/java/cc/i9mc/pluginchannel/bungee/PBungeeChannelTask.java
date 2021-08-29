package cc.i9mc.pluginchannel.bungee;

import cc.i9mc.pluginchannel.logger.PLogger;
import cc.i9mc.pluginchannel.runable.PChannelResult;
import cc.i9mc.pluginchannel.timeable.Timeable;
import cc.i9mc.pluginchannel.util.ArrayUtils;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;


public class PBungeeChannelTask extends Timeable {
	
	private PChannelResult runnable;
	private Runnable runnableTimeless;
	private PBungeeChannel channel;
	private ProxiedPlayer target;
	private String[] commands;
	private UUID uid = UUID.randomUUID();
	
	private PBungeeChannelTask(long effective) {
		super(effective);
	}
	
	public static PBungeeChannelTask createTask() {
		return new PBungeeChannelTask(5 * 1000L);
	}
	
	public static PBungeeChannelTask createTask(long effective) {
		return new PBungeeChannelTask(effective);
	}
	
	public PBungeeChannelTask channel(PBungeeChannel channel) {
		this.channel = channel;
		return this;
	}
	
	public PBungeeChannelTask target(ProxiedPlayer target) {
		this.target = target;
		return this;
	}
	
	public PBungeeChannelTask result(PChannelResult runnable) {
		this.runnable = runnable;
		return this;
	}
	
	public PBungeeChannelTask timeless(Runnable runnable) {
		this.runnableTimeless = runnable;
		return this;
	}
	
	public PBungeeChannelTask command(String... commands) {
		this.commands = ArrayUtils.addFirst(commands, uid.toString());
		return this;
	}
	
	public void run() {
		if (target == null || channel == null || commands == null) {
			PLogger.error("Invalid PluginCommand: &cMissing parameters");
			PLogger.error("Sender: &c" + (target != null));
			PLogger.error("Channel: &c" + (channel != null));
			PLogger.error("Commands: &c" + (commands != null));
			return;
		}
		channel.sendBungeeMessage(target.getServer(), commands);
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

	public PBungeeChannel getChannel() {
		return this.channel;
	}

	public ProxiedPlayer getTarget() {
		return this.target;
	}

	public String[] getCommands() {
		return this.commands;
	}

	public UUID getUid() {
		return this.uid;
	}
}
