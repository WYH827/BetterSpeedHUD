package com.horizon.speedhud;

import com.horizon.speedhud.config.HudConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(HorizonSpeedHUD.MOD_ID)
public class HorizonSpeedHUD {
    public static final String MOD_ID = "speedhud";

    public HorizonSpeedHUD(IEventBus modBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, HudConfig.SPEC);
    }
}
