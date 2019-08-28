package com.mati.warp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListWarps implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;

		if (args.length != 0) {
			player.sendMessage(ChatColor.BLUE + "[WARP] " + ChatColor.DARK_RED + "Nieprawid�owe u�ycie!");
			player.sendMessage(
					ChatColor.BLUE + "[WARP] " + ChatColor.GREEN + "U�ycie: " + ChatColor.DARK_AQUA + "/listwarps");
			return false;
		}

		UUID uuid = player.getUniqueId();

		String getWarpQuery = "SELECT name, coord_x, coord_y, coord_z, world FROM mc_warp WHERE uuid='" + uuid
				+ "';".toString();
		try {
			ResultSet rs = Main.prepareStatement(getWarpQuery).executeQuery();

			if (rs.next()) {
				player.sendMessage(ChatColor.BLUE + "[WARP] " + ChatColor.GREEN + "Oto lista Twoich warp�w:");

				do {
					int x = rs.getInt("coord_x");
					int y = rs.getInt("coord_y");
					int z = rs.getInt("coord_z");
					String name = rs.getString("name");
					String world = rs.getString("world");

					switch (world) {

					case "world_nether":
						world = "Piek�o";
						break;

					case "world_the_end":
						world = "�wiat ko�cowy";
						break;

					default:
						world = "Normalny �wiat";

					}

					player.sendMessage(ChatColor.BLUE + " - " + ChatColor.YELLOW + world + ": " + ChatColor.DARK_AQUA
							+ name + " " + ChatColor.RED + x + ", " + ChatColor.GREEN + y + ", " + ChatColor.BLUE + z);
				} while (rs.next());
			} else {
				player.sendMessage(ChatColor.BLUE + "[WARP] " + ChatColor.DARK_RED + "Nie posiadasz �adnego warpa!");
				player.sendMessage(ChatColor.BLUE + "[WARP] " + ChatColor.GREEN + "Mo�esz go doda� wpisuj�c: "
						+ ChatColor.DARK_AQUA + "/setwarp " + ChatColor.GOLD + "<nazwa>");
				return false;
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	

}
