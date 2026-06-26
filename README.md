# Yumesumi Economy

Yumesumi Economy is a lightweight NeoForge economy mod for Minecraft 1.21.1. It adds four stackable coins, configurable monster drops, crafting recipes, and quick in-hand exchange.

梦澄经济是一个面向 Minecraft 1.21.1 的轻量级 NeoForge 经济模组。它添加四种可堆叠梦澄币、可配置怪物掉落、合成表配方，以及手持快速兑换。

## Features / 功能

| Coin / 货币 | Rarity / 稀有度 | Default monster drop / 默认怪物掉落 | Amount / 数量 | Notes / 说明 |
| --- | --- | ---: | ---: | --- |
| Copper Coin / 铜币 | Common / 普通 | 94.9% | 1-2 | Base coin / 基础货币 |
| Silver Coin / 银币 | Uncommon / 罕见 | 5.0% | 1 | 8 Copper = 1 Silver |
| Gold Coin / 金币 | Rare / 稀有 | 0.1% | 1 | 8 Silver = 1 Gold |
| Diamond Coin / 钻石币 | Epic / 史诗 | 0.0% | 1 | Glint and fire resistant / 闪烁且防火 |

Exchange chain / 兑换链：

```text
Copper -> Silver -> Gold -> Diamond
铜币 -> 银币 -> 金币 -> 钻石币
```

## Exchange / 兑换

Coins support both crafting-table recipes and in-hand exchange.

梦澄币同时支持合成表配方和手持兑换。

| Action / 操作 | Result / 结果 |
| --- | --- |
| Craft 8 lower-tier coins / 合成 8 个低阶币 | 1 higher-tier coin / 1 个高阶币 |
| Craft 1 higher-tier coin / 合成 1 个高阶币 | 8 lower-tier coins / 8 个低阶币 |
| Hold coins and right-click / 手持梦澄币右键 | Converts full stacks upward at 8:1 / 按 8:1 将整组向上兑换 |
| Hold coins and sneak-right-click / 手持梦澄币潜行右键 | Decomposes full stacks downward at 1:8 / 按 1:8 将整组向下分解 |

When the inventory is full, extra exchange results are dropped at the player.

若背包已满，额外兑换产物会掉落在玩家身边。

## Configuration / 配置

The common config file is generated at:

```text
.minecraft/config/yumesumi_eco-common.toml
```

Available settings / 可配置项：

| Key / 键 | Description / 说明 |
| --- | --- |
| `general.enable_drops` | Enables or disables monster coin drops / 启用或禁用怪物掉落梦澄币 |
| `<coin>.chance` | Drop chance from monsters, in percent / 怪物掉落概率，单位为百分比 |
| `<coin>.min_count` | Minimum drop amount / 最小掉落数量 |
| `<coin>.max_count` | Maximum drop amount / 最大掉落数量 |

Coin sections are `copper_coin`, `silver_coin`, `gold_coin`, and `diamond_coin`.

币种配置段为 `copper_coin`、`silver_coin`、`gold_coin`、`diamond_coin`。

## Compatibility / 兼容性

| Dependency / 依赖 | Version / 版本 |
| --- | --- |
| Minecraft | 1.21.1 |
| NeoForge | 21.1.200 or newer / 21.1.200 及以上 |
| Java | 21 |

## Development / 开发

```bash
git clone https://github.com/Yumesumi/Yumesumi-Economy.git
cd Yumesumi-Economy
./gradlew build
```

Useful Gradle tasks / 常用 Gradle 任务：

| Command / 命令 | Purpose / 用途 |
| --- | --- |
| `./gradlew build` | Build and validate the mod jar / 构建并验证模组 jar |
| `./gradlew runClient` | Start a development client / 启动开发客户端 |
| `./gradlew runServer` | Start a development server / 启动开发服务器 |

Generated folders such as `build/`, `run/`, `.gradle/`, and IDE metadata are ignored and should not be committed.

`build/`、`run/`、`.gradle/` 和 IDE 元数据等生成目录不应提交到仓库。

## License / 许可

All Rights Reserved.
