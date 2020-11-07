package cz.SkyWars.Arena;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerDropItemEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.item.Item;
import cz.SkyWars.SWPlayer;
import cz.SkyWars.SkyWars;
import cz.SkyWars.Manager.LanguageManager;

public class ArenaListener implements Listener {
	
	@EventHandler
	public void handleLuckyBlockBreak(BlockBreakEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getBlock();
		SWPlayer data = SkyWars.getPlayer(player);
		try {
		Arena a = data.getArena();
		if(a.game(player)) {
			if(a.gameStatus < 2){ 
				if(player.getLevel() == Server.getInstance().getLevelByName(a.worldname))
				{
					event.setCancelled();
				}
			}
		}
		} catch(NullPointerException e) {
			
		}
	}

    @EventHandler
    public void handleAntiMove(PlayerMoveEvent event)
	{
    	Player player = event.getPlayer();
    	SWPlayer data = SkyWars.getPlayer(player);
		try {
			Arena a = data.getArena();
			if (a.arenaplayers.containsKey(player.getName()))
			{
				if (a.gameStatus > 2)
				{
					if (player.getY() < 0)
					{
						SkyWars.getPlayer(player).addDeath();
						a.leave(player, "void");
					}
				}
        	}
		} catch(NullPointerException e) {
			
		}
    }

    @EventHandler
    public void handlePlayerQuit(PlayerQuitEvent event)
	{
        Player player = event.getPlayer();
        SWPlayer data = SkyWars.getPlayer(player);
		try {
			Arena a = data.getArena();
			if (a.arenaplayers.containsKey(player.getName()))
			{
        		a.leave(player, "leave");
        	}
		} catch(NullPointerException e) {
			
		}
    }
    
    @EventHandler
    public void handleDamage(EntityDamageEvent event)
    {
    	if(event.getEntity() instanceof Player) {
    		Player player = (Player) event.getEntity();
    		SWPlayer data = SkyWars.getPlayer(player);
			try {
				Arena a = data.getArena();
				if(a.gameStatus < 2)
    			{
    				if(player.getLevel() == Server.getInstance().getLevelByName(a.worldname))
    				{
    					event.setCancelled();
    				}
    			}
			} catch(NullPointerException e) {
				
			}
    	}
    }

	@EventHandler
    public void handleDamageEvent(EntityDamageEvent event)
	{
        if (event instanceof EntityDamageByEntityEvent)
		{
            if(event.getEntity() instanceof Player && ((EntityDamageByEntityEvent) event).getDamager() instanceof Player) {
                Entity damager = ((EntityDamageByEntityEvent) event).getDamager();
            	Player hitnutyHrac = (Player) event.getEntity();
            	Player player = (Player) damager;
            	SWPlayer data = SkyWars.getPlayer(player);
            	try {
            		Arena a = data.getArena();
            		if ((hitnutyHrac.getHealth() - event.getDamage()) < 1)
            		{
            			if(!a.arenaplayers.isEmpty()) {
                			for (Player ingame : a.arenaplayers.values())
                			{
                				if(a.game(player) && a.game(ingame) && a.game(hitnutyHrac)) {
                					SkyWars.getPlayer(player).addKill();
                					SkyWars.getPlayer(hitnutyHrac).addDeath();
            						ingame.sendMessage(LanguageManager.translate("sw_solo_all_death_cause_kill", ingame, hitnutyHrac.getName(), player.getName()));
            						a.leave(hitnutyHrac, "kill");
                				}
                			}
            			}
            		} 
            	} catch(NullPointerException e) {
			
            	}
            }
		}
	} 


    @EventHandler
    public void onDeath(PlayerDeathEvent event)
	{
        Entity entity = event.getEntity();
        Player player = (Player) entity;
        SWPlayer data = SkyWars.getPlayer(player);
		try {
			Arena a = data.getArena();
        if (a.arenaplayers.containsKey(player.getName()))
		{
			event.setDeathMessage("");
			event.setDrops(new Item[0]);
        }
		} catch(NullPointerException e) {
			
		}
    }
    
    


	@EventHandler
    public void kitSelect(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		Item item = event.getItem();
		Block block = event.getBlock();
		SWPlayer data = SkyWars.getPlayer(player);
		try {
			Arena a = data.getArena();
		if (a.arenaplayers.containsKey(player.getName()))
		{
			if(item.getCustomName().equals("§eBack to lobby"))
			{
				event.setCancelled();
				a.leave(player, "leave");
			}
			if(block.getId() == Block.CHEST) {
				if(a.gameStatus < 2) {
					event.setCancelled();
				}
			}
		}
		} catch(NullPointerException e) {
			
		}
    }
	
	
	@EventHandler
	public void handleDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		SWPlayer data = SkyWars.getPlayer(player);
		Arena a = data.getArena();
		try {
				if(a.gameStatus < 2) {
					event.setCancelled();
				}
		} catch(NullPointerException e) {
			
		}
	}

}
