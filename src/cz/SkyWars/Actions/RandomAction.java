package cz.SkyWars.Actions;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.item.Item;
import cn.nukkit.level.Explosion;
import cn.nukkit.level.Position;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
import cz.SkyWars.Manager.LanguageManager;
import cn.nukkit.*;

import java.lang.*;
import java.io.*;
import java.util.*;

public class RandomAction {
	
	public Player player;
	
	public RandomAction(Player player)
	{
		this.player = player;
		random(player);
	}
	
	public void random(Player player)
	{
		Random random = new Random();
		FullChunk fullchunk = player.getLevel().getChunk((int) player.x >> 4, (int) player.z >> 4);
		CompoundTag nbt = new CompoundTag()
                .putList(new ListTag<DoubleTag>("Pos")
                        .add(new DoubleTag("", (float) player.x + 0.5))
                        .add(new DoubleTag("", (float) player.y))
                        .add(new DoubleTag("", (float) player.z + 0.5)))
                .putList(new ListTag<DoubleTag>("Motion")
                        .add(new DoubleTag("", 0.0))
                        .add(new DoubleTag("", 0.0))
                        .add(new DoubleTag("", 0.0)))
                .putList(new ListTag<FloatTag>("Rotation")
                        .add(new FloatTag("", 0))
                        .add(new FloatTag("", 0)));
                
                
		int randomPriz = random.nextInt(21);
		switch(randomPriz)
		{
			case 1:
				player.getInventory().addItem(Item.get(5,0,32));
				player.getInventory().addItem(Item.get(258,0,1));
				player.sendMessage(LanguageManager.translate("open_inventory", player, new String[0]));
			break;
			case 2:
				player.getInventory().addItem(Item.get(310,0,1));
				player.getInventory().addItem(Item.get(311,0,1));
				player.getInventory().addItem(Item.get(312,0,1));
				player.getInventory().addItem(Item.get(313,0,1));
				player.sendMessage(LanguageManager.translate("open_inventory", player, new String[0]));
			break;
			case 3:
				player.sendMessage("�aThis block wasnt lucky");
			break;
			case 4:
				player.setHealth(player.getHealth() - 10);
				player.sendMessage("�aYou have lost a lot of blood");
			break;
			case 5:
				player.sendMessage("�aThis block wasnt lucky");
			break;
			case 6:
				player.sendMessage("�aThis block wasnt lucky");
			break;
			case 7:
				player.getInventory().addItem(Item.get(20, 0, 64));
				player.sendMessage(LanguageManager.translate("open_inventory", player, new String[0]));
			break;
			case 8:
				player.getInventory().addItem(Item.get(20, 0, 32));
				player.sendMessage(LanguageManager.translate("open_inventory", player, new String[0]));
			break;
			case 9:
				player.getInventory().addItem(Item.get(360, 0, 1));
				player.getInventory().addItem(Item.get(391, 0, 5));
				player.getInventory().addItem(Item.get(297, 0, 5));
				player.sendMessage(LanguageManager.translate("open_inventory", player, new String[0]));
			break;
			case 10:
				player.sendMessage("�aThis block wasnt lucky");
			break;
			case 11:
				player.getInventory().addItem(Item.get(262,0,16));
				player.sendMessage(LanguageManager.translate("open_inventory", player, new String[0]));
			break;
			case 12:
				player.getInventory().addItem(Item.get(298,0,1));
				player.getInventory().addItem(Item.get(299,0,1));
				player.getInventory().addItem(Item.get(300,0,1));
				player.getInventory().addItem(Item.get(301,0,1));
				player.sendMessage(LanguageManager.translate("open_inventory", player, new String[0]));
			break;
			case 13:
				player.getInventory().addItem(Item.get(276,0,1));
				player.sendMessage(LanguageManager.translate("open_inventory", player, new String[0]));
			break;
			case 14:
				player.getInventory().addItem(Item.get(368,0,16));
				player.sendMessage(LanguageManager.translate("open_inventory", player, new String[0]));
			break;
			case 15:
				player.getInventory().addItem(Item.get(368,0,4));
				player.sendMessage(LanguageManager.translate("open_inventory", player, new String[0]));
			break;
			case 16:
				player.getInventory().addItem(Item.get(368,0,7));
				player.sendMessage(LanguageManager.translate("open_inventory", player, new String[0]));
			break;
			case 17:
				player.getInventory().setArmorItem(1, Item.get(444));
				player.sendMessage("�eYee, elytra. U re lucky...");
			break;
			case 18:
				player.getInventory().addItem(Item.get(264, 0, 2));
				player.getInventory().addItem(Item.get(3, 0, 20));
				player.sendMessage(LanguageManager.translate("open_inventory", player, new String[0]));
			break;
			case 19:
				player.getInventory().addItem(Item.get(46, 0, 3));
				player.sendMessage(LanguageManager.translate("open_inventory", player, new String[0]));
			break;
		}
	}

}
