package cz.SkyWars.Database;

public interface Database {
	
	/*
	 * This is stats
	 */
	
	boolean checkAccStats(String username);

	int getKills(String username);

	void addKills(String username, int kills);

	int getWins(String username);

	void addWins(String username, int wins);

	int getDeaths(String username);

	void addDeaths(String username, int kills);

  	void createDataStats(String username);
  	
  	/*
  	 * And this is kits
  	 */

  	boolean checkAccKits(String username);

  	String getKit(String username);

  	void setKit(String username, String kit);

  	String getBerseeker(String username);

  	void setBerseeker(String username, String berseeker);

  	String getArcher(String username);

  	void setArcher(String username, String kit);

  	String getKnight(String username);

  	void setKnight(String username, String kit);

  	String getAssassin(String username);

  	void setAssassin(String username, String kit);

  	String getWizard(String username);

	 void setWizard(String username, String kit);

	 String getElytra(String username);

	 void setElytra(String username, String kit);

	 String getSoulSeller(String username);

	 void setSoulSeller(String username, String kit);

	 String getCursed(String username);

	 void setCursed(String username, String kit);

	 void createDataKits(String username);
  	
}
