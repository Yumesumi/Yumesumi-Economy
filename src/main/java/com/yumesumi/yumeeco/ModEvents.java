package com.yumesumi.yumeeco;

import java.util.List;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.registries.DeferredItem;

@EventBusSubscriber(modid = YumesumiEconomy.MOD_ID)
public final class ModEvents {
    private static final int EXCHANGE_RATE = 8;

    private static final List<DeferredItem<Item>> COIN_CHAIN = ModItems.COINS;
    private static final List<CoinDrop> MONSTER_DROPS = List.of(
            new CoinDrop(ModItems.DIAMOND_COIN, ModConfig.DIAMOND),
            new CoinDrop(ModItems.GOLD_COIN, ModConfig.GOLD),
            new CoinDrop(ModItems.SILVER_COIN, ModConfig.SILVER),
            new CoinDrop(ModItems.COPPER_COIN, ModConfig.COPPER)
    );

    private ModEvents() {
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!ModConfig.ENABLE_DROPS.get()
                || event.getEntity().level().isClientSide()
                || !(event.getEntity() instanceof Monster)) {
            return;
        }

        MONSTER_DROPS.forEach(drop -> tryDropCoin(event, drop));
    }

    @SubscribeEvent
    public static void onRightClickCoin(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        boolean decomposing = player.isShiftKeyDown();

        if (!canExchange(stack, decomposing)) {
            return;
        }

        if (!player.level().isClientSide() && !exchangeHeldCoin(player, event.getHand(), stack, decomposing)) {
            return;
        }

        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);
    }

    private static void tryDropCoin(LivingDropsEvent event, CoinDrop drop) {
        double chance = drop.config().chance().get();
        if (chance <= 0.0) {
            return;
        }

        var random = event.getEntity().getRandom();
        if (random.nextDouble() * 100.0 >= chance) {
            return;
        }

        int min = Math.min(drop.config().minCount().get(), drop.config().maxCount().get());
        int max = Math.max(drop.config().minCount().get(), drop.config().maxCount().get());
        int amount = min + random.nextInt(max - min + 1);
        var entity = event.getEntity();

        event.getDrops().add(new ItemEntity(
                entity.level(),
                entity.getX(),
                entity.getY(),
                entity.getZ(),
                new ItemStack(drop.item().get(), amount)
        ));
    }

    private static boolean canExchange(ItemStack stack, boolean decomposing) {
        Item target = adjacentCoin(stack.getItem(), decomposing ? -1 : 1);
        return target != null && (decomposing || stack.getCount() >= EXCHANGE_RATE);
    }

    private static boolean exchangeHeldCoin(Player player, InteractionHand hand, ItemStack stack, boolean decomposing) {
        Item target = adjacentCoin(stack.getItem(), decomposing ? -1 : 1);
        if (target == null) {
            return false;
        }

        if (decomposing) {
            replaceHeldStack(player, hand, target, stack.getCount() * EXCHANGE_RATE);
            return true;
        }

        if (stack.getCount() < EXCHANGE_RATE) {
            return false;
        }

        int targetCount = stack.getCount() / EXCHANGE_RATE;
        int remainder = stack.getCount() % EXCHANGE_RATE;
        player.setItemInHand(hand, new ItemStack(target, targetCount));

        if (remainder > 0) {
            giveOrDrop(player, new ItemStack(stack.getItem(), remainder));
        }
        return true;
    }

    private static void replaceHeldStack(Player player, InteractionHand hand, Item item, int totalCount) {
        int maxStackSize = new ItemStack(item).getMaxStackSize();
        int heldCount = Math.min(totalCount, maxStackSize);
        player.setItemInHand(hand, new ItemStack(item, heldCount));

        for (int remaining = totalCount - heldCount; remaining > 0; remaining -= maxStackSize) {
            giveOrDrop(player, new ItemStack(item, Math.min(remaining, maxStackSize)));
        }
    }

    private static void giveOrDrop(Player player, ItemStack stack) {
        if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
        }
    }

    private static Item adjacentCoin(Item item, int offset) {
        int index = coinIndex(item);
        int targetIndex = index + offset;
        if (index < 0 || targetIndex < 0 || targetIndex >= COIN_CHAIN.size()) {
            return null;
        }
        return COIN_CHAIN.get(targetIndex).get();
    }

    private static int coinIndex(Item item) {
        for (int index = 0; index < COIN_CHAIN.size(); index++) {
            if (COIN_CHAIN.get(index).get() == item) {
                return index;
            }
        }
        return -1;
    }

    private record CoinDrop(DeferredItem<Item> item, ModConfig.CoinDropConfig config) {
    }
}
