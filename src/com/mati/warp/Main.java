package com.mati.warp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Connection connection;
	private static String host;
	private static String database;
	private static String username;
	private static String password;
	private static int port;

	@Override
	public void onEnable() {

		getCommand("warp").setExecutor(new Warp());
		getCommand("setwarp").setExecutor(new SetWarp());
		getCommand("delwarp").setExecutor(new DelWarp());
		getCommand("listwarps").setExecutor(new ListWarps());
		//connect();
	}
	
	private static void connect() {
		host = "ndb.mineserv.eu";
		port = 3306;
		database = "s33591";
		username = "s33591";
		password = "3UtgzySkVE";

		openConnection();
	}
	
	public static void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("------------------------------------------------");
			System.out.println(connection);
			System.out.println("------------------------------------------------");
			e.printStackTrace();
			System.out.println("------------------------------------------------");
		}
	}

	private static void openConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				return;
			}
		} catch (SQLException e1) {
			System.out.println("------------------------------------------------");
			System.out.println(connection);
			System.out.println("------------------------------------------------");
			e1.printStackTrace();
			System.out.println("------------------------------------------------");
		}

		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + Main.host + ":" + Main.port + "/" + Main.database,
					Main.username, Main.password);
		} catch (SQLException e) {
			System.out.println("------------------------------------------------");
			System.out.println(connection);
			System.out.println("------------------------------------------------");
			e.printStackTrace();
			System.out.println("------------------------------------------------");
		}
	}

	public static PreparedStatement prepareStatement(String query) throws SQLException {
		if(connection == null || connection.isClosed()) {
			connect();
		}
		
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
