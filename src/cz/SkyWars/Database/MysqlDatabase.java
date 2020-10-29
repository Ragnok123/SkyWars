package cz.SkyWars.Database;

import cz.SkyWars.SWPlayer;
import cz.SkyWars.SkyWars;
import ru.ragnok123.sqlNukkitLib.utils.Pair;

public class MysqlDatabase implements Database{
	
	public SkyWars skywars;
	
	public MysqlDatabase(SkyWars s) {
		this.skywars = s;
	}

	@Override
	public void loadData(SWPlayer data) {
		String username = data.getPlayer().getName().toLowerCase();
		skywars.mysql.select("skywars_stats", "nickname", username, map -> {
			if(!map.isEmpty()) {
				data.kills = (int)map.get("kills");
				data.deaths = (int)map.get("deaths");
				data.wins = (int)map.get("wins");
			} else {
				skywars.mysql.insert("skywars", new Pair[] {
						new Pair("nickname", username),
						new Pair("kills",0),
						new Pair("deaths",0),
						new Pair("wins",0)
				});
			}
		});
		
	}

	@Override
	public void saveData(SWPlayer data) {
		String username = data.getPlayer().getName().toLowerCase();
		skywars.mysql.update("skywars_stats", "nickname", username, new Pair[] {
				new Pair("kills", data.kills),
				new Pair("deaths", data.deaths),
				new Pair("wins", data.wins)
		});
	}

}
