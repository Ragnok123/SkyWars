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

	public MySQL(SkyWars plugin)
	{
		this.plugin = plugin;
	}


	public Connection connect()
	{
        String login = "";
        String database = "";
        String password = "";
        try
		{
            Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("" + database + "?" + "user=" + login + "&" + "password=" + password);
			conn.createStatement();
			plugin.getServer().getLogger().info("§eSW> §aPřipojeno k MySQL databázi");
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
