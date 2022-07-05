package com.loucaskreger.searchablecontainers.fabric;

import com.loucaskreger.searchablecontainers.SearchableContainers;
import net.fabricmc.api.ClientModInitializer;

public class SearchableContainersFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SearchableContainers.init();
    }
}
