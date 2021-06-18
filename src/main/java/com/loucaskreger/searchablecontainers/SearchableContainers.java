package com.loucaskreger.searchablecontainers;

import com.loucaskreger.searchablecontainers.config.Config;
import net.fabricmc.api.ModInitializer;

public class SearchableContainers implements ModInitializer {
    public static final String MOD_ID = "searchablecontainers";

    @Override
    public void onInitialize() {
        Config.init();
    }
}
