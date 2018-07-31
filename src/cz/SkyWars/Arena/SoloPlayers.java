package cz.SkyWars.Arena;

import cz.SkyWars.Arena.SoloArena;
import cz.SkyWars.SkyWars;
import cz.SkyWars.Manager.*;

import cn.nukkit.level.Position;
import cn.nukkit.item.Item;

import cn.nukkit.Player;
import cn.nukkit.Server;

import java.util.*;


public class SoloPlayers
{

    public SoloArena arena;
    public String arenaname;
    public String worldname;
    public int maxPlayerCount;
    public int actualPos;

    public SoloPlayers(SoloArena arena, String arenaname, String worldname, int maxPlayerCount)
	{
		this.arena = arena;
		this.arenaname = arenaname;
		this.worldname = worldname;
		this.maxPlayerCount = maxPlayerCount;
		this.actualPos = 0;
	}

    public void openKitMenu(Player player)
	{
		arena.nopage.remove(player.getName());
		arena.firstpage.put(player.getName(), player);
		player.getInventory().setItem(0, Item.get(280, 0, 1).setCustomName("§eBerseeker"));
		player.getInventory().setItem(1, Item.get(280, 0, 1).setCustomName("§eArcher"));
		player.getInventory().setItem(2, Item.get(280, 0, 1).setCustomName("§eKnight"));
		player.getInventory().setItem(3, Item.get(280, 0, 1).setCustomName("§eAssassin"));
		player.getInventory().setItem(4, Item.get(280, 0, 1).setCustomName("§eWizard"));
		player.getInventory().setItem(5, Item.get(280, 0, 1).setCustomName("§eElytra"));
		player.getInventory().setItem(6, Item.get(339, 0, 1).setCustomName("§eNext page"));
		player.getInventory().setItem(8, Item.get(340, 0, 1).setCustomName("§cClose menu"));
		player.sendMessage(LanguageManager.translate("sw_solo_menu_open", player, new String[0]));
	}

	public void nextPage(Player player)
	{
		if (arena.firstpage.containsKey(player.getName()))
		{
			player.getInventory().clearAll();
			arena.firstpage.remove(player.getName());
			arena.secondpage.put(player.getName(), player);
			player.getInventory().setItem(0, Item.get(339, 0, 1).setCustomName("§ePrevious page"));
			player.getInventory().setItem(1, Item.get(280, 0, 1).setCustomName("§eSoul seller"));
			player.getInventory().setItem(2, Item.get(280, 0, 1).setCustomName("§eCursed"));
			player.getInventory().setItem(8, Item.get(340, 0, 1).setCustomName("§cClose menu"));
		}
	}

	public void previousPage(Player player)
	{
        if (arena.secondpage.containsKey(player.getName()))
		{
			player.getInventory().clearAll();
			arena.secondpage.remove(player.getName());
			arena.firstpage.put(player.getName(), player);
			player.getInventory().setItem(0, Item.get(280, 0, 1).setCustomName("§eBerseeker"));
			player.getInventory().setItem(1, Item.get(280, 0, 1).setCustomName("§eArcher"));
			player.getInventory().setItem(2, Item.get(280, 0, 1).setCustomName("§eKnight"));
			player.getInventory().setItem(3, Item.get(280, 0, 1).setCustomName("§eAssassin"));
			player.getInventory().setItem(4, Item.get(280, 0, 1).setCustomName("§eWizard"));
			player.getInventory().setItem(5, Item.get(280, 0, 1).setCustomName("§eElytra"));
			player.getInventory().setItem(6, Item.get(339, 0, 1).setCustomName("§eNext page"));
			player.getInventory().setItem(8, Item.get(340, 0, 1).setCustomName("§cClose menu"));
		}
	}

	public void closeKitMenu(Player player)
	{
		player.getInventory().clearAll();
		arena.secondpage.remove(player.getName());
		arena.firstpage.remove(player.getName());
		arena.nopage.put(player.getName(), player);
		player.getInventory().setItem(0, Item.get(340, 0, 1).setCustomName("§eKits"));
		player.sendMessage(LanguageManager.translate("sw_solo_menu_close", player, new String[0]));
	}

    public void addPlayer(Player player, String worldname, Position pos1, Position pos2, Position pos3, Position pos4, Position pos5, Position pos6, Position pos7, Position pos8)
	{
		String hraci = String.valueOf(arena.arenaplayers.size() + 1);
		if (arena.gameStatus < 2)
		{
			if (arena.arenaplayers.size() < 8)
			{
				switch (this.actualPos)
				//switch (this.actualPos)
				{
					case 0:
						arena.arenaplayers.put(player.getName(), player);
						player.teleport(pos1);
						player.getInventory().setItem(1, Item.get(Item.COMPASS).setCustomName("�eBack to lobby"));
						player.sendMessage(LanguageManager.translate("sw_solo_player_join", player, arena.arenaname));
						player.sendMessage(LanguageManager.translate("open_inventory_compass", player, new String[0]));
						Server.getInstance().getLogger().info("Player " + player.getName() + " teleported to " + pos1);
						for (Player all : arena.arenaplayers.values())
						{
							all.sendMessage(LanguageManager.translate("sw_solo_all_join", all, player.getName()));
							all.sendMessage(LanguageManager.translate("sw_solo_list_players", all, hraci));
						}
						this.actualPos = 1;
						break;
					case 1:
						arena.arenaplayers.put(player.getName(), player);
						player.teleport(pos2);
						Server.getInstance().getLogger().info("Player " + player.getName() + " teleported to " + pos2);
						player.getInventory().setItem(1, Item.get(Item.COMPASS).setCustomName("�eBack to lobby"));
						player.sendMessage(LanguageManager.translate("sw_solo_player_join", player, arena.arenaname));
						player.sendMessage(LanguageManager.translate("open_inventory_compass", player, new String[0]));
						for (Player all : arena.arenaplayers.values())
						{
							all.sendMessage(LanguageManager.translate("sw_solo_all_join", all, player.getName()));
							all.sendMessage(LanguageManager.translate("sw_solo_list_players", all, hraci));
						}
						this.actualPos = 2;
						break;
					case 2:
						arena.arenaplayers.put(player.getName(), player);
						player.teleport(pos3);
						player.getInventory().setItem(1, Item.get(Item.COMPASS).setCustomName("�eBack to lobby"));
						player.sendMessage(LanguageManager.translate("sw_solo_player_join", player, arena.arenaname));
						player.sendMessage(LanguageManager.translate("open_inventory_compass", player, new String[0]));
						for (Player all : arena.arenaplayers.values())
						{
							all.sendMessage(LanguageManager.translate("sw_solo_all_join", all, player.getName()));
							all.sendMessage(LanguageManager.translate("sw_solo_list_players", all, hraci));
						}
						this.actualPos = 3;
						break;
					case 3:
						arena.arenaplayers.put(player.getName(), player);
						player.teleport(pos4);
						player.getInventory().setItem(1, Item.get(Item.COMPASS).setCustomName("�eBack to lobby"));
						player.sendMessage(LanguageManager.translate("sw_solo_player_join", player, arena.arenaname));
						player.sendMessage(LanguageManager.translate("open_inventory_compass", player, new String[0]));
						for (Player all : arena.arenaplayers.values())
						{
							all.sendMessage(LanguageManager.translate("sw_solo_all_join", all, player.getName()));
							all.sendMessage(LanguageManager.translate("sw_solo_list_players", all, hraci));
						}
						this.actualPos = 4;
						break;
					case 4:
						arena.arenaplayers.put(player.getName(), player);
						player.teleport(pos5);
						player.getInventory().setItem(1, Item.get(Item.COMPASS).setCustomName("�eBack to lobby"));
						player.sendMessage(LanguageManager.translate("sw_solo_player_join", player, arena.arenaname));
						player.sendMessage(LanguageManager.translate("open_inventory_compass", player, new String[0]));
						for (Player all : arena.arenaplayers.values())
						{
							all.sendMessage(LanguageManager.translate("sw_solo_all_join", all, player.getName()));
							all.sendMessage(LanguageManager.translate("sw_solo_list_players", all, hraci));
						}
						this.actualPos = 5;
						break;
					case 5:
						arena.arenaplayers.put(player.getName(), player);
						player.teleport(pos6);
						player.getInventory().setItem(1, Item.get(Item.COMPASS).setCustomName("�eBack to lobby"));
						player.sendMessage(LanguageManager.translate("sw_solo_player_join", player, arena.arenaname));
						player.sendMessage(LanguageManager.translate("open_inventory_compass", player, new String[0]));
						for (Player all : arena.arenaplayers.values())
						{
							all.sendMessage(LanguageManager.translate("sw_solo_all_join", all, player.getName()));
							all.sendMessage(LanguageManager.translate("sw_solo_list_players", all, hraci));
						}
						this.actualPos = 6;
						break;
					case 6:
						arena.arenaplayers.put(player.getName(), player);
						player.teleport(pos7);
						player.getInventory().setItem(1, Item.get(Item.COMPASS).setCustomName("�eBack to lobby"));
						player.sendMessage(LanguageManager.translate("sw_solo_player_join", player, arena.arenaname));
						player.sendMessage(LanguageManager.translate("open_inventory_compass", player, new String[0]));
						for (Player all : arena.arenaplayers.values())
						{
							all.sendMessage(LanguageManager.translate("sw_solo_all_join", all, player.getName()));
							all.sendMessage(LanguageManager.translate("sw_solo_list_players", all, hraci));
						}
						this.actualPos = 7;
						break;
					case 7:
						arena.arenaplayers.put(player.getName(), player);
						player.teleport(pos8);
						player.getInventory().setItem(1, Item.get(Item.COMPASS).setCustomName("�eBack to lobby"));
						player.sendMessage(LanguageManager.translate("sw_solo_player_join", player, arena.arenaname));
						player.sendMessage(LanguageManager.translate("open_inventory_compass", player, new String[0]));
						for (Player all : arena.arenaplayers.values())
						{
							all.sendMessage(LanguageManager.translate("sw_solo_all_join", all, player.getName()));
							all.sendMessage(LanguageManager.translate("sw_solo_list_players", all, hraci));
						}
						this.actualPos = 8;
						break;
					case 8:
						player.sendMessage(LanguageManager.translate("error", player, new String[0]));
						break;
				}


				//player.getInventory().setItem(0, Item.get(340, 0, 1).setCustomName("§eKits"));

			}

			if (arena.arenaplayers.size() == 8)
			{
				player.sendMessage(LanguageManager.translate("sw_solo_full", player, new String[0]));
			}
		}
		else
		{
			player.sendMessage(LanguageManager.translate("sw_solo_running", player, new String[0]));
		}
	}

    public void removePlayer(Player player, String cause)
	{
		if (cause == "kill")
		{
			arena.arenaplayers.remove(player.getName());
			SkyWars.getInstance().statsmanager.addDeaths(player.getName(), 1);
			SkyWars.getInstance().lobbyplayers.put(player.getName(), player);
			player.getInventory().clearAll();
			player.setHealth(20);
			player.getFoodData().setLevel(20);
			player.teleport(arena.skywars.lobbyXYZ);
			for (Player ingame : arena.arenaplayers.values()) 
			{
				ingame.sendMessage(LanguageManager.translate("sw_solo_all_death", ingame, player.getName()));
			}
		}
		if (cause == "quit")
		{
			arena.arenaplayers.remove(player.getName());
		}
		if (cause == "hub")
		{
			arena.arenaplayers.remove(player.getName());
			SkyWars.getInstance().lobbyplayers.put(player.getName(), player);
			player.getInventory().clearAll();
			player.setHealth(20);
			player.getFoodData().setLevel(20);
			player.teleport(arena.skywars.lobbyXYZ);
		}
	}

    public void removePlayers()
	{
		arena.arenaplayers.clear();
	}
    
    public int getPlayerCount()
    {
    	return arena.arenaplayers.size();
    }

}
