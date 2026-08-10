package com.horizon.speedhud.client;

import com.horizon.speedhud.config.HudConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class HorizonHudRenderer implements IGuiOverlay {

    @Override
    public void render(ForgeGui gui, GuiGraphics g, float partial, int width, int height) {
        if (!HudConfig.isEnabled()) return;
        if (Minecraft.getInstance().screen instanceof HudSettingsScreen) return;

        boolean show = HudConfig.isGlobalHud()
                || (VehicleSpeedReader.riding() && HudConfig.isShowOnVehicle())
                || (VehicleSpeedReader.isFlying() && HudConfig.isShowOnElytra());
        if (!show) return;

        HorizonHud.draw(g, width, height,
                VehicleSpeedReader.getSpeed(), VehicleSpeedReader.getProgress());
    }
}
