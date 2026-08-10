package com.horizon.speedhud.client;

import com.horizon.speedhud.config.HudConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

public class VehicleSpeedReader {

    private static final float KMH_PER_BLOCK_PER_TICK = 72.0F;

    private static float speed;
    private static float display;
    private static double lastX;
    private static double lastZ;
    private static boolean hasLast;

    public static void tick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || !isActive()) {
            display *= 0.9f;
            hasLast = false;
            return;
        }

        // Some vehicles (e.g. MrCrayfish's Vehicle Mod) are moved by directly
        // updating their position, so getDeltaMovement() stays 0. Track the
        // player's actual position change instead; it follows every vehicle.
        Vec3 pos = mc.player.position();
        if (hasLast) {
            double dx = pos.x - lastX;
            double dz = pos.z - lastZ;
            speed = (float) (Math.sqrt(dx * dx + dz * dz) * KMH_PER_BLOCK_PER_TICK);
        } else {
            speed = 0.0F;
        }
        lastX = pos.x;
        lastZ = pos.z;
        hasLast = true;

        display += (speed - display) * 0.12f;
    }

    public static boolean riding() {
        Minecraft mc = Minecraft.getInstance();
        return mc.player != null && mc.player.getVehicle() != null;
    }

    public static boolean isFlying() {
        Minecraft mc = Minecraft.getInstance();
        return mc.player != null && mc.player.isFallFlying();
    }

    public static boolean isActive() {
        return riding() || isFlying() || HudConfig.isGlobalHud();
    }

    public static int getSpeed() {
        int raw = Math.round(display);
        if (HudConfig.isShowActualSpeed()) {
            return raw;
        }
        return Math.min(raw, HudConfig.getMaxSpeed());
    }

    public static float getProgress() {
        int max = HudConfig.getMaxSpeed();
        if (max <= 0) return 0.0F;
        return Math.min(display / max, 1.0F);
    }
}
