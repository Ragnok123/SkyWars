package cz.SkyWars.Kits;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.potion.Effect;

public interface Kit {
	
	public Item[] getItems();
	public Effect[] getEffects();
	public Item[] getArmor();
	public void handle(Player p);
	public String getName();

}
