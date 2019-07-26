package cz.SkyWars.Actions;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;
import cz.SkyWars.SkyWars;
import cz.SkyWars.API.PlayerEconomyRewardEvent;

public class MoneyRewardAction {
	
	public Player p;
	public int coins;
	
	public MoneyRewardAction(Player p) {
		this.p = p;
		this.coins = SkyWars.getInstance().getReward();
		handle();
	}
	
	public void handle() {
		/*if((boolean) SkyWars.getInstance().settingsc.get("economyapi-enabled") == true) {
			Plugin eapi = Server.getInstance().getPluginManager().getPlugin("EconomyAPI");
			if (eapi == null) {
				Server.getInstance().getLogger().info("EconomyAPI not found. Please, if you want to run SkyWars with EconomyAPI, install it.");
			} else {
				me.onebone.economyapi.EconomyAPI.getInstance().addMoney(this.p, (double) this.coins);
			}
		}
		if((boolean) SkyWars.getInstance().settingsc.get("customeconomy-enabled") == true) {
			PlayerEconomyRewardEvent event = new PlayerEconomyRewardEvent(this.p, this.coins);
			Server.getInstance().getPluginManager().callEvent(event);
		}*/
		SkyWars.getInstance().economy.addMoney(this.p,this.coins);
		PlayerEconomyRewardEvent event = new PlayerEconomyRewardEvent(this.p, this.coins);
		Server.getInstance().getPluginManager().callEvent(event);
	}

}
