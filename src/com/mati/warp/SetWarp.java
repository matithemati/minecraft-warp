package com.mati.warp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarp implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;

		if (args.length != 1) {
			player.sendMessage(ChatColor.BLUE + "[WARP] " + ChatColor.DARK_RED + "Nieprawid³owe u¿ycie!");
			player.sendMessage(ChatColor.BLUE + "[WARP] " + ChatColor.GREEN + "U¿ycie: " + ChatColor.DARK_AQUA
					+ "/setwarp " + ChatColor.GOLD + "<nazwa>");
			return false;
		}

		String name = args[0];
		UUID uuid = player.getUniqueId();

		try {
			String checkWarpQuery = "SELECT COUNT(uuid) FROM mc_warp WHERE name='" + name + "' AND uuid='" + uuid
					+ "';".toString();
			ResultSet rs = Main.prepareStatement(checkWarpQuery).executeQuery();
			rs.next();

			if (rs.getInt(1) == 0) { // not in database

				try {
					Location coords = player.getLocation();
					int x = coords.getBlockX();
					int y = coords.getBlockY();
					int z = coords.getBlockZ();
					String world = coords.getWorld().getName().toString();

					String setWarpQuery = "INSERT INTO mc_warp(id, uuid, name, coord_x, coord_y, coord_z, world) VALUES (null, '"
							+ uuid + "', '" + name + "', " + x + ", " + y + ", " + z + ",'" + world + "');".toString();
					Main.prepareStatement(setWarpQuery).executeUpdate();
					player.sendMessage(ChatColor.BLUE + "[WARP] " + ChatColor.DARK_GREEN
							+ "Pomyœlnie dodano warp o nazwie " + ChatColor.YELLOW + name);
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
					rs.close();
					player.sendMessage(ChatColor.RED + "Wyst¹pi³ b³¹d!");
				}

			} else { // already in database
				player.sendMessage(ChatColor.BLUE + "[WARP] " + ChatColor.DARK_RED + "Warp o nazwie " + ChatColor.YELLOW
						+ name + ChatColor.DARK_RED + " ju¿ istnieje!");
				rs.close();
				return false;
			}

		} catch (SQLException x) {
			x.printStackTrace();
			player.sendMessage(ChatColor.RED + "Wyst¹pi³ b³¹d!");
		}

		return false;

	}

}
