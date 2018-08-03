package cz.SkyWars.Actions;

import cn.nukkit.Player;
import cn.nukkit.Server;
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
		if((boolean) SkyWars.getInstance().settingsc.get("economyapi-enabled") == true) {
			me.onebone.economyapi.EconomyAPI.getInstance().addMoney(this.p, (double) this.coins);
		}
		if((boolean) SkyWars.getInstance().settingsc.get("customeconomy-enabled") == true) {
			PlayerEconomyRewardEvent event = new PlayerEconomyRewardEvent(this.p, this.coins);
			Server.getInstance().getPluginManager().callEvent(event);
		}
	}

}
