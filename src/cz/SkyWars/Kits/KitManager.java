package cz.SkyWars.Kits;

import java.util.HashMap;

import cz.SkyWars.SkyWars;

public class KitManager {
	
	private SkyWars plugin;
	
	public HashMap<String, Kit> kits = new HashMap<String, Kit>();
	
	public KitManager(SkyWars p) {
		plugin = p;
		registerKits();
	}
	
	public void registerKits(){
		registerKit("builder", new KitBuilder());
		registerKit("soldier", new KitSoldier());
	}
	
	public void registerKit(String kit, Kit k) {
		kits.put(kit,k);
	}

}
