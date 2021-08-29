package cc.i9mc.pluginchannel.enums;


public enum ServerType {
	
	BUNGEECORD, BUKKIT;
	
	private static ServerType serverType;

	public static ServerType getServerType() {
		return ServerType.serverType;
	}

	public static void setServerType(ServerType serverType) {
		ServerType.serverType = serverType;
	}
}
