package cz.SkyWars.Database;

import cz.SkyWars.SkyWars;

public class MysqlDatabase implements Database{
	
	public SkyWars skywars;
	
	public MysqlDatabase(SkyWars s) {
		this.skywars = s;
	}

	@Override
	public boolean checkAccStats(String username){
        return !skywars.mysql.getPlayerStatsData(username).isEmpty();
     }

	public int getKills(String username){
      return (int) skywars.mysql.getPlayerStatsData(username).get("kills");
    }

	public void addKills(String username, int kills){
       skywars.mysql.updateData("UPDATE `skywars_stats` SET `kills` = `kills` + '" + kills + "' WHERE `nickname` = '" + username + "'");
    }

	public int getWins(String username){
      return (int) skywars.mysql.getPlayerStatsData(username).get("wins");
    }

	public void addWins(String username, int wins){
       skywars.mysql.updateData("UPDATE `skywars_stats` SET `wins` = `wins` + '" + wins + "' WHERE `nickname` = '" + username + "'");
    }

	public int getDeaths(String username){
      return (int) skywars.mysql.getPlayerStatsData(username).get("deaths");
    }

	public void addDeaths(String username, int kills){
       skywars.mysql.updateData("UPDATE `skywars_stats` SET `deaths` = `deaths` + '" + kills + "' WHERE `nickname` = '" + username + "'");
    }
	
	public void buyKit(String username, String kit) {
		skywars.mysql.updateData("UPDATE `skywars_kits` SET `"+kit+"` = 'true' WHERE `nickname` = '"+username+"'");
	}
	
	public boolean hasKit(String username, String kit) {
		return (boolean) skywars.mysql.getPlayerKitData(username).get(kit);
	}

	public void createDataStats(String username){
         skywars.mysql.updateData("INSERT INTO `skywars_stats` (`nickname`, `kills`, `deaths`, `wins`) VALUES ('" + username + "', '0', '0', '0')");
         skywars.mysql.updateData("INSERT INTO `skywars_kits` (`nickname`, `builder`,`soldier`) VALUES ('" + username + "', 'false', 'false')");
    }

}
