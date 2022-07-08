package com.loucaskreger.searchablecontainers;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.option.KeyBinding;

public class KeyBindingHelperExpectPlatform {
    @ExpectPlatform
    public static KeyBinding registerKey(KeyBinding key) {
        throw new AssertionError();
    }
}
