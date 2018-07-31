package cz.SkyWars.Manager.WorldManager.Functions;

import cz.SkyWars.Manager.WorldManager.Functions.RestartArenaWorld;

import org.apache.commons.io.FileUtils;

import java.util.*;
import java.io.*;

import cn.nukkit.Server;

public class DeleteArenaWorld{

     public RestartArenaWorld restarter;
     public String worldname;

     public DeleteArenaWorld(RestartArenaWorld restarter, String worldname){
           this.restarter = restarter;
           this.worldname = worldname;
           executeData(worldname);
     }

    public void executeData(String worldname) {
        try {
            File dir= new File(Server.getInstance().getDataPath() + "/worlds/" + worldname);
            FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}