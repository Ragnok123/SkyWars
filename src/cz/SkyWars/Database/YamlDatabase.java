package cz.SkyWars.Database;

import cn.nukkit.utils.Config;

public class YamlDatabase implements Database{
	
	public cz.SkyWars.SkyWars skywars;
	
	public YamlDatabase(cz.SkyWars.SkyWars skywars) {
		this.skywars = skywars;
		cfg = skywars.cplayers;
	}
	
	private Config cfg;
	
	
	@Override
	public boolean checkAccStats(String username){
        return cfg.get(username) != null;
     }

	public int getKills(String username){
      return (int) cfg.get(username+".stats.kills");
    }

	public void addKills(String username, int kills){
		cfg.set(username+".stats.kills", kills);
    }

	public int getWins(String username){
      return (int) cfg.get(username+".stats.wins");
    }

	public void addWins(String username, int wins){
		cfg.set(username+".stats.wins", wins);
    }

	public int getDeaths(String username){
      return (int) cfg.get(username+".stats.deaths");
    }

	public void addDeaths(String username, int kills){
		cfg.set(username+".stats.deaths", kills);
    }

	public void createDataStats(String username){
		cfg.set(username+".stats.kills", 0);
		cfg.set(username+".stats.wins", 0);
		cfg.set(username+".stats.deaths", 0);
	}



}
