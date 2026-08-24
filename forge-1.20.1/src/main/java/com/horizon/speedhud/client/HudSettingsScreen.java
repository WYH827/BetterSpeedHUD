package com.horizon.speedhud.client;

import com.horizon.speedhud.config.HudConfig;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class HudSettingsScreen extends Screen {

    public static final KeyMapping OPEN_SETTINGS_KEY = new KeyMapping(
            "key.speedhud.open_settings",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            "key.categories.speedhud");

    private static final int STEP = 5;
    private static final int STEP_BIG = 25;
    private static final float SCALE_STEP = 0.1F;

    private enum Target {
        OVERALL("overall"),
        GEAR("gear"),
        SPEED("speed"),
        UNIT("unit"),
        BAR("bar");

        final String key;

        Target(String key) {
            this.key = key;
        }
    }

    private Target target = Target.OVERALL;
    private boolean dragging = false;
    private boolean buttonsHidden = false;
    private final Screen parent;

    private Button hideButton;
    private Button vehicleButton;
    private Button elytraButton;
    private Button globalButton;
    private Button gearVisButton;
    private Button speedVisButton;
    private Button unitVisButton;
    private Button barVisButton;
    private Button hudToggleButton;
    private Button actualSpeedButton;
    private Button fontButton;
    private Button presetButton;
    private final List<Button> allButtons = new ArrayList<>();

    public HudSettingsScreen() {
        this(null);
    }

    public HudSettingsScreen(Screen parent) {
        super(Component.translatable("screen.speedhud.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int cx = this.width / 2;
        int yCond = this.height - 124;
        int yMod = this.height - 100;
        int yMain = this.height - 76;
        int yExtra = this.height - 52;

        this.hideButton = Button.builder(Component.translatable("screen.speedhud.hide_buttons"), b -> {
                    this.buttonsHidden = !this.buttonsHidden;
                    refreshLabels();
                })
                .bounds(this.width - 84, 6, 78, 20)
                .build();

        this.vehicleButton = Button.builder(Component.literal(""), b -> {
                    HudConfig.setShowOnVehicle(!HudConfig.isShowOnVehicle());
                    refreshLabels();
                })
                .bounds(cx - 235, yCond, 150, 20)
                .build();

        this.elytraButton = Button.builder(Component.literal(""), b -> {
                    HudConfig.setShowOnElytra(!HudConfig.isShowOnElytra());
                    refreshLabels();
                })
                .bounds(cx - 75, yCond, 150, 20)
                .build();

        this.globalButton = Button.builder(Component.literal(""), b -> {
                    HudConfig.setGlobalHud(!HudConfig.isGlobalHud());
                    refreshLabels();
                })
                .bounds(cx + 85, yCond, 150, 20)
                .build();

        this.gearVisButton = Button.builder(Component.literal(""), b -> {
                    HudConfig.setShowGear(!HudConfig.isShowGear());
                    refreshLabels();
                })
                .bounds(cx - 192, yMod, 90, 20)
                .build();

        this.speedVisButton = Button.builder(Component.literal(""), b -> {
                    HudConfig.setShowSpeed(!HudConfig.isShowSpeed());
                    refreshLabels();
                })
                .bounds(cx - 94, yMod, 90, 20)
                .build();

        this.unitVisButton = Button.builder(Component.literal(""), b -> {
                    HudConfig.setShowUnit(!HudConfig.isShowUnit());
                    refreshLabels();
                })
                .bounds(cx + 4, yMod, 90, 20)
                .build();

        this.barVisButton = Button.builder(Component.literal(""), b -> {
                    HudConfig.setShowBar(!HudConfig.isShowBar());
                    refreshLabels();
                })
                .bounds(cx + 102, yMod, 90, 20)
                .build();

        this.hudToggleButton = Button.builder(Component.literal(""), b -> {
                    HudConfig.setEnabled(!HudConfig.isEnabled());
                    refreshLabels();
                })
                .bounds(cx - 220, yMain, 120, 20)
                .build();

        this.actualSpeedButton = Button.builder(Component.literal(""), b -> {
                    HudConfig.setShowActualSpeed(!HudConfig.isShowActualSpeed());
                    refreshLabels();
                })
                .bounds(cx - 90, yMain, 100, 20)
                .build();

        Button maxMinusButton = Button.builder(Component.translatable("screen.speedhud.max_minus"), b ->
                        HudConfig.setMaxSpeed(HudConfig.getMaxSpeed() - 10))
                .bounds(cx + 20, yMain, 95, 20)
                .build();

        Button maxPlusButton = Button.builder(Component.translatable("screen.speedhud.max_plus"), b ->
                        HudConfig.setMaxSpeed(HudConfig.getMaxSpeed() + 10))
                .bounds(cx + 125, yMain, 95, 20)
                .build();

        this.fontButton = Button.builder(Component.literal(""), b -> {
                    HudConfig.setFont("modern".equals(HudConfig.getFont()) ? "minecraft" : "modern");
                    refreshLabels();
                })
                .bounds(cx - 225, yExtra, 120, 20)
                .build();

        this.presetButton = Button.builder(Component.translatable("screen.speedhud.preset"), b -> {
                    HudConfig.applyModernPreset();
                    refreshLabels();
                })
                .bounds(cx - 95, yExtra, 110, 20)
                .build();

        Button resetButton = Button.builder(Component.translatable("screen.speedhud.reset"), b -> {
                    HudConfig.reset();
                    refreshLabels();
                })
                .bounds(cx + 25, yExtra, 90, 20)
                .build();

        Button closeButton = Button.builder(Component.translatable("screen.speedhud.close"), b -> this.onClose())
                .bounds(cx + 125, yExtra, 100, 20)
                .build();

        this.addRenderableWidget(this.hideButton);
        this.addRenderableWidget(this.vehicleButton);
        this.addRenderableWidget(this.elytraButton);
        this.addRenderableWidget(this.globalButton);
        this.addRenderableWidget(this.gearVisButton);
        this.addRenderableWidget(this.speedVisButton);
        this.addRenderableWidget(this.unitVisButton);
        this.addRenderableWidget(this.barVisButton);
        this.addRenderableWidget(this.hudToggleButton);
        this.addRenderableWidget(this.actualSpeedButton);
        this.addRenderableWidget(maxMinusButton);
        this.addRenderableWidget(maxPlusButton);
        this.addRenderableWidget(this.fontButton);
        this.addRenderableWidget(this.presetButton);
        this.addRenderableWidget(resetButton);
        this.addRenderableWidget(closeButton);

        this.allButtons.add(this.vehicleButton);
        this.allButtons.add(this.elytraButton);
        this.allButtons.add(this.globalButton);
        this.allButtons.add(this.gearVisButton);
        this.allButtons.add(this.speedVisButton);
        this.allButtons.add(this.unitVisButton);
        this.allButtons.add(this.barVisButton);
        this.allButtons.add(this.hudToggleButton);
        this.allButtons.add(this.actualSpeedButton);
        this.allButtons.add(maxMinusButton);
        this.allButtons.add(maxPlusButton);
        this.allButtons.add(this.fontButton);
        this.allButtons.add(this.presetButton);
        this.allButtons.add(resetButton);
        this.allButtons.add(closeButton);

        refreshLabels();
    }

    private static Component onOff(boolean v) {
        return Component.translatable(v ? "screen.speedhud.on" : "screen.speedhud.off");
    }

    private static Component showHide(boolean v) {
        return Component.translatable(v ? "screen.speedhud.show" : "screen.speedhud.hide");
    }

    private void refreshLabels() {
        boolean global = HudConfig.isGlobalHud();

        this.hideButton.setMessage(Component.translatable(this.buttonsHidden ? "screen.speedhud.show_buttons" : "screen.speedhud.hide_buttons"));
        this.vehicleButton.setMessage(Component.translatable("screen.speedhud.show_on_vehicle", onOff(HudConfig.isShowOnVehicle())));

        Component elytraLabel = Component.translatable("screen.speedhud.show_on_elytra", onOff(HudConfig.isShowOnElytra()));
        if (global) {
            elytraLabel = elytraLabel.copy().append(Component.translatable("screen.speedhud.locked"));
        }
        this.elytraButton.setMessage(elytraLabel);

        this.globalButton.setMessage(Component.translatable("screen.speedhud.global_hud", onOff(global)));
        this.gearVisButton.setMessage(Component.translatable("screen.speedhud.module_gear", showHide(HudConfig.isShowGear())));
        this.speedVisButton.setMessage(Component.translatable("screen.speedhud.module_speed", showHide(HudConfig.isShowSpeed())));
        this.unitVisButton.setMessage(Component.translatable("screen.speedhud.module_unit", showHide(HudConfig.isShowUnit())));
        this.barVisButton.setMessage(Component.translatable("screen.speedhud.module_bar", showHide(HudConfig.isShowBar())));
        this.hudToggleButton.setMessage(Component.translatable("screen.speedhud.master", onOff(HudConfig.isEnabled())));
        this.actualSpeedButton.setMessage(Component.translatable("screen.speedhud.real_speed", onOff(HudConfig.isShowActualSpeed())));
        this.fontButton.setMessage(Component.translatable("screen.speedhud.font",
                Component.translatable("modern".equals(HudConfig.getFont()) ? "screen.speedhud.font.modern" : "screen.speedhud.font.minecraft")));
        this.presetButton.setMessage(Component.translatable("screen.speedhud.preset"));

        for (Button b : this.allButtons) {
            b.visible = !this.buttonsHidden;
            b.active = !this.buttonsHidden;
        }
        if (global) {
            this.vehicleButton.active = false;
            this.elytraButton.active = false;
        }
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(g);

        int demo = VehicleSpeedReader.riding() ? VehicleSpeedReader.getSpeed()
                : (HudConfig.isShowActualSpeed() ? 81 : Math.min(81, HudConfig.getMaxSpeed()));
        float demoProgress = VehicleSpeedReader.riding() ? VehicleSpeedReader.getProgress()
                : Math.min(1.0F, 81.0F / Math.max(1, HudConfig.getMaxSpeed()));
        HorizonHud.draw(g, this.width, this.height, demo, demoProgress);

        g.drawCenteredString(this.font,
                Component.translatable("screen.speedhud.hint"),
                this.width / 2, 8, 0xFFFFFFFF);
        g.drawCenteredString(this.font,
                Component.translatable("screen.speedhud.info",
                        Component.translatable("screen.speedhud.target." + this.target.key),
                        HudConfig.getMaxSpeed(),
                        HudConfig.getXOffset(),
                        HudConfig.getYOffset(),
                        HudConfig.getScale()),
                this.width / 2, 24, 0xFFAAAAAA);

        g.drawCenteredString(this.font,
                Component.translatable("screen.speedhud.desc_real_speed"),
                this.width / 2, 40, 0xFFAAAAAA);
        g.drawCenteredString(this.font,
                Component.translatable("screen.speedhud.desc_max_speed"),
                this.width / 2, 52, 0xFFAAAAAA);

        g.drawCenteredString(this.font,
                Component.translatable("screen.speedhud.author"),
                this.width / 2, this.height - 18, 0xFFAAAAAA);

        super.render(g, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) return true;
        this.dragging = true;
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.dragging) {
            moveTarget((int) Math.round(dragX), (int) Math.round(dragY));
        }
        return this.dragging || super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.dragging = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        scaleTarget((float) delta * SCALE_STEP);
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        int step = (modifiers & GLFW.GLFW_MOD_SHIFT) != 0 ? STEP_BIG : STEP;
        switch (keyCode) {
            case GLFW.GLFW_KEY_1:
                this.target = Target.OVERALL;
                return true;
            case GLFW.GLFW_KEY_2:
                this.target = Target.GEAR;
                return true;
            case GLFW.GLFW_KEY_3:
                this.target = Target.SPEED;
                return true;
            case GLFW.GLFW_KEY_4:
                this.target = Target.UNIT;
                return true;
            case GLFW.GLFW_KEY_5:
                this.target = Target.BAR;
                return true;
            case GLFW.GLFW_KEY_LEFT:
                moveTarget(-step, 0);
                return true;
            case GLFW.GLFW_KEY_RIGHT:
                moveTarget(step, 0);
                return true;
            case GLFW.GLFW_KEY_UP:
                moveTarget(0, -step);
                return true;
            case GLFW.GLFW_KEY_DOWN:
                moveTarget(0, step);
                return true;
            case GLFW.GLFW_KEY_EQUAL:
            case GLFW.GLFW_KEY_KP_ADD:
                scaleTarget(SCALE_STEP);
                return true;
            case GLFW.GLFW_KEY_MINUS:
            case GLFW.GLFW_KEY_KP_SUBTRACT:
                scaleTarget(-SCALE_STEP);
                return true;
            case GLFW.GLFW_KEY_V:
                HudConfig.setEnabled(!HudConfig.isEnabled());
                refreshLabels();
                return true;
            case GLFW.GLFW_KEY_C:
                HudConfig.setShowActualSpeed(!HudConfig.isShowActualSpeed());
                refreshLabels();
                return true;
            case GLFW.GLFW_KEY_F:
                HudConfig.setFont("modern".equals(HudConfig.getFont()) ? "minecraft" : "modern");
                refreshLabels();
                return true;
            case GLFW.GLFW_KEY_LEFT_BRACKET:
                HudConfig.setMaxSpeed(HudConfig.getMaxSpeed() - step);
                return true;
            case GLFW.GLFW_KEY_RIGHT_BRACKET:
                HudConfig.setMaxSpeed(HudConfig.getMaxSpeed() + step);
                return true;
            default:
                return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        HudConfig.save();
        if (this.parent != null) {
            this.minecraft.setScreen(this.parent);
        } else {
            super.onClose();
        }
    }

    private void moveTarget(int dx, int dy) {
        switch (this.target) {
            case OVERALL:
                HudConfig.setX(HudConfig.getXOffset() + dx);
                HudConfig.setY(HudConfig.getYOffset() + dy);
                break;
            case GEAR:
                HudConfig.setGearX(HudConfig.getGearX() + dx);
                HudConfig.setGearY(HudConfig.getGearY() + dy);
                break;
            case SPEED:
                HudConfig.setSpeedX(HudConfig.getSpeedX() + dx);
                HudConfig.setSpeedY(HudConfig.getSpeedY() + dy);
                break;
            case UNIT:
                HudConfig.setUnitX(HudConfig.getUnitX() + dx);
                HudConfig.setUnitY(HudConfig.getUnitY() + dy);
                break;
            case BAR:
                HudConfig.setBarX(HudConfig.getBarX() + dx);
                HudConfig.setBarY(HudConfig.getBarY() + dy);
                break;
        }
    }

    private void scaleTarget(float delta) {
        switch (this.target) {
            case OVERALL:
                HudConfig.setScale(HudConfig.getScale() + delta);
                break;
            case GEAR:
                HudConfig.setGearScale(HudConfig.getGearScale() + delta);
                break;
            case SPEED:
                HudConfig.setSpeedScale(HudConfig.getSpeedScale() + delta);
                break;
            case UNIT:
                HudConfig.setUnitScale(HudConfig.getUnitScale() + delta);
                break;
            case BAR:
                HudConfig.setBarScale(HudConfig.getBarScale() + delta);
                break;
        }
    }
}
