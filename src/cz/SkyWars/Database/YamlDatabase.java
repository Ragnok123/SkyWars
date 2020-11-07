package cz.SkyWars.Database;

import cn.nukkit.utils.Config;
import cz.SkyWars.SWPlayer;

public class YamlDatabase implements Database{
	
	public cz.SkyWars.SkyWars skywars;
	
	public YamlDatabase(cz.SkyWars.SkyWars skywars) {
		this.skywars = skywars;
	}
	
	@Override
	public void loadData(SWPlayer data) {
		String username = data. getPlayer().getName().toLowerCase();
		if(skywars.cplayers.get(username) != null) {
			data.wins = skywars.cplayers.getInt(username+".stats.wins");
			data.kills = skywars.cplayers.getInt(username+".stats.kills");
			data.deaths = skywars.cplayers.getInt(username+".stats.deaths");
		} else {
			skywars.cplayers.set(username+".stats.kills", 0);
			skywars.cplayers.set(username+".stats.kills", 0);
			skywars.cplayers.set(username+".stats.kills", 0);
			skywars.cplayers.save();
		}
	}


	@Override
	public void saveData(SWPlayer data) {
		String username = data.getPlayer().getName().toLowerCase();
		skywars.cplayers.set(username+".stats.wins", data.wins);
		skywars.cplayers.set(username+".stats.kills", data.kills);
		skywars.cplayers.set(username+".stats.deaths", data.deaths);
		skywars.cplayers.save();
	}



}
