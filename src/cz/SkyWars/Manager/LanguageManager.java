package cz.SkyWars.Manager;



import cn.nukkit.Player;

import java.util.*;

public class LanguageManager{
    public Set<String> list;
    public static int language = 0;
    public static HashMap<Integer, HashMap<String, String>> languages = new HashMap<Integer, HashMap<String, String>>();
    public static HashMap<String, String> englishMsg = new HashMap<String, String>();
    public static HashMap<String, String> czechMsg = new HashMap<String, String>();
    public static HashMap<String, String> russianMsg= new HashMap<String, String>();


    public static void initEnglishMsg(){
             englishMsg.put("perms", "&eSkyWars> &cSorry, but you dont have permissions");
			 englishMsg.put("teleport", "&eSkyWars> &aTeleporting xou to wait lobby");
			 englishMsg.put("error", "&eSkyWars> &cUnknow error");
			 
			 englishMsg.put("arena_setup", "&eSkyWars> &aYou're setting arena §f%0");
			 englishMsg.put("arena_sign", "&eSkyWars> &aTap on sign");
			 englishMsg.put("arena_click", "&eSkyWars> &aOk, now click for position");
			 englishMsg.put("arena_click", "&eSkyWars> &aYou've finished setup");

             englishMsg.put("sw_solo_menu_open", "&eSkyWars> &aYou have opened kit menu");
             englishMsg.put("sw_solo_menu_close", "&eSkyWars> &aYou have closed kit menu");
             englishMsg.put("open_inventory_compass", "§eSkyWars> §aOpen your inventory to get compass");
             englishMsg.put("open_inventory", "§eSkyWars> §aOpen your inventory");

             englishMsg.put("effect_used", "&eKits> &aYou have used this kit");
             
             englishMsg.put("tap_again", "&eKits> &aHit NPC again to buy this kit");
             englishMsg.put("not_enought_coins", "&eKits> &cSorry, but you dont have enoughcoins...\n&eKits> &cThis kit cost &f%0 &ctokens\n&eKits> &cYou have &f%1 &ctokens");
             englishMsg.put("kit_buy", "&eKits> &aYou have sucessfuly bought this kit");
             englishMsg.put("kit_already_bought", "&eKits> &cYou have already biught this kit");
             englishMsg.put("kit_selected", "&eKits> &aYou have selected kit");
             englishMsg.put("kit_not_bought", "&eKits> &aSorry, but you didnt have bought this kit");
             
             englishMsg.put("kit_info_berseeker", "&eKits> &aIf you will buy berseeker kit, you will get\n   &6- Damage attack +5");
             englishMsg.put("kit_info_archer", "&eKits> &aIf you will buy archer kit, you will get\n   &6- Bow x2\n   &6- Arrow x16");
             englishMsg.put("kit_info_knight", "&eKits> &aIf you will buy knight kit, you will get\n   &6-Iron sword x1\n   &6- Fire resistance II\n   &c- Slowness II");
             englishMsg.put("kit_info_assassin", "&eKits> &aIf you will buy assassin kit, you will get\n   &6- Invisibler (makes you invisible for 25 seconds)");
             englishMsg.put("kit_info_wizard", "&eKits> &aIf you will buy wizard kit, you will get\n   &6- Enderpearl x16");
             englishMsg.put("kit_info_elytra", "&eKits> &aIf you will buy elytra kit, you will get\n   &6- Elytra x1");
             englishMsg.put("kit_info_soul_seller", "&eKits> &aIf you will buy soul seller kit, you will get\n   &6- Soul seller item\n&aAdvantages: after using you will get 20 seconds of god mode\n&aAfter god mode you will get Blindness III for 10 seconds");
             englishMsg.put("kit_info_cursed", "&eKits> &aIf you will buy cursed kit, you will get\n   &6- Cursed shield\n   &6- Cursed blood (+5 damage)\n   &6- Cursed healer");
             
             
             englishMsg.put("sw_solo_player_join", "&eSkyWars> &aJoining to &b%0 &a...");
             englishMsg.put("sw_solo_player_lobby" ,"&eSkyWars> &aTeleporting you to lobby...");
             englishMsg.put("sw_solo_full", "&eSkyWars> &cSorry, but this arena is full");
             englishMsg.put("sw_solo_running", "&eSkyWars> &cSorry, but this arena is running");
             englishMsg.put("sw_solo_all_join", "&eSkyWars> &aPlayer &b%0 &ahas joined");
             englishMsg.put("sw_solo_chest_refill", "&eSkyWars> &aChests are now refilled");
             englishMsg.put("sw_solo_all_quit", "&eSkyWars> &aPlayer &b%0 &ahas left");
             englishMsg.put("sw_solo_game_started", "&eSkyWars> &aGame started");
             englishMsg.put("sw_solo_list_players", "&eSkyWars> &aPlayers: &f%0");
             englishMsg.put("sw_solo_time", "&eSkyWars> &aTime remain: &f%0 &aseconds"); 
             englishMsg.put("sw_solo_countdown_started", "&eSkyWars> &aCountdown started");
             englishMsg.put("sw_solo_game_end", "&eSkyWars> &cGame has ended");
             englishMsg.put("sw_solo_game_end_no_winners", "&eSkyWars> &cNoone didn't won");
             englishMsg.put("sw_solo_game_end_with_winners", "&eSkyWars> &b%0 &ahas won the game");
             englishMsg.put("sw_solo_all_death", "&eSkyWars> %0 &ajust died");
             englishMsg.put("sw_solo_all_death_cause_kill", "&eSkyWars> %0 &awas killed by %1");

             languages.put(0, englishMsg);
    }


    public static String translate(String message, Player p, String... args) {
        return translate(message, language, args);
    }
    
    public static String translate(String message, int lang, String... args) {
        String base = LanguageManager.languages.get(lang).get(message).replaceAll("&", "�");
        for (int i = 0; i < args.length; ++i) {
            base = base.replace("%" + i, args[i]);
        }
        return base;
    }
    
    public static int[] getLanguages() {
        return new int[] { 1, 0 };
    }
    
    public static HashMap<Integer, String> getTranslations(final String msg, final String... args) {
        final HashMap<Integer, String> translations = new HashMap<Integer, String>();
        for (final int i : getLanguages()) {
            translations.put(i, translate(msg, i, args));
        }
        return translations;
    }
    
}
