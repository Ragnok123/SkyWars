package cz.SkyWars.Economy;

import cz.SkyWars.SkyWars;
import cn.nukkit.Player;

public interface Economy {
	
	void addMoney(Player p, int money);
	int getMoney(Player p);

}
