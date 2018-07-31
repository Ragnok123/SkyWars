package cz.SkyWars.Manager.WorldManager.Functions;

import cz.SkyWars.Manager.WorldManager.ArenaWorldManager;
import cz.SkyWars.Manager.Weight;
import cn.nukkit.item.Item;
import cn.nukkit.Server;
import cn.nukkit.blockentity.*;

import java.util.*;
import java.lang.*;

public class ChestRefillManager{
	
	public ArenaWorldManager worldmanager;
	public String worldname;
	public Random random;
	public int finalWeight = 0;
	
	public HashMap<Integer, Weight> armor1 = new HashMap<Integer, Weight>();
	public HashMap<Integer, Weight> armor2 = new HashMap<Integer, Weight>();
	public HashMap<Integer, Weight> armor3 = new HashMap<Integer, Weight>();
	public HashMap<Integer, Weight> armor4 = new HashMap<Integer, Weight>();
	public HashMap<Integer, Weight> sword1 = new HashMap<Integer, Weight>();
	public HashMap<Integer, Weight> itemarray = new HashMap<Integer, Weight>();
	public HashMap<Integer, Weight> blockarray = new HashMap<Integer, Weight>();
	
	public ChestRefillManager(ArenaWorldManager worldmanager, String worldname){
		this.worldmanager = worldmanager;
		this.worldname = worldname;
		random = new Random();
		initializeArrays();
	}
	
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
	
	
}
