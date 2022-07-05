package com.loucaskreger.searchablecontainers;

import com.loucaskreger.searchablecontainers.config.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class SearchableContainers {
    public static final String MOD_ID = "searchablecontainers";
    public static final Logger LOGGER = LoggerFactory.getLogger("Searchable Containers");
    
    public static void init() {
        Config.init();
        LOGGER.info("Initialized.");
    }
}
