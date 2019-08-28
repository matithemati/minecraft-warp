package com.mati.warp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Warp implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = (Player) sender;

		if (args.length != 1) {
			player.sendMessage(ChatColor.BLUE + "[WARP] " + ChatColor.DARK_RED + "Nieprawid³owe u¿ycie!");
			player.sendMessage(ChatColor.BLUE + "[WARP] " + ChatColor.GREEN + "U¿ycie: " + ChatColor.DARK_AQUA
					+ "/warp " + ChatColor.GOLD + "<nazwa>");
			return false;
		}

		String name = args[0];
		UUID uuid = player.getUniqueId();

		try {
			String getWarpQuery = "SELECT coord_x, coord_y, coord_z, world, COUNT(world) FROM mc_warp WHERE uuid='" + uuid + "' AND name='" + name + "';".toString();
			ResultSet rs = Main.prepareStatement(getWarpQuery).executeQuery();
			rs.next();
			
			if (rs.getInt("COUNT(world)") == 0) {
				player.sendMessage(ChatColor.BLUE + "[WARP] " + ChatColor.DARK_RED + "Brak warpa o takiej nazwie!");
				player.sendMessage(ChatColor.BLUE + "[WARP] " + ChatColor.GREEN + "Mo¿esz go dodaæ wpisuj¹c: " + ChatColor.DARK_AQUA
						+ "/setwarp " + ChatColor.GOLD + name);
				rs.close();
				return false;
			}

			int x = rs.getInt("coord_x");
			int y = rs.getInt("coord_y");
			int z = rs.getInt("coord_z");
			String world = rs.getString("world");
			Location warp = new Location(Bukkit.getServer().getWorld(world), x, y, z);
			player.teleport(warp);
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
			player.sendMessage(ChatColor.RED + "Wyst¹pi³ b³¹d!");
		}

		return false;
	}

}
