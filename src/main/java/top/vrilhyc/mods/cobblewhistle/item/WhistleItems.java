package top.vrilhyc.mods.cobblewhistle.item;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import top.vrilhyc.mods.cobblewhistle.IdHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class WhistleItems {
    public static List<Item> blocks = new ArrayList<>();
    public static MundaneItem WHISTLE = register("whistle",()->new MundaneItem("whistle",new Item.Properties().rarity(Rarity.COMMON)));

    private static MundaneItem register(String name, Supplier<MundaneItem> b) {
        var blockKey = IdHelper.blockKey(name);
        var block = b.get();
        String[] names = name.split("_");
        var itemKey = IdHelper.itemKey(name);
        Registry.register(BuiltInRegistries.ITEM, itemKey,block);
        blocks.add(block);
        return block;
    }

    public static void register() {
    }
}
