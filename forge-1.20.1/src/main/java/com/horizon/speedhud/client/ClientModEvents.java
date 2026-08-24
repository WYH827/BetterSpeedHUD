package com.horizon.speedhud.client;

import com.horizon.speedhud.HorizonSpeedHUD;
import com.horizon.speedhud.config.HudConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = HorizonSpeedHUD.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll(HorizonSpeedHUD.MOD_ID, new HorizonHudRenderer());
    }

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(HudSettingsScreen.OPEN_SETTINGS_KEY);
    }

    @SubscribeEvent
    public static void onModConfig(ModConfigEvent event) {
        if (event.getConfig().getSpec() == HudConfig.SPEC) {
            HudConfig.init();
        }
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Allow opening the HUD settings screen from the mod menu's "Config" button.
        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (mc, parent) -> new HudSettingsScreen(parent)));
    }
}
