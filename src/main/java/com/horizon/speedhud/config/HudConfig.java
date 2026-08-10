package com.horizon.speedhud.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class HudConfig {

    // Forge config spec: used ONLY to load the file (and create it with defaults
    // on first launch). Runtime values live in the plain fields below so that
    // adjusting the HUD never writes to disk (ConfigValue.set() autosaves and
    // crashed the game when the file was briefly locked during dragging).
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.IntValue X_OFFSET;
    public static final ForgeConfigSpec.IntValue Y_OFFSET;
    public static final ForgeConfigSpec.DoubleValue SCALE;
    public static final ForgeConfigSpec.BooleanValue ENABLED;
    public static final ForgeConfigSpec.BooleanValue SHOW_ON_VEHICLE;
    public static final ForgeConfigSpec.BooleanValue SHOW_ON_ELYTRA;
    public static final ForgeConfigSpec.BooleanValue GLOBAL_HUD;
    public static final ForgeConfigSpec.IntValue MAX_SPEED;
    public static final ForgeConfigSpec.BooleanValue SHOW_ACTUAL_SPEED;
    public static final ForgeConfigSpec.ConfigValue<String> FONT;
    public static final ForgeConfigSpec.BooleanValue SHOW_GEAR;
    public static final ForgeConfigSpec.BooleanValue SHOW_SPEED;
    public static final ForgeConfigSpec.BooleanValue SHOW_UNIT;
    public static final ForgeConfigSpec.BooleanValue SHOW_BAR;
    public static final ForgeConfigSpec.IntValue GEAR_X;
    public static final ForgeConfigSpec.IntValue GEAR_Y;
    public static final ForgeConfigSpec.DoubleValue GEAR_SCALE;
    public static final ForgeConfigSpec.IntValue SPEED_X;
    public static final ForgeConfigSpec.IntValue SPEED_Y;
    public static final ForgeConfigSpec.DoubleValue SPEED_SCALE;
    public static final ForgeConfigSpec.IntValue UNIT_X;
    public static final ForgeConfigSpec.IntValue UNIT_Y;
    public static final ForgeConfigSpec.DoubleValue UNIT_SCALE;
    public static final ForgeConfigSpec.IntValue BAR_X;
    public static final ForgeConfigSpec.IntValue BAR_Y;
    public static final ForgeConfigSpec.DoubleValue BAR_SCALE;

    // Runtime values (in-memory only; written to disk on HudConfig.save()).
    private static int xOffset;
    private static int yOffset;
    private static float scale;
    private static boolean enabled;
    private static boolean showOnVehicle;
    private static boolean showOnElytra;
    private static boolean globalHud;
    private static int maxSpeed;
    private static boolean showActualSpeed;
    private static String font;
    private static boolean showGear;
    private static boolean showSpeed;
    private static boolean showUnit;
    private static boolean showBar;
    private static int gearX;
    private static int gearY;
    private static float gearScale;
    private static int speedX;
    private static int speedY;
    private static float speedScale;
    private static int unitX;
    private static int unitY;
    private static float unitScale;
    private static int barX;
    private static int barY;
    private static float barScale;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("BetterSpeedHUD settings").push("hud");

        X_OFFSET = builder
                .comment("Horizontal shift from the default bottom-right position (pixels). Positive moves right.")
                .defineInRange("xOffset", 0, -3000, 3000);
        Y_OFFSET = builder
                .comment("Vertical shift from the default bottom-right position (pixels). Positive moves down.")
                .defineInRange("yOffset", 0, -3000, 3000);
        SCALE = builder
                .comment("Overall HUD size multiplier (0.5x to 3.0x).")
                .defineInRange("scale", 1.0, 0.5, 3.0);
        ENABLED = builder
                .comment("Whether the HUD is shown (master switch).")
                .define("enabled", true);

        SHOW_ON_VEHICLE = builder
                .comment("Show the HUD while riding a vehicle.")
                .define("showOnVehicle", true);

        SHOW_ON_ELYTRA = builder
                .comment("Show the HUD while gliding with an elytra.")
                .define("showOnElytra", true);

        GLOBAL_HUD = builder
                .comment("Always show the HUD. While enabled, showOnVehicle and showOnElytra are locked to on.")
                .define("globalHud", false);

        MAX_SPEED = builder
                .comment("Speed shown by the bar as 100% (km/h).")
                .defineInRange("maxSpeed", 200, 10, 999);
        SHOW_ACTUAL_SPEED = builder
                .comment("Show the real speed number even when it exceeds maxSpeed.")
                .define("showActualSpeed", false);

        FONT = builder
                .comment("HUD font: 'minecraft' (default) or 'modern' (custom bitmap font).")
                .define("font", "minecraft");

        builder.comment("Per-module visibility.")
                .push("modules");
        SHOW_GEAR = builder.define("gear", true);
        SHOW_SPEED = builder.define("speed", true);
        SHOW_UNIT = builder.define("unit", true);
        SHOW_BAR = builder.define("bar", true);
        builder.pop();

        builder.comment("Per-element position (pixels from the HUD block top-left) and size multiplier.")
                .push("elements");

        builder.push("gear");
        GEAR_X = builder.defineInRange("x", 16, -2000, 2000);
        GEAR_Y = builder.defineInRange("y", 40, -2000, 2000);
        GEAR_SCALE = builder.defineInRange("scale", 1.0, 0.1, 10.0);
        builder.pop();

        builder.push("speed");
        SPEED_X = builder.defineInRange("x", 76, -2000, 2000);
        SPEED_Y = builder.defineInRange("y", 34, -2000, 2000);
        SPEED_SCALE = builder.defineInRange("scale", 1.0, 0.1, 10.0);
        builder.pop();

        builder.push("unit");
        UNIT_X = builder.defineInRange("x", 176, -2000, 2000);
        UNIT_Y = builder.defineInRange("y", 46, -2000, 2000);
        UNIT_SCALE = builder.defineInRange("scale", 1.0, 0.1, 10.0);
        builder.pop();

        builder.push("bar");
        BAR_X = builder.defineInRange("x", 0, -2000, 2000);
        BAR_Y = builder.defineInRange("y", 58, -2000, 2000);
        BAR_SCALE = builder.defineInRange("scale", 1.0, 0.1, 10.0);
        builder.pop();

        builder.pop();
        SPEC = builder.build();
    }

    private HudConfig() {
    }

    /** Copy the loaded Forge config into the runtime fields (called on config load/reload). */
    public static void init() {
        xOffset = X_OFFSET.get();
        yOffset = Y_OFFSET.get();
        scale = SCALE.get().floatValue();
        enabled = ENABLED.get();
        showOnVehicle = SHOW_ON_VEHICLE.get();
        showOnElytra = SHOW_ON_ELYTRA.get();
        globalHud = GLOBAL_HUD.get();
        maxSpeed = MAX_SPEED.get();
        showActualSpeed = SHOW_ACTUAL_SPEED.get();
        font = FONT.get();
        showGear = SHOW_GEAR.get();
        showSpeed = SHOW_SPEED.get();
        showUnit = SHOW_UNIT.get();
        showBar = SHOW_BAR.get();
        gearX = GEAR_X.get();
        gearY = GEAR_Y.get();
        gearScale = GEAR_SCALE.get().floatValue();
        speedX = SPEED_X.get();
        speedY = SPEED_Y.get();
        speedScale = SPEED_SCALE.get().floatValue();
        unitX = UNIT_X.get();
        unitY = UNIT_Y.get();
        unitScale = UNIT_SCALE.get().floatValue();
        barX = BAR_X.get();
        barY = BAR_Y.get();
        barScale = BAR_SCALE.get().floatValue();
    }

    // Getters

    public static int getXOffset() { return xOffset; }

    public static int getYOffset() { return yOffset; }

    public static float getScale() { return scale; }

    public static boolean isEnabled() { return enabled; }

    public static boolean isShowOnVehicle() { return showOnVehicle; }

    public static boolean isShowOnElytra() { return showOnElytra; }

    public static boolean isGlobalHud() { return globalHud; }

    public static int getMaxSpeed() { return maxSpeed; }

    public static boolean isShowActualSpeed() { return showActualSpeed; }

    public static String getFont() { return font; }

    public static boolean isShowGear() { return showGear; }

    public static boolean isShowSpeed() { return showSpeed; }

    public static boolean isShowUnit() { return showUnit; }

    public static boolean isShowBar() { return showBar; }

    public static int getGearX() { return gearX; }

    public static int getGearY() { return gearY; }

    public static float getGearScale() { return gearScale; }

    public static int getSpeedX() { return speedX; }

    public static int getSpeedY() { return speedY; }

    public static float getSpeedScale() { return speedScale; }

    public static int getUnitX() { return unitX; }

    public static int getUnitY() { return unitY; }

    public static float getUnitScale() { return unitScale; }

    public static int getBarX() { return barX; }

    public static int getBarY() { return barY; }

    public static float getBarScale() { return barScale; }

    // Setters (memory only, never touch the config file)

    public static void setX(int v) {
        xOffset = clamp(v, -3000, 3000);
    }

    public static void setY(int v) {
        yOffset = clamp(v, -3000, 3000);
    }

    public static void setScale(double v) {
        scale = (float) clampD(v, 0.5, 3.0);
    }

    public static void setEnabled(boolean v) {
        enabled = v;
    }

    public static void setShowOnVehicle(boolean v) {
        showOnVehicle = globalHud || v;
    }

    public static void setShowOnElytra(boolean v) {
        showOnElytra = globalHud || v;
    }

    public static void setGlobalHud(boolean v) {
        globalHud = v;
        if (v) {
            showOnVehicle = true;
            showOnElytra = true;
        }
    }

    public static void setShowGear(boolean v) {
        showGear = v;
    }

    public static void setShowSpeed(boolean v) {
        showSpeed = v;
    }

    public static void setShowUnit(boolean v) {
        showUnit = v;
    }

    public static void setShowBar(boolean v) {
        showBar = v;
    }

    public static void setMaxSpeed(int v) {
        maxSpeed = clamp(v, 10, 999);
    }

    public static void setShowActualSpeed(boolean v) {
        showActualSpeed = v;
    }

    public static void setFont(String f) {
        if ("modern".equals(f) || "minecraft".equals(f)) {
            font = f;
        }
    }

    public static void setGearX(int v) { gearX = clamp(v, -2000, 2000); }

    public static void setGearY(int v) { gearY = clamp(v, -2000, 2000); }

    public static void setGearScale(double v) { gearScale = (float) clampD(v, 0.1, 10.0); }

    public static void setSpeedX(int v) { speedX = clamp(v, -2000, 2000); }

    public static void setSpeedY(int v) { speedY = clamp(v, -2000, 2000); }

    public static void setSpeedScale(double v) { speedScale = (float) clampD(v, 0.1, 10.0); }

    public static void setUnitX(int v) { unitX = clamp(v, -2000, 2000); }

    public static void setUnitY(int v) { unitY = clamp(v, -2000, 2000); }

    public static void setUnitScale(double v) { unitScale = (float) clampD(v, 0.1, 10.0); }

    public static void setBarX(int v) { barX = clamp(v, -2000, 2000); }

    public static void setBarY(int v) { barY = clamp(v, -2000, 2000); }

    public static void setBarScale(double v) { barScale = (float) clampD(v, 0.1, 10.0); }

    public static void reset() {
        xOffset = 0;
        yOffset = 0;
        scale = 1.0F;
        enabled = true;
        showOnVehicle = true;
        showOnElytra = true;
        globalHud = false;
        maxSpeed = 200;
        showActualSpeed = false;
        font = "minecraft";
        showGear = true;
        showSpeed = true;
        showUnit = true;
        showBar = true;
        gearX = 16;
        gearY = 40;
        gearScale = 1.0F;
        speedX = 76;
        speedY = 34;
        speedScale = 1.0F;
        unitX = 176;
        unitY = 46;
        unitScale = 1.0F;
        barX = 0;
        barY = 58;
        barScale = 1.0F;
    }

    /** Apply the "Modern preset" shipped with the mod (from the uploaded speedhud-client.toml). */
    public static void applyModernPreset() {
        xOffset = -21;
        yOffset = -6;
        scale = 1.0F;
        enabled = true;
        showOnVehicle = true;
        showOnElytra = true;
        globalHud = false;
        maxSpeed = 200;
        showActualSpeed = false;
        font = "modern";
        showGear = true;
        showSpeed = true;
        showUnit = true;
        showBar = true;
        gearX = 37;
        gearY = 33;
        gearScale = 2.0000002F;
        speedX = 70;
        speedY = 10;
        speedScale = 1.7000002F;
        unitX = 162;
        unitY = 37;
        unitScale = 1.0F;
        barX = 0;
        barY = 58;
        barScale = 1.0F;
    }

    /** Write the runtime values to the config file. Called once when the settings screen closes. */
    public static void save() {
        try {
            Path path = FMLPaths.CONFIGDIR.get().resolve("speedhud-client.toml");
            Path tmp = path.resolveSibling("speedhud-client.toml.tmp");
            Files.writeString(tmp, buildToml(), StandardCharsets.UTF_8);
            Files.move(tmp, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            // Never crash the game because of a config write.
            e.printStackTrace();
        }
    }

    private static String buildToml() {
        StringBuilder sb = new StringBuilder();
        sb.append("# BetterSpeedHUD client config\n\n");
        sb.append("[hud]\n");
        sb.append("xOffset = ").append(xOffset).append('\n');
        sb.append("yOffset = ").append(yOffset).append('\n');
        sb.append("scale = ").append(scale).append('\n');
        sb.append("enabled = ").append(enabled).append('\n');
        sb.append("showOnVehicle = ").append(showOnVehicle).append('\n');
        sb.append("showOnElytra = ").append(showOnElytra).append('\n');
        sb.append("globalHud = ").append(globalHud).append('\n');
        sb.append("maxSpeed = ").append(maxSpeed).append('\n');
        sb.append("showActualSpeed = ").append(showActualSpeed).append('\n');
        sb.append("font = \"").append(font).append("\"\n");
        sb.append("\n[hud.modules]\n");
        sb.append("gear = ").append(showGear).append('\n');
        sb.append("speed = ").append(showSpeed).append('\n');
        sb.append("unit = ").append(showUnit).append('\n');
        sb.append("bar = ").append(showBar).append('\n');
        sb.append("\n[hud.elements.gear]\n");
        sb.append("x = ").append(gearX).append('\n');
        sb.append("y = ").append(gearY).append('\n');
        sb.append("scale = ").append(gearScale).append('\n');
        sb.append("\n[hud.elements.speed]\n");
        sb.append("x = ").append(speedX).append('\n');
        sb.append("y = ").append(speedY).append('\n');
        sb.append("scale = ").append(speedScale).append('\n');
        sb.append("\n[hud.elements.unit]\n");
        sb.append("x = ").append(unitX).append('\n');
        sb.append("y = ").append(unitY).append('\n');
        sb.append("scale = ").append(unitScale).append('\n');
        sb.append("\n[hud.elements.bar]\n");
        sb.append("x = ").append(barX).append('\n');
        sb.append("y = ").append(barY).append('\n');
        sb.append("scale = ").append(barScale).append('\n');
        return sb.toString();
    }

    private static int clamp(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }

    private static double clampD(double v, double min, double max) {
        return Math.max(min, Math.min(max, v));
    }
}
