package cz.SkyWars.Arena;


import cz.SkyWars.SkyWars;
import cz.SkyWars.Arena.TeamPlayers;
import cz.SkyWars.Arena.Timer.TeamArenaTimer;
import cz.SkyWars.Manager.*;
import cz.SkyWars.Manager.WorldManager.ArenaWorldManager;
import cz.SkyWars.Manager.Kits.*;
import cz.SkyWars.Actions.RandomAction;
import cz.SkyWars.Actions.RewardAction;

import cn.nukkit.block.*;
import cn.nukkit.blockentity.*;
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
import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.entity.*;

import java.util.*;

public class TeamArena implements Listener
{

	public TeamPlayers swplayers;
	public SkyWars skywars;
	public ArenaWorldManager worldmanager;
	public String arenaname;
	public String worldname;

	public double signX;
	public double signY;
	public double signZ;

	public double spawnX;
	public double spawnY;
	public double spawnZ;

	public Position redPos;
	public Position bluePos;
	public Position greenPos;
	public Position yellowPos;

	public Team blueteam;
	public Team redteam;
	public Team greenteam;
	public Team yellowteam;

	public int waitTime;
	public int godTime;
	public int gameTime;
	public int endTime;
	public int lastTime;
	public int gameStatus;
	public int finalWeight = 0;

	public HashMap<String, Player> waitplayers = new HashMap<String, Player>();
	public HashMap<String, Player> arenaplayers = new HashMap<String, Player>();

	public TeamArena(SkyWars skywars, String arenaname, String worldname, double signX, double signY, double signZ, double spawnX, double spawnY, double spawnZ, Position redPos, Position bluePos, Position greenPos, Position yellowPos)
	{
		this.skywars = skywars;
		this.arenaname = arenaname;
		this.worldname = worldname;
		this.signX = signX;
		this.signY = signY;
		this.signZ = signZ;
		this.spawnX = spawnX;
		this.spawnY = spawnY;
		this.spawnZ = spawnZ;
		this.redPos = redPos;
		this.bluePos = bluePos;
		this.greenPos = greenPos;
		this.yellowPos = yellowPos;
		swplayers = new TeamPlayers(this, arenaname, worldname);
		redteam = new Team(this, redPos, 0, "red");
		blueteam = new Team(this, bluePos, 1, "blue");
		greenteam = new Team(this, greenPos, 2, "green");
		yellowteam = new Team(this, yellowPos, 3, "yellow");
		Server.getInstance().getScheduler().scheduleRepeatingTask(new TeamArenaTimer(this, arenaname, worldname, signX, signY, signZ, 8), 20);
		this.gameStatus = 0;
		this.waitTime = 30;
		this.godTime = 0;
		this.gameTime = 150;
		this.endTime = 150;
		this.lastTime = 0;
    }

	public void gameTimer(String arenaname, String worldname, double signX, double signY, double signZ, int maxPlayerCount)
	{
		if (this.gameStatus == 0)
		{
			if (waitplayers.size() > 1)
			{
				this.gameStatus = 1;
				this.lastTime = this.waitTime;
			}
		}
		if (this.gameStatus == 1)
		{
			
			this.lastTime--;
			BlockEntitySign sign = (BlockEntitySign)skywars.getServer().getDefaultLevel().getBlockEntity(new Vector3(signX, signY, signZ));
			sign.setText("�e�[SkyWars]", "�a" + this.arenaname + "", "�e" + arenaplayers.size() + "/" + maxPlayerCount + "", "�aJOIN");
			switch (this.lastTime)
			{
				case 1:
					for (Player ingame : arenaplayers.values())
					{
						ingame.sendPopup("§aGame starts in §b" + this.lastTime + " §aseconds");
						ingame.sendTitle("§b1");
					}
					break;
				case 2:
					for (Player ingame : arenaplayers.values())
					{
						ingame.sendPopup("§aGame starts in §b" + this.lastTime + " §aseconds");
						ingame.sendTitle("§b2");
					}
					break;
				case 3:
					for (Player ingame : arenaplayers.values())
					{
						ingame.sendPopup("§aGame starts in §b" + this.lastTime + " §aseconds");
						ingame.sendTitle("§b3");
					}
					break;
				case 4:
					for (Player ingame : arenaplayers.values())
					{
						ingame.sendPopup("§aGame starts in §b" + this.lastTime + " §aseconds");
						ingame.sendTitle("§b4");
					}
					break;
				case 5:
					for (Player ingame : arenaplayers.values())
					{
						ingame.sendPopup("§aGame starts in §b" + this.lastTime + " §aseconds"); 
						ingame.sendTitle("§b5");
					}
					break;
				case 6:
				case 7:
				case 8:
				case 9:
					for (Player ingame : arenaplayers.values())
					{
						ingame.sendPopup("§aGame starts in §b" + this.lastTime + " §aseconds");
					}
					break;
				case 10:
					for (Player ingame : arenaplayers.values())
					{
						ingame.sendPopup("§aGame starts in §b" + this.lastTime + " §aseconds");
						ingame.sendTitle("§b10");
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
						ingame.sendPopup("§aGame starts in §b" + this.lastTime + " §aseconds");
					}
					break;
				case 60:
					for (Player ingame : arenaplayers.values())
					{
						ingame.sendPopup("§aGame starts in §b" + this.lastTime + " §aseconds");
					}
					break;
				case 0:
					this.gameStatus = 2;
					this.lastTime = this.godTime;
					Server.getInstance().getLogger().info("Game started on arena" + this.arenaname);
					for (Player ingame : arenaplayers.values())
					{
						String hraci = String.valueOf(arenaplayers.size());
						ingame.sendMessage(LanguageManager.translate("sw_solo_game_started", ingame, new String[0]));
						ingame.sendMessage(LanguageManager.translate("sw_solo_player_list", ingame, hraci));
						ingame.getInventory().clearAll();
					}
					for (Player reds : redteam.teammates.values())
					{
						reds.teleport(redteam.getTeamPosition());
					}
					for (Player blues : blueteam.teammates.values())
					{
						blues.teleport(blueteam.getTeamPosition());
					}
					for (Player greens : greenteam.teammates.values())
					{
						greens.teleport(greenteam.getTeamPosition());
					}
					for (Player yellows : yellowteam.teammates.values())
					{
						yellows.teleport(yellowteam.getTeamPosition());
					}
					waitplayers.clear();
					break;
			}
		}
		if (this.gameStatus == 2)
		{
			this.gameStatus = 3;
			this.lastTime = this.gameTime;
		}
		if (this.gameStatus >= 2)
		{
			if (blueteam.getPlayers() == 0 && greenteam.getPlayers() == 0 && yellowteam.getPlayers() == 0)
			{
				for(Player pla : arenaplayers.values())
				{
					pla.getInventory().clearAll();
					pla.teleport(skywars.lobbyXYZ);
					skywars.kitmanager.setKit(pla.getName(), "any_kit");
					skywars.statsmanager.addWins(pla.getName(), 1);
					new RewardAction(pla, 100, 50);
					worldmanager.restartArena(worldname);
					redteam.removePlayers();
					for(Player reds : redteam.teammates.values())
					{
						reds.setDisplayName(reds.getNameTag());
					}
				}
			}
			if (redteam.getPlayers() == 0 && greenteam.getPlayers() == 0 && yellowteam.getPlayers() == 0)
			{
				for(Player pla : arenaplayers.values())
				{
					pla.getInventory().clearAll();
					pla.teleport(skywars.lobbyXYZ);
					skywars.kitmanager.setKit(pla.getName(), "any_kit");
					skywars.statsmanager.addWins(pla.getName(), 1);
					new RewardAction(pla, 100, 50);
					worldmanager.restartArena(worldname);
					blueteam.removePlayers();
					for(Player blues : blueteam.teammates.values())
					{
						blues.setDisplayName(blues.getNameTag());
					}
				}
			}
			if (blueteam.getPlayers() == 0 && redteam.getPlayers() == 0 && yellowteam.getPlayers() == 0)
			{
				for(Player pla : arenaplayers.values())
				{
					pla.getInventory().clearAll();
					pla.teleport(skywars.lobbyXYZ);
					skywars.kitmanager.setKit(pla.getName(), "any_kit");
					skywars.statsmanager.addWins(pla.getName(), 1);
					new RewardAction(pla, 100, 50);
					worldmanager.restartArena(worldname);
					greenteam.removePlayers();
					for(Player reds : greenteam.teammates.values())
					{
						reds.setDisplayName(reds.getNameTag());
					}
				}
			}
			if (blueteam.getPlayers() == 0 && greenteam.getPlayers() == 0 && redteam.getPlayers() == 0)
			{
				for(Player pla : arenaplayers.values())
				{
					pla.getInventory().clearAll();
					pla.teleport(skywars.lobbyXYZ);
					skywars.kitmanager.setKit(pla.getName(), "any_kit");
					skywars.statsmanager.addWins(pla.getName(), 1);
					new RewardAction(pla, 100, 50);
					worldmanager.restartArena(worldname);
					yellowteam.removePlayers();
					for(Player reds : yellowteam.teammates.values())
					{
						reds.setDisplayName(reds.getNameTag());
					}
				}
			}
		}
		if (this.gameStatus == 3)
		{
			BlockEntitySign sign = (BlockEntitySign)skywars.getServer().getDefaultLevel().getBlockEntity(new Vector3(signX, signY, signZ));
			sign.setText("�e�[SkyWars]", "�a" + this.arenaname + "", "�e" + arenaplayers.size() + "/" + maxPlayerCount + "", "�aJOIN");
			
			this.lastTime--;
			switch (this.lastTime)
			{
				case 0:
					this.gameStatus = 4;
					this.lastTime = this.endTime;
					break;
			}
		}
		if(this.gameStatus == 4)
		{
			BlockEntitySign sign = (BlockEntitySign)skywars.getServer().getDefaultLevel().getBlockEntity(new Vector3(signX, signY, signZ));
			sign.setText("�e�[SkyWars]", "�a" + this.arenaname + "", "�e" + arenaplayers.size() + "/" + maxPlayerCount + "", "�aJOIN");
			
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
					for (Player ingame : arenaplayers.values())
					{
						ingame.sendMessage(LanguageManager.translate("sw_solo_time", ingame, String.valueOf(this.lastTime)));
					}
					break;
				case 0:
					for (Player ingame : arenaplayers.values())
					{
						ingame.sendMessage(LanguageManager.translate("sw_solo_game_end", ingame, new String[0]));
						ingame.sendMessage(LanguageManager.translate("sw_solo_game_end_no_winner", ingame, new String[0]));
						ingame.getInventory().clearAll();
						ingame.teleport(skywars.lobbyXYZ);
						skywars.kitmanager.setKit(ingame.getName(), "any_kit");
						worldmanager.restartArena(worldname);
						swplayers.removePlayers();
						redteam.removePlayers();
						blueteam.removePlayers();
						greenteam.removePlayers();
						yellowteam.removePlayers();
						this.lastTime = 0;
						this.gameStatus = 0;

					}
					break;
			}
		}

	}
	
	@EventHandler
	public void checkVoid(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		if(player.getY() < 10)
		{
			swplayers.removePlayer(player, "kill");
		}
	}
	
	@EventHandler
	public void checkLeave(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		swplayers.removePlayer(player, "quit");
		redteam.removePlayer(player);
		blueteam.removePlayer(player);
		greenteam.removePlayer(player);
		yellowteam.removePlayer(player);
	}
	
	@EventHandler
	public void dropRand(BlockBreakEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getBlock();
		if (block.getId() == 19)
		{
			if (player.getLevel() == Server.getInstance().getLevelByName(this.worldname))
			{
				new RandomAction(player);
			}
		}
	}

	@EventHandler
	public void handlePlayerInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getBlock();
		if (block.getX() == this.signX && block.getY() == this.signY && block.getZ() == this.signZ)
		{
			joinToWaitArena(player);
		}
		if (block.getId() == 35 && block.getDamage() == 14)
		{
			event.setCancelled();
			if (waitplayers.containsKey(player.getName()))
			{
				swplayers.setTeam(player, redteam);
				arenaplayers.remove(player.getName());
				arenaplayers.put(player.getName(), player);
				player.sendMessage(LanguageManager.translate("red_team", player, new String[0]));
				player.setNameTag("§c[RED] " + player.getDisplayName());
			}
		}
		if (block.getId() == 35 && block.getDamage() == 11)
		{
			event.setCancelled();
			if (waitplayers.containsKey(player.getName()))
			{
				swplayers.setTeam(player, blueteam);
				arenaplayers.remove(player.getName());
				arenaplayers.put(player.getName(), player);
				player.sendMessage(LanguageManager.translate("blue_team", player, new String[0]));
				player.setNameTag("§b[BLUE] " + player.getDisplayName());
			}
		}
		if (block.getId() == 35 && block.getDamage() == 5)
		{
			event.setCancelled();
			if (waitplayers.containsKey(player.getName()))
			{
				swplayers.setTeam(player, greenteam);
				arenaplayers.remove(player.getName());
				arenaplayers.put(player.getName(), player);
				player.sendMessage(LanguageManager.translate("green_team", player, new String[0]));
				player.setNameTag("§a[GREEN] " + player.getDisplayName());
			}
		}
		if (block.getId() == 35 && block.getDamage() == 4)
		{
			event.setCancelled();
			if (waitplayers.containsKey(player.getName()))
			{
				swplayers.setTeam(player, yellowteam);
				arenaplayers.remove(player.getName());
				arenaplayers.put(player.getName(), player);
				player.sendMessage(LanguageManager.translate("yellow_team", player, new String[0]));
				player.setNameTag("§e[YELLOW] " + player.getDisplayName());
			}
		}
	}

	@EventHandler
	public void handleTeamPvP(EntityDamageEvent event)
	{
		if (event instanceof EntityDamageByEntityEvent)
		{
			Entity player = ((EntityDamageByEntityEvent) event).getDamager();
			Entity entity = event.getEntity();
			Player hitnutyHrac = (Player) entity;
			Player damager = (Player) player;
			if (redteam.isInTeam(hitnutyHrac) && redteam.isInTeam(damager))
			{
				event.setCancelled();
			}
			if (blueteam.isInTeam(hitnutyHrac) && blueteam.isInTeam(damager))
			{
				event.setCancelled();
			}
			if (greenteam.isInTeam(hitnutyHrac) && greenteam.isInTeam(damager))
			{
				event.setCancelled();
			}
			if (yellowteam.isInTeam(hitnutyHrac) && yellowteam.isInTeam(damager))
			{
				event.setCancelled();
			}
		}
	}
	
	
	@EventHandler
    public void handleDamageEvent(EntityDamageEvent event)
	{
        Entity ent = event.getEntity();
        Player playerEntity = (Player) ent;
		if ((playerEntity.getHealth() - event.getDamage()) < 1)
		{
			for (Player ingames : arenaplayers.values())
			{
				ingames.sendMessage(LanguageManager.translate("sw_solo_all_death", ingames, playerEntity.getName()));
				swplayers.removePlayer(playerEntity, "kill");
				skywars.statsmanager.addDeaths(playerEntity.getName(), 1);
			}
		}
        if (event instanceof EntityDamageByEntityEvent)
		{
            Entity damager = ((EntityDamageByEntityEvent) event).getDamager();
            Entity entity = event.getEntity();
            Player hitnutyHrac = (Player) entity;
            Player player = (Player) damager;
			if ((hitnutyHrac.getHealth() - event.getDamage()) < 1)
			{
				for (Player ingame : arenaplayers.values())
				{
					ingame.sendMessage(LanguageManager.translate("sw_solo_all_death_cause_kill", ingame, hitnutyHrac.getName(), player.getName()));
					swplayers.removePlayer(hitnutyHrac, "kill");
					skywars.statsmanager.addKills(player.getName(), 1);
					skywars.statsmanager.addDeaths(hitnutyHrac.getName(), 1);
				}
			} 
		}
	}


    public void joinToWaitArena(Player player)
	{
		if (this.gameStatus < 2)
		{
			waitplayers.put(player.getName(), player);
			player.teleport(new Position(this.spawnX, this.spawnY, this.spawnZ, Server.getInstance().getLevelByName(this.worldname)));
			player.sendMessage(LanguageManager.translate("teleport", player, new String[0]));
		}
    }

    

}
