package com.yumesumi.yumeeco;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(yumesumieconomy.MODID);

    // 基础硬币
    public static final DeferredItem<Item> COPPER_COIN = ITEMS.registerSimpleItem("copper_coin", props(Rarity.COMMON));
    public static final DeferredItem<Item> SILVER_COIN = ITEMS.registerSimpleItem("silver_coin", props(Rarity.UNCOMMON));
    public static final DeferredItem<Item> GOLD_COIN = ITEMS.registerSimpleItem("gold_coin", props(Rarity.RARE));

    // 特殊钻石币：闪烁 + 防火
    public static final DeferredItem<Item> DIAMOND_COIN = ITEMS.register("diamond_coin",
            () -> new Item(props(Rarity.EPIC).fireResistant()) {
                @Override
                public boolean isFoil(ItemStack stack) { return true; }
            });

    private static Item.Properties props(Rarity rarity) {
        return new Item.Properties().rarity(rarity).stacksTo(64);
    }
}