package cz.SkyWars;

import cn.nukkit.Player;

public class SWPlayer {
	
	private Player player;
	private boolean isLobby = true;
	
	public SWPlayer(Player player) {
		this.player = player;
	}
	
	public boolean isInLobby() {
		return isLobby;
	}
	
	public void setLobby(boolean value) {
		isLobby = value;
	}

}
