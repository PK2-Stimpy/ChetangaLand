package me.pk2.chetangaland.config.defaults.undefined;

import me.pk2.chetangaland.config.defaults.prefabs.PrefixPrefab;
import me.pk2.chetangaland.config.defaults.prefabs.PrefixShowPrefab;

public class PrefixUndefined extends PrefixPrefab {
    public PrefixUndefined() {
        super("NONE", "", new PrefixShowPrefab(false, false, false));
    }
}