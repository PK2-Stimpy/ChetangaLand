package me.pk2.chetangaland.commands;

import me.pk2.chetangaland.Chetanga;
import me.pk2.chetangaland.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.pk2.chetangaland.util.ChatColorUtil.c;
import static me.pk2.chetangaland.util.TimeUtil.toms;

public class CommandMute implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length < 1) {
            sender.sendMessage(c("&aChetangaLand Core v" + Chetanga.INSTANCE.getDescription().getVersion() + " &emade by &aPK2_Stimpy. &b/mute <player> <time>"));
            return true;
        }

        if(!sender.hasPermission("chetanga.command.mute")) {
            sender.sendMessage(c("&cInvalid permission!"));
            return true;
        }

        if(args.length < 2) {
            sender.sendMessage(c("&c/mute <player> <time>"));
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if(player == null) {
            sender.sendMessage(c("&cThe specified player is offline."));
            return true;
        }

        User user = Chetanga.INSTANCE.userManager.users.get(player.getUniqueId().toString());
        if(user == null) {
            sender.sendMessage(c("&cThe player is not listed as user? &4Contact PK2_Stimpy#7089"));
            return true;
        }

        long ms = toms(args[2]);
        if(ms < 0) {
            sender.sendMessage(c("&cInvalid time expression! Expressions: &4{y/d/h/m/s}"));
            return true;
        }

        user.CONFIG.muted = System.currentTimeMillis()+ms;
        user.save();
        sender.sendMessage(c("&aMuted player &e" + player.getName() + " &afor &e" + ms));
        return true;
    }
}