package cz.SkyWars.Manager;

import cz.SkyWars.SkyWars;
import cz.SkyWars.MySQL;

import cn.nukkit.Player;

import java.util.*;
import java.sql.*;

public class StatsManager{

    public SkyWars skywars;
   
     public StatsManager(SkyWars skywars){
         this.skywars = skywars;
         }

     public boolean checkAcc(String username){
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

     public void createData(String username){
             skywars.mysql.updateData("INSERT INTO `skywars_stats` (`id`, `nickname`, `kills`, `deaths`, `wins`) VALUES (NULL, '" + username + "', '0', '0', '0')");
        }
}
