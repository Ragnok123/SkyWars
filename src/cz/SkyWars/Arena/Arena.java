package cz.SkyWars.Arena;



import cz.SkyWars.SkyWars;
import cz.SkyWars.Arena.Timer.*;
import cz.SkyWars.Manager.*;
import cz.SkyWars.Manager.WorldManager.ArenaWorldManager;
import cz.SkyWars.Manager.Kits.*;
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
	public int finalWeight = 0;

	public Random random;
	public HashMap<Integer, Weight> armor1 = new HashMap<Integer, Weight>();
	public HashMap<Integer, Weight> armor2 = new HashMap<Integer, Weight>();
	public HashMap<Integer, Weight> armor3 = new HashMap<Integer, Weight>();
	public HashMap<Integer, Weight> armor4 = new HashMap<Integer, Weight>();
	public HashMap<Integer, Weight> sword1 = new HashMap<Integer, Weight>();
	public HashMap<Integer, Weight> itemarray = new HashMap<Integer, Weight>();
	public HashMap<Integer, Weight> blockarray = new HashMap<Integer, Weight>();

	public HashMap<String, Player> arenaplayers = new HashMap<String, Player>();

	public HashMap<String, Player> buyingKit = new HashMap<String, Player>();

	public HashMap<String, Player> nopage = new HashMap<String, Player>();
	public HashMap<String, Player> firstpage = new HashMap<String, Player>();
	public HashMap<String, Player> secondpage = new HashMap<String, Player>();
	
	public HashMap<String, Double> arenadata;


	public Arena(SkyWars skywars, String arenaname, ArenaSettings settings)
	{
		random = new Random();
        this.skywars = skywars;
        this.arenaname = arenaname;
		this.waitTime = 60;
		this.godTime = 0;
		this.gameTime = 150;
		this.endTime = 150;
		this.lastTime = 0;
		this.gameStatus = 0;
        worldmanager = new ArenaWorldManager(this);
        worldmanager.restartArena(worldname);
        Server.getInstance().getScheduler().scheduleRepeatingTask(new SoloArenaTimer(this, arenaname, worldname, signX, signY, signZ, maxPlayerCount), 20);
	}


	/*
	public void initializeArrays()
	{
		armor1.put(298, new Weight(20, 1, 1));
		armor1.put(302, new Weight(15, 1, 1));
		armor1.put(306, new Weight(8, 1, 1));
		armor1.put(310, new Weight(2, 1, 1));
		armor1.put(314, new Weight(10, 1, 1));

		armor2.put(299, new Weight(20, 1, 1));
		armor2.put(303, new Weight(15, 1, 1));
		armor2.put(307, new Weight(8, 1, 1));
		armor2.put(311, new Weight(2, 1, 1));
		armor2.put(315, new Weight(10, 1, 1));

		armor3.put(300, new Weight(20, 1, 1));
		armor3.put(304, new Weight(15, 1, 1));
		armor3.put(308, new Weight(8, 1, 1));
		armor3.put(312, new Weight(2, 1, 1));
		armor3.put(316, new Weight(10, 1, 1));

		armor4.put(301, new Weight(20, 1, 1));
		armor4.put(305, new Weight(15, 1, 1));
		armor4.put(309, new Weight(8, 1, 1));
		armor4.put(313, new Weight(2, 1, 1));
		armor4.put(317, new Weight(10, 1, 1));

		sword1.put(267, new Weight(10, 1, 2));
		sword1.put(268, new Weight(20, 1, 2));
		sword1.put(272, new Weight(17, 1, 2));
		sword1.put(276, new Weight(5, 1, 2));

		itemarray.put(258, new Weight(30, 1, 1));
		itemarray.put(261, new Weight(28, 1, 1));
		itemarray.put(262, new Weight(54, 1, 18));
		itemarray.put(260, new Weight(43, 1, 10));
		itemarray.put(264, new Weight(13, 1, 1));
		itemarray.put(275, new Weight(25, 1, 1));
		itemarray.put(332, new Weight(39, 1, 16));

		blockarray.put(3, new Weight(50, 32, 64));
		blockarray.put(4, new Weight(50, 32, 64));
		blockarray.put(5, new Weight(50, 32, 64));
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

	public Item getLegginsItem()
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
		int num = random.nextInt(finalWeight);
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
		int num = random.nextInt(finalWeight);
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
	*/
	
	public void signChange(String arenaname, double signX, double signY, double signZ, int maxPlayerCount)
	{
		if(this.gameStatus < 2)
		{
			BlockEntitySign sign = (BlockEntitySign)skywars.getServer().getDefaultLevel().getBlockEntity(new Vector3(signX, signY, signZ));
			sign.setText("�e[LuckyWars]", "�a" + this.arenaname + "", "�e" + String.valueOf(arenaplayers.size()) + "/" + String.valueOf(maxPlayerCount) + "", "�aJOIN");
			
		}
		if(this.gameStatus > 2)
		{
			BlockEntitySign sign = (BlockEntitySign)skywars.getServer().getDefaultLevel().getBlockEntity(new Vector3(signX, signY, signZ));
			sign.setText("�e[LuckyWars]", "�a" + this.arenaname + "", "�e" + String.valueOf(arenaplayers.size()) + "/" + String.valueOf(maxPlayerCount) + "", "�cRUNNING");
			
		}
		
	}

    public void gameTimer(String arenaname, String worldname, double signX, double signY, double signZ, int maxPlayerCount)
	{
    	signChange(arenaname, signX, signY, signZ, maxPlayerCount);
		String hraci = String.valueOf(arenaplayers.size());
		if (this.gameStatus == 0)
		{
			if (arenaplayers.size() > 2)
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
			switch (this.lastTime)
			{

				case 1:
					for (Player ingame : arenaplayers.values())
					{
						ingame.sendPopup("�aGame starts in �b" + this.lastTime + " �aseconds");
						ingame.sendTitle("�b1");
					}
					break;
				case 2:
					for (Player ingame : arenaplayers.values())
					{
						ingame.sendPopup("�aGame starts in �b" + this.lastTime + " �aseconds");
						ingame.sendTitle("�b2");
					}
					break;
				case 3:
					for (Player ingame : arenaplayers.values())
					{
						ingame.sendPopup("�aGame starts in �b" + this.lastTime + " �aseconds");
						ingame.sendTitle("�b3");
					}
					break;
				case 4:
					for (Player ingame : arenaplayers.values())
					{
						ingame.sendPopup("�aGame starts in �b" + this.lastTime + " �aseconds");
						ingame.sendTitle("�b4");
					}
					break;
				case 5:
					for (Player ingame : arenaplayers.values())
					{
						ingame.sendPopup("�aGame starts in �b" + this.lastTime + " �aseconds"); 
						ingame.sendTitle("�b5");
					}
					break;
				case 6:
				case 7:
				case 8:
				case 9:
					for (Player ingame : arenaplayers.values())
					{
						ingame.sendPopup("�aGame starts in �b" + this.lastTime + " �aseconds");
					}
					break;
				case 10:
					for (Player ingame : arenaplayers.values())
					{
						ingame.sendPopup("�aGame starts in �b" + this.lastTime + " �aseconds");
						ingame.sendTitle("�b10");
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
						ingame.sendPopup("�aGame starts in �b" + this.lastTime + " �aseconds");
					}
					break;
				case 60:
					for (Player ingame : arenaplayers.values())
					{
						ingame.sendPopup("�aGame starts in �b" + this.lastTime + " �aseconds");
					}
					break;
				case 0:
					this.gameStatus = 2;
					this.lastTime = this.godTime;
					Server.getInstance().getLogger().info("Game started on arena " + this.arenaname);
					for (Player ingame : arenaplayers.values())
					{
						ingame.sendMessage(LanguageManager.translate("sw_solo_game_started", ingame, new String[0]));
						ingame.sendMessage(LanguageManager.translate("sw_solo_list_players", ingame, String.valueOf(arenaplayers.size())));
						ingame.sendTitle("�bGame started");
						ingame.getInventory().clearAll();
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
				ingame.sendPopup("�aPlayers: " + arenaplayers.size());
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
						pla.getInventory().clearAll();
						skywars.lobbyplayers.put(pla.getName(), pla);
						pla.teleport(skywars.lobbyXYZ);
						pla.getFoodData().setLevel(20);
						skywars.kitmanager.setKit(pla.getName(), "any_kit");
						skywars.statsmanager.addWins(pla.getName(), 1); 
						Server.getInstance().broadcastMessage("�eSkyWars> " + pla.getDisplayName() + " �awon the game on arena �b" + this.arenaname + "");

						Server.getInstance().unloadLevel(Server.getInstance().getLevelByName(worldname));
						worldmanager.restartArena(worldname);
						Server.getInstance().loadLevel(worldname);
						Server.getInstance().getLevelByName(worldname).setTime(6000);
						Server.getInstance().getLevelByName(worldname).stopTime();
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
				case 100:
				case 120:
				case 150:
					for (Player ingame : arenaplayers.values())
					{
						ingame.sendMessage(LanguageManager.translate("sw_solo_time", ingame, String.valueOf(this.lastTime)));
					}
					break;
				case 0:
					for (Player pla : arenaplayers.values())
					{
						pla.getInventory().clearAll();
						skywars.lobbyplayers.put(pla.getName(), pla);
						pla.teleport(skywars.lobbyXYZ);
						pla.getFoodData().setLevel(20);
						skywars.kitmanager.setKit(pla.getName(), "any_kit");
						skywars.statsmanager.addWins(pla.getName(), 1); 
						Server.getInstance().broadcastMessage("�eSkyWars> �aNoone didnt won the game on arena �b" + this.arenaname + "");
						Server.getInstance().unloadLevel(Server.getInstance().getLevelByName(worldname));
						worldmanager.restartArena(worldname);
						Server.getInstance().loadLevel(worldname);
						Server.getInstance().getLevelByName(worldname).setTime(6000);
						Server.getInstance().getLevelByName(worldname).stopTime();
						this.gameStatus = 0;
						this.lastTime = 0;

					}
					break;
			}
		}
	}

    @EventHandler
    public void handleInteractForJoiningToArena(PlayerInteractEvent event)
	{
    	Position pos1 = new Position(this.arenadata.get("pos1X"), this.arenadata.get("pos1Y"), this.arenadata.get("pos1Z"), Server.getInstance().getLevelByName(this.worldname));
    	Position pos2 = new Position(this.arenadata.get("pos2X"), this.arenadata.get("pos2Y"), this.arenadata.get("pos2Z"), Server.getInstance().getLevelByName(this.worldname));
    	Position pos3 = new Position(this.arenadata.get("pos3X"), this.arenadata.get("pos3Y"), this.arenadata.get("pos3Z"), Server.getInstance().getLevelByName(this.worldname));
    	Position pos4 = new Position(this.arenadata.get("pos4X"), this.arenadata.get("pos4Y"), this.arenadata.get("pos4Z"), Server.getInstance().getLevelByName(this.worldname));
    	Position pos5 = new Position(this.arenadata.get("pos5X"), this.arenadata.get("pos5Y"), this.arenadata.get("pos5Z"), Server.getInstance().getLevelByName(this.worldname));
    	Position pos6 = new Position(this.arenadata.get("pos6X"), this.arenadata.get("pos6Y"), this.arenadata.get("pos6Z"), Server.getInstance().getLevelByName(this.worldname));
    	Position pos7 = new Position(this.arenadata.get("pos7X"), this.arenadata.get("pos7Y"), this.arenadata.get("pos7Z"), Server.getInstance().getLevelByName(this.worldname));
    	Position pos8 = new Position(this.arenadata.get("pos8X"), this.arenadata.get("pos8Y"), this.arenadata.get("pos8Z"), Server.getInstance().getLevelByName(this.worldname));
		Player player = event.getPlayer();
		Block block = event.getBlock();
		if (block.getX() == this.signX && block.getY() == this.signY && block.getZ() == this.signZ)
		{
			skywars.lobbyplayers.remove(player.getName());
		}
    }

	@EventHandler
	public void handleLuckyBlockBreak(BlockBreakEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getBlock();
		if(this.gameStatus > 2)
		{
			if(block.getId() == 19)
			{
				if(player.getLevel() == Server.getInstance().getLevelByName(this.worldname))
				{
					event.setDrops(new Item[]{});
					new RandomAction(player);
				}
			}
		} else {
			if(player.getLevel() == Server.getInstance().getLevelByName(this.worldname))
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
			if (this.gameStatus < 2)
			{
				event.setCancelled();
            }
			if (this.gameStatus > 2)
			{
				if (player.getY() < 10)
				{
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
        if (playerEntity instanceof Player)
        {
        	if ((playerEntity.getHealth() - event.getDamage()) < 1)
        	{
        		for (Player ingames : arenaplayers.values())
        		{
				ingames.sendMessage(LanguageManager.translate("sw_solo_all_death", ingames, playerEntity.getName()));
				skywars.statsmanager.addDeaths(playerEntity.getName(), 1);
        		}
        	}
        }
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
					ingame.sendMessage(LanguageManager.translate("sw_solo_all_death_cause_kill", ingame, hitnutyHrac.getName(), player.getName()));

					skywars.statsmanager.addKills(player.getName(), 1);
					skywars.statsmanager.addDeaths(hitnutyHrac.getName(), 1);
            		}
            	} 
            }
		}
	} 

    
    @EventHandler(ignoreCancelled=true)
    public void handleChat(PlayerChatEvent event)
    {
    	Player player = event.getPlayer();
    	if (arenaplayers.containsKey(player.getName())) 
    	{
    		event.setCancelled();
    		//sendMessageToAll(event.getMessage(), player);
    	}
    }
    
    public void sendMessageToAll(String message, Player player)
    {
    	for(Player ingame : arenaplayers.values())
    	{
    		ingame.sendMessage("�b[�a" + this.arenaname + "�b] �r" + player.getDisplayName() + ": " + message);
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
		if (arenaplayers.containsKey(player.getName()))
		{
			if(item.getCustomName().equals("�eBack to lobby"))
			{
				
			}
		}
    }
	
	
	


}
