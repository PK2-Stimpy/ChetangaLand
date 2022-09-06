package me.pk2.chetangaland.util;

import org.bukkit.ChatColor;

public class ChatColorUtil {
    public static String c(String text) { return ChatColor.translateAlternateColorCodes('&', text); }
}