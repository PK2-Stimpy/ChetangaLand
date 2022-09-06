package me.pk2.chetangaland.config;

import me.pk2.chetangaland.config.defaults.ConfigDefault;

public class ConfigLoader {
    public static void load() {
        ConfigDefault.load();
    }

    public static void save() {
        ConfigDefault.save();
    }
}