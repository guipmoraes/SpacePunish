package me.spaceplugins.com.br.api;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;

import me.spaceplugins.spacepunish.Main;


public class Punish {


	static Main plugin = Main.getPlugin(Main.class);
	@SuppressWarnings("deprecation")
	public static Boolean isPunished(String player) {
		PreparedStatement stmt;
		try {
			stmt = plugin.getConnection().prepareStatement("SELECT * FROM punicoes WHERE uuid = ?");
			stmt.setString(1, Bukkit.getOfflinePlayer(player).getUniqueId().toString());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				if(rs.getString("banido") != "false") {
					return true;
				}
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}
	public static ResultSet getPunishByNick(String player) {
		PreparedStatement stmt;
		try {
			stmt = plugin.getConnection().prepareStatement("SELECT * FROM punicoes WHERE uuid = ?");
			stmt.setString(1, Bukkit.getOfflinePlayer(player).getUniqueId().toString());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs;
			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

		

	}

	public static Boolean punishPlayer(String player, String staff, String motivo) {
		PreparedStatement stmt;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");

		// Pegar hora atual com a função Date()
	       Date date = new Date();

		try {
			stmt = plugin.getConnection().prepareStatement("INSERT INTO `punicoes` (`uuid`, `data`, `banido`, `staffQuePuniu`, `motivo`) VALUES (?, ?, ?, ?, ?);");
			stmt.setString(1, Bukkit.getOfflinePlayer(player).getUniqueId().toString());
			stmt.setString(2, dateFormat.format(date));
			stmt.setString(3, "true");
			stmt.setString(4, staff);
			stmt.setString(5, motivo);
			int rs = stmt.executeUpdate();
			return true;
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return false;

	}
	public static Boolean unpunishPlayer(String player) {
		try {
			PreparedStatement pstmt2 = plugin.getConnection().prepareStatement("DELETE FROM `punicoes` WHERE `uuid` = ?;");
    		
    		pstmt2.setString(1, Bukkit.getOfflinePlayer(player).getUniqueId().toString());
    		int rs2 = pstmt2.executeUpdate();
    		return true;

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return false;

	}

}
