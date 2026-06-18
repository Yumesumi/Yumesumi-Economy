# 💰 Yumesumi Economy

一个轻量级的 Minecraft 经济系统模组，为你的冒险之旅增添财富的积累感！

## ✨ 特性

### 🪙 四种货币

| 货币 | 稀有度 | 默认掉落率 | 掉落数量 | 特殊效果 |
|:---:|:---:|:---:|:---:|:---:|
| 铜币 | ⚪ 普通 | 94.9% | 1-2 | — |
| 银币 | 🟢 非普通 | 5% | 1 | — |
| 金币 | 🔵 稀有 | 0.1% | 1 | — |
| 钻石币 | 🟣 史诗 | 0% (默认关闭) | 1 | ✨ 闪烁特效 + 🔥 防火 |

### ⚔️ 击杀怪物掉落

击杀任意怪物都有概率掉落硬币，每种硬币独立判定，支持同时掉落多种货币。

### 🔄 货币兑换

无需工作台，手持硬币即可随时兑换：

- **右键** — 合成（8 低级 → 1 高级）
- **Shift + 右键** — 分解（1 高级 → 8 低级）

兑换链：`铜币 ↔ 银币 ↔ 金币 ↔ 钻石币`

### ⚙️ 高度可配置

所有参数均可通过配置文件调整，包括：

- 全局开关：启用/禁用怪物掉落
- 每种货币的掉落概率（0-100%）
- 每种货币的最小/最大掉落数量

配置文件路径：`.minecraft/config/yumesumi_eco-common.toml`

## 📦 环境要求

| 依赖 | 版本 |
|:---:|:---:|
| Minecraft | 1.21.1 |
| NeoForge | 21.1.220+ |

## 🚀 安装

1. 安装 [NeoForge](https://neoforged.net/) 加载器
2. 下载最新版本的模组 jar 文件
3. 将文件放入 `.minecraft/mods/` 目录
4. 启动游戏，开始赚钱！

## 🔧 开发

```bash
# 克隆仓库
git clone https://github.com/Yumesumi/Yumesumi-Economy.git
cd Yumesumi-Economy

# 刷新依赖
./gradlew --refresh-dependencies

# 构建模组
./gradlew build

# 启动开发客户端
./gradlew runClient
```

## 📜 许可证

All Rights Reserved
