package cz.SkyWars;

import cz.SkyWars.SkyWars;
import cn.nukkit.scheduler.Task;

public class RestartTask extends Task{
	
	public SkyWars skywars;
	
	public RestartTask(SkyWars skywars)
	{
		this.skywars = skywars;
	}
	
	@Override
	public void onRun(int currentTick)
	{
		skywars.getServer().shutdown();
	}

}
