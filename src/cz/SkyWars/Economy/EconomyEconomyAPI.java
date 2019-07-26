package cz.SkyWars.Economy;

import cn.nukkit.Player;
import cz.SkyWars.SkyWars;

public class EconomyEconomyAPI implements Economy {
	
	private SkyWars sw = null;
	
	public EconomyEconomyAPI(SkyWars sw) { this.sw = sw; }

	@Override
	public void addMoney(Player p, int money) {
		me.onebone.economyapi.EconomyAPI.getInstance().addMoney(p,(double) money);

	}

	@Override
	public int getMoney(Player p) {
		return (int) me.onebone.economyapi.EconomyAPI.getInstance().myMoney(p);
	}

}
