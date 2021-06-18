package com.loucaskreger.searchablecontainers;

import com.loucaskreger.searchablecontainers.config.Config;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class SearchableContainers implements ModInitializer {
    public static final String MOD_ID = "searchablecontainers";
    public static final KeyBinding HIDE_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            MOD_ID + ".key.hide", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, MOD_ID + ".key.categories"));

    @Override
    public void onInitialize() {
        Config.init();
    }
}
