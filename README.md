# SkyWars
SkyWars plugin for Nukkit

Progress: 46%

Setup:
  1. Type /sw create <arena_name> <slots> <time (in minutes, only full numbers!!!)>
  2. Then tap a sign in lobby world
  3. Then tap on positions, when plugin will say that you finished setup

API example:
```java

@EventHandler
public void handleMoney(PlayerEconomyRewardEvent event){
  Player player = event.getPlayer();
  int money = event.getMoney();
  yourMoneyPlugin.addMoney(player, money);
}
```
