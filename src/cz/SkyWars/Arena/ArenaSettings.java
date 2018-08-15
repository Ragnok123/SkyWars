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
		 int slots = (int) SkyWars.getInstance().arenass.get(this.arena+".settings.slots");
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
		d2.put("y", y + 2);
		d2.put("z", z);
		d2.put("world", world);
		SkyWars.getInstance().arenass.set(arena+".pos" + String.valueOf(position), d2);
		SkyWars.getInstance().arenass.save();
	}
	
	public void setSettings(int time) {
		HashMap<String, Object> d1 = new HashMap<String, Object>();
		d1.put("slots", -1);
		d1.put("time", time);
		SkyWars.getInstance().arenass.set(arena+".settings", d1);
		SkyWars.getInstance().arenass.save();
	}
	
	public void setSlots(int slots) {
		SkyWars.getInstance().arenass.set(arena+".settings.slots", slots);
		SkyWars.getInstance().arenass.set(arena+".settings.time", 5);
		SkyWars.getInstance().arenass.save();
	}
	
	public int getTime() {
		 int time = (int) SkyWars.getInstance().arenass.get(this.arena+".settings.time");
		 return time;
	}
	
	public Position getPosition(int slot) {
		 double x = (double)SkyWars.getInstance().arenass.get(this.arena+".pos"+slot+".x");
		 double y = (double)SkyWars.getInstance().arenass.get(this.arena+".pos"+slot+".y");
		 double z = (double)SkyWars.getInstance().arenass.get(this.arena+".pos"+slot+".z");
		 String name = (String)SkyWars.getInstance().arenass.get(this.arena+".pos"+slot+".world");
		 Position pos = new Position(x,y,z, Server.getInstance().getLevelByName(name));
		 return pos;
	}
	
	public Level getLevel() {
		String name = (String)SkyWars.getInstance().arenass.get(this.arena+".pos1.world");
		return Server.getInstance().getLevelByName(name);
	}
	
	public Block getSign() {
		 double x = (double)SkyWars.getInstance().arenass.get(this.arena+".sign.x");
		 double y = (double)SkyWars.getInstance().arenass.get(this.arena+".sign.y");
		 double z = (double)SkyWars.getInstance().arenass.get(this.arena+".sign.z");
		 String name = (String)SkyWars.getInstance().arenass.get(this.arena+".sign.world");
		 Position pos = new Position(x,y,z, Server.getInstance().getLevelByName(name));
		 Block sign  = Server.getInstance().getLevelByName(name).getBlock(pos);
		 return sign;
	}
	

}
