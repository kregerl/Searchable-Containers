package com.loucaskreger.searchablecontainers;

import com.loucaskreger.searchablecontainers.config.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class SearchableContainers {
    public static final String MOD_ID = "searchablecontainers";
    public static final Logger LOGGER = LoggerFactory.getLogger("Searchable Containers");
    public static KeyBinding HIDE_KEY;
    public static KeyBinding FOCUS_KEY;
    
    public static void init() {
        Config.init();
        HIDE_KEY = KeyBindingHelperExpectPlatform.registerKey(new KeyBinding(
                MOD_ID + ".key.hide", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, MOD_ID + ".key.categories"));
        FOCUS_KEY = KeyBindingHelperExpectPlatform.registerKey(new KeyBinding(
                MOD_ID + ".key.focus", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F, MOD_ID + ".key.categories"));
        LOGGER.info("Initialized.");
    }
}
