package com.yumesumi.yumeeco;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class ModConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_DROPS;
    public static final CoinDropConfig COPPER;
    public static final CoinDropConfig SILVER;
    public static final CoinDropConfig GOLD;
    public static final CoinDropConfig DIAMOND;

    public static final ModConfigSpec SPEC;

    static {
        BUILDER.push("general");
        ENABLE_DROPS = BUILDER
                .comment("Enable monster coin drops. / 是否启用怪物掉落梦澄币。")
                .define("enable_drops", true);
        BUILDER.pop();

        COPPER = defineCoinDrop("copper_coin", "Copper Coin / 铜币", 94.9, 1, 2);
        SILVER = defineCoinDrop("silver_coin", "Silver Coin / 银币", 5.0, 1, 1);
        GOLD = defineCoinDrop("gold_coin", "Gold Coin / 金币", 0.1, 1, 1);
        DIAMOND = defineCoinDrop("diamond_coin", "Diamond Coin / 钻石币", 0.0, 1, 1);

        SPEC = BUILDER.build();
    }

    private ModConfig() {
    }

    private static CoinDropConfig defineCoinDrop(
            String path,
            String label,
            double defaultChance,
            int defaultMin,
            int defaultMax
    ) {
        BUILDER.push(path);
        var chance = BUILDER
                .comment(label + " drop chance from monsters, in percent. / 怪物掉落概率，单位为百分比。")
                .defineInRange("chance", defaultChance, 0.0, 100.0);
        var min = BUILDER
                .comment(label + " minimum drop amount. / 最小掉落数量。")
                .defineInRange("min_count", defaultMin, 1, 64);
        var max = BUILDER
                .comment(label + " maximum drop amount. / 最大掉落数量。")
                .defineInRange("max_count", defaultMax, 1, 64);
        BUILDER.pop();

        return new CoinDropConfig(chance, min, max);
    }

    public record CoinDropConfig(
            ModConfigSpec.DoubleValue chance,
            ModConfigSpec.IntValue minCount,
            ModConfigSpec.IntValue maxCount
    ) {
    }
}
