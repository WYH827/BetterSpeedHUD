package com.horizon.speedhud.client;

import com.horizon.speedhud.config.HudConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

public final class HorizonHud {

    private static final int HUD_W = 200;
    private static final int HUD_H = 70;
    private static final int MARGIN = 12;

    private static final int BAR_W = 200;
    private static final int BAR_H_BASE = 6;

    private static final int COLOR_WHITE = 0xFFFFFFFF;
    private static final int COLOR_GRAY = 0xFF9A9A9A;
    private static final int COLOR_DIM = 0xFFBBBBBB;
    private static final int COLOR_BAR_BG = 0x55333333;
    private static final int COLOR_BAR_FILL = 0xFF00D8FF;

    // Font IDs do NOT include the "font/" prefix: assets/speedhud/font/modern.json
    // is registered as "speedhud:modern".
    private static final ResourceLocation MODERN_FONT = ResourceLocation.fromNamespaceAndPath("speedhud", "modern");

    // Modern bitmap font layout: 64x192 cells, glyph ink bottom-aligned to the
    // cell bottom (row 192), tall glyphs start at texture row 110. Minecraft
    // always places the cell's bottom row on the text baseline, so the ink must
    // sit in the lower part of the cell; the draw position is compensated by
    // MODERN_INK_TOP units inside the scaled space.
    private static final int MODERN_INK_TOP = 110;
    private static final float GEAR_BASE_MODERN = 0.11F;
    private static final float UNIT_BASE_MODERN = 0.15F;
    private static final float SPEED_BASE_MODERN = 0.29F;
    private static final float SPEED_BASE_DEFAULT = 2.5F;

    private HorizonHud() {
    }

    public static void draw(GuiGraphics g, int screenW, int screenH, int speed, float progress) {
        Minecraft mc = Minecraft.getInstance();
        boolean modern = "modern".equals(HudConfig.getFont());

        float overall = HudConfig.getScale();
        int xOff = HudConfig.getXOffset();
        int yOff = HudConfig.getYOffset();

        // Anchor: bottom-right corner, scaled block keeps a small margin from the screen edge
        float right = screenW - MARGIN + xOff;
        float bottom = screenH - MARGIN + yOff;

        PoseStack pose = g.pose();
        pose.pushPose();
        pose.translate(right, bottom, 0);
        pose.scale(overall, overall, 1.0F);
        pose.translate(-HUD_W, -HUD_H, 0);

        // Gear indicator "D"
        if (HudConfig.isShowGear()) {
            drawText(g, pose, mc, "D",
                    HudConfig.getGearX(), HudConfig.getGearY(),
                    HudConfig.getGearScale() * (modern ? GEAR_BASE_MODERN : 1.0F),
                    COLOR_WHITE, modern);
        }

        // Three speed digits (leading zeros gray)
        if (HudConfig.isShowSpeed()) {
            int v = Math.max(0, Math.min(999, speed));
            int hundreds = v / 100;
            int tens = (v / 10) % 10;
            int ones = v % 10;
            int step = modern ? 47 : 7;
            int digitY = modern ? -MODERN_INK_TOP : 0;
            float digitScale = HudConfig.getSpeedScale()
                    * (modern ? SPEED_BASE_MODERN : SPEED_BASE_DEFAULT);

            pose.pushPose();
            pose.translate(HudConfig.getSpeedX(), HudConfig.getSpeedY(), 0);
            pose.scale(digitScale, digitScale, 1.0F);
            drawDigit(g, mc, Integer.toString(hundreds), 0, digitY, v < 100 ? COLOR_GRAY : COLOR_WHITE, modern);
            drawDigit(g, mc, Integer.toString(tens), step, digitY, v < 10 ? COLOR_GRAY : COLOR_WHITE, modern);
            drawDigit(g, mc, Integer.toString(ones), step * 2, digitY, COLOR_WHITE, modern);
            pose.popPose();
        }

        // Unit label
        if (HudConfig.isShowUnit()) {
            drawText(g, pose, mc, "km/h",
                    HudConfig.getUnitX(), HudConfig.getUnitY(),
                    HudConfig.getUnitScale() * (modern ? UNIT_BASE_MODERN : 1.0F),
                    COLOR_DIM, modern);
        }

        // Speed bar (width fixed, thickness scales, position adjustable on its own)
        if (HudConfig.isShowBar()) {
            int barX = HudConfig.getBarX();
            int barY = HudConfig.getBarY();
            int barH = Math.max(2, Math.round(BAR_H_BASE * HudConfig.getBarScale()));
            int fill = Math.round(BAR_W * Math.max(0.0F, Math.min(1.0F, progress)));
            g.fill(barX, barY, barX + BAR_W, barY + barH, COLOR_BAR_BG);
            g.fill(barX, barY, barX + fill, barY + barH, COLOR_BAR_FILL);
        }

        pose.popPose();
    }

    private static void drawDigit(GuiGraphics g, Minecraft mc, String s, int x, int y, int color, boolean modern) {
        if (modern) {
            g.drawString(mc.font, Component.literal(s).withStyle(Style.EMPTY.withFont(MODERN_FONT)),
                    x, y, color, false);
        } else {
            g.drawString(mc.font, s, x, y, color, false);
        }
    }

    private static void drawText(GuiGraphics g, PoseStack pose, Minecraft mc, String s,
                                 int x, int y, float scale, int color, boolean modern) {
        if (scale == 1.0F && !modern) {
            g.drawString(mc.font, s, x, y, color, false);
            return;
        }
        pose.pushPose();
        pose.translate(x, y, 0);
        pose.scale(scale, scale, 1.0F);
        int ly = modern ? -MODERN_INK_TOP : 0;
        if (modern) {
            g.drawString(mc.font, Component.literal(s).withStyle(Style.EMPTY.withFont(MODERN_FONT)),
                    0, ly, color, false);
        } else {
            g.drawString(mc.font, s, 0, ly, color, false);
        }
        pose.popPose();
    }
}
