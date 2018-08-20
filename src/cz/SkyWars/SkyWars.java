package cz.SkyWars;

import cz.SkyWars.Arena.Arena;
import cz.SkyWars.Arena.ArenaSettings;
import cz.SkyWars.Manager.*;
import cz.SkyWars.Manager.WorldManager.*;
import cz.SkyWars.MySQL;
import cz.SkyWars.Actions.*;
import cz.SkyWars.JoinTask;
import cz.SkyWars.Database.*;

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
import java.util.*;
import java.util.Map.Entry;

public class SkyWars extends PluginBase implements Listener
{

	public static SkyWars instance;
	public MySQL mysql;
	public Position lobbyXYZ;
	public Position hologramXYZ;
	public Database database;

	public HashMap<String, Arena> arenas = new HashMap<String, Arena>();
	public Config settingsc;
	public Config arenass;
	public Config cplayers;
	
	public HashMap<Player, ArenaSettings> setup = new HashMap<Player, ArenaSettings>();
	public HashMap<Player, Integer> step = new HashMap<Player, Integer>();
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
			settingsc.set("economyapi-enabled", false);
			settingsc.set("customeconomy-enabled", false);
			settingsc.set("joinMessage-enabled", false);
			settingsc.set("quitMessage-enabled", false);
			settingsc.set("hologram-enabled", false);
			settingsc.set("hologramX", getServer().getDefaultLevel().getSafeSpawn().getX());
			settingsc.set("hologramY", getServer().getDefaultLevel().getSafeSpawn().getY() + 1.5);
			settingsc.set("hologramZ", getServer().getDefaultLevel().getSafeSpawn().getZ());
			settingsc.set("hologramWorld", getServer().getDefaultLevel().getName());
			
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
		
		arenass = new Config(getDataFolder() + "/arenas.yml", Config.YAML);
		cplayers = new Config(getDataFolder() + "/players.yml", Config.YAML);
		/* Positions */
		String world =  (String) settingsc.getString("lobbyWorld") ;
		lobbyXYZ = new Position((double)settingsc.get("lobbyX"), (double)settingsc.get("lobbyY"), (double)settingsc.get("lobbyZ"));
		lobbyXYZ.setLevel(getServer().getLevelByName(world));
		hologramXYZ = new Position((double)settingsc.get("lobbyX"), (double)settingsc.get("lobbyY"), (double)settingsc.get("lobbyZ"));
		String world1 =  (String) settingsc.getString("hologramWorld") ;
		hologramXYZ.setLevel(getServer().getLevelByName(world1));
		getServer().getScheduler().scheduleDelayedTask(new StartupTask(this), 20);
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
    	Server.getInstance().getPluginManager().registerEvents(arena, this);
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
	
	public int getReward() {
		return (int) settingsc.get("reward-amount");
	}
	
	@EventHandler
	public void antiPvp(EntityDamageEvent event)
	{
		Player player = (Player) event.getEntity();
		if(player.getLevel() == getServer().getDefaultLevel()){
			{
				event.setCancelled();
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
    	antiDupe.put(player, System.currentTimeMillis()+1000);
    	if(antiDupe.get(player) >= System.currentTimeMillis()) {
    		antiDupe.remove(player);
    		antiDupe.put(player, System.currentTimeMillis()+100);
    	}
    	if(setup.containsKey(player)) {
    		ArenaSettings settings = setup.get(player);
    		int currentStep = step.get(player);
    			if (currentStep == 0) {
    				if(block instanceof BlockSignPost || block instanceof BlockWallSign) {
            			player.sendMessage(LanguageManager.translate("arena_click", player, new String[0]));
            			settings.setSign(block, block.x, block.y, block.z, block.getLevel().getName());
            			settings.fakeBlock = block;
            			settings.fakeLevel1 = player.getLevel().getName();
            			resetStep(player);
            		}
				} else if (currentStep > 0) {
					settings.setPositions(currentStep, block.x, block.y, block.z, block.getLevel().getName());
	        		player.sendMessage(LanguageManager.translate("arena_click", player, new String[0]));
	        		settings.f(block.getLevel().getName());
	        		settings.positions.add(new Position(block.x, block.y + 1, block.z, block.getLevel()));
	        		resetStep(player);
				} 
    		
    	}
    }
    
    public void resetStep(Player player) {
    	ArenaSettings set = setup.get(player);
    	int currentStep = step.get(player);
    	step.remove(player);
    	step.put(player, currentStep + 1);
    }
    
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
    	Player player = (Player)sender;
    	switch(command.getName()) 
    	{
    	case "sw":
    		if(((Player) sender).isOp())
    		{
    			if(args[0].equals("create")) {
    				String arenaname = (String) args[1];
    				ArenaSettings set = new ArenaSettings(arenaname, true);
    				setup.put(player, set);
    				step.put(player, 0);
    				set.setSettings(5);
    				player.sendMessage(LanguageManager.translate("arena_sign", ((Player) sender), new String[0]));
    			}
    			if(args[0].equals("finish")) {
    				player.sendMessage(LanguageManager.translate("arena_finish", player, new String[0]));
    				ArenaSettings set = setup.get(player);
    				set.setSlots(step.get(player) -1);
    	    		step.remove(player);
    	    		registerArena(set.getName(), new Arena(this, set.getName(), set));
    	    		setup.remove(player);
    			}
    		} else {
    			((Player) sender).sendMessage(LanguageManager.translate("perms", ((Player) sender), new String[0]));
    		}
    			
    		break;
    	}
    	return false;
    }
    


}
