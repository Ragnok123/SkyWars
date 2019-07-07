package cz.SkyWars.Manager.WorldManager;

import cz.SkyWars.SkyWars;
import cz.SkyWars.Arena.Arena;
import cz.SkyWars.Manager.WorldManager.Functions.*;
import cn.nukkit.Player;
import cn.nukkit.Server;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Map;

public class ArenaWorldManager{


    public ArenaWorldManager(){
   }
	
    public void restartArena(String worldname) throws IOException {
        if(!Server.getInstance().isLevelLoaded(worldname)) {
        	deleteWorld(worldname, "/worlds/");
        	copyWorld(worldname, "/arenas/", "/worlds/");
        	Server.getInstance().loadLevel(worldname);
        } else {
            try {
                Map<Long, Player> players = Server.getInstance().getLevelByName(worldname).getPlayers();
                if (players.size() > 0) {
                    players.forEach((k, v) -> {
                        v.teleport(Server.getInstance().getDefaultLevel().getSpawnLocation());
                    });
                }
            } catch (Exception e) {
            }
            Server.getInstance().unloadLevel(Server.getInstance().getLevelByName(worldname));
            deleteWorld(worldname, "/worlds/");
        	copyWorld(worldname, "/arenas/", "/worlds/");
        	Server.getInstance().loadLevel(worldname);
        }

    }
    
    public void copyWorld(String worldname, String from, String to) {
    	try {
            File worldInArenaFile = new File(Server.getInstance().getDataPath() + from + worldname);
            File worldToWorldsFile= new File(Server.getInstance().getDataPath() + to + worldname);
            FileUtils.copyDirectory(worldInArenaFile, worldToWorldsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    
    public void deleteWorld(String worldname, String source) {
    	try {
            File dir= new File(Server.getInstance().getDataPath() + source + worldname);
            FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
