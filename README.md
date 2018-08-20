# SkyWars
SkyWars plugin for Nukkit
Current version: 0.1 beta 

# WARNING:
Before setup copy your skywars worlds to "arenas" folder

# Setup:
  1. Type /sw create <arena_name> 
  2. Then tap a sign in lobby world
  3. Then tap on positions
  4. When you will tap positions, just type /sw finish
  
# ToDo:
  1. Bug fixes
  2. Team arena
  3. Lucky Block mode
  4. Make it better

# API example:
```java

@EventHandler
public void handleMoney(PlayerEconomyRewardEvent event){
  Player player = event.getPlayer();
  int money = event.getMoney();
  yourMoneyPlugin.addMoney(player, money);
}
```
