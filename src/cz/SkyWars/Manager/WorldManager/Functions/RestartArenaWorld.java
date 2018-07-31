package cz.SkyWars.Manager.WorldManager.Functions;

import cz.SkyWars.Manager.WorldManager.ArenaWorldManager;
import cz.SkyWars.Manager.WorldManager.Functions.CopyArenaWorld;
import cz.SkyWars.Manager.WorldManager.Functions.DeleteArenaWorld;

public class RestartArenaWorld{

    public ArenaWorldManager worldmanager;
    public String worldname;

    public RestartArenaWorld(ArenaWorldManager worldmanager, String worldname){
        this.worldmanager = worldmanager;
        this.worldname = worldname;
        kekWorld(worldname); 
    }

    public void kekWorld(String worldname){
        new DeleteArenaWorld(this, worldname);
        new CopyArenaWorld(this, worldname);
    }

}