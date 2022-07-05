package com.loucaskreger.searchablecontainers.forge;

import com.loucaskreger.searchablecontainers.SearchableContainers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SearchableContainers.MOD_ID)
public class SearchableContainersForge {
    public SearchableContainersForge() {
        IEventBus EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
        EVENT_BUS.addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        SearchableContainers.init();
    }
}
