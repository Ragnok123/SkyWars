# SkyWars
SkyWars plugin for Nukkit
Current version: 1.0.9 alpha pre 1.1

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
Create custom class of ecomony handler

```java
public class MyEconomy extends CustomEconomy{
	public MyEconomy(SkyWars sw){ super(sw) }
	public void addMoney(Player p, int money){
		myPlugin.addMoney(p,money);
	}
	public int getMoney(Player p){
		return myPlugin.getMoney(p);
	}

```
and than, simple with 

```
SkyWars.getInstance().setEconomyHandler(myEconomy);
```