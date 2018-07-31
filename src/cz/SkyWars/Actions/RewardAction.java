package cz.SkyWars.Actions;

import cn.nukkit.Player;

public class RewardAction
{

	public int coins;
	public int exp;
	public Player player;

	public RewardAction(Player player, int coins, int exp)
	{
		this.player = player;
		this.coins = coins;
		this.exp = exp;
		sendReward(player, coins, exp);
    }

    public void sendReward(Player player, int coins, int exp)
	{
        
    }

}
