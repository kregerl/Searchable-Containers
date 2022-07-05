package com.loucaskreger.searchablecontainers.widget;

import com.loucaskreger.searchablecontainers.SearchableContainers;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.Vec3i;

public class ArrowButtonWidget extends ButtonWidget {
    private static final Identifier TEXTURE = new Identifier(SearchableContainers.MOD_ID, "textures/gui/arrow.png");
    public static final int BUTTON_WIDTH = 13;
    public static final int BUTTON_HEIGHT = 13;
    public static final int ARROW_WIDTH = 9;
    public static final int ARROW_HEIGHT = 8;

    private final Orientation orientation;

    public ArrowButtonWidget(int x, int y, PressAction onPress, Orientation orientation, TooltipSupplier tooltip) {
        super(x, y, BUTTON_WIDTH, BUTTON_HEIGHT, Text.empty(), onPress, tooltip);
        this.orientation = orientation;
    }

    public ArrowButtonWidget(int x, int y, PressAction onPress) {
        this(x, y, onPress, Orientation.UP, EMPTY);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.drawTexture(matrices, this.x, this.y, 0, this.isHovered() ? BUTTON_HEIGHT : 0, BUTTON_WIDTH, BUTTON_HEIGHT);
        matrices.push();
        var translation = this.orientation.getTranslation();
        matrices.translate(translation.getX() + this.x + 2, translation.getY() + this.y + 2, translation.getZ());
        matrices.multiply(this.orientation.getRotation());
        this.drawTexture(matrices, 0, 0, 0, BUTTON_HEIGHT * 2, ARROW_WIDTH, ARROW_HEIGHT);
        matrices.pop();
        if (this.isHovered()) {
            this.renderTooltip(matrices, mouseX, mouseY);
        }
    }


    public enum Orientation {
        UP(Vec3f.POSITIVE_Z.getDegreesQuaternion(0.0f), new Vec3i(0, 0, 0)),
        DOWN(Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0f), new Vec3i(ARROW_WIDTH, ARROW_HEIGHT, 0)),
        LEFT(Vec3f.POSITIVE_Z.getDegreesQuaternion(-90.0f), new Vec3i(0, ARROW_WIDTH, 0)),
        RIGHT(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0f), new Vec3i(ARROW_WIDTH, 0, 0));

        private final Quaternion quaternion;
        private final Vec3i translation;

        Orientation(Quaternion quaternion, Vec3i translation) {
            this.quaternion = quaternion;
            this.translation = translation;
        }

        public Quaternion getRotation() {
            return this.quaternion;
        }

        public Vec3i getTranslation() {
            return this.translation;
        }
    }

    private void onTooltip(ButtonWidget button, MatrixStack matrices, int mouseX, int mouseY) {
    }
}
