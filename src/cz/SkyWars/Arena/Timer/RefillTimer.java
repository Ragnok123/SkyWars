package cz.SkyWars.Arena.Timer;

import cn.nukkit.scheduler.*;
import cz.SkyWars.Arena.Arena;

public class RefillTimer extends AsyncTask{

	public Arena a;
	
	public RefillTimer(Arena a) {
		this.a = a;
	}
	
	@Override
	public void onRun() {
		a.clearChest();
		a.resetChest();
	}

}
