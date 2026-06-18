package com.yumesumi.yumeeco;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ModConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_DROPS;

    // 铜币 (Copper)
    public static final ModConfigSpec.DoubleValue COPPER_CHANCE;
    public static final ModConfigSpec.IntValue COPPER_MIN, COPPER_MAX;

    // 银币 (Silver)
    public static final ModConfigSpec.DoubleValue SILVER_CHANCE;
    public static final ModConfigSpec.IntValue SILVER_MIN, SILVER_MAX;

    // 金币 (Gold)
    public static final ModConfigSpec.DoubleValue GOLD_CHANCE;
    public static final ModConfigSpec.IntValue GOLD_MIN, GOLD_MAX;

    // 钻石币 (Diamond)
    public static final ModConfigSpec.DoubleValue DIAMOND_CHANCE;
    public static final ModConfigSpec.IntValue DIAMOND_MIN, DIAMOND_MAX;

    static {
        BUILDER.push("General Settings | 通用设置");
        ENABLE_DROPS = BUILDER.comment("Enable monster coin drops | 是否开启怪物掉落硬币功能")
                .define("enable_drops", true);
        BUILDER.pop();

        BUILDER.push("Copper Coin | 铜币");
        // 为了满足你约 94.9% 的总掉落率 (64.9 + 30)
        COPPER_CHANCE = BUILDER.comment("Drop chance (0.0 - 100.0) | 掉落概率 (0-100)")
                .defineInRange("chance", 94.9, 0.0, 100.0);
        COPPER_MIN = BUILDER.comment("Minimum drop amount | 最小掉落数量").defineInRange("min_count", 1, 1, 64);
        COPPER_MAX = BUILDER.comment("Maximum drop amount | 最大掉落数量").defineInRange("max_count", 2, 1, 64);
        BUILDER.pop();

        BUILDER.push("Silver Coin | 银币");
        SILVER_CHANCE = BUILDER.comment("Drop chance (0.0 - 100.0) | 掉落概率 (0-100)")
                .defineInRange("chance", 5.0, 0.0, 100.0);
        SILVER_MIN = BUILDER.comment("Minimum drop amount | 最小掉落数量").defineInRange("min_count", 1, 1, 64);
        SILVER_MAX = BUILDER.comment("Maximum drop amount | 最大掉落数量").defineInRange("max_count", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Gold Coin | 金币");
        GOLD_CHANCE = BUILDER.comment("Drop chance (0.0 - 100.0) | 掉落概率 (0-100)")
                .defineInRange("chance", 0.1, 0.0, 100.0);
        GOLD_MIN = BUILDER.comment("Minimum drop amount | 最小掉落数量").defineInRange("min_count", 1, 1, 64);
        GOLD_MAX = BUILDER.comment("Maximum drop amount | 最大掉落数量").defineInRange("max_count", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Diamond Coin | 钻石币");
        DIAMOND_CHANCE = BUILDER.comment("Drop chance (0.0 - 100.0) | 掉落概率 (0-100)")
                .defineInRange("chance", 0.0, 0.0, 100.0);
        DIAMOND_MIN = BUILDER.comment("Minimum drop amount | 最小掉落数量").defineInRange("min_count", 1, 1, 64);
        DIAMOND_MAX = BUILDER.comment("Maximum drop amount | 最大掉落数量").defineInRange("max_count", 1, 1, 64);
        BUILDER.pop();
    }

    static final ModConfigSpec SPEC = BUILDER.build();
}