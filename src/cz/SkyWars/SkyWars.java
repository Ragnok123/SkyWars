package cz.SkyWars;

import cz.SkyWars.Arena.Arena;
import cz.SkyWars.Arena.ArenaListener;
import cz.SkyWars.Arena.ArenaSettings;
import cz.SkyWars.Manager.*;
import cz.SkyWars.Manager.WorldManager.*;
import cz.SkyWars.entity.KitNPC;
import cz.SkyWars.MySQL;
import cz.SkyWars.Actions.*;
import cz.SkyWars.JoinTask;
import cz.SkyWars.Database.*;
import cz.SkyWars.Economy.*;
import cz.SkyWars.Kits.KitManager;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.*;
import cn.nukkit.blockentity.BlockEntitySign;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.nbt.tag.*;
import cn.nukkit.entity.passive.EntityVillager;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.event.server.DataPacketSendEvent;
import cn.nukkit.event.server.QueryRegenerateEvent;
import cn.nukkit.inventory.transaction.data.UseItemData;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.particle.FloatingTextParticle;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.InventoryTransactionPacket;
import cn.nukkit.network.protocol.PlayerActionPacket;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;

public class SkyWars extends PluginBase implements Listener
{

	public static SkyWars instance;
	public MySQL mysql;
	public Position lobbyXYZ;
	public Position hologramXYZ;
	public Database database;
	public Economy economy;
	public ArenaWorldManager worldmanager;
	public KitManager kitMgr;

	public HashMap<String, Arena> arenas = new HashMap<String, Arena>();
	public Config settingsc;
	public Config arenass;
	public Config cplayers;
	
	public HashMap<Player, ArenaSettings> setup = new HashMap<Player, ArenaSettings>();
	public HashMap<Player, Integer> stepSolo = new HashMap<Player, Integer>();
	public HashMap<Player, Integer> stepTeam = new HashMap<Player, Integer>();
	public static HashMap<String, SWPlayer> players = new HashMap<String, SWPlayer>();
	HashMap<Player, Long> antiDupe = new HashMap<Player, Long>();

	@Override
	public void onLoad()
	{
		LanguageManager.initEnglishMsg();
		
	}
	

	@Override
    public void onEnable()
	{ 
		instance = this;
		getServer().getPluginManager().registerEvents(this, this);
		worldmanager = new ArenaWorldManager();
		loadWorlds();
		new File(getServer().getDataPath() + "arenas/").mkdirs();
		
		File settingsFile = new File(getDataFolder() + "/settings.yml");
		if(!settingsFile.exists()) {
			settingsc = new Config(getDataFolder() + "/settings.yml", Config.YAML);
			settingsc.set("lobbyX", getServer().getDefaultLevel().getSafeSpawn().getX());
			settingsc.set("lobbyY", getServer().getDefaultLevel().getSafeSpawn().getY() + 4.00);
			settingsc.set("lobbyZ", getServer().getDefaultLevel().getSafeSpawn().getZ());
			settingsc.set("lobbyWorld", getServer().getDefaultLevel().getName());
			settingsc.set("dataProvider.yaml", true);
			settingsc.set("dataProvider.mysql", false);
			settingsc.set("economyapi-enabled", true);
			settingsc.set("customeconomy-enabled", false);
			settingsc.set("joinMessage-enabled", false);
			settingsc.set("quitMessage-enabled", false);
			settingsc.set("hologram-enabled", false);
			settingsc.set("hologramX", getServer().getDefaultLevel().getSafeSpawn().getX());
			settingsc.set("hologramY", getServer().getDefaultLevel().getSafeSpawn().getY() + 1.5);
			settingsc.set("hologramZ", getServer().getDefaultLevel().getSafeSpawn().getZ());
			settingsc.set("hologramWorld", getServer().getDefaultLevel().getName());
			settingsc.set("price.kit.builder", 10000);
			settingsc.set("price.kit.soldier", 15000);
			
			settingsc.set("reward-amount", 100);
			settingsc.set("mysql.url", "");
			settingsc.set("mysql.login", "");
			settingsc.set("mysql.password", "");
			settingsc.set("mysql.database", "");
			settingsc.save();
		} else {
			settingsc = new Config(getDataFolder() + "/settings.yml", Config.YAML);
		}
		
		if((boolean)settingsc.get("dataProvider.yaml") == true && (boolean)settingsc.get("dataProvider.mysql") == false) {
			database = new YamlDatabase(this);
			getLogger().info("Choosed Yaml data provider");
		}
		
		if((boolean)settingsc.get("dataProvider.yaml") == false && (boolean)settingsc.get("dataProvider.mysql") == true) {
			database = new MysqlDatabase(this);
			getLogger().info("Choosed MySQL data provider");
			String host = (String) settingsc.get("mysql.url");
			String login = (String) settingsc.get("mysql.login");
			String pass = (String) settingsc.get("mysql.password");
			String db = (String) settingsc.get("mysql.database");
			mysql = new MySQL(this, login, pass, db, host);
			mysql.connect();
			mysql.init();
		}
		
		if((boolean)settingsc.get("dataProvider.yaml") == false && (boolean)settingsc.get("dataProvider.mysql") == false) {
			getLogger().info("Select data provider firstly");
			getServer().getPluginManager().disablePlugin(this);
		}
		
		if((boolean)settingsc.get("dataProvider.yaml") == true && (boolean)settingsc.get("dataProvider.mysql") == true) {
			getLogger().info("Choose only one data provider");
			getServer().getPluginManager().disablePlugin(this);
		}
		
		if((boolean)settingsc.get("economyapi-enabled") == true && (boolean)settingsc.get("customeconomy-enabled") == false) {
			Plugin eapi = Server.getInstance().getPluginManager().getPlugin("EconomyAPI");
			if (eapi == null) {
				Server.getInstance().getLogger().info("EconomyAPI not found. Please, if you want to run SkyWars with EconomyAPI, install it.");
			} else {
				this.economy = new EconomyEconomyAPI(this);
			}
		}
		
		arenass = new Config(getDataFolder() + "/arenas.yml", Config.YAML);
		cplayers = new Config(getDataFolder() + "/players.yml", Config.YAML);
		/* Positions */
		String world =  (String) settingsc.getString("lobbyWorld") ;
		lobbyXYZ = new Position((double)settingsc.get("lobbyX"), (double)settingsc.get("lobbyY"), (double)settingsc.get("lobbyZ"));
		lobbyXYZ.setLevel(getServer().getLevelByName(world));
		hologramXYZ = new Position((double)settingsc.get("lobbyX"), (double)settingsc.get("lobbyY"), (double)settingsc.get("lobbyZ"));
		String world1 =  (String) settingsc.getString("hologramWorld") ;
		hologramXYZ.setLevel(getServer().getLevelByName(world1));
		Entity.registerEntity("KitNPC", KitNPC.class);
		kitMgr = new KitManager(this);
		Server.getInstance().getPluginManager().registerEvents(new ArenaListener(), this);
		getServer().getScheduler().scheduleDelayedTask(new StartupTask(this), 20);
	}	
	
	public void loadWorlds() {
        String[] ws = new File(this.getDataFolder() + "/worlds/").list();
        if (ws == null) return;
        for (String w : ws) {
            if (new File(this.getDataFolder() + "/worlds/" + w).isFile()) continue;
            try {
				worldmanager.restartArena(w);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
	
	public void setEconomyHandler(Economy economy) {
		this.economy = economy;
	}
	
    public void initializeArrays()
    {
    	if (arenass.getKeys() != null) {
    		Set<String> aren = arenass.getKeys(false);
        	for(String arena : aren) {
        		ArenaSettings settings = new ArenaSettings(arena, false);
        		registerArena(arena, new Arena(this, arena, settings));
        	}
    	}
    }
    
    public void registerArena(String name, Arena arena) {
    	arenas.put(name, arena);
    }
	
	
	@EventHandler
	public void antiBreak(BlockBreakEvent event)
	{
		Player player = event.getPlayer();
		if(player.getLevel() == getServer().getDefaultLevel() && !player.isOp())
		{
			event.setCancelled();
		}
	}
	

	


	@EventHandler
	public void antiBreak(BlockPlaceEvent event)
	{
		Player player = event.getPlayer();
		if(player.getLevel() == getServer().getDefaultLevel() && !player.isOp())
		{
			event.setCancelled();
		}
	}
	
	public int getKitPrice(String kit) {
		return (int) settingsc.get("price.kit."+kit.toLowerCase());
	}
	
	public int getReward() {
		return (int) settingsc.get("reward-amount");
	}
	
	@EventHandler
	public void antiPvp(EntityDamageEvent event)
	{
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(player.getLevel() == getServer().getDefaultLevel()){
				{
					event.setCancelled();
				}
			}
		}
		if(event instanceof EntityDamageByEntityEvent) {
			Entity target = ((EntityDamageByEntityEvent)event).getEntity();
			if(target instanceof KitNPC) {
				KitNPC hit = (KitNPC) target;
				Player hrac = (Player) ((EntityDamageByEntityEvent)event).getDamager();
				SWPlayer p = getPlayer(hrac);
				event.setCancelled();
				p.isBuying = true;
				p.buyingKit = hit.getKitId();
				hrac.sendMessage(LanguageManager.translate("buy_kit",hrac,hit.getNameTag(), String.valueOf(getKitPrice(hit.getKitId()))));
			}
		}
	}
	
	@EventHandler
	public void buy(PlayerChatEvent event) {
		Player player = event.getPlayer();
		SWPlayer p = getPlayer(player);
		String message = event.getMessage();
		if(p.isBuying) {
			event.setCancelled();
			switch(message) {
			case "yes":
				if(p.getMoney() >= getKitPrice(p.buyingKit)) {
					p.buyKit(p.buyingKit);
					p.addMoney(- getKitPrice(p.buyingKit));
					player.sendMessage("§eSkyWars> §aSuccesfully bought kit §b" + p.buyingKit);
					p.isBuying = false;
					p.buyingKit = "";
				} else {
					player.sendMessage("§eSkyWars> §cYou have no money");
					p.isBuying = false;
					p.buyingKit = "";
				}
				break;
			case "no":
				player.sendMessage("§eSkyWars> §aCancelled buying");
				p.isBuying = false;
				p.buyingKit = "";
				break;
			}
		}
	}
	
	public static SWPlayer getPlayer(Player player) {
		return players.get(player.getName().toLowerCase());
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		SWPlayer data = new SWPlayer(player);
		players.put(player.getName().toLowerCase(), data);
		data.setInLobby(true);
		if(settingsc.getBoolean("joinMessage-enabled") == true) {
			event.setJoinMessage("");
		}
		if(settingsc.getBoolean("hologram-enabled") == true) {
			String world1 =  (String) settingsc.getString("hologramWorld");
			FloatingTextParticle floatparticle = new FloatingTextParticle(hologramXYZ, "§aNickname: §f " + player.getName() + "\n§aKills: §f" + data.getKills() + "\n§aDeaths: §f" + data.getDeaths() + "\n§aWins: §f" + data.getWins() + "\n", "§l§b[§eSkyWars §bstats]");
			getServer().getLevelByName(world1).addParticle(floatparticle, player);
		}
	}
	
	
	@EventHandler
	public void handleQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		antiDupe.remove(player);
		players.remove(player.getName().toLowerCase());
		if(settingsc.getBoolean("quitMessage-enabled") == true) {
			event.setQuitMessage("");
		}
	}

    public static SkyWars getInstance()
	{
		return instance;
	}
    
    @EventHandler
    public void handleHunger(PlayerFoodLevelChangeEvent event)
    {
    	Player player = event.getPlayer();
    	if(player.getLevel() == getServer().getDefaultLevel())
    	{
    		event.setCancelled();
    	}
    }
    
    @EventHandler
    public void onTp(PlayerMoveEvent event)
    {
    	Player player = event.getPlayer();
    	if(player.getLevel() == Server.getInstance().getDefaultLevel() && player.getY() < 10)
    	{
    		player.teleport(new Vector3(getServer().getDefaultLevel().getSafeSpawn().getX(), getServer().getDefaultLevel().getSafeSpawn().getY() + 4.00, getServer().getDefaultLevel().getSafeSpawn().getZ()));
    	}
    }
    
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
    	Player player = event.getPlayer();
    	Block block = event.getBlock();
    	for(Arena arenas : this.arenas.values()) {
    		if((block.getX() == arenas.signX && block.getY() == arenas.signY && block.getZ() == arenas.signZ)){
    			if(arenas.gameStatus > 2) {
    				player.sendMessage(LanguageManager.translate("sw_solo_running", player, new String[0]));
    			}
    			else if(arenas.arenaplayers.size() >= arenas.maxPlayerCount) {
    				player.sendMessage(LanguageManager.translate("sw_solo_full", player, new String[0]));
    			} else {
    				arenas.join(player);	
    				for(Player p : arenas.arenaplayers.values()) {
    					if(arenas.game(p)) {
    						p.sendMessage(LanguageManager.translate("sw_solo_all_join", player, player.getName()));
    					}
    				}
    			}
    		}
    	}
    	if(setup.containsKey(player)) {
    		ArenaSettings settings = setup.get(player);
    		int currentStep = stepSolo.get(player);
    			if (currentStep == 0) {
    				if(block instanceof BlockSignPost || block instanceof BlockWallSign) {
            			player.sendMessage(LanguageManager.translate("arena_click", player, new String[0]));
            			settings.setSign(block, block.x, block.y, block.z, block.getLevel().getFolderName());
            			settings.fakeBlock = block;
            			settings.fakeLevel1 = player.getLevel().getName();
            			resetStep(player);
            		}
				} else if (currentStep > 0) {
					settings.setPositions(currentStep, block.x, block.y, block.z, block.getLevel().getFolderName());
	        		player.sendMessage(LanguageManager.translate("arena_click", player, new String[0]));
	        		settings.f(block.getLevel().getName());
	        		settings.positions.add(new Position(block.x, block.y + 1, block.z, block.getLevel()));
	        		resetStep(player);
				} 
    		
    	}
    }
    
    public void resetStep(Player player) {
    	ArenaSettings set = setup.get(player);
    	int currentStep = stepSolo.get(player);
    	stepSolo.remove(player);
    	stepSolo.put(player, currentStep + 1);
    }
    
    public CompoundTag getNPC(Player pos, Vector3 vector,String kit) {
		CompoundTag nbt = new CompoundTag()
				.putList(new ListTag<DoubleTag>("Pos")
						.add(new DoubleTag("", pos.getX()))
						.add(new DoubleTag("", pos.getY())) //maybe work?
						.add(new DoubleTag("", pos.getZ())))
		        .putList(new ListTag<DoubleTag>("Motion")
		        		.add(new DoubleTag("", vector.x))
		        		.add(new DoubleTag("", vector.y))
		        		.add(new DoubleTag("", vector.z)))
		        .putList(new ListTag<FloatTag>("Rotation")
		        		.add(new FloatTag("", (float) pos.getYaw()))
		        		.add(new FloatTag("", (float) pos.getPitch())))
		        .putString("KitId", kit.toLowerCase())
				.putCompound("Skin",
						new CompoundTag()
								.putByteArray("Data", pos.getSkin().getSkinData().data)
								.putString("ModelId",UUID.randomUUID().toString())
								.putString("GeometryName", "geometry.humanoid.custom")
								.putByteArray("GeometryData",pos.getSkin().getGeometryData().getBytes(StandardCharsets.UTF_8)))
				.putBoolean("Sneak", pos.isSneaking());
		return nbt;
	}
    
    public HashMap<String,String> kitnametag = new HashMap<String,String>(){{
    	put("builder","§l§e> §aBuilder §e<");
    	put("soldier","§l§e> §aSoldier §e<");
    }};
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
    	Player player = (Player)sender;
    	switch(command.getName()) 
    	{
    	case "sw":
    		if(sender instanceof ConsoleCommandSender) {
    			getLogger().error("[SkyWars] Commands are not accessable from console");
    		} else {
    			if(player.hasPermission("skywars.command"))
        		{
    				if(args.length == 0) {
    					player.sendMessage(LanguageManager.translate("cmd", player, new String[0]));
    				} else {
    					if(args[0].equals("npc")) {
    						String kit = args[1];
    						KitNPC npc = new KitNPC(player.chunk,getNPC(player,new Vector3(0,0,0),kit));
    						npc.setNameTag(kitnametag.get(kit.toLowerCase()));
    						npc.setNameTagVisible();
    						npc.setNameTagAlwaysVisible();
    						npc.spawnToAll();
    						player.sendMessage(LanguageManager.translate("npc_spawned",player,new String[0]));
    					}
    					else if(args[0].equals("kits")) {
    						if(args.length == 1) {
    							player.sendMessage("§l§e---===[SkyWars kits help]===---");
    							player.sendMessage("§l§e- §a/sw kits add <kit_id> §7- Creates new kit");
        						player.sendMessage("§l§e- §a/sw kits modify <kit_id> §7- Modifies existing kit");
        						player.sendMessage("§l§e- §a/sw kits remove <kit_id> §7- Deletes");
    						}
    					}
    					else if(args[0].equals("help")) {
    						player.sendMessage("§l§e---===[SkyWars help]===---");
    						player.sendMessage("§l§e- §a/sw create <arena_name> §7- Creates new arena");
    						player.sendMessage("§l§e- §a/sw finish §7- Finishes setup");
    						player.sendMessage("§l§e- §a/sw npc <spawn|delete> <builder|soldier|{custom kit id}> §7- Creates/Deletes kit NPC");
    					}
    					else if(args[0].equals("create")) {
            				String arenaname = (String) args[1];
            				ArenaSettings set = new ArenaSettings(arenaname, true);
            				setup.put(player, set);
            				stepSolo.put(player, 0);
            				set.setSettings(5);
            				player.sendMessage(LanguageManager.translate("arena_sign", ((Player) sender), new String[0]));
            			}
    					else if(args[0].equals("finish")) {
            				player.sendMessage(LanguageManager.translate("arena_finish", player, new String[0]));
            				ArenaSettings set = setup.get(player);
            				set.setSlots(stepSolo.get(player) -1);
            	    		stepSolo.remove(player);
            	    		worldmanager.copyWorld(player.getLevel().getFolderName(), "/worlds/", "/arenas/");
            	    		registerArena(set.getName(), new Arena(this, set.getName(), set));
            	    		setup.remove(player);
            			}
    				}
        			
        		} else {
        			((Player) sender).sendMessage(LanguageManager.translate("perms", ((Player) sender), new String[0]));
        		}
        			
    		}
    		
    		break;
    	}
    	return false;
    }
    


}
