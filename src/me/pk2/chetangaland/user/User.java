package me.pk2.chetangaland.user;

import me.pk2.chetangaland.config.defaults.UserDefault;
import org.bukkit.entity.Player;

public class User {
    public final String UUID;
    public final Player PLAYER;
    public final UserDefault CONFIG;
    public User(String uuid, Player player) {
        this.UUID = uuid;
        this.PLAYER = player;
        this.CONFIG = new UserDefault();
    }

    public void load() {
        CONFIG.load(this);
    }

    public void save() {
        CONFIG.save();
    }

    public boolean isMuted() { return CONFIG.muted<0||System.currentTimeMillis()<CONFIG.muted; }
}