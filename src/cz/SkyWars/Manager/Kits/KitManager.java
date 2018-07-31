package cz.SkyWars.Manager.Kits;

import cz.SkyWars.SkyWars;
import cz.SkyWars.MySQL;

import cn.nukkit.Player;

import java.util.*;
import java.sql.*;

public class KitManager{

    public SkyWars skywars;
   
     public KitManager(SkyWars skywars){
         this.skywars = skywars;
         }

     public boolean checkAcc(String username){
             return !skywars.mysql.getPlayerKitData(username).isEmpty();
         }

     public String getKit(String username){
          return (String) skywars.mysql.getPlayerKitData(username).get("kit");
        }

     public void setKit(String username, String kit){
           skywars.mysql.updateData("UPDATE `skywars_kits` SET `kit` = '" + kit + "' WHERE `nickname` = '" + username + "'");
        }

     public String getBerseeker(String username){
          return (String) skywars.mysql.getPlayerKitData(username).get("berseeker");
        }

     public void setBerseeker(String username, String berseeker){
           skywars.mysql.updateData("UPDATE `skywars_kits` SET `berseeker` = '" + berseeker + "' WHERE `nickname` = '" + username + "'");
        }

     public String getArcher(String username){
          return (String) skywars.mysql.getPlayerKitData(username).get("archer");
        }

     public void setArcher(String username, String kit){
           skywars.mysql.updateData("UPDATE `skywars_kits` SET `archer` = '" + kit + "' WHERE `nickname` = '" + username + "'");
        }

     public String getKnight(String username){
          return (String) skywars.mysql.getPlayerKitData(username).get("knight");
        }

     public void setKnight(String username, String kit){
           skywars.mysql.updateData("UPDATE `skywars_kits` SET `knight` = '" + kit + "' WHERE `nickname` = '" + username + "'");
        }

     public String getAssassin(String username){
          return (String) skywars.mysql.getPlayerKitData(username).get("assassin");
        }

     public void setAssassin(String username, String kit){
           skywars.mysql.updateData("UPDATE `skywars_kits` SET `assassin` = '" + kit + "' WHERE `nickname` = '" + username + "'");
        }

     public String getWizard(String username){
          return (String) skywars.mysql.getPlayerKitData(username).get("wizard");
        }

     public void setWizard(String username, String kit){
           skywars.mysql.updateData("UPDATE `skywars_kits` SET `wizard` = '" + kit + "' WHERE `nickname` = '" + username + "'");
        }

     public String getElytra(String username){
          return (String) skywars.mysql.getPlayerKitData(username).get("elytra");
        }

     public void setElytra(String username, String kit){
           skywars.mysql.updateData("UPDATE `skywars_kits` SET `elytra` = '" + kit + "' WHERE `nickname` = '" + username + "'");
        }

     public String getSoulSeller(String username){
          return (String) skywars.mysql.getPlayerKitData(username).get("soul_seller");
        }

     public void setSoulSeller(String username, String kit){
           skywars.mysql.updateData("UPDATE `skywars_kits` SET `soul_seller` = '" + kit + "' WHERE `nickname` = '" + username + "'");
        }

     public String getCursed(String username){
          return (String) skywars.mysql.getPlayerKitData(username).get("cursed");
        }

     public void setCursed(String username, String kit){
           skywars.mysql.updateData("UPDATE `skywars_kits` SET `cursed` = '" + kit + "' WHERE `nickname` = '" + username + "'");
        }

     public void createData(String username){
             skywars.mysql.updateData("INSERT INTO `skywars_kits` (`id`, `nickname`, `berseeker`, `archer`, `knight`, `assassin`, `wizard`, `elytra`, `soul_seller`, `cursed`, `kit`) VALUES (NULL, '" + username + "', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'any_kit')");
        }
}