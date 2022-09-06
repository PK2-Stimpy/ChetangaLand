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

        Set<String> keys = configuration.getConfigurationSection("prefixes").getKeys(false);
        for(String key : keys) {
            System.out.println(key);
            ConfigurationSection section = configuration.getConfigurationSection("prefixes").getConfigurationSection(key);
            PrefixPrefab prefab = new PrefixPrefab(key, section.getString("prefix"), section.getBoolean("show.tab"), section.getBoolean("show.chat"), section.getBoolean("show.tag"));
            PrefixPrefab.prefabs.put(prefab.id, prefab);

            prefixes.put(key, prefab);
        }
    }

    public static void save() {
        FileConfiguration configuration = INSTANCE.getConfig();
        configuration.set("prefixes", null);

        for(PrefixPrefab prefab : prefixes.values()) {
            configuration.set("prefixes." + prefab.id + ".prefix", prefab.prefix);
            configuration.set("prefixes." + prefab.id + ".show.tab", prefab.show.tab);
            configuration.set("prefixes." + prefab.id + ".show.chat", prefab.show.chat);
            configuration.set("prefixes." + prefab.id + ".show.tag", prefab.show.tag);
        }

        INSTANCE.saveConfig();
    }
}