package me.pk2.chetangaland.config.defaults.prefabs;

import java.util.HashMap;

public class PrefixPrefab {
    public static final HashMap<String, PrefixPrefab> prefabs = new HashMap<>();

    public final String id;
    public String prefix;
    public PrefixShowPrefab show;

    public PrefixPrefab(String id, String prefix, PrefixShowPrefab show) {
        this.id = id;
        this.prefix = prefix;
        this.show = show;
    }
    public PrefixPrefab(String id, String prefix, boolean tab, boolean chat, boolean tag) { this(id, prefix, new PrefixShowPrefab(tab, chat, tag)); }
}