package com.loucaskreger.searchablecontainers.forge;

import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class ConfigPathExpectPlatformImpl {
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
