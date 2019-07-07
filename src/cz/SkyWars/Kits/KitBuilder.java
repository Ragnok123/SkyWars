package cz.SkyWars.Kits;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.potion.Effect;

public class KitBuilder implements Kit{

	@Override
	public Item[] getItems() {
		return new Item[]{Item.get(Item.COBBLESTONE,0,15)};
	}

	@Override
	public Effect[] getEffects() {
		return new Effect[] {};
	}

	@Override
	public Item[] getArmor() {
		return new Item[] {};
	}

	@Override
	public void handle(Player p) {
		p.getInventory().addItem(getItems()[0]);
	}

	@Override
	public String getName() {
		return "builder";
	}

}
