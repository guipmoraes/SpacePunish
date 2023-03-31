package me.spaceplugins.com.br.comandos;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.spaceplugins.com.br.api.Punish;
import me.spaceplugins.spacepunish.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class punir implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if(sender.hasPermission("spacepunish.punir")) {
			if(args.length == 0) {
				if(!(sender instanceof Player)) {
					sender.sendMessage("§cUse /punir <jogador> <motivo>");
					return false;
				}else {
				sender.sendMessage("§cUse /punir <jogador> (motivo)");
				return false;
				}
			}
			if(Punish.isPunished(args[0]) == false) {
				
				if(args.length == 1) {
					if(!(sender instanceof Player)) {
						sender.sendMessage("§cUse /punir <jogador> <motivo>");
						return false;
					}
					Player p = (Player)sender;
					TextComponent trapaças = new TextComponent();
					trapaças.setText("   §7• Trapaças");
					trapaças.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/punir " + args[0] + " Trapaças"));
					trapaças.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("/punir " + args[0] + " Trapaças")).create()));;
					TextComponent preconceito = new TextComponent();
					preconceito.setText("   §7• Preconceito");
					preconceito.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/punir " + args[0] + " Preconceito"));
					preconceito.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("/punir " + args[0] + " Preconceito")).create()));;
					TextComponent abuso = new TextComponent();
					abuso.setText("   §7• Abuso de Bugs");
					abuso.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/punir " + args[0] + " Absuo de Bugs"));
					abuso.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("/punir " + args[0] + " Absuo de Bugs")).create()));;
					TextComponent antijogo = new TextComponent();
					antijogo.setText("   §7• Anti-Jogo");
					antijogo.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/punir " + args[0] + " args"));
					antijogo.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("/punir " + args[0] + " Anti-Jogo")).create()));;
					TextComponent outros = new TextComponent();
					outros.setText("   §7• Outros");
					outros.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/punir " + args[0] + " "));
					outros.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("/punir " + args[0] + " ...")).create()));;
					p.sendMessage("§fSelecione o motivo da Punição");
					p.sendMessage("");
					p.spigot().sendMessage(trapaças);
					p.spigot().sendMessage(preconceito);
					p.spigot().sendMessage(abuso);
					p.spigot().sendMessage(antijogo);
					p.spigot().sendMessage(outros);
					
				}else if(args.length > 1) {
					final StringBuilder sb = new StringBuilder();
			        for (int i = 1; i < args.length; ++i) {
			            sb.append(args[i]);
			            sb.append(" ");
			        }
			        String s = sb.toString().trim();
			        s = s.replaceAll("\\\\n", "\n");
					Punish.punishPlayer(args[0], sender.getName(), s);
					if(Bukkit.getOfflinePlayer(args[0]).isOnline()) {
						Bukkit.getPlayer(args[0]).kickPlayer(Main.getInstance().getConfig().getString("messages.banido_mensagem").replace("%BANIDO%", args[0]).replace("%MOTIVO%", s).replace("%STAFF%", sender.getName()).replace("&", "§"));
					}
					Bukkit.broadcastMessage(Main.getInstance().getConfig().getString("messages.banido_servidor").replace("%BANIDO%", args[0]).replace("%MOTIVO%", s).replace("%STAFF%", sender.getName()).replace("&", "§"));
				}
				
			} else {
				sender.sendMessage("§c§lERRO §fEste jogador já está banito");
			}
		}
		return false;
	}

}