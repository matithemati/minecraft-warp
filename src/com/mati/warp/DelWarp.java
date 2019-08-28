package com.mati.warp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelWarp implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = (Player) sender;

		if (args.length != 1) {
			player.sendMessage(ChatColor.BLUE + "[WARP] " + ChatColor.DARK_RED + "Nieprawid³owe u¿ycie!");
			player.sendMessage(ChatColor.BLUE + "[WARP] " + ChatColor.GREEN + "U¿ycie: " + ChatColor.DARK_AQUA
					+ "/delwarp " + ChatColor.GOLD + "<nazwa>");
			return false;
		}
		
		String name = args[0];
		UUID uuid = player.getUniqueId();

		try {
			String checkWarpQuery = "SELECT COUNT(name) from mc_warp WHERE name='" + name + "' AND uuid='" + uuid
					+ "';".toString();
			ResultSet rs = Main.prepareStatement(checkWarpQuery).executeQuery();
			rs.next();

			if (rs.getInt("COUNT(name)") != 1) {
				player.sendMessage(ChatColor.BLUE + "[WARP] " + ChatColor.DARK_RED + "Brak warpa o nazwie "
						+ ChatColor.YELLOW + name);
				rs.close();
			} else {

				try {
					String delWarpQuery = "DELETE FROM mc_warp WHERE name='" + name + "' AND uuid='" + uuid
							+ "';".toString();
					Main.prepareStatement(delWarpQuery).executeUpdate();
					player.sendMessage(ChatColor.BLUE + "[WARP] " + ChatColor.GREEN
							+ "Pomyœlnie usuniêto warp o nazwie " + ChatColor.YELLOW + name);
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
					rs.close();
					player.sendMessage(ChatColor.RED + "Wyst¹pi³ b³¹d!");
				}
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			player.sendMessage(ChatColor.RED + "Wyst¹pi³ b³¹d!");
		}
		
		return false;
	}

}
