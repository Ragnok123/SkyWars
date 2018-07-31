package cz.SkyWars;

import cz.SkyWars.SkyWars;
import cn.nukkit.Player;
import cn.nukkit.scheduler.Task;

public class JoinTask extends Task{
	
	public SkyWars skywars;
	public Player player;
	
	public JoinTask(SkyWars skywars, Player player)
	{
		this.skywars = skywars;
		this.player = player;
	}
	
	@Override
	public void onRun(int currentTick)
	{
		player.teleport(skywars.lobbyXYZ);
		skywars.lobbyplayers.put(player.getName(), player);
	}

}
