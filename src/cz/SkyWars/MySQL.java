package cz.SkyWars;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import cz.SkyWars.SkyWars;

public class MySQL
{

	public SkyWars plugin;
	public Connection conn;
	
	String login = "";
    String database = "";
    String password = "";
    String url = "";

	public MySQL(SkyWars plugin, String login, String pass, String database, String url)
	{
		this.plugin = plugin;
		this.login= login;
		this.password = pass;
		this.database = database;
		this.url = url;
	}


	public Connection connect()
	{
        
        try
		{
            Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("" + database + "?" + "user=" + login + "&" + "password=" + password);
			conn.createStatement();
			plugin.getServer().getLogger().info("Connected to MySQL database");
        }
		catch (SQLException var7)
		{
            return null;
        }
		catch (ClassNotFoundException var8)
		{
            return null;
        }

        return conn; 
    }

	public void updateData(String query)
	{
        try
		{
            PreparedStatement e = conn.prepareStatement(query);
			e.executeUpdate();
        }
		catch (SQLException exception)
		{
            exception.printStackTrace();
        }
    }
	
	public void init() {
		updateData("CREATE TABLE IF NOT EXISTS `skywars_stats` (`nickname` varchar(50) NOT NULL default '', `kills` int(11) NOT NULL default '0', `deaths` int(11) NOT NULL default '0', `wins` int(11) NOT NULL default '0')");
	}


    public HashMap<String, Object> getPlayerStatsData(String username)
	{
        try
		{
            PreparedStatement e = conn.prepareStatement("SELECT * FROM `skywars_stats` WHERE `nickname` = '" + username + "'");
            ResultSet result = e.executeQuery();
            ResultSetMetaData md = result.getMetaData();
            int columns = md.getColumnCount();
            HashMap<String, Object> row = new HashMap<String, Object>();

            while (result.next())
			{
                for (int i = 1; i <= columns; ++i)
				{
                    row.put(md.getColumnName(i), result.getObject(i));
                }
            }

            return row;
        }
		catch (SQLException var9)
		{
            var9.printStackTrace();
            return null;
        }
    }

    public HashMap<String, Object> getPlayerKitData(String username)
	{
        try
		{
            PreparedStatement e = conn.prepareStatement("SELECT * FROM `skywars_kits` WHERE `nickname` = '" + username + "'");
            ResultSet result = e.executeQuery();
            ResultSetMetaData md = result.getMetaData();
            int columns = md.getColumnCount();
            HashMap<String, Object> row = new HashMap<String, Object>();

            while (result.next())
			{
                for (int i = 1; i <= columns; ++i)
				{
                    row.put(md.getColumnName(i), result.getObject(i));
                }
            }

            return row;
        }
		catch (SQLException var9)
		{
            var9.printStackTrace();
            return null;
        }
    }


}
