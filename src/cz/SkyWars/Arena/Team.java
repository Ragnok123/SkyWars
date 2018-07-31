package cz.SkyWars.Arena;

import cn.nukkit.Server;
import cz.SkyWars.Arena.TeamPlayers;
import cz.SkyWars.Arena.TeamArena;

import cn.nukkit.level.Position;
import cn.nukkit.Player;

import java.util.*;

public class Team
{

	public TeamArena teamarena;
	public Position teampos;
	public int colorname;
	public String teamname;

	public HashMap<String, Player> teammates = new HashMap<String, Player>();

	public Team(TeamArena teamarena, Position teampos, int colorname, String teamname)
	{
		this.teamarena = teamarena;
		this.teampos = teampos;
		
	}

	public void addPlayer(Player player)
	{
		teammates.put(player.getName(), player);
	}

	public void removePlayer(Player player)
	{
		teammates.remove(player.getName());
	}

	public boolean isInTeam(Player player)
	{
		return teammates.containsKey(player.getName());
	}

	public void removePlayers()
	{
		teammates.clear();
	}

	public int getPlayers()
	{
		return teammates.size();
	}

	public Position getTeamPosition()
	{
		return this.teampos;
	}

}
