package top.vrilhyc.mods.cobblewhistle.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import top.vrilhyc.mods.cobblewhistle.IdHelper;

import java.util.function.Supplier;

public class ModItemGroups {
     public static final CreativeModeTab CUSTOM_ITEM_GROUP = register("item_group",()->CreativeModeTab.builder(CreativeModeTab.Row.TOP,9)
                     .icon(()->new ItemStack(WhistleItems.WHISTLE))
                     .title(Component.translatable("itemGroup.cobblewhistle_blockgroup")).build());

    private static CreativeModeTab register(String name, Supplier<CreativeModeTab> b) {
        var groupKey = ResourceKey.create(IdHelper.groupKey(name),Registries.CREATIVE_MODE_TAB.registry());
        var tab = b.get();
        CreativeModeTab tabs = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,groupKey,tab);
        ItemGroupEvents.modifyEntriesEvent(groupKey).register(group->{
            WhistleItems.blocks.forEach(group::accept);
        });
        return tabs;
    }

    public static void register() {}
}
