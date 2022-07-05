package com.loucaskreger.searchablecontainers;

import dev.architectury.injectables.annotations.ExpectPlatform;

import java.nio.file.Path;

public class ConfigPathExpectPlatform {
    @ExpectPlatform
    public static Path getConfigDirectory() {
        throw new AssertionError();
    }
}
