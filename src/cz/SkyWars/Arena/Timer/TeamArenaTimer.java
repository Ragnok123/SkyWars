package cz.SkyWars.Arena.Timer;

import cz.SkyWars.Arena.TeamArena;
import cz.SkyWars.SkyWars;

import cn.nukkit.scheduler.Task;

public class TeamArenaTimer extends Task
{

    public TeamArena arena;
    public String arenaname;
    public String worldname;
    public double signX;
    public double signY;
    public double signZ;
    public int maxPlayerCount;

    public TeamArenaTimer(TeamArena arena, String arenaname, String worldname, double signX, double signY, double signZ, int maxPlayerCount)
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

   
