package cz.SkyWars.Manager.WorldManager.Functions;

import cz.SkyWars.Manager.WorldManager.Functions.RestartArenaWorld;

import org.apache.commons.io.FileUtils;

import java.util.*;
import java.io.*;

import cn.nukkit.Server;

public class CopyArenaWorld{

     public RestartArenaWorld restarter;
     public String worldname;

     public CopyArenaWorld(RestartArenaWorld restarter, String worldname){
           this.restarter = restarter;
           this.worldname = worldname;
           executeData(worldname);
     }

    public void executeData(String worldname){
    	try {
        File worldInArenaFile = new File(Server.getInstance().getDataPath() + "/arenas/" + worldname);
        File worldToWorldsFile= new File(Server.getInstance().getDataPath() + "/worlds/" + worldname);
        FileUtils.copyDirectory(worldInArenaFile, worldToWorldsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
