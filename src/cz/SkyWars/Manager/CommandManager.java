package cz.SkyWars.Manager;

import cz.SkyWars.SkyWars;

import cn.nukkit.command.Command;

public abstract class CommandManager extends Command {

   public SkyWars skywars;

   public CommandManager(String name, SkyWars skywars){
       super(name);
       this.skywars = skywars;
   }

}