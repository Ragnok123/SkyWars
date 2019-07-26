package cz.SkyWars.Economy;

import cn.nukkit.Player;
import cz.SkyWars.SkyWars;

public abstract class CustomEconomy implements Economy{
	
	private SkyWars sw = null;
	public CustomEconomy(SkyWars sw) { this.sw = sw; }

	public abstract void addMoney(Player p, int money);

	public abstract int getMoney(Player p);

}
