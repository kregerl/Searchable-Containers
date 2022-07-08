package com.loucaskreger.searchablecontainers.forge;

import net.minecraft.client.option.KeyBinding;

import java.util.ArrayList;
import java.util.List;

public class KeyBindingHelperExpectPlatformImpl {
    public static final List<KeyBinding> keyLists = new ArrayList<>();

    public static KeyBinding registerKey(KeyBinding key) {
        keyLists.add(key);
        return key;
    }
}
