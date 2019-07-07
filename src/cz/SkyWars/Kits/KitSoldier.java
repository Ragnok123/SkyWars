package cz.SkyWars.Kits;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.potion.Effect;

public class KitSoldier implements Kit{

	@Override
	public Item[] getItems() {
		return new Item[] {Item.get(Item.STONE_SWORD,0,1)};
	}

	@Override
	public Effect[] getEffects() {
		return new Effect[] {};
	}

	@Override
	public Item[] getArmor() {
		return new Item[] {Item.get(Item.CHAIN_CHESTPLATE,0,1)};
	}

	@Override
	public void handle(Player p) {
		p.getInventory().addItem(getItems()[0]);
		p.getInventory().setArmorItem(1,getArmor()[0]);
		
	}

	@Override
	public String getName() {
		return "soldier";
	}

}
