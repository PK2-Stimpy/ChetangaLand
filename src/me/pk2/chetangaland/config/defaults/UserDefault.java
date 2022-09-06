package me.pk2.chetangaland.config.defaults;

import me.pk2.chetangaland.Chetanga;
import me.pk2.chetangaland.config.defaults.prefabs.PrefixPrefab;
import me.pk2.chetangaland.config.defaults.undefined.Undefined;
import me.pk2.chetangaland.user.User;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicReference;

public class UserDefault {
    public User user;
    public AtomicReference<PrefixPrefab> prefix;
    public long muted;
    public YamlConfiguration configuration;

    public UserDefault() {
        prefix = new AtomicReference<>();
    }

    public void load(User user) {
        this.user = user;

        File file = new File(Chetanga.INSTANCE.getDataFolder(), "data/" + user.UUID + ".yml");
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                PrintWriter writer = new PrintWriter(file);
                writer.write("prefix: NONE\nmuted: 0");
                writer.close();
            } catch (FileNotFoundException e) { e.printStackTrace(); }
        }

        configuration = new YamlConfiguration();
        try {
            configuration.load(file);
        } catch (Exception exception) { exception.printStackTrace(); }

        if(configuration.get("prefix") == null)
            configuration.set("prefix", "NONE");
        if(configuration.get("muted") == null)
            configuration.set("muted", 0L);

        String cPrefix = configuration.getString("prefix");
        prefix.set(PrefixPrefab.prefabs.getOrDefault(cPrefix, Undefined.PREFIX_UNDEFINED));
        muted = configuration.getLong("muted");
    }

    public void save() {
        File file = new File(Chetanga.INSTANCE.getDataFolder(), "data/" + user.UUID + ".yml");

        configuration.set("prefix", prefix.get().id);
        configuration.set("muted", muted);
        try {
            configuration.save(file);
        } catch (Exception exception) { exception.printStackTrace(); }
    }
}