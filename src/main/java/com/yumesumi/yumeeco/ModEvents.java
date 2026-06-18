package com.yumesumi.yumeeco;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import java.util.Random;

@EventBusSubscriber(modid = yumesumieconomy.MODID)
public class ModEvents {
    private static final Random RANDOM = new Random();
    private static final int COIN_EXCHANGE_RATE = 8;

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!ModConfig.ENABLE_DROPS.get()) return;
        if (event.getEntity().level().isClientSide || !(event.getEntity() instanceof Monster)) return;

        // 为每种硬币进行独立的掉落判定 (互不干扰，支持同时掉落)
        tryDropCoin(event, ModItems.DIAMOND_COIN.get(), ModConfig.DIAMOND_CHANCE.get(), ModConfig.DIAMOND_MIN.get(), ModConfig.DIAMOND_MAX.get());
        tryDropCoin(event, ModItems.GOLD_COIN.get(), ModConfig.GOLD_CHANCE.get(), ModConfig.GOLD_MIN.get(), ModConfig.GOLD_MAX.get());
        tryDropCoin(event, ModItems.SILVER_COIN.get(), ModConfig.SILVER_CHANCE.get(), ModConfig.SILVER_MIN.get(), ModConfig.SILVER_MAX.get());
        tryDropCoin(event, ModItems.COPPER_COIN.get(), ModConfig.COPPER_CHANCE.get(), ModConfig.COPPER_MIN.get(), ModConfig.COPPER_MAX.get());
    }

    /**
     * 辅助方法：处理单一物品的掉落概率与数量逻辑
     */
    private static void tryDropCoin(LivingDropsEvent event, Item coinItem, double chance, int min, int max) {
        // 如果概率为0，直接跳过
        if (chance <= 0) return;

        // RANDOM.nextDouble() 产生 0.0~1.0 的小数，乘以 100 转换为 0~100 的百分比格式
        double roll = RANDOM.nextDouble() * 100.0;

        // 如果掷骰子结果小于设定概率，则触发掉落
        if (roll < chance) {
            // 防止玩家填错配置（把最小填得比最大还大），做个安全处理
            int actualMin = Math.min(min, max);
            int actualMax = Math.max(min, max);

            // 在 Min 和 Max 之间随机生成掉落数量
            int amount = actualMin + (actualMax > actualMin ? RANDOM.nextInt(actualMax - actualMin + 1) : 0);

            event.getDrops().add(new ItemEntity(
                    event.getEntity().level(),
                    event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(),
                    new ItemStack(coinItem, amount)
            ));
        }
    }

    @SubscribeEvent
    public static void onRightClickCoin(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        if (!isCoin(stack.getItem())) return;

        if (!player.level().isClientSide) {
            boolean converted = player.isShiftKeyDown()
                    ? decomposeHeldCoin(player, event.getHand(), stack)
                    : composeHeldCoin(player, event.getHand(), stack);
            if (!converted) return;
        }

        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);
    }

    private static boolean composeHeldCoin(Player player, InteractionHand hand, ItemStack stack) {
        Item higherCoin = getHigherCoin(stack.getItem());
        if (higherCoin == null || stack.getCount() < COIN_EXCHANGE_RATE) return false;

        int higherCount = stack.getCount() / COIN_EXCHANGE_RATE;
        int remainder = stack.getCount() % COIN_EXCHANGE_RATE;

        player.setItemInHand(hand, new ItemStack(higherCoin, higherCount));
        if (remainder > 0) {
            giveOrDrop(player, new ItemStack(stack.getItem(), remainder));
        }
        return true;
    }

    private static boolean decomposeHeldCoin(Player player, InteractionHand hand, ItemStack stack) {
        Item lowerCoin = getLowerCoin(stack.getItem());
        if (lowerCoin == null) return false;

        int totalLowerCoins = stack.getCount() * COIN_EXCHANGE_RATE;
        int heldCount = Math.min(totalLowerCoins, lowerCoin.getDefaultMaxStackSize());

        player.setItemInHand(hand, new ItemStack(lowerCoin, heldCount));
        totalLowerCoins -= heldCount;

        while (totalLowerCoins > 0) {
            int count = Math.min(totalLowerCoins, lowerCoin.getDefaultMaxStackSize());
            giveOrDrop(player, new ItemStack(lowerCoin, count));
            totalLowerCoins -= count;
        }
        return true;
    }

    private static void giveOrDrop(Player player, ItemStack stack) {
        if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
        }
    }

    private static boolean isCoin(Item item) {
        return item == ModItems.COPPER_COIN.get()
                || item == ModItems.SILVER_COIN.get()
                || item == ModItems.GOLD_COIN.get()
                || item == ModItems.DIAMOND_COIN.get();
    }

    private static Item getHigherCoin(Item item) {
        if (item == ModItems.COPPER_COIN.get()) return ModItems.SILVER_COIN.get();
        if (item == ModItems.SILVER_COIN.get()) return ModItems.GOLD_COIN.get();
        if (item == ModItems.GOLD_COIN.get()) return ModItems.DIAMOND_COIN.get();
        return null;
    }

    private static Item getLowerCoin(Item item) {
        if (item == ModItems.DIAMOND_COIN.get()) return ModItems.GOLD_COIN.get();
        if (item == ModItems.GOLD_COIN.get()) return ModItems.SILVER_COIN.get();
        if (item == ModItems.SILVER_COIN.get()) return ModItems.COPPER_COIN.get();
        return null;
    }
}
