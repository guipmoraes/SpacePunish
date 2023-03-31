package me.spaceplugins.spacepunish;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


import me.spaceplugins.com.br.api.AutoUpdate;
import me.spaceplugins.com.br.comandos.despunir;
import me.spaceplugins.com.br.comandos.punir;
import me.spaceplugins.com.br.eventos.onJoin;
import me.spaceplugins.com.br.hookplugin.HookPlugin;

public class Main extends JavaPlugin {

	public static Main instance;
	public static FileConfiguration config;

	public static Map<String, String> playerTags = new HashMap<>();
	private Connection connection;

	public static Main getInstance() {
		return instance;
	}

	public void mysqlSetup() {
		String host = config.getString("host");
		String port = config.getString("port");
		String database = config.getString("database");
		String username = config.getString("user");
		String password = config.getString("password");
		

		try {
			synchronized (this) {
				if (getConnection() != null && !getConnection().isClosed())
					return;
				setConnection(DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database,
						username, password));
				Bukkit.getConsoleSender().sendMessage("" + ChatColor.GREEN + "MySQL Conectado");
				PreparedStatement stmt;

				// Pegar hora atual com a função Date()
			       Date date = new Date();

				try {
					stmt = this.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `punicoes` (\r\n"
							+ "				  `uuid` text NOT NULL,\r\n"
							+ "				  `data` text NOT NULL,\r\n"
							+ "				  `banido` text NOT NULL,\r\n"
							+ "				  `staffQuePuniu` text NOT NULL,\r\n"
							+ "				  `motivo` text NOT NULL\r\n"
							+ "				) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;");

					int rs = stmt.executeUpdate();
					
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void onEnable() {
		saveDefaultConfig();
		this.instance = this;
		this.config = getConfig();
		
		mysqlSetup();
		PluginManager pm = Bukkit.getPluginManager();

		Bukkit.getPluginCommand("punir").setExecutor(new punir());
		Bukkit.getPluginCommand("despunir").setExecutor(new despunir());
		pm.registerEvents(new onJoin(), this);
		Bukkit.getConsoleSender().sendMessage("§7==========================================");
		Bukkit.getConsoleSender().sendMessage(" §fO plugin §b§Space§c§lPunish §ffoi §aIniciado!");
		Bukkit.getConsoleSender().sendMessage("§fDesenvolvido pela §bSpacePlugins§f! ");
		Bukkit.getConsoleSender().sendMessage("§7==========================================");
	}

	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("§7==========================================");
		Bukkit.getConsoleSender().sendMessage(" §fO plugin §b§Space§c§lPunish §ffoi §cDesativado");
		Bukkit.getConsoleSender().sendMessage("§fDesenvolvido pela §bSpacePlugins§f! ");
		Bukkit.getConsoleSender().sendMessage("§7==========================================");

	}

	public void onJogadorEntrar(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!p.hasPermission("ultrapvp.admin")) {
			e.setJoinMessage("§c[StaffJoin]§f" + p.getDisplayName());
		}
	}

}