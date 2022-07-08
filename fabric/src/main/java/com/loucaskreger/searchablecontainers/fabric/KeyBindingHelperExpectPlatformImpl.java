package com.loucaskreger.searchablecontainers.fabric;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;

public class KeyBindingHelperExpectPlatformImpl {
    public static KeyBinding registerKey(KeyBinding key) {
        return KeyBindingHelper.registerKeyBinding(key);
    }
}
