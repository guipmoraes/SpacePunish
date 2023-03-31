package me.spaceplugins.com.br.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.spaceplugins.com.br.api.Punish;

public class despunir implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if(sender.hasPermission("spacepunish.despunir")) {
			if(Punish.isPunished(args[0]) == true) {
				sender.sendMessage("§aPlayer desbanido com sucesso");
				Bukkit.broadcastMessage("§aO jogador " + args[0] + " foi desbanido por " + sender.getName());
				Punish.unpunishPlayer(args[0]);
			}else {
				sender.sendMessage("§c§lERRO §fO player não está banido");
			}
		}
		return false;
	}

}
