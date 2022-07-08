package com.loucaskreger.searchablecontainers.mixin;

import com.loucaskreger.searchablecontainers.SearchableContainers;
import com.loucaskreger.searchablecontainers.config.Config;
import com.loucaskreger.searchablecontainers.widget.ArrowButtonWidget;
import com.loucaskreger.searchablecontainers.widget.SmartTextField;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("unused")
@Mixin(HandledScreen.class)
public class HandledScreenMixin extends Screen {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static final int BUTTON_PADDING = 5;
    private static final int COLOR = 0x90000000;
    private static final int PLAYER_INVENTORY_INDEX_SIZE = 35;
    private SmartTextField textField;
    private final ButtonWidget.TooltipSupplier toInventoryTooltipSupplier = (button, matrices, mouseX, mouseY) -> this.renderOrderedTooltip(matrices, Arrays.asList(Text.literal("Moves items from container").asOrderedText(), Text.literal(" to the players inventory").asOrderedText()), mouseX, mouseY);
    private final ButtonWidget.TooltipSupplier toContainerTooltipSupplier = (button, matrices, mouseX, mouseY) -> this.renderOrderedTooltip(matrices, Arrays.asList(Text.literal("Moves items from the player's").asOrderedText(), Text.literal("inventory to the open container").asOrderedText()), mouseX, mouseY);
    private HandledScreenAccessor accessor;

    protected HandledScreenMixin(Text title) {
        super(title);
    }

    // Inject at end of init and add textField to the screen.
    @Inject(at = @At(value = "TAIL"), method = "init()V", cancellable = true)
    private void onInit(CallbackInfo ci) {
        // whether or not this is the creative screen.
        this.accessor = ((HandledScreenAccessor) this);
        var isCreativeScreen = (Screen) this instanceof CreativeInventoryScreen;
        var isInventoryScreen = (Screen) this instanceof AbstractInventoryScreen;
        var x = this.accessor.getContainerX() + (this.accessor.getBackgroundWidth() / 2) - (SmartTextField.FIELD_WIDTH / 2);
        var y = this.accessor.getContainerY() - SmartTextField.FIELD_HEIGHT - Config.INSTANCE.verticalPadding;
        if (!isCreativeScreen) {
            this.textField = new SmartTextField(mc.textRenderer, x, y, Text.literal(SmartTextField.currentText));
            this.textField.setChangedListener(this::textFieldChanged);
            this.addDrawableChild(this.textField);
        }
        if (!isInventoryScreen) {
            // int x, int y, int width, int height, Text message, ButtonWidget.PressAction onPress, ButtonWidget.TooltipSupplier tooltipSupplier
            ArrowButtonWidget toInventoryButton = new ArrowButtonWidget(x + SmartTextField.FIELD_WIDTH + BUTTON_PADDING, y, this::toInventoryButtonPressed, ArrowButtonWidget.Orientation.DOWN, Config.INSTANCE.showButtonTooltips ? toInventoryTooltipSupplier : ButtonWidget.EMPTY);
            ArrowButtonWidget toContainerButton = new ArrowButtonWidget(x + SmartTextField.FIELD_WIDTH + ((2 * BUTTON_PADDING) + ArrowButtonWidget.BUTTON_WIDTH), y, this::toContainerButtonPressed, ArrowButtonWidget.Orientation.UP, Config.INSTANCE.showButtonTooltips ? toContainerTooltipSupplier : ButtonWidget.EMPTY);
            this.addDrawableChild(toInventoryButton);
            this.addDrawableChild(toContainerButton);
        }
    }

    @Inject(at = @At(value = "TAIL"), method = "render")
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        DefaultedList<Slot> slots = ((HandledScreen) (Screen) this).getScreenHandler().slots;
        for (Slot slot : slots) {
            if (this.textField != null && !SmartTextField.currentText.equals("") && !this.stackMatches(this.textField.getText(), slot.getStack())) {
                var x = slot.x + this.accessor.getContainerX();
                var y = slot.y + this.accessor.getContainerY();
                RenderSystem.disableDepthTest();
                DrawableHelper.fill(matrices, x, y, x + 16, y + 16, COLOR);
                RenderSystem.enableDepthTest();
            }
        }
    }


    private void textFieldChanged(String text) {
        SmartTextField.currentText = text;
    }

    private void toInventoryButtonPressed(ButtonWidget button) {
        var interactionManager = mc.interactionManager;
        var player = mc.player;
        var slots = ((HandledScreen) (Screen) this).getScreenHandler().slots;
        for (int i = 0; i < slots.size() - PLAYER_INVENTORY_INDEX_SIZE; i++) {
            if (this.textField != null && !SmartTextField.currentText.equals("") && this.stackMatches(this.textField.getText(), slots.get(i).getStack())) {
                interactionManager.clickSlot(((HandledScreen) (Screen) this).getScreenHandler().syncId, i, 0, SlotActionType.QUICK_MOVE, player);
            }
        }
    }

    private void toContainerButtonPressed(ButtonWidget button) {
        var interactionManager = mc.interactionManager;
        var player = mc.player;
        var slots = ((HandledScreen) (Screen) this).getScreenHandler().slots;
        for (int i = slots.size() - 1 - PLAYER_INVENTORY_INDEX_SIZE; i < slots.size(); i++) {
            if (this.textField != null && !SmartTextField.currentText.equals("") && this.stackMatches(this.textField.getText(), slots.get(i).getStack())) {
                interactionManager.clickSlot(((HandledScreen) (Screen) this).getScreenHandler().syncId, i, 0, SlotActionType.QUICK_MOVE, player);
            }
        }

    }


    @Inject(at = @At(value = "HEAD"), method = "keyPressed", cancellable = true)
    private void onKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> ci) {
        if (this.textField != null) {
            if (keyCode == GLFW.GLFW_KEY_E && this.textField.isFocused()) {
                ci.cancel();
            }

            if (keyCode == SearchableContainers.HIDE_KEY.getDefaultKey().getCode() && modifiers == 2 && !this.textField.isFocused()) {
                SmartTextField.isVisible = !SmartTextField.isVisible;
                this.textField.setVisible(SmartTextField.isVisible);
            }

            if (keyCode == SearchableContainers.FOCUS_KEY.getDefaultKey().getCode() && modifiers == 2) {
                this.textField.setTextFieldFocused(true);
            }
        }
    }


    /**
     * Checks if the ItemStack has the passed string as its name or tooltip.
     *
     * @param text  The text to compare to the item
     * @param stack The ItemStack to compare to the text
     * @return true if the ItemStack contains the text
     */
    public boolean stackMatches(String text, ItemStack stack) {
        if (stack.getItem().equals(Items.AIR)) {
            return false;
        }
        if (stack.getItem().equals(Items.AIR) && text.equals("")) {
            return true;
        }
        ArrayList<String> keys = new ArrayList<>();

        List<Text> tooltip = stack.getTooltip(mc.player, mc.options.advancedItemTooltips ? TooltipContext.Default.ADVANCED : TooltipContext.Default.NORMAL);

        // if searchName, only search in the name. Otherwise search every part of tooltip.
        if (!Config.INSTANCE.searchTooltips) {
            keys.add(tooltip.get(0).getString());
        } else {
            for (Text line : tooltip) {
                keys.add(line.getString());
            }
        }

        for (String key : keys) {
            if (key.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))) {
                return true;
            }
        }
        return false;
    }

}
