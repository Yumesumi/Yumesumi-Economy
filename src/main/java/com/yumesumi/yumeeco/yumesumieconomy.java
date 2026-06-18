package com.yumesumi.yumeeco;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(yumesumieconomy.MODID)
public class yumesumieconomy {
    public static final String MODID = "yumesumi_eco";

    // 注册表定义
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // 创造模式物品栏分组
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ECO_TAB = CREATIVE_MODE_TABS.register("eco_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.yumesumi_eco"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.GOLD_COIN.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItems.COPPER_COIN.get());
                output.accept(ModItems.SILVER_COIN.get());
                output.accept(ModItems.GOLD_COIN.get());
                output.accept(ModItems.DIAMOND_COIN.get());
            }).build());

    public yumesumieconomy(IEventBus modEventBus, ModContainer modContainer) {
        // 注册物品和标签页
        ModItems.ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        modContainer.registerConfig(net.neoforged.fml.config.ModConfig.Type.COMMON, ModConfig.SPEC);
    }
}