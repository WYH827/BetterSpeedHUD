# BetterSpeedHUD

(English vesion:https://github.com/WYH827/BetterSpeedHUD/blob/main/README_EN.md)

一个用于 Minecraft Forge 的速度 HUD 模组：在载具或鞘翅滑翔时，于屏幕右下角显示速度、档位与速度条的抬头显示，并且所有元素都可以在游戏内自由调整。

> A Better HUD mod to show your speed on your screen. Highly customizable. @WYH827

![BetterSpeedHUD](images/hud.png)

## 支持版本

| 目录 | Minecraft | Forge | Java |
| --- | --- | --- | --- |
| [forge-1.20.1](forge-1.20.1) | 1.20.1 | 47.x | 17 |
| [forge-1.21.1](forge-1.21.1) | 1.21.1 | 52.x | 21 |

## 功能特性

- **实时速度显示**：基于玩家实际坐标位移测速（方块/秒 × 72 换算 km/h），兼容原版载具以及 MrCrayfish 等模组载具，鞘翅滑翔同样支持。
- **可调整布局**：左侧 `D` 档、中间三位大号速度数字（前导 0 灰色）、右侧 `km/h`、下方速度条。
- **最高速度可调**（默认 200）：速度条始终按「速度 / 最高速度」百分比显示，超过后封顶 100%。
- **真实速度开关**：关闭时速度到达最高速度后不再增加（爆表）；开启时始终显示真实速度。
- **两种字体**：Minecraft 默认字体 / Modern 位图字体（游戏内可切换）。
- **游戏内设置界面**（按 **H** 键，或 Mod 菜单「配置」按钮）：
  - 整体 HUD 与 D、数字、km/h、速度条各自独立调整位置、大小、显隐；
  - 拖拽、方向键、滚轮、快捷键均可调节；
  - 右上角「隐藏按钮」可隐藏全部按钮，方便观察与摆放；
  - 一键切换 Modern 预设 / 恢复默认。
- **显示条件**：载具时显示 / 鞘翅滑翔时显示 / 全局显示（开启全局后前两项锁定为开）。
- **中英双语界面**：跟随游戏语言——简体/繁体中文显示中文，其他语言显示英文。

![游戏内配置界面](images/settings.png)

## 安装

1. 按上表选择对应版本的 jar（可从 Releases 下载或自行构建）。
2. 将 jar 放入 `.minecraft/mods` 文件夹。
3. 启动游戏，进入世界后骑乘载具或鞘翅滑翔即可看到 HUD。

## 游戏内配置

按 **H** 键打开设置界面。（可在按键设置中改变按键，默认为 **H** 键）

| 按键 | 功能 |
| --- | --- |
| `1` ~ `5` | 选择要调整的元素（整体 / 档位D / 数字 / km/h / 速度条） |
| 方向键 / 鼠标拖拽 | 移动当前元素 |
| 鼠标滚轮 / `+` `-` | 缩放当前元素 |
| `V` | HUD 总开关 |
| `C` | 真实速度开关 |
| `F` | 字体切换（Minecraft / Modern） |
| `[` `]` | 最高速度 -/+ |
| `Esc` / 「保存并关闭」 | 保存配置并关闭 |

配置文件位于 `.minecraft/config/speedhud-client.toml`。

## 构建

进入对应版本目录后：

```bat
gradlew build
```

产物位于 `build/libs/`。

## Modern 字体

Modern 字体为自定义位图字体（由得意黑 Smiley Sans Oblique 生成），规格见各版本目录内的 `src/main/resources/assets/speedhud/font/modern.json`。

## 开源许可

本项目以 **MIT 许可证** 发布，详见 [LICENSE.txt](LICENSE.txt)。

作者：@WYH827
