package cz.SkyWars.Manager.WorldManager.Functions;

import cz.SkyWars.SkyWars;
import cz.SkyWars.Manager.WorldManager.ArenaWorldManager;
import cn.nukkit.level.Level;

import org.apache.commons.io.FileUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import cn.nukkit.Player;
import cn.nukkit.Server;

public class RestartArenaWorld{

    public ArenaWorldManager worldmanager;
    public String worldname;
    public Level level;

    public RestartArenaWorld(ArenaWorldManager worldmanager, String worldname) throws IOException{
        this.worldmanager = worldmanager;
        this.worldname = worldname;
    }

    
 }