package me.pk2.chetangaland;

import com.bringholm.nametagchanger.NameTagChanger;
import me.pk2.chetangaland.commands.CommandCPrefix;
import me.pk2.chetangaland.config.ConfigLoader;
import me.pk2.chetangaland.config.defaults.prefabs.PrefixPrefab;
import me.pk2.chetangaland.user.User;
import me.pk2.chetangaland.user.listeners.UserListener;
import me.pk2.chetangaland.user.managers.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Chetanga extends JavaPlugin {
    public static Chetanga INSTANCE;

    public UserManager userManager;

    @Override
    public void onEnable() {
        INSTANCE = this;

        saveDefaultConfig();
        ConfigLoader.load();

        userManager = new UserManager();

        Bukkit.getPluginManager().registerEvents(new UserListener(), this);

        /* SCHEDULER */
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            for(User user : userManager.users.values()) {
                if(!user.PLAYER.isValid())
                    return;

                Player player = user.PLAYER;
                PrefixPrefab prefix = user.CONFIG.prefix.get();
                String colored = ChatColor.translateAlternateColorCodes('&', prefix.prefix);

                if(prefix.show.tab)
                    player.setPlayerListName(prefix + player.getName());
                else player.setPlayerListName(player.getName());

                if(prefix.show.tag)
                    NameTagChanger.INSTANCE.changePlayerName(player, prefix + player.getName());
                else NameTagChanger.INSTANCE.changePlayerName(player, player.getName());
            }
        }, 35L, 5L);

        getCommand("cprefix").setExecutor(new CommandCPrefix());
    }
}