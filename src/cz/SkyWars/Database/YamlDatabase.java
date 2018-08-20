package cz.SkyWars.Database;

import cn.nukkit.utils.Config;

public class YamlDatabase implements Database{
	
	public cz.SkyWars.SkyWars skywars;
	
	public YamlDatabase(cz.SkyWars.SkyWars skywars) {
		this.skywars = skywars;
	}
	
	
	@Override
	public boolean checkAccStats(String username){
        return skywars.cplayers.get(username) != null;
     }

	public int getKills(String username){
      return skywars.cplayers.getInt(username+".stats.kills");
    }

	public void addKills(String username, int kills){
		skywars.cplayers.set(username+".stats.kills", getKills(username)+kills);
		skywars.cplayers.save();
    }

	public int getWins(String username){
      return skywars.cplayers.getInt(username+".stats.wins");
    }

	public void addWins(String username, int wins){
		skywars.cplayers.set(username+".stats.wins", getWins(username)+wins);
		skywars.cplayers.save();
    }

	public int getDeaths(String username){
      return skywars.cplayers.getInt(username+".stats.deaths");
    }

	public void addDeaths(String username, int deaths){
		skywars.cplayers.set(username+".stats.deaths", getDeaths(username) +deaths);
		skywars.cplayers.save();
    }

	public void createDataStats(String username){
		skywars.cplayers.set(username+".stats.kills", 0);
		skywars.cplayers.set(username+".stats.wins", 0);
		skywars.cplayers.set(username+".stats.deaths", 0);
		skywars.cplayers.save();
	}



}
