package cz.SkyWars.Arena;

import cz.SkyWars.SkyWars;
import cn.nukkit.Server;
import cn.nukkit.level.*;
import cn.nukkit.block.Block;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;

import java.util.*;

public class ArenaSettings {
	
	private String arena;
	public Block fakeBlock = null;
	public String fakeLevel1;
	private boolean s;
	
	public ArenaSettings(String arena, boolean s) {
		this.arena = arena;
		this.s = s;
	}
	
	
	public String getName() {
		return this.arena;
	}
	
	public int getSlots() {
		 int slots = SkyWars.getInstance().arenass.getInt(this.arena+".settings.slots");
		 return slots;
	}
	
	public void setSign(double x, double y, double z, String world) {
		HashMap<String, Object> d2 = new HashMap<String, Object>();
		d2.put("x", x);
		d2.put("y", y);
		d2.put("z", z);
		d2.put("world", world);
		SkyWars.getInstance().arenass.set(arena+".sign", d2);
		SkyWars.getInstance().arenass.save();
	}
	
	public void setPositions(int position, double x, double y, double z, String world) {
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
		 int time = SkyWars.getInstance().arenass.getInt(this.arena+".settings.time");
		 return time;
	}
	
	public Position getPosition(int slot) {
		 double x = SkyWars.getInstance().arenass.getDouble(this.arena+".pos"+slot+".x");
		 double y = SkyWars.getInstance().arenass.getDouble(this.arena+".pos"+slot+".y");
		 double z = SkyWars.getInstance().arenass.getDouble(this.arena+".pos"+slot+".z");
		 String name = SkyWars.getInstance().arenass.getString(this.arena+".pos"+slot+".world");
		 Position pos = new Position(x,y,z, Server.getInstance().getLevelByName(name));
		 return pos;
	}
	
	public Level getLevel() {
		String name = SkyWars.getInstance().arenass.getString(this.arena+".pos1.world");
		return Server.getInstance().getLevelByName(name);
	}
	
	public Block getSign() {
		 Block b;
		 double x = SkyWars.getInstance().arenass.getDouble(this.arena+".sign.x");
		 double y = SkyWars.getInstance().arenass.getDouble(this.arena+".sign.y");
		 double z = SkyWars.getInstance().arenass.getDouble(this.arena+".sign.z");
		 String name = (String)SkyWars.getInstance().arenass.getString(this.arena+".sign.world");
		 Position pos = new Position(x,y,z, Server.getInstance().getLevelByName(name));
		 Block sign;
		 if(s) {
			 sign = Server.getInstance().getLevelByName(fakeLevel1).getBlock(pos);
		 } else {
			 sign = Server.getInstance().getLevelByName(name).getBlock(pos);
		 }
		 if(fakeBlock != null) {
			 b = fakeBlock;
		 } else {
			 b = sign;
		 }
		 return b;
		 
	}
	

}
