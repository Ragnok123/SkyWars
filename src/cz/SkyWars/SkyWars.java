package cz.SkyWars;

import cz.SkyWars.Arena.SoloArena;
import cz.SkyWars.Arena.TeamArena;
import cz.SkyWars.Manager.*;
import cz.SkyWars.Manager.Kits.*;
import cz.SkyWars.Manager.WorldManager.*;
import cz.SkyWars.MySQL;
import cz.SkyWars.Actions.*;
import cz.SkyWars.JoinTask;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
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
import cn.nukkit.event.server.QueryRegenerateEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.particle.FloatingTextParticle;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.PlayerActionPacket;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.TextFormat;

import java.util.*;
import java.util.Map.Entry;

public class SkyWars extends PluginBase implements Listener
{

	public static SkyWars instance;
	public MySQL mysql;
	public KitManager kitmanager;
	public StatsManager statsmanager;

	public Position lobbyXYZ;

	public SoloArena sw1;
	public SoloArena sw2;
	public SoloArena sw3;
	public SoloArena sw4;
	public SoloArena sw5;
	
	public HashMap<String, Player> npcremoving = new HashMap<String, Player>();
	public HashMap<String, Player> lobbyplayers = new HashMap<String, Player>();
	public HashMap<String, HashMap<String, Double>> mapsdata = new HashMap<String, HashMap<String, Double>>();

	@Override
	public void onLoad()
	{
		LanguageManager.initEnglishMsg();
		LanguageManager.initCzechMsg();
		initializeArrays();	
		
	}
	
	
	@Override
    public void onEnable()
	{ 
		instance = this;
		getServer().getPluginManager().registerEvents(this, this);
		mysql = new MySQL(this);
		mysql.connect();
		kitmanager = new KitManager(this);
		statsmanager = new StatsManager(this);
		
		/* Positions */
		HashMap<String, Double> worldsw1 = mapsdata.get("world_sw1");
    	HashMap<String, Double> worldsw2 = mapsdata.get("Skywars_ScienceFiction");
    	HashMap<String, Double> worldsw3 = mapsdata.get("HypixelMap1");
    	HashMap<String, Double> worldsw5 = mapsdata.get("HypixelMap3");
		sw1 = new SoloArena(this, "LW-1", "world_sw1", 93.0, 29.0, 175.0, worldsw1, 8);
		sw2 = new SoloArena(this, "LW-2", "Skywars_ScienceFiction", 92.0, 29.0, 175.0, worldsw2, 8);
		sw3 = new SoloArena(this, "LW-3", "HypixelMap1", 91.0, 29.0, 175.0, worldsw3, 8);
		sw5 = new SoloArena(this, "LW-5", "HypixelMap3", 89.0, 29.0, 175.0, worldsw5, 8);
		getServer().getPluginManager().registerEvents((Listener) sw1, this);
		getServer().getPluginManager().registerEvents((Listener) sw2, this);
		getServer().getPluginManager().registerEvents((Listener) sw3, this);
		getServer().getPluginManager().registerEvents((Listener) sw5, this);
		lobbyXYZ = new Position(getServer().getDefaultLevel().getSafeSpawn().getX(), getServer().getDefaultLevel().getSafeSpawn().getY() + 4.00, getServer().getDefaultLevel().getSafeSpawn().getZ(), getServer().getLevelByName("swhub"));
		getServer().loadLevel("world_sw1");
		getServer().loadLevel("Skywars_ScienceFiction");
		getServer().loadLevel("HypixelMap1");
		getServer().loadLevel("HypixelMap3");
		getServer().getLevelByName("world_sw1").setTime(6000);
		getServer().getLevelByName("world_sw1").stopTime();
		getServer().getLevelByName("Skywars_ScienceFiction").setTime(6000);
		getServer().getLevelByName("Skywars_ScienceFiction").stopTime();
		getServer().getLevelByName("HypixelMap1").setTime(6000);
		getServer().getLevelByName("HypixelMap1").stopTime();
		getServer().getLevelByName("HypixelMap3").setTime(6000);
		getServer().getLevelByName("HypixelMap3").stopTime();
		getServer().getScheduler().scheduleDelayedTask(new RestartTask(this), 72000);
	}
	
	

	
	
    
    public void initializeArrays()
    {
    	mapsdata.put("world_sw1", new HashMap<String, Double>());
    	mapsdata.put("Skywars_ScienceFiction", new HashMap<String, Double>());
    	mapsdata.put("HypixelMap1", new HashMap<String, Double>());
    	mapsdata.put("HypixelMap2", new HashMap<String, Double>());
    	mapsdata.put("HypixelMap3", new HashMap<String, Double>());
    	HashMap<String, Double> worldsw1 = mapsdata.get("world_sw1");
    	HashMap<String, Double> worldsw2 = mapsdata.get("Skywars_ScienceFiction");
    	HashMap<String, Double> worldsw3 = mapsdata.get("HypixelMap1");
    	HashMap<String, Double> worldsw4 = mapsdata.get("HypixelMap2");
    	HashMap<String, Double> worldsw5 = mapsdata.get("HypixelMap3");
    	worldsw1.put("pos1X", 371.55);
    	worldsw1.put("pos1Y", 67.0);
    	worldsw1.put("pos1Z", 158.08);
    	worldsw1.put("pos2X", 352.3);
    	worldsw1.put("pos2Y", 61.0);
    	worldsw1.put("pos2Z", 187.45);
    	worldsw1.put("pos3X", 371.55);
    	worldsw1.put("pos3Y", 67.0);
    	worldsw1.put("pos3Z", 215.67);
    	worldsw1.put("pos4X", 400.4);
    	worldsw1.put("pos4Y", 61.0);
    	worldsw1.put("pos4Z", 236.66);
    	worldsw1.put("pos5X", 430.47);
    	worldsw1.put("pos5Y", 67.0);
    	worldsw1.put("pos5Z", 216.15);
    	worldsw1.put("pos6X", 448.67);
    	worldsw1.put("pos6Y", 61.0);
    	worldsw1.put("pos6Z", 189.45);
    	worldsw1.put("pos7X", 420.52);
    	worldsw1.put("pos7Y", 67.0);
    	worldsw1.put("pos7Z", 157.61);
    	worldsw1.put("pos8X", 403.57);
    	worldsw1.put("pos8Y", 61.0);
    	worldsw1.put("pos8Z", 138.49);
    	
    	worldsw2.put("pos1X", -0.31);
    	worldsw2.put("pos1Y", 96.0);
    	worldsw2.put("pos1Z", 1.46);
    	worldsw2.put("pos2X", -55.50);
    	worldsw2.put("pos2Y", 95.0);
    	worldsw2.put("pos2Z", -29.63);
    	worldsw2.put("pos3X", -62.97);
    	worldsw2.put("pos3Y", 95.0);
    	worldsw2.put("pos3Z", 1.54);
    	worldsw2.put("pos4X", -31.23);
    	worldsw2.put("pos4Y", 95.0);
    	worldsw2.put("pos4Z", 55.41);
    	worldsw2.put("pos5X", -0.57);
    	worldsw2.put("pos5Y", 95.0);
    	worldsw2.put("pos5Z", 63.45);
    	worldsw2.put("pos6X", 54.68);
    	worldsw2.put("pos6Y", 95.0);
    	worldsw2.put("pos6Z", 32.67);
    	worldsw2.put("pos7X", 61.58);
    	worldsw2.put("pos7Y", 95.0);
    	worldsw2.put("pos7Z", 92.24);
    	worldsw2.put("pos8X", 30.23);
    	worldsw2.put("pos8Y", 95.0);
    	worldsw2.put("pos8Z", -53.18);
    	
    	worldsw3.put("pos1X", 26.82);
    	worldsw3.put("pos1Y", 77.0);
    	worldsw3.put("pos1Z", 16.51);
    	worldsw3.put("pos2X", 23.28);
    	worldsw3.put("pos2Y", 77.0);
    	worldsw3.put("pos2Z", 3.29);
    	worldsw3.put("pos3X", 1.4);
    	worldsw3.put("pos3Y", 77.0);
    	worldsw3.put("pos3Z", -11.31);
    	worldsw3.put("pos4X", -12.87);
    	worldsw3.put("pos4Y", 77.0);
    	worldsw3.put("pos4Z", -6.42);
    	worldsw3.put("pos5X", -26.28);
    	worldsw3.put("pos5Y", 77.0);
    	worldsw3.put("pos5Z", 16.5);
    	worldsw3.put("pos6X", -21.7);
    	worldsw3.put("pos6Y", 77.0);
    	worldsw3.put("pos6Z", 29.64);
    	worldsw3.put("pos7X", -0.09);
    	worldsw3.put("pos7Y", 77.0);
    	worldsw3.put("pos7Z", 43.36);
    	worldsw3.put("pos8X", 13.25);
    	worldsw3.put("pos8Y", 77.0);
    	worldsw3.put("pos8Z", 39.54);
    	
    	worldsw4.put("pos1X", -49.4);
    	worldsw4.put("pos1Y", 100.0);
    	worldsw4.put("pos1Z", -34.1);
    	worldsw4.put("pos2X", -73.85);
    	worldsw4.put("pos2Y", 100.0);
    	worldsw4.put("pos2Z", -13.34);
    	worldsw4.put("pos3X", -73.89);
    	worldsw4.put("pos3Y", 100.0);
    	worldsw4.put("pos3Z", 2.44);
    	worldsw4.put("pos4X", -63.29);
    	worldsw4.put("pos4Y", 100.0);
    	worldsw4.put("pos4Z", 19.32);
    	worldsw4.put("pos5X", 61.95);
    	worldsw4.put("pos5Y", 100.0);
    	worldsw4.put("pos5Z", 22.89);
    	worldsw4.put("pos6X", 87.21);
    	worldsw4.put("pos6Y", 100.0);
    	worldsw4.put("pos6Z", 1.79);
    	worldsw4.put("pos7X", 87.51);
    	worldsw4.put("pos7Y", 100.0);
    	worldsw4.put("pos7Z", -13.53);
    	worldsw4.put("pos8X", 61.53);
    	worldsw4.put("pos8Y", 100.0);
    	worldsw4.put("pos8Z", -34.47);
    
    	worldsw5.put("pos1X", -10.4);
    	worldsw5.put("pos1Y", 81.0);
    	worldsw5.put("pos1Z", 43.73);
    	worldsw5.put("pos2X", 6.11);
    	worldsw5.put("pos2Y", 81.0);
    	worldsw5.put("pos2Z", 42.56);
    	worldsw5.put("pos3X", 30.67);
    	worldsw5.put("pos3Y", 81.0);
    	worldsw5.put("pos3Z", 18.77);
    	worldsw5.put("pos4X", 29.33);
    	worldsw5.put("pos4Y", 81.0);
    	worldsw5.put("pos4Z", 2.1);
    	worldsw5.put("pos5X", 5.74);
    	worldsw5.put("pos5Y", 81.0);
    	worldsw5.put("pos5Z", -22.52);
    	worldsw5.put("pos6X", -10.8);
    	worldsw5.put("pos6Y", 81.0);
    	worldsw5.put("pos6Z", -21.61);
    	worldsw5.put("pos7X", -35.7);
    	worldsw5.put("pos7Y", 81.0);
    	worldsw5.put("pos7Z", 2.76);
    	worldsw5.put("pos8X", -34.38);
    	worldsw5.put("pos8Y", 81.0);
    	worldsw5.put("pos8Z", 19.39);
    }

	@EventHandler
	public void dmg(EntityDamageEvent event)
	{
        Entity ent = event.getEntity();
        if (event instanceof EntityDamageByEntityEvent)
		{
			Entity damager = ((EntityDamageByEntityEvent) event).getDamager();
			Player player = (Player) damager;
			if (npcremoving.containsKey(player.getName()))
			{
                ent.close();
                npcremoving.remove(player.getName());
            }
		}
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

	@EventHandler
	public void onJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		event.setJoinMessage("");
		if (!statsmanager.checkAcc(player.getName()))
		{
            statsmanager.createData(player.getName());
            Server.getInstance().getLogger().info("�eSW> �aHr�� �b" + player.getName() + " �anen� zaps�n v datab�zi. Vytv���m z�znam...");
		}
		else
		{
            Server.getInstance().getLogger().info("�eSW> �aHr�� �b" + player.getName() + " �aje zaps�n v datab�zi statistik, v�echno je ok");
		}
		getServer().getScheduler().scheduleDelayedTask(new JoinTask(this, player), 20);
		FloatingTextParticle floatparticle = new FloatingTextParticle(new Vector3(97.00, 30.00, 156.00), "�aNickname: �f " + player.getName() + "\n�aKills: �f" + statsmanager.getKills(player.getName()) + "\n�aDeaths: �f" + statsmanager.getDeaths(player.getName()) + "\n�aWins: �f" + statsmanager.getWins(player.getName()) + "\n", "�l�b[�eLuckyWars �bstats]");
		FloatingTextParticle floatparticle1 = new FloatingTextParticle(new Vector3(91.00, 30.00, 164.00), "   �eLuckyWars beta\n   �eLuckyWars �bTeam �c(not avaible)", "�l�ePlay");
		FloatingTextParticle floatparticle2 = new FloatingTextParticle(new Vector3(101.00, 30.00, 173.00), "�a - Fixed trouble with teleport\n�a - Fixed block breaking before start\n�a -Uncoming changes:\n�c  - Fix positions in arena LW-2", "�eChangelog 5.7.2017");
		getServer().getDefaultLevel().addParticle(floatparticle, player);
		getServer().getDefaultLevel().addParticle(floatparticle1, player);
		getServer().getDefaultLevel().addParticle(floatparticle2, player);
	}

    public static SkyWars getInstance()
	{
		return instance;
	}
    
    @EventHandler
    public void handleHunger(PlayerFoodLevelChangeEvent event)
    {
    	Player player = event.getPlayer();
    	if(player.getLevel() == getServer().getDefaultLevel() && lobbyplayers.containsKey(player.getName()))
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
    
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
    	switch(command.getName()) 
    	{
    	case "sw":
    		if(((Player) sender).isOp())
    		{
    			if(args[0].equals("start"))
    			{
    				if(args[1].equals("sw1"))
    				{
    					sw1.gameStatus = 1;
    					sw1.lastTime = 5;
    				}
    				if(args[1].equals("sw2"))
    				{
    					sw2.gameStatus = 1;
    					sw2.lastTime = 5;
    				}
    				if(args[1].equals("sw3"))
    				{
    					sw3.gameStatus = 1;
    					sw3.lastTime = 5;
    				}
    				if(args[1].equals("sw4"))
    				{
    					sw4.gameStatus = 1;
    					sw4.lastTime = 5;
    				}
    				if(args[1].equals("sw5"))
    				{
    					sw5.gameStatus = 1;
    					sw5.lastTime = 5;
    				}
    			
    			}
    			if(args[0].equals("stop"))
    			{
    				if(args[1].equals("sw1"))
    				{
    					sw1.gameStatus = 4;
    					sw1.lastTime = 5;
    				}
    				if(args[1].equals("sw2"))
    				{
    					sw2.gameStatus = 4;
    					sw2.lastTime = 5;
    				}
    				if(args[1].equals("sw3"))
    				{
    					sw3.gameStatus = 4;
    					sw3.lastTime = 5;
    				}
    				if(args[1].equals("sw4"))
    				{
    					sw4.gameStatus = 4;
    					sw4.lastTime = 5;
    				}
    				if(args[1].equals("sw5"))
    				{
    					sw5.gameStatus = 4;
    					sw5.lastTime = 5;
    				}
    			
    			}
    		} else {
    			((Player) sender).sendMessage(LanguageManager.translate("perms", ((Player) sender), new String[0]));
    		}
    			
    		break;
    	}
    	return false;
    }
    


}
