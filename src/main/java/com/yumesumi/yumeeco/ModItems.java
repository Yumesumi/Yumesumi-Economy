package com.yumesumi.yumeeco;

import java.util.List;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(YumesumiEconomy.MOD_ID);

    public static final DeferredItem<Item> COPPER_COIN = registerCoin("copper_coin", Rarity.COMMON);
    public static final DeferredItem<Item> SILVER_COIN = registerCoin("silver_coin", Rarity.UNCOMMON);
    public static final DeferredItem<Item> GOLD_COIN = registerCoin("gold_coin", Rarity.RARE);
    public static final DeferredItem<Item> DIAMOND_COIN = ITEMS.register(
            "diamond_coin",
            () -> new Item(coinProperties(Rarity.EPIC).fireResistant()) {
                @Override
                public boolean isFoil(ItemStack stack) {
                    return true;
                }
            }
    );

    public static final List<DeferredItem<Item>> COINS = List.of(
            COPPER_COIN,
            SILVER_COIN,
            GOLD_COIN,
            DIAMOND_COIN
    );

    private ModItems() {
    }

    private static DeferredItem<Item> registerCoin(String name, Rarity rarity) {
        return ITEMS.registerSimpleItem(name, coinProperties(rarity));
    }

    private static Item.Properties coinProperties(Rarity rarity) {
        return new Item.Properties().rarity(rarity).stacksTo(64);
    }
}
