package cz.SkyWars;

import cz.SkyWars.Arena.Arena;

public class StartupTask extends cn.nukkit.scheduler.Task{

	
	public SkyWars pl;
	
	public StartupTask(SkyWars name) {
		this.pl = name;
	}
	
	@Override
	public void onRun(int tick) {
		this.pl.initializeArrays();
	}
	
	

}
