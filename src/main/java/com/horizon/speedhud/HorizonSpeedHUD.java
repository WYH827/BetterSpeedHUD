package com.horizon.speedhud;

import com.horizon.speedhud.config.HudConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.common.Mod;

@Mod(HorizonSpeedHUD.MOD_ID)
public class HorizonSpeedHUD {
    public static final String MOD_ID = "speedhud";

    public HorizonSpeedHUD() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, HudConfig.SPEC);
    }
}
