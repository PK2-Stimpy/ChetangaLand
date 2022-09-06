package me.pk2.chetangaland.commands;

import me.pk2.chetangaland.storage.ChatStorage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static me.pk2.chetangaland.util.ChatColorUtil.c;

public class CommandToggleChat implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("chetanga.command.togglechat")) {
            sender.sendMessage(c("&cInvalid permission!"));
            return true;
        }

        ChatStorage.chat_enabled = !ChatStorage.chat_enabled;
        sender.sendMessage(c("&eChat has been " + (ChatStorage.chat_enabled ? "&aENABLED" : "&cDISABLED")));
        return false;
    }
}