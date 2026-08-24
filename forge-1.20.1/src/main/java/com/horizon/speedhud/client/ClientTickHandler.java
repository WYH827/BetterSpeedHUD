package com.horizon.speedhud.client;

import com.horizon.speedhud.HorizonSpeedHUD;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HorizonSpeedHUD.MOD_ID, value = Dist.CLIENT)
public class ClientTickHandler {

    @SubscribeEvent
    public static void tick(TickEvent.ClientTickEvent e) {
        if (e.phase == TickEvent.Phase.END) {
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
}
