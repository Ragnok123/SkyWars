package cz.SkyWars.Arena;

import cz.SkyWars.Arena.TeamArena;
import cz.SkyWars.SkyWars;
import cz.SkyWars.Manager.*;

import cn.nukkit.level.Position;
import cn.nukkit.item.Item;

import cn.nukkit.Player;
import cn.nukkit.Server;

import java.util.*;

public class TeamPlayers
{

    public TeamArena arena;
    public String arenaname;
    public String worldname;
    public int maxPlayerCount;

	public double spawnPosX;
	public double spawnPosY;
	public double spawnPosZ;

    public TeamPlayers(TeamArena arena, String arenaname, String worldname)
	{
		this.arena = arena;
		this.arenaname = arenaname;
		this.worldname = worldname;
	}

    public void setTeam(Player player, Team team)
	{
		team.addPlayer(player);
	}

    public void unsetTeam(Player player, Team team)
	{
		team.removePlayer(player);
	}
    
    public void removePlayer(Player player, String cause)
	{
		if (cause == "kill")
		{
			arena.arenaplayers.remove(player.getName());
			player.getInventory().clearAll();
			player.setHealth(20);
			player.teleport(arena.skywars.lobbyXYZ);
		}
		if (cause == "quit")
		{
			arena.arenaplayers.remove(player.getName());
		}
	}
    
    public void removePlayers()
    {
    	arena.arenaplayers.clear();
    }

}
