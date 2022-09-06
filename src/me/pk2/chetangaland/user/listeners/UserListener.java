package me.pk2.chetangaland.user.listeners;

import me.pk2.chetangaland.Chetanga;
import me.pk2.chetangaland.config.defaults.prefabs.PrefixPrefab;
import me.pk2.chetangaland.user.User;
import me.pk2.chetangaland.user.managers.UserManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static me.pk2.chetangaland.util.ChatColorUtil.c;

public class UserListener implements Listener {
    private UserManager userManager;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        userManager = Chetanga.INSTANCE.userManager;

        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        if (userManager.users.containsKey(uuid))
            return;

        User user = new User(uuid, player);
        user.load();
        Chetanga.INSTANCE.userManager.users.put(uuid, user);
    }

    private void handleQuit(Player player) {
        userManager = Chetanga.INSTANCE.userManager;

        String uuid = player.getUniqueId().toString();
        if(!userManager.users.containsKey(uuid))
            return;

        Chetanga.INSTANCE.userManager.users.remove(uuid);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        handleQuit(event.getPlayer());
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        handleQuit(event.getPlayer());
    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        User user = Chetanga.INSTANCE.userManager.users.get(event.getPlayer().getUniqueId().toString());
        if(user.isMuted()) {
            user.PLAYER.sendMessage(c("You are muted."));

            event.setCancelled(true);
            return;
        }

        PrefixPrefab prefab = user.CONFIG.prefix.get();
        String colored = ChatColor.translateAlternateColorCodes('&', prefab.prefix);
        event.setFormat(event.getFormat().replaceFirst(event.getPlayer().getName(), colored + event.getPlayer().getName()));
    }
}