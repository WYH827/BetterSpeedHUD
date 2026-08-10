# BetterSpeedHUD

![BetterSpeedHUD](images/hud.png)

一个用于 **Minecraft Forge 1.20.1** 的速度 HUD 模组：在载具或鞘翅滑翔时，于屏幕右下角显示速度、档位与速度条的精美速度抬头显示，并且所有元素都可以在游戏内自由调整。

> A Better HUD mod to show your speed on your screen. Highly customizable. @WYH827

---

## 功能特性

- **实时速度显示**：基于玩家实际坐标位移测速（方块/秒 × 72 换算 km/h），兼容原版载具以及 MrCrayfish 等模组载具，鞘翅滑翔同样支持。
- **可视HUD**：左侧 `D` 档、中间三位大号速度数字（前导 0 显示为灰色）、右侧 `km/h`、下方速度条。
- **最高速度可调**（默认 200）：速度条始终按「速度 / 最高速度」的百分比显示，超过后封顶 100%。
- **真实速度开关**：关闭时速度到达最高速度后不再增加（爆表）；开启时始终显示真实速度，速度条仍按百分比封顶。
- **两种字体**：Minecraft 默认字体 / Modern 位图字体（可在游戏内一键切换）。
- **游戏内设置界面**（默认按 **H** 键打开）：
  - 整体 HUD 与 D、数字、km/h、速度条各自独立调整位置、大小、显隐；
  - 拖拽、方向键、滚轮、快捷键均可调节；
  - 右上角「隐藏按钮」可隐藏全部按钮，方便观察与摆放；
  - 一键切换 Modern 预设 / 恢复默认；
  - 最高速度 +/- 按钮。
- **显示条件**：载具时显示 / 鞘翅滑翔时显示 / 全局显示（开启全局后前两项锁定为开）。
- **HUD 总开关**：可随时彻底隐藏 HUD。
- **配置安全写入**：拖动、调节只修改内存，关闭设置界面时才写入一次配置文件，避免频繁写盘。

---

## 安装

1. 需要 **Minecraft 1.20.1 + Forge 47.x**（客户端），Java 17。
2. 将 `BetterSpeedHUD-1.1.jar` 放入 `.minecraft/mods` 文件夹。
3. 启动游戏，进入世界后骑乘载具或鞘翅滑翔即可看到 HUD。

---

## 游戏内配置

按 **H** 键打开设置界面（也可在 Mod 菜单中点「配置」进入）。

### 按键说明

| 按键 | 功能 |
| --- | --- |
| `1` ~ `5` | 选择要调整的元素（整体 / 档位D / 数字 / km/h / 速度条） |
| 方向键 / 鼠标拖拽 | 移动当前元素 |
| 鼠标滚轮 / `+` `-` | 缩放当前元素 |
| `V` | HUD 总开关 |
| `C` | 真实速度开关 |
| `F` | 字体切换（Minecraft / Modern） |
| `[` `]` | 最高速度 -/+（Shift 一次 25） |
| `Esc` / 「保存并关闭」 | 保存配置并关闭 |

### 按钮说明

- 第一行：在载具上时显示 / 鞘翅滑翔时显示 / 全局HUD（始终显示HUD）
- 第二行：D / 数字 / km/h / 速度条 的单独显隐
- 第三行：HUD（总开关）/ 真实速度 / 最高速度-10 / 最高速度+10
- 第四行：字体 / 切换为modern预设 / 恢复默认 / 保存并关闭
- 右上角：隐藏按钮（隐藏全部按钮便于观察与摆放 HUD）

> 说明：关闭真实速度时，当速度达到最大速度，速度将不再增加（爆表了），开启真实速度时将始终显示真实速度。
>
> 说明：最大速度：速度条满格时的速度，速度条占比为速度与最大速度之比。

![游戏内配置界面](images/settings.png)

---

## 配置文件

配置文件位于 `.minecraft/config/speedhud-client.toml`，主要字段：

```toml
[hud]
xOffset = 0        # 整体水平偏移（正=右）
yOffset = 0        # 整体垂直偏移（正=下）
scale = 1.0        # 整体大小
enabled = true     # HUD 总开关
showOnVehicle = true   # 载具时显示
showOnElytra = true    # 鞘翅滑翔时显示
globalHud = false      # 全局显示
maxSpeed = 200         # 最高速度（速度条满格值）
showActualSpeed = false # 显示真实速度
font = "minecraft"     # minecraft / modern

[hud.modules]          # 各模块显隐
gear = true
speed = true
unit = true
bar = true

[hud.elements.gear]    # D 档位置与缩放
x = 16
y = 40
scale = 1.0

[hud.elements.speed]   # 速度数字
x = 76
y = 34
scale = 1.0

[hud.elements.unit]    # km/h
x = 176
y = 46
scale = 1.0

[hud.elements.bar]     # 速度条
x = 0
y = 58
scale = 1.0
```

---

## Modern 字体

Modern 字体为自定义位图字体（由得意黑 Smiley Sans Oblique 生成）：

- 纹理：`assets/speedhud/textures/font/modern.png`，1152×192，单行 18 个字符，每格 64×192；
- 字符顺序：`0123456789DKM/Hkmh`；
- 字形底线对齐格子底边（第 192 行）；
- 定义文件：`assets/speedhud/font/modern.json`（height 192 / ascent 7）。

如需替换字体，请按上述规格提供 PNG 并替换 `assets/speedhud/textures/font/modern.png`。

---

## 构建（开发者）

环境：JDK 17+、Gradle 8.x。

```bat
gradlew build
```

产物位于 `build/libs/BetterSpeedHUD-1.1.jar`。

---

## 开源许可

本项目以 **MIT 许可证** 发布，详见 [LICENSE.txt](LICENSE.txt)。

作者：@WYH827
