package cz.SkyWars;

import cn.nukkit.block.Block;
import cn.nukkit.plugin.Plugin;
import cz.SkyWars.Arena.Arena;
import cz.SkyWars.Kits.Kit;
import cn.nukkit.Player;
import cn.nukkit.Server;

public class SWPlayer {
	
	private Player player;
	private boolean isLobby = true;
	private int currentPosition;
	private boolean isAdmin = false;
	
	private Kit kit;
	public boolean isBuying = false;
	public String buyingKit = "";
	
	private Arena arena;
	
	public SWPlayer(Player player) {
		this.player = player;
		init();
	}
	
	public void init() {
		if(!SkyWars.getInstance().database.checkAccStats(this.player.getName().toLowerCase())) {
			SkyWars.getInstance().database.createDataStats(this.player.getName().toLowerCase());
		}
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
	
	public Kit getKit() {
		return kit;
	}
	
	public void setKit(Kit kit) {
		unsetKit();
		this.kit = kit;
	}
	
	public void unsetKit() {
		kit = null;
	}
	
	public void giveKit() {
		getKit().handle(this.player);
	}
	
	public int getMoney() {
		int money = 0;
		if((boolean) SkyWars.getInstance().settingsc.get("economyapi-enabled") == true) {
			Plugin eapi = Server.getInstance().getPluginManager().getPlugin("EconomyAPI");
			if (eapi == null) {
				Server.getInstance().getLogger().info("EconomyAPI not found. Please, if you want to run SkyWars with EconomyAPI, install it.");
			} else {
				money = (int) me.onebone.economyapi.EconomyAPI.getInstance().myMoney(this.player);
			}
		}
		return money;
	}
	
	public void addMoney(int money) {
		if((boolean) SkyWars.getInstance().settingsc.get("economyapi-enabled") == true) {
			Plugin eapi = Server.getInstance().getPluginManager().getPlugin("EconomyAPI");
			if (eapi == null) {
				Server.getInstance().getLogger().info("EconomyAPI not found. Please, if you want to run SkyWars with EconomyAPI, install it.");
			} else {
				me.onebone.economyapi.EconomyAPI.getInstance().addMoney(this.player, money);
			}
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
	
	public boolean hasKit(String kit) {
		return SkyWars.getInstance().database.hasKit(this.player.getName().toLowerCase(),kit);
	}
	
	public void buyKit(String kit) {
		SkyWars.getInstance().database.buyKit(this.player.getName().toLowerCase(),kit);
	}
	
}
