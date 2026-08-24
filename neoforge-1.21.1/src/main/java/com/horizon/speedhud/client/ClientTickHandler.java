package com.horizon.speedhud.client;

import com.horizon.speedhud.HorizonSpeedHUD;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = HorizonSpeedHUD.MOD_ID, value = Dist.CLIENT)
public class ClientTickHandler {

    @SubscribeEvent
    public static void tick(ClientTickEvent.Post e) {
        VehicleSpeedReader.tick();

        while (HudSettingsScreen.OPEN_SETTINGS_KEY.consumeClick()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.screen == null) {
                mc.setScreen(new HudSettingsScreen());
            } else if (mc.screen instanceof HudSettingsScreen) {
                ((HudSettingsScreen) mc.screen).onClose();
            }
        }
    }
}
