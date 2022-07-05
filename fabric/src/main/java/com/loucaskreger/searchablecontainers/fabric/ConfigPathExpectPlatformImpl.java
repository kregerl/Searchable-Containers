package com.loucaskreger.searchablecontainers.fabric;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class ConfigPathExpectPlatformImpl {
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
