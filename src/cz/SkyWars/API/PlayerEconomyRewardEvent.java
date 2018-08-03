package cz.SkyWars.API;

import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;
import cn.nukkit.Player;

public class PlayerEconomyRewardEvent extends PlayerEvent {
	    private static HandlerList handlers = new HandlerList();
	    private int coins;

	    public static HandlerList getHandlers() {
	        return handlers;
	    }
	    
	    public PlayerEconomyRewardEvent(Player player, int coins) {
	    	this.player = player;
	    	this.coins = coins;
	    }
	    
	    
	    public int getMoney() {
	    	return this.coins;
	    }
	
}
