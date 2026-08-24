package com.horizon.speedhud.client;

import com.horizon.speedhud.config.HudConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public final class BetterHudRender {

    private BetterHudRender() {
    }

    /** Called from GuiMixin after the in-game HUD has rendered. */
    public static void render(GuiGraphics g) {
        if (!HudConfig.isEnabled()) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen instanceof HudSettingsScreen) return;

        boolean show = HudConfig.isGlobalHud()
                || (VehicleSpeedReader.riding() && HudConfig.isShowOnVehicle())
                || (VehicleSpeedReader.isFlying() && HudConfig.isShowOnElytra());
        if (!show) return;

        int width = mc.getWindow().getGuiScaledWidth();
        int height = mc.getWindow().getGuiScaledHeight();
        HorizonHud.draw(g, width, height,
                VehicleSpeedReader.getSpeed(), VehicleSpeedReader.getProgress());
    }
}
