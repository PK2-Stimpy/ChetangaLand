package me.pk2.chetangaland.commands;

import static me.pk2.chetangaland.util.ChatColorUtil.c;

import me.pk2.chetangaland.Chetanga;
import me.pk2.chetangaland.config.ConfigLoader;
import me.pk2.chetangaland.config.defaults.prefabs.PrefixPrefab;
import me.pk2.chetangaland.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

public class CommandCPrefix implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length < 1) {
            sender.sendMessage(c("&aChetangaLand Core v" + Chetanga.INSTANCE.getDescription().getVersion() + " &emade by &aPK2_Stimpy. &b/cprefix help"));
            return true;
        }

        mode: switch (args[0].toLowerCase(Locale.ROOT)) {
            case "set" -> {
                if(!sender.hasPermission("chetanga.command.cprefix.set")) {
                    sender.sendMessage(c("&cInvalid permission!"));
                    break;
                }

                if(args.length < 3) {
                    sender.sendMessage(c("&c/cprefix set <player> <prefix>"));
                    break;
                }

                Player player = Bukkit.getPlayer(args[1]);
                if(player == null || !player.isValid()) {
                    sender.sendMessage(c("&cThe specified player is disconnected."));
                    break;
                }

                PrefixPrefab prefab = PrefixPrefab.prefabs.get(args[2]);
                if(prefab == null) {
                    sender.sendMessage(c("&cThe specified prefix does not exist."));
                    break;
                }

                User user = Chetanga.INSTANCE.userManager.users.get(player.getUniqueId().toString().toLowerCase());
                user.CONFIG.prefix.set(prefab);
                user.save();
                sender.sendMessage(c("&aChanged &e" + player.getName() + " &aprefix to &e\"&r" + prefab.prefix + "&e\""));
            }
            case "edit" -> {
                if(!sender.hasPermission("chetanga.command.cprefix.edit")) {
                    sender.sendMessage(c("&cInvalid permission!"));
                    break;
                }

                if(args.length < 3) {
                    sender.sendMessage(c("&c/cprefix edit <prefix> <text>"));
                    break;
                }

                PrefixPrefab prefab = PrefixPrefab.prefabs.get(args[1]);
                if(prefab == null) {
                    sender.sendMessage(c("&cThe specified prefix does not exist."));
                    break;
                }

                StringBuilder builder = new StringBuilder();
                for(int i = 2; i < args.length; i++)
                    builder.append(args[i] + " ");

                String text = builder.toString().substring(0, builder.length()-1);
                text = text.replaceAll("\"", "");
                prefab.prefix = text;

                ConfigLoader.save();
                sender.sendMessage(c("&aThe prefix &a" + prefab.id + " &ais now &r" + prefab.prefix));
            }
            case "show" -> {
                if(!sender.hasPermission("chetanga.command.cprefix.show")) {
                    sender.sendMessage(c("&cInvalid permission!"));
                    break;
                }

                if(args.length < 3) {
                    sender.sendMessage(c("&c/cprefix show <prefix> {tab/chat/tag}"));
                    break;
                }

                PrefixPrefab prefab = PrefixPrefab.prefabs.get(args[1]);
                if(prefab == null) {
                    sender.sendMessage(c("&cThe specified prefix does not exist."));
                    break;
                }

                option: switch(args[2].toLowerCase()) {
                    case "tab" -> {
                        prefab.show.tab = !prefab.show.tab;
                        sender.sendMessage(c("&eToggled tab from " + prefab.id + " " + (prefab.show.tab?"&aON":"&cOFF")));
                    }

                    case "chat" -> {
                        prefab.show.chat = !prefab.show.chat;
                        sender.sendMessage(c("&eToggled chat from " + prefab.id + " " + (prefab.show.chat?"&aON":"&cOFF")));
                    }

                    case "tag" -> {
                        prefab.show.tag = !prefab.show.tag;
                        sender.sendMessage(c("&eToggled tag from " + prefab.id + " " + (prefab.show.tag?"&aON":"&cOFF")));
                    }

                    default -> sender.sendMessage(c("&c/cprefix show <prefix> {tab/chat/tag}"));
                }

                ConfigLoader.save();
            }
            case "create" -> {
                if(!sender.hasPermission("chetanga.command.cprefix.create")) {
                    sender.sendMessage(c("&cInvalid permission!"));
                    break;
                }

                if(args.length < 3) {
                    sender.sendMessage(c("&c/cprefix create <prefix> <text>"));
                    break;
                }

                PrefixPrefab prefab = PrefixPrefab.prefabs.get(args[1]);
                if(prefab != null) {
                    sender.sendMessage(c("&cThe specified prefix already exists."));
                    break;
                }

                StringBuilder builder = new StringBuilder();
                for(int i = 2; i < args.length; i++)
                    builder.append(args[i] + " ");
                String prefix = builder.toString().substring(0, builder.length()-1).replaceAll("\"", "");

                prefab = new PrefixPrefab(args[1], prefix, false, false, false);
                PrefixPrefab.prefabs.put(prefab.id, prefab);
                sender.sendMessage(c("&aCreated new prefix with name &e" + prefab.id + ": &r" + prefab.prefix));

                ConfigLoader.save();
            }
            case "remove" -> {
                if(!sender.hasPermission("chetanga.command.cprefix.remove")) {
                    sender.sendMessage(c("&cInvalid permission!"));
                    break;
                }

                if(args.length < 2) {
                    sender.sendMessage(c("&c/cprefix remove <prefix>"));
                    break;
                }

                if(PrefixPrefab.prefabs.containsKey(args[1])) {
                    PrefixPrefab.prefabs.remove(args[1]);
                    sender.sendMessage(c("&aRemoved prefix &e" + args[1]));
                    break;
                } else sender.sendMessage(c("&cCould not find prefix &e" + args[1]));

                ConfigLoader.save();
            }

            default -> {
                sender.sendMessage(c("&a \n&r" +
                        "&e----- ChetangaLand Prefix -----\n" +
                        "  &a/cprefix help\n" +
                        "  &a/cprefix set <player> <prefix>\n" +
                        "  &a/cprefix edit <prefix> <text>\n" +
                        "  &a/cprefix show <prefix> {tab/chat/tag}\n" +
                        "  &a/cprefix create <prefix> <text>\n" +
                        "  &a/cprefix remove <prefix>\n&r&l &r"));
            }
        }
        return true;
    }
}