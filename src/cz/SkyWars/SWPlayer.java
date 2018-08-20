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
		init();
	}
	
	public void init() {
		if(!SkyWars.getInstance().database.checkAccStats(this.player.getName().toLowerCase())) {
			SkyWars.getInstance().database.createDataStats(this.player.getName().toLowerCase());
		}
	}
	
	public boolean isInLobby() {
		return isLobby;
	}
	
	public void setInLobby(boolean value) {
		isLobby = value;
	}
	
	public int getKills() {
		return SkyWars.getInstance().database.getKills(this.player.getName().toLowerCase());
	}
	
	public int getWins() {
		return SkyWars.getInstance().database.getWins(this.player.getName().toLowerCase());
	}
	
	public int getDeaths() {
		return SkyWars.getInstance().database.getDeaths(this.player.getName().toLowerCase());
	}
	
	public void addKill() {
		SkyWars.getInstance().database.addKills(this.player.getName().toLowerCase(),1);
	}
	
	public void addWin() {
		SkyWars.getInstance().database.addWins(this.player.getName().toLowerCase(),1);
	}
	
	public void addDeath() {
		SkyWars.getInstance().database.addDeaths(this.player.getName().toLowerCase(),1);
	}
	
}
