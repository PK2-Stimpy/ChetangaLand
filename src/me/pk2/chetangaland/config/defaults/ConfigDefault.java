package me.pk2.chetangaland.config.defaults;

import static me.pk2.chetangaland.Chetanga.INSTANCE;

import me.pk2.chetangaland.config.defaults.prefabs.PrefixPrefab;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Set;

public class ConfigDefault {
    public static final HashMap<String, PrefixPrefab> prefixes = new HashMap<>();
    public static void load() {
        INSTANCE.reloadConfig();

        FileConfiguration configuration = INSTANCE.getConfig();
        PrefixPrefab.prefabs.clear();

        Set<String> keys = configuration.getKeys(false);
        for(String key : keys) {
            ConfigurationSection section = configuration.getConfigurationSection("prefixes." + key);
            PrefixPrefab prefab = new PrefixPrefab(key, section.getString("prefix"), section.getBoolean("show.tab"), section.getBoolean("show.chat"), section.getBoolean("show.tag"));
            PrefixPrefab.prefabs.put(prefab.id, prefab);

            prefixes.put(key, prefab);
        }
    }

    public static void save() {
        FileConfiguration configuration = INSTANCE.getConfig();
        configuration.set("prefixes", null);

        for(PrefixPrefab prefab : prefixes.values()) {
            ConfigurationSection section = configuration.createSection("prefixes." + prefab.id);
            section.set("prefix", prefab.prefix);

            ConfigurationSection show = configuration.createSection("show");
            show.set("tab", prefab.show.tab);
            show.set("chat", prefab.show.chat);
            show.set("tag", prefab.show.tag);

            section.set("show", show);
            configuration.set("prefixes." + prefab.id, section);
        }

        INSTANCE.saveConfig();
    }
}