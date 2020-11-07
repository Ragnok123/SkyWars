package cz.SkyWars;

import cn.nukkit.block.Block;
import cn.nukkit.plugin.Plugin;
import cz.SkyWars.Arena.Arena;
import cn.nukkit.Player;
import cn.nukkit.Server;

public class SWPlayer {
	
	private Player player;
	private boolean isLobby = true;
	
	public int wins = 0;
	public int kills = 0;
	public int deaths = 0;
	
	private Arena arena;
	
	public SWPlayer(Player player) {
		this.player = player;
		init();
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void init() {
		SkyWars.getInstance().database.loadData(this);
	}
	
	public void save() {
		SkyWars.getInstance().database.saveData(this);
	}
	
	public Arena getArena() {
		if(this.arena != null) {
			return this.arena;
			} else {
				return null;
			}
	}
	
	public void setArena(Arena a) {
		arena = a;
	}
	
	public boolean isInLobby() {
		return isLobby;
	}
	
	public void setInLobby(boolean value) {
		isLobby = value;
	}
	
	public int getKills() {
		return kills;
	}
	
	public int getWins() {
		return wins;
	}
	
	public int getDeaths() {
		return deaths;
	}
	
	public void addKill() {
		kills += 1;
	}
	
	public void addWin() {
		wins += 1;
	}
	
	public void addDeath() {
		deaths += 1;
	}
	
}
