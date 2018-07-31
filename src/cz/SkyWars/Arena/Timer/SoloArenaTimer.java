package cz.SkyWars.Arena.Timer;

import cz.SkyWars.Arena.SoloArena;
import cn.nukkit.blockentity.BlockEntitySign;
import cn.nukkit.math.Vector3;
import cn.nukkit.scheduler.Task;

import java.lang.*;

public class SoloArenaTimer extends Task
{

    public SoloArena arena;
    public String arenaname;
    public String worldname;
    public double signX;
    public double signY;
    public double signZ;
    public int maxPlayerCount;

    public SoloArenaTimer(SoloArena arena, String arenaname, String worldname, double signX, double signY, double signZ, int maxPlayerCount)
	{
		this.arena = arena;
		this.arenaname = arenaname;
		this.worldname = worldname;
		this.signX = signX;
		this.signY = signY;
		this.signZ = signZ;
		this.maxPlayerCount = maxPlayerCount;
	}

    @Override
    public void onRun(int currentTick)
	{
		arena.gameTimer(arenaname, worldname, signX, signY, signZ, maxPlayerCount);
    }
    

}

   
