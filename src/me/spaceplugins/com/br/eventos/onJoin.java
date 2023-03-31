package me.spaceplugins.com.br.eventos;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

import me.spaceplugins.com.br.api.Punish;
import me.spaceplugins.spacepunish.Main;

public class onJoin implements Listener {
	
	
	@EventHandler
	public void onPreJoin(PlayerJoinEvent e) throws SQLException {
		if(Punish.isPunished(e.getPlayer().getName())) {
			e.getPlayer().kickPlayer(Main.getInstance().getConfig().getString("messages.banido_mensagem").replace("%BANIDO%", e.getPlayer().getName()).replace("%MOTIVO%", Punish.getPunishByNick(e.getPlayer().getName()).getString("motivo")).replace("%STAFF%", Punish.getPunishByNick(e.getPlayer().getName()).getString("staffQuePuniu")));
		}
		Bukkit.getConsoleSender().sendMessage("Jogador entrando " + e.getPlayer().getName());
	}

}
