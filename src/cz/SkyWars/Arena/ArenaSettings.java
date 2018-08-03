package cz.SkyWars.Arena;

import cz.SkyWars.SkyWars;
import cn.nukkit.Server;
import cn.nukkit.level.*;
import cn.nukkit.block.Block;

import java.util.*;

public class ArenaSettings {
	
	private String arena;
	
	public ArenaSettings(String arena) {
		this.arena = arena;
	}
	
	/*
	 * arenaname:
	 * 		settings:
	 * 			slots: int
	 * 			time: int
	 * 			//kits: boolean
	 * 		sign:
	 * 			x: double
	 * 			y: double
	 * 			z: double
	 * 			world: String
	 * 		pos+int:
	 * 			x: double
	 * 			y: double
	 * 			z: double
	 * 			world: String
	 * 
	 * Config = String, new HashMap<String, new HashMap<String, Object>>
	 */
	
	public String getName() {
		return this.arena;
	}
	
	public int getSlots() {
		 HashMap<String,  HashMap<String, Object>> var1 = (HashMap<String, HashMap<String, Object>>) SkyWars.getInstance().arenass.get(this.arena);
		 HashMap<String, Object> var2 = var1.get("settings");
		 int slots = (int) var2.get("slots");
		 return slots;
	}
	
	public void setSign(double x, double y, double z, String world) {
		HashMap<String, HashMap<String, Object>> d1 = new HashMap<String, HashMap<String, Object>>();
		HashMap<String, Object> d2 = new HashMap<String, Object>();
		d2.put("x", x);
		d2.put("y", y);
		d2.put("z", z);
		d2.put("world", world);
		SkyWars.getInstance().arenass.set(arena+".sign", d2);
		SkyWars.getInstance().arenass.save();
	}
	
	public void setPositions(int position, double x, double y, double z, String world) {
		HashMap<String, HashMap<String, Object>> d1 = new HashMap<String, HashMap<String, Object>>();
    	HashMap<String, Object> d2 = new HashMap<String, Object>();
    	d2.put("x", x);
		d2.put("y", y);
		d2.put("z", z);
		d2.put("world", world);
		SkyWars.getInstance().arenass.set(arena+".pos" + String.valueOf(position), d2);
		SkyWars.getInstance().arenass.save();
	}
	
	public void setSettings(int slots, int time) {
		HashMap<String, Object> d1 = new HashMap<String, Object>();
		d1.put("slots", slots);
		d1.put("time", time);
		SkyWars.getInstance().arenass.set(arena+".settings", d1);
		SkyWars.getInstance().arenass.save();
	}
	
	
	
	public int getTime() {
		 HashMap<String,  HashMap<String, Object>> var1 = (HashMap<String, HashMap<String, Object>>) SkyWars.getInstance().arenass.get(this.arena);
		 HashMap<String, Object> var2 = var1.get("settings");
		 int time = (int) var2.get("time");
		 return time;
	}
	
	public Position getPosition(int slot) {
		 Position pos;
		 HashMap<String,  HashMap<String, Object>> var1 = (HashMap<String, HashMap<String, Object>>) SkyWars.getInstance().arenass.get(this.arena);
		 HashMap<String, Object> var2 = var1.get("pos"+slot);
		 double x = (double)var2.get("x");
		 double y = (double)var2.get("y");
		 double z = (double)var2.get("z");
		 String name = (String)var2.get("world");
		 if(var2 != null) {
			 pos = new Position(x,y,z, Server.getInstance().getLevelByName(name));
		 } else {
			 return null;
		 }
		 return pos;
	}
	
	public Level getLevel() {
		HashMap<String,  HashMap<String, Object>> var1 = (HashMap<String, HashMap<String, Object>>) SkyWars.getInstance().arenass.get(this.arena);
		HashMap<String, Object> var2 = var1.get("pos1");
		String name = (String)var2.get("world");
		return Server.getInstance().getLevelByName(name);
	}
	
	public Block getSign() {
		 Block sign;
		 HashMap<String,  HashMap<String, Object>> var1 = (HashMap<String, HashMap<String, Object>>) SkyWars.getInstance().arenass.get(this.arena);
		 HashMap<String, Object> var2 = var1.get("sign");
		 double x = (double)var2.get("x");
		 double y = (double)var2.get("y");
		 double z = (double)var2.get("z");
		 String name = (String)var2.get("world");
		 Position pos = new Position(x,y,z, Server.getInstance().getLevelByName(name));
		 if(var2 != null) {
			 sign  = Server.getInstance().getLevelByName(name).getBlock(pos);
		 } else {
			 return null;
		 }
		 return sign;
	}
	

}
