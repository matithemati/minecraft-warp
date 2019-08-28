package com.mati.warp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Connection connection;
	private String host, database, username, password;
	private int port;

	@Override
	public void onEnable() {

		getCommand("warp").setExecutor(new Warp());
		getCommand("setwarp").setExecutor(new SetWarp());
		getCommand("delwarp").setExecutor(new DelWarp());
		getCommand("listwarps").setExecutor(new ListWarps());

		host = "---";
		port = 3306;
		database = "---";
		username = "---";
		password = "---";

		try {
			openConnection();
			System.out.println("Connected!");

		}

		catch (SQLException x) {
			x.printStackTrace();
		}
	}

	private void openConnection() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			return;
		}

		connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database,
				this.username, this.password);
	}

	public static PreparedStatement prepareStatement(String query) {

		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(query);
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}
	
	
}
