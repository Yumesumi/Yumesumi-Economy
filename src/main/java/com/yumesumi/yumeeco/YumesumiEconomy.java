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

@Mod(YumesumiEconomy.MOD_ID)
public final class YumesumiEconomy {
    public static final String MOD_ID = "yumesumi_eco";

    private static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ECO_TAB = CREATIVE_TABS.register(
            "eco_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.yumesumi_eco"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ModItems.GOLD_COIN.get().getDefaultInstance())
                    .displayItems((parameters, output) -> ModItems.COINS.forEach(coin -> output.accept(coin.get())))
                    .build()
    );

    public YumesumiEconomy(IEventBus modEventBus, ModContainer modContainer) {
        ModItems.ITEMS.register(modEventBus);
        CREATIVE_TABS.register(modEventBus);
        modContainer.registerConfig(net.neoforged.fml.config.ModConfig.Type.COMMON, ModConfig.SPEC);
    }
}
