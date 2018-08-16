package cz.SkyWars.Arena;

import cz.SkyWars.SkyWars;
import cz.SkyWars.SWPlayer;
import cz.SkyWars.Arena.Timer.*;
import cz.SkyWars.Manager.*;
import cz.SkyWars.Manager.WorldManager.ArenaWorldManager;
import cz.SkyWars.Actions.RandomAction;
import cz.SkyWars.Actions.MoneyRewardAction;

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
import cn.nukkit.network.protocol.ExplodePacket;
import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.entity.*;

import java.util.*;

public class Arena implements Listener
{


	public SkyWars skywars;
	public ArenaWorldManager worldmanager;
	public ArenaSettings settings;
	public List<Position> positions = new ArrayList<Position>();
	public int spawnIndex;
	
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
	public HashMap<Integer, Weight> armor1 = new HashMap<Integer, Weight>();
	public HashMap<Integer, Weight> armor2 = new HashMap<Integer, Weight>();
	public HashMap<Integer, Weight> armor3 = new HashMap<Integer, Weight>();
	public HashMap<Integer, Weight> armor4 = new HashMap<Integer, Weight>();
	public HashMap<Integer, Weight> sword1 = new HashMap<Integer, Weight>();
	public HashMap<Integer, Weight> itemarray = new HashMap<Integer, Weight>();
	public HashMap<Integer, Weight> blockarray = new HashMap<Integer, Weight>();

	public HashMap<String, Player> arenaplayers = new HashMap<String, Player>();

	public HashMap<String, Double> arenadata;


	public Arena(SkyWars skywars, String arenaname, ArenaSettings settings)
	{
		random = new Random();
        this.skywars = skywars;
        this.arenaname = arenaname;
        this.settings = settings;
        if(!skywars.getServer().isLevelLoaded(settings.getLevel().getName())) {
        	Server.getInstance().loadLevel(settings.getLevel().getName());
        }
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
		initPositions();
		initChests();
		
		worldname = settings.getLevel().getName();
        worldmanager = new ArenaWorldManager(this);
        worldmanager.restartArena(worldname);
        Server.getInstance().getScheduler().scheduleRepeatingTask(new SoloArenaTimer(this, arenaname, worldname, signX, signY, signZ, maxPlayerCount), 20);
	}
	
	public void initPositions() {
		for(int i = 1; i <= maxPlayerCount; i++) {
			Position pos = this.settings.getPosition(i);
			positions.add(pos);
		}
	}
	
	
	public void initChests()
	{
		armor1.put(298, new Weight(1, 1, 1));
		armor1.put(302, new Weight(2, 1, 1));
		armor1.put(306, new Weight(3, 1, 1));
		armor1.put(310, new Weight(4, 1, 1));
		armor1.put(314, new Weight(5, 1, 1));
		
 		armor2.put(299, new Weight(1, 1, 1));
		armor2.put(303, new Weight(2, 1, 1));
		armor2.put(307, new Weight(3, 1, 1));
		armor2.put(311, new Weight(4, 1, 1));
		armor2.put(315, new Weight(5, 1, 1));
		
 		armor3.put(300, new Weight(1, 1, 1));
		armor3.put(304, new Weight(2, 1, 1));
		armor3.put(308, new Weight(3, 1, 1));
		armor3.put(312, new Weight(4, 1, 1));
		armor3.put(316, new Weight(5, 1, 1));
		
 		armor4.put(301, new Weight(1, 1, 1));
		armor4.put(305, new Weight(2, 1, 1));
		armor4.put(309, new Weight(3, 1, 1));
		armor4.put(313, new Weight(4, 1, 1));
		armor4.put(317, new Weight(5, 1, 1));
		
 		sword1.put(267, new Weight(1, 1, 2));
		sword1.put(268, new Weight(2, 1, 2));
		sword1.put(272, new Weight(3, 1, 2));
		sword1.put(276, new Weight(4, 1, 2));
		
 		itemarray.put(258, new Weight(1, 1, 1));
		itemarray.put(261, new Weight(2, 1, 1));
		itemarray.put(262, new Weight(3, 1, 18));
		itemarray.put(260, new Weight(4, 1, 10));
		itemarray.put(264, new Weight(5, 1, 1));
		itemarray.put(275, new Weight(6, 1, 1));
		itemarray.put(332, new Weight(7, 1, 16));
		
		blockarray.put(1, new Weight(1, 32, 64));
		blockarray.put(2, new Weight(2, 32, 64));
 		blockarray.put(3, new Weight(3, 32, 64));
		blockarray.put(4, new Weight(4, 32, 64));
		blockarray.put(5, new Weight(5, 32, 64));
	}
	
 	public Item getHelmetItem()
	{
		int count = 0;
		int num = random.nextInt(finalWeight);
		for (Map.Entry<Integer, Weight> entry : armor1.entrySet())
		{
			count += entry.getValue().weight;
			if (count >= num)
			{
				return Item.get(entry.getKey(), 0, new Random().nextInt((entry.getValue().max - entry.getValue().min) + 1) + entry.getValue().min);
			}
		}
		return Item.get(0);
	}
 	
 	public Item getChestplaceItem()
	{
		int count = 0;
		int num = random.nextInt(finalWeight);
		for (Map.Entry<Integer, Weight> entry : armor2.entrySet())
		{
			count += entry.getValue().weight;
			if (count >= num)
			{
				return Item.get(entry.getKey(), 0, new Random().nextInt((entry.getValue().max - entry.getValue().min) + 1) + entry.getValue().min);
			}
		}
		return Item.get(0);
	}
 	
 	public Item getLeggingsItem()
	{
		int count = 0;
		int num = random.nextInt(finalWeight);
		for (Map.Entry<Integer, Weight> entry : armor3.entrySet())
		{
			count += entry.getValue().weight;
			if (count >= num)
			{
				return Item.get(entry.getKey(), 0, new Random().nextInt((entry.getValue().max - entry.getValue().min) + 1) + entry.getValue().min);
			}
		}
		return Item.get(0);
	}
 	
 	public Item getBootsItem()
	{
		int count = 0;
		int num = random.nextInt(finalWeight);
		for (Map.Entry<Integer, Weight> entry : armor4.entrySet())
		{
			count += entry.getValue().weight;
			if (count >= num)
			{
				return Item.get(entry.getKey(), 0, new Random().nextInt((entry.getValue().max - entry.getValue().min) + 1) + entry.getValue().min);
			}
		}
		return Item.get(0);
	}
 	
 	public Item getSwordItem()
	{
		int count = 0;
		int num = random.nextInt(4);
		for (Map.Entry<Integer, Weight> entry : sword1.entrySet())
		{
			count += entry.getValue().weight;
			if (count >= num)
			{
				return Item.get(entry.getKey(), 0, new Random().nextInt((entry.getValue().max - entry.getValue().min) + 1) + entry.getValue().min);
			}
		}
		return Item.get(0);
	}
 	
 	public Item getRandomItem()
	{
		int count = 0;
		int num = random.nextInt(7);
		for (Map.Entry<Integer, Weight> entry : itemarray.entrySet())
		{
			count += entry.getValue().weight;
			if (count >= num)
			{
				return Item.get(entry.getKey(), 0, new Random().nextInt((entry.getValue().max - entry.getValue().min) + 1) + entry.getValue().min);
			}
		}
		return Item.get(0);
	}
 	
 	public Item getRandomBlock()
	{
		int count = 0;
		int num = random.nextInt(finalWeight);
		for (Map.Entry<Integer, Weight> entry : blockarray.entrySet())
		{
			count += entry.getValue().weight;
			if (count >= num)
			{
				return Item.get(entry.getKey(), 0, new Random().nextInt((entry.getValue().max - entry.getValue().min) + 1) + entry.getValue().min);
			}
		}
		return Item.get(0);
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
					clearChest();
					resetChest();
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
				Server.getInstance().unloadLevel(Server.getInstance().getLevelByName(worldname));
				worldmanager.restartArena(worldname);
				Server.getInstance().loadLevel(worldname);
				Server.getInstance().getLevelByName(worldname).setTime(6000);
				Server.getInstance().getLevelByName(worldname).stopTime();
				this.gameStatus = 0;
				this.lastTime = 0;
			}
			if (arenaplayers.size() == 1)
			{
				for (Player pla : arenaplayers.values())
				{
					if(game(pla)) {
						pla.getInventory().clearAll();
						SkyWars.getPlayer(pla).setInLobby(true);
						pla.teleport(skywars.lobbyXYZ);
						pla.getFoodData().setLevel(20);
						Server.getInstance().broadcastMessage("§eSkyWars> " + pla.getDisplayName() + " §awon the game on arena §b" + this.arenaname + "");
						new MoneyRewardAction(pla);
						Server.getInstance().unloadLevel(Server.getInstance().getLevelByName(worldname));
						worldmanager.restartArena(worldname);
						Server.getInstance().loadLevel(worldname);
						Server.getInstance().getLevelByName(worldname).setTime(6000);
						Server.getInstance().getLevelByName(worldname).stopTime();	
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
				case 60:
					clearChest();
					resetChest();
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
							Server.getInstance().broadcastMessage("§eSkyWars> §aNoone didnt won the game on arena §b" + this.arenaname + "");
							Server.getInstance().unloadLevel(Server.getInstance().getLevelByName(worldname));
							worldmanager.restartArena(worldname);
							Server.getInstance().loadLevel(worldname);
							Server.getInstance().getLevelByName(worldname).setTime(6000);
							Server.getInstance().getLevelByName(worldname).stopTime();
							this.gameStatus = 0;
							this.lastTime = 0;	
						}
					}
					break;
			}
		}
	}
    
    public void clearChest() {
    	Level level = settings.getLevel();
    	Map<Long, BlockEntity> be = level.getBlockEntities();
    	for(Map.Entry<Long,BlockEntity> entry : be.entrySet()) {
    		BlockEntity b = entry.getValue();
    		if(b instanceof BlockEntityChest) {
    			BlockEntityChest chest = (BlockEntityChest) b;
    			for(int i = 0; i <= chest.getSize(); i++) {
    				chest.getInventory().setItem(i, new Item(0,0,0));
    			}
    		}
    	}
    }
    
    public void resetChest() {
    	Level level = settings.getLevel();
    	Map<Long, BlockEntity> be = level.getBlockEntities();
    	for(Map.Entry<Long,BlockEntity> entry : be.entrySet()) {
    		BlockEntity b = entry.getValue();
    		if(b instanceof BlockEntityChest) {
    			BlockEntityChest chest = (BlockEntityChest) b;
    			chest.getInventory().setItem(0, getHelmetItem());
    			chest.getInventory().setItem(1, getChestplaceItem());
    			chest.getInventory().setItem(2, getLeggingsItem());
    			chest.getInventory().setItem(3, getBootsItem());
    			chest.getInventory().setItem(4, getSwordItem());
    			int rand = new Random(1).nextInt(8);
    			for(int i = 1; 1 <= rand; i++) {
    				chest.getInventory().setItem(new Random(5).nextInt(10), getRandomItem());
    				chest.getInventory().setItem(new Random(11).nextInt(16), getRandomBlock());
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
    	if(cause == "void") {
    		data.setInLobby(true);
			player.getInventory().clearAll();
			player.setHealth(20);
			player.getFoodData().setLevel(20);
			player.teleport(skywars.lobbyXYZ);
			for (Player ingame : arenaplayers.values()) 
			{
				ingame.sendMessage(LanguageManager.translate("sw_solo_all_death", ingame, player.getName()));
			}
    	}
    	if(cause == "leave") {
    		data.setInLobby(true);
			player.getInventory().clearAll();
			player.setHealth(20);
			player.getFoodData().setLevel(20);
			player.teleport(skywars.lobbyXYZ);
			for (Player ingame : arenaplayers.values()) 
			{
				ingame.sendMessage(LanguageManager.translate("sw_solo_all_quit", ingame, player.getName()));
			}
    	}
    }

    @EventHandler
    public void handleInteractForJoiningToArena(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getBlock();
		if (block.getX() == this.signX && block.getY() == this.signY && block.getZ() == this.signZ)
		{
			if(gameStatus > 2) {
				player.sendMessage(LanguageManager.translate("sw_solo_running", player, new String[0]));
			}
			else if(arenaplayers.size() >= maxPlayerCount) {
				player.sendMessage(LanguageManager.translate("sw_solo_full", player, new String[0]));
			} else {
				join(player);	
				for(Player p : arenaplayers.values()) {
					if(game(p)) {
						p.sendMessage(LanguageManager.translate("sw_solo_all_join", player, player.getName()));
					}
				}
			}
		}
    }

	@EventHandler
	public void handleLuckyBlockBreak(BlockBreakEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getBlock();
		if(this.gameStatus > 2){ 
			if(player.getLevel() == Server.getInstance().getLevelByName(this.worldname) && game(player))
			{
				event.setCancelled();
			}
		}
	}

    @EventHandler
    public void handleAntiMove(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		if (arenaplayers.containsKey(player.getName()))
		{
			if (this.gameStatus > 2)
			{
				if (player.getY() < 10)
				{
					leave(player, "void");
				}
            }
        }
    }

    @EventHandler
    public void handlePlayerQuit(PlayerQuitEvent event)
	{
        Player player = event.getPlayer();
        if (arenaplayers.containsKey(player.getName()))
		{
        	leave(player, "leave");
        }
    }
    
    @EventHandler
    public void handleDamage(EntityDamageEvent event)
    {
    	Player player = (Player) event.getEntity();
    	if(this.gameStatus < 2)
    	{
    		if(player.getLevel() == Server.getInstance().getLevelByName(this.worldname))
    		{
    			event.setCancelled();
    		}
    	}
    }

	@EventHandler
    public void handleDamageEvent(EntityDamageEvent event)
	{
        Player playerEntity = (Player) event.getEntity();
        if (event instanceof EntityDamageByEntityEvent)
		{
            Entity damager = ((EntityDamageByEntityEvent) event).getDamager();
            Player hitnutyHrac = (Player) event.getEntity();
            Player player = (Player) damager;
            if (player instanceof Player && hitnutyHrac instanceof Player)
            {
            	if ((hitnutyHrac.getHealth() - event.getDamage()) < 1)
            	{
            		for (Player ingame : arenaplayers.values())
            		{
            			if(game(player) && game(ingame)) {
        					ingame.sendMessage(LanguageManager.translate("sw_solo_all_death_cause_kill", ingame, hitnutyHrac.getName(), player.getName()));
            			}
            		}
            	} 
            }
		}
	} 


    @EventHandler
    public void onDeath(PlayerDeathEvent event)
	{
        Entity entity = event.getEntity();
        Player player = (Player) entity;
        if (arenaplayers.containsKey(player.getName()))
		{
			event.setDeathMessage("");
        }
    }
    
    


	@EventHandler
    public void kitSelect(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		Item item = event.getItem();
		Block block = event.getBlock();
		if (arenaplayers.containsKey(player.getName()))
		{
			if(item.getCustomName().equals("§eBack to lobby"))
			{
				event.setCancelled();
				leave(player, "leave");
			}
			if(block.getId() == Block.CHEST) {
				if(this.gameStatus < 2) {
					event.setCancelled();
				}
			}
		}
    }
	
	
	@EventHandler
	public void handleDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if(game(player)) {
			event.setCancelled();
		}
	}


}
