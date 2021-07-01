package com.loucaskreger.searchablecontainers.widget;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_2;

public class SmartTextField extends TextFieldWidget {
    public static boolean isVisible = true;
    public static String currentText = "";

    public static final int FIELD_WIDTH = 101;
    public static final int FIELD_HEIGHT = 11;


    public SmartTextField(TextRenderer textRenderer, int x, int y, Text text) {
        super(textRenderer, x, y, FIELD_WIDTH, FIELD_HEIGHT, text);
        this.setText(currentText);
        this.setVisible(isVisible);
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean isWithinBounds = mouseX >= (double) this.x && mouseX < (double) (this.x + this.width) && mouseY >= (double) this.y && mouseY < (double) (this.y + this.height);
        // Clear text on right click
        if (button == GLFW_MOUSE_BUTTON_2 && isWithinBounds) {
            currentText = "";
            this.setText(currentText);
            // Fake a left click to select the widget.
            return super.mouseClicked(mouseX, mouseY, 0);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
