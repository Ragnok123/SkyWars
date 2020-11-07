package cz.SkyWars.Arena;

import cz.SkyWars.SkyWars;
import cz.SkyWars.SWPlayer;
import cz.SkyWars.Arena.Timer.*;
import cz.SkyWars.Manager.*;
import cz.SkyWars.Manager.WorldManager.ArenaWorldManager;
import cz.SkyWars.Actions.RandomAction;

import cn.nukkit.block.*;
import cn.nukkit.blockentity.*;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.entity.*;
import cn.nukkit.event.player.*;
import cn.nukkit.inventory.*;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.particle.LavaDripParticle;
import cn.nukkit.level.particle.SmokeParticle;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.Listener;
import cn.nukkit.math.Vector3;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.entity.*;

import java.io.IOException;
import java.util.*;

public class Arena implements Listener
{

	public SkyWars skywars;
	public ArenaWorldManager worldmanager;
	public ArenaSettings settings;
	public List<Position> positions = new ArrayList<Position>();
	public int spawnIndex;
	
	public List<Team> teams = new ArrayList<Team>();
	
	public String arenaname;
	public String worldname;

	public double signX;
	public double signY;
	public double signZ;

	public int maxPlayerCount;

	public int waitTime;
	public int godTime;
	public int gameTime;
	public int endTime;
	public int lastTime;
	public int repeatTime = 20;
	public int gameStatus;
	public int finalWeight = 5;

	public Random random;


	public HashMap<String, Player> arenaplayers = new HashMap<String, Player>();

	public HashMap<String, Double> arenadata;


	public Arena(SkyWars skywars, String arenaname, ArenaSettings settings)
	{
		random = new Random();
        this.skywars = skywars;
        this.arenaname = arenaname;
        this.settings = settings;
		skywars.getLogger().info("arenaId: " + arenaname + "\ntime: " + settings.getTime() + "\nmaxPlayers: " + settings.getSlots());
		this.waitTime = 60;
		this.godTime = 0;
		this.gameTime = (settings.getTime()*60)-60;
		this.endTime = 60;
		this.lastTime = 0;
		this.gameStatus = 0;
		signX = settings.getSign().x;
		signY = settings.getSign().y;
		signZ = settings.getSign().z;
		maxPlayerCount = settings.getSlots();

		worldname = settings.getWorld();
        worldmanager = skywars.worldmanager;
        reloadLevel();
		initPositions();
		Server.getInstance().getScheduler().scheduleRepeatingTask(new SoloArenaTimer(this, arenaname, worldname, signX, signY, signZ, maxPlayerCount), 20);
	}
	
	public void reloadLevel() {
		try {
			worldmanager.restartArena(worldname);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Server.getInstance().getLevelByName(worldname).setTime(6000);
		Server.getInstance().getLevelByName(worldname).stopTime();
	}
	
	public void initPositions() {
		for(int i = 1; i <= maxPlayerCount; i++) {
			Position pos = this.settings.getPosition(i);
			positions.add(pos);
		}
	}
	
	public void resetPositions() {
		for(int i = 1; i <= maxPlayerCount; i++) {
			Position pos = this.settings.getPosition(i);
			positions.remove(pos);
			positions.add(pos);
		}
	}
	
	public short[] armor1 = { 298, 298, 314, 298, 298, 314, 298, 298, 314, 302, 302, 302, 302, 302, 302, 306, 306, 310 };
	public short[] armor2 = { 299, 299, 315, 299, 299, 315, 299, 299, 315, 303, 303, 303, 307, 307, 311, 307, 307, 311 };
	public short[] armor3 = { 300, 300, 316, 300, 300, 316, 300, 300, 316, 304, 304, 304, 308, 308, 312, 308, 308, 312 };
	public short[] armor4 = { 301, 301, 317, 301, 301, 317, 301, 301, 317, 305, 305, 305, 309, 309, 313, 309, 309, 313 };
	public short[] sword1 = { 268, 268, 283, 268, 268, 283, 268, 268, 283, 272, 272, 267, 267, 267, 276, 267, 267, 276 };
	public String[] itemarray = { "258", "259", "261", "262", "264", "265", "266", "267", "272", "273", "274", "275", "277", "278", "279", "280", "287", "318", "297", "320", "322", "332", "344", "350", "364", "366", "357" };
	public short[] blockarray = { 1, 2, 3, 4, 5 };

	
 	public Item getHelmetItem()
	{
 		return Item.get(armor1[mt_rand(0,armor1.length)], 0, 1);
	}
 	
 	public Item getChestplaceItem()
	{
 		return Item.get(armor2[mt_rand(0,armor2.length)], 0, 1);
	}
 	
 	public Item getLeggingsItem()
	{
 		return Item.get(armor3[mt_rand(0,armor3.length)], 0, 1);
	}
 	
 	public Item getBootsItem()
	{
 		return Item.get(armor4[mt_rand(0,armor4.length)], 0, 1);
	}
 	
 	public Item getSwordItem()
	{
 		return Item.get(sword1[mt_rand(0,sword1.length)], 0, 1);
	}
 	
 	public Item getRandomItem()
	{
 		return Item.get(Short.valueOf(itemarray[mt_rand(0,itemarray.length)]), 0, mt_rand(1,12));
	}
 	
 	public Item getRandomBlock()
	{
 		return Item.get(blockarray[mt_rand(0,blockarray.length)], 0, mt_rand(12,30));
	}
 	
 	public int mt_rand(int min, int max) {
 		return (int) (Math.random()*(max-min))+min;
 	}
	
	public void signChange(String arenaname, double signX, double signY, double signZ, int maxPlayerCount)
	{
		if(this.gameStatus < 2)
		{
			BlockEntitySign sign = (BlockEntitySign)skywars.getServer().getDefaultLevel().getBlockEntity(new Vector3(signX, signY, signZ));
			sign.setText("§e[SkyWars]", "§a" + this.arenaname + "", "§e" + String.valueOf(arenaplayers.size()) + "/" + String.valueOf(maxPlayerCount) + "", "§aJOIN");
			
		}
		if(this.gameStatus > 2)
		{
			BlockEntitySign sign = (BlockEntitySign)skywars.getServer().getDefaultLevel().getBlockEntity(new Vector3(signX, signY, signZ));
			sign.setText("§e[SkyWars]", "§a" + this.arenaname + "", "§e" + String.valueOf(arenaplayers.size()) + "/" + String.valueOf(maxPlayerCount) + "", "§cRUNNING");
			
		}
		
	}

    public void gameTimer(String arenaname, String worldname, double signX, double signY, double signZ, int maxPlayerCount)
	{
    	signChange(arenaname, signX, signY, signZ, maxPlayerCount);
		String hraci = String.valueOf(arenaplayers.size());
		if (this.gameStatus == 0)
		{
			int i = 0;
			for(Player ingame : arenaplayers.values()) {
				if(game(ingame)) {
					ingame.teleport(positions.get(i));
					ingame.setImmobile(true);
				}
				i++;
			}
			if (arenaplayers.size() >= 2)
			{
				this.gameStatus = 1;
				this.lastTime = this.waitTime;
				for (Player ingames : arenaplayers.values())
				{
					ingames.sendMessage(LanguageManager.translate("sw_solo_countdown_started", ingames, new String[0]));
				}
			}
		}
		if (this.gameStatus == 1)
		{
			this.lastTime--;
			if(arenaplayers.size() < 2) {
				this.gameStatus = 0;
				this.lastTime = 0;
			} else {
			int i = 0;
			for(Player ingame : arenaplayers.values()) {
				if(game(ingame)) {
					ingame.teleport(positions.get(i));
					ingame.setImmobile(true);
				}
				i++;
			}
			switch (this.lastTime)
			{

				case 1:
					for (Player ingame : arenaplayers.values())
					{
						if(game(ingame)) {
							ingame.sendPopup("§aGame starts in §b" + this.lastTime + " §aseconds");
							ingame.sendTitle("§a1");
						}
						
					}
					break;
				case 2:
					for (Player ingame : arenaplayers.values())
					{
						if(game(ingame)) {
							ingame.sendPopup("§aGame starts in §b" + this.lastTime + " §aseconds");
							ingame.sendTitle("§e2");
						}
					}
					break;
				case 3:
					for (Player ingame : arenaplayers.values())
					{
						if(game(ingame)) {
							ingame.sendPopup("§aGame starts in §b" + this.lastTime + " §aseconds");
							ingame.sendTitle("§63");
						}
					}
					break;
				case 4:
					for (Player ingame : arenaplayers.values())
					{
						if(game(ingame)) {
							ingame.sendPopup("§aGame starts in §b" + this.lastTime + " §aseconds");
							ingame.sendTitle("§c4");
						}
					}
					break;
				case 5:
					for (Player ingame : arenaplayers.values())
					{
						if(game(ingame)) {
							ingame.sendPopup("§aGame starts in §b" + this.lastTime + " §aseconds");
							ingame.sendTitle("§45");
						} 
					}
					break;
				case 6:
				case 7:
				case 8:
				case 9:
					for (Player ingame : arenaplayers.values())
					{
						if(game(ingame)) {
							ingame.sendPopup("§aGame starts in §b" + this.lastTime + " §aseconds");
						}
					}
					break;
				case 10:
					for (Player ingame : arenaplayers.values())
					{
						if(game(ingame)) {
							ingame.sendPopup("§aGame starts in §b" + this.lastTime + " §aseconds");
							ingame.sendTitle("§b10");
						}
					}
					break;
				case 11:
				case 12:
				case 13:
				case 14:
				case 15:
				case 16:
				case 17:
				case 18:
				case 19:
				case 20:
				case 21:
				case 22:
				case 23:
				case 24:
				case 25:
				case 26:
				case 27:
				case 28:
				case 29:
				case 30:
				case 31:
				case 32:
				case 33:
				case 34:
				case 35:
				case 36:
				case 37:
				case 38:
				case 39:
				case 40:
				case 41:
				case 42:
				case 43:
				case 44:
				case 45:
				case 46:
				case 47:
				case 48:
				case 49:
				case 50:
				case 51:
				case 52:
				case 53:
				case 54:
				case 55:
				case 56:
				case 57:
				case 58:
				case 59:
					for (Player ingame : arenaplayers.values())
					{
						if(game(ingame)) {
							ingame.sendPopup("§aGame starts in §b" + this.lastTime + " §aseconds");
						}
					}
					break;
				case 60:
					for (Player ingame : arenaplayers.values())
					{
						if(game(ingame)) {
							ingame.sendPopup("§aGame starts in §b" + this.lastTime + " §aseconds");
						}
					}
					break;
				case 0:
					Server.getInstance().getScheduler().scheduleAsyncTask(new RefillTimer(this));
					this.gameStatus = 2;
					this.lastTime = this.godTime;
					Server.getInstance().getLogger().info("Game started on arena " + this.arenaname);
					for (Player ingame : arenaplayers.values())
					{
						if(game(ingame)) {
							ingame.sendMessage(LanguageManager.translate("sw_solo_game_started", ingame, new String[0]));
							ingame.sendMessage(LanguageManager.translate("sw_solo_list_players", ingame, String.valueOf(arenaplayers.size())));
							ingame.sendTitle("§bGame started");
							ingame.getInventory().clearAll();
							ingame.setImmobile(false);
						}
					}
					break;
			}
			}
		}
		if (this.gameStatus == 2)
		{
			this.gameStatus = 3;
			this.lastTime = this.gameTime;
		}
		if (this.gameStatus > 2 && this.gameStatus < 5)
		{
			for(Player ingame : arenaplayers.values())
			{
				if(game(ingame)) {
					ingame.sendPopup("§aPlayers: " + arenaplayers.size());
				}
			}
		
			if (arenaplayers.size() == 0)
			{
				reloadLevel();
				resetPositions();
				this.gameStatus = 0;
				this.lastTime = 0;
			}
			if (arenaplayers.size() == 1)
			{
				for (Player pla : arenaplayers.values())
				{
					if(game(pla)) {
						pla.getInventory().clearAll();
						SkyWars.getPlayer(pla).addWin();
						SkyWars.getPlayer(pla).setInLobby(true);
						pla.teleport(skywars.lobbyXYZ);
						pla.getFoodData().setLevel(20);
						leave(pla, "leave");
						Server.getInstance().broadcastMessage("§eSkyWars> " + pla.getDisplayName() + " §awon the game on arena §b" + this.arenaname + "");
						reloadLevel();
						resetPositions();
					}
					this.gameStatus = 0;
					this.lastTime = 0;
				}
			}
		}
		if (this.gameStatus == 3)
		{
			this.lastTime--;
			switch (this.lastTime)
			{
				case 0:
					this.gameStatus = 4;
					this.lastTime = this.endTime;
					break;
			}
		}
		if (this.gameStatus == 4)
		{
			this.lastTime--;
			switch (this.lastTime)
			{
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 10:
				case 15:
				case 30:
				case 59:
					for (Player ingame : arenaplayers.values())
					{
						if(game(ingame)) {
							ingame.sendMessage(LanguageManager.translate("sw_solo_time", ingame, String.valueOf(this.lastTime)));
						}
					}
					break;
				case 60:
					Server.getInstance().getScheduler().scheduleAsyncTask(new RefillTimer(this));
					for (Player ingame : arenaplayers.values())
					{
						if(game(ingame)) {
							ingame.sendMessage(LanguageManager.translate("sw_solo_time", ingame, String.valueOf(this.lastTime)));
							ingame.sendMessage(LanguageManager.translate("chest_refill", ingame, new String[0]));
						}
					}
					break;
				case 0:
					for (Player pla : arenaplayers.values())
					{
						if(game(pla)) {
							pla.getInventory().clearAll();
							SkyWars.getPlayer(pla).setInLobby(true);
							pla.teleport(skywars.lobbyXYZ);
							pla.getFoodData().setLevel(20);
							leave(pla, "leave");
							Server.getInstance().broadcastMessage("§eSkyWars> §aNoone didnt won the game on arena §b" + this.arenaname + "");
							reloadLevel();
							this.gameStatus = 0;
							this.lastTime = 0;	
						}
					}
					break;
			}
		}
	}
    
    public void clearChest() {
    	Level level = Server.getInstance().getLevelByName(settings.getWorld());
    	Map<Long, BlockEntity> be = level.getBlockEntities();
    	for(BlockEntity b : be.values()) {
    		if(b instanceof BlockEntityChest) {
    			BlockEntityChest chest = (BlockEntityChest) b;
    			for(int i = 0; i <= chest.getSize(); i++) {
    				chest.getInventory().setItem(i, new Item(0,0,0));
    			}
    		}
    	}
    }
    
    public void resetChest() {
    	Level level = Server.getInstance().getLevelByName(settings.getWorld());
    	Map<Long, BlockEntity> be = level.getBlockEntities();
    	for(BlockEntity b : be.values()) {
    		if(b instanceof BlockEntityChest) {
    			BlockEntityChest chest = (BlockEntityChest) b;
    			chest.getInventory().setItem(0, getHelmetItem());
    			chest.getInventory().setItem(1, getChestplaceItem());
    			chest.getInventory().setItem(2, getLeggingsItem());
    			chest.getInventory().setItem(3, getBootsItem());
    			chest.getInventory().setItem(4, getSwordItem());
    			int rand = mt_rand(1,8);
    			for(int i = 1; i <= rand; i++) {
    				chest.getInventory().setItem(mt_rand(5,11), getRandomItem());
    				chest.getInventory().setItem(mt_rand(12,16), getRandomBlock());
    			}
    		}
    	}
    }
    
    public boolean game(Player player) {
    	return arenaplayers.containsKey(player.getName());
    }
    
    public void join(Player player) {
    	arenaplayers.put(player.getName(), player);
    	SkyWars.getPlayer(player).setInLobby(false);
    	SkyWars.getPlayer(player).setArena(this);
    	Player ingame = player;
		ingame.setGamemode(0);
		ingame.getInventory().clearAll();
		ingame.setHealth(20);
		ingame.getFoodData().setLevel(20);
		Item clock = Item.get(Item.CLOCK);
		clock.setCustomName("§eBack to lobby");
		ingame.getInventory().setItem(4, clock);
    }
    
    public void leave(Player player, String cause) {
    	SWPlayer data = SkyWars.getPlayer(player);
    	data.setArena(null);
    	if(cause == "void") {
    		arenaplayers.remove(player.getName());
    		data.setInLobby(true);
			player.getInventory().clearAll();
			player.setHealth(20);
			player.getFoodData().setLevel(20);
			player.setImmobile(false);
			player.teleport(skywars.lobbyXYZ);
			for (Player ingame : arenaplayers.values()) 
			{
				if(game(ingame)) {
					ingame.sendMessage(LanguageManager.translate("sw_solo_all_death", ingame, player.getName()));
				}
			}
    	}
    	if(cause == "kill") {
    		arenaplayers.remove(player.getName());
    		data.setInLobby(true);
    		Map<Integer, Item> items = player.getInventory().getContents();
    		for(Item item : items.values()) {
    			player.getLevel().dropItem(player.getPosition(), item);
    		}
			player.getInventory().clearAll();
			player.setHealth(20);
			player.getFoodData().setLevel(20);
			player.setImmobile(false);
			player.teleport(skywars.lobbyXYZ);
    	}
    	if(cause == "leave") {
    		arenaplayers.remove(player.getName());
    		data.setInLobby(true);
			player.getInventory().clearAll();
			player.setHealth(20);
			player.getFoodData().setLevel(20);
			player.setImmobile(false);
			player.teleport(skywars.lobbyXYZ);
			for (Player ingame : arenaplayers.values()) 
			{
				if(game(ingame)) {
					ingame.sendMessage(LanguageManager.translate("sw_solo_all_quit", ingame, player.getName()));	
				}
			}
    	}
    }


}
