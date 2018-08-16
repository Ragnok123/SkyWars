package cz.SkyWars;

import cn.nukkit.block.Block;

import cn.nukkit.Player;

public class SWPlayer {
	
	private Player player;
	private boolean isLobby = true;
	private int currentPosition;
	private boolean isAdmin = false;
	
	public SWPlayer(Player player) {
		this.player = player;
	}
	
	public boolean isInLobby() {
		return isLobby;
	}
	
	public void setInLobby(boolean value) {
		isLobby = value;
	}
	
	public int getSpawnPosition() {
		return currentPosition;
	}

	public void setSpawnPosition(int i) {
		currentPosition = i;
	}
	
}
