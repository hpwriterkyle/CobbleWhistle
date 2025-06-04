package top.vrilhyc.mods.cobblewhistle;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class IdHelper {

    public static ResourceLocation modLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(Cobblewhistle.MOD_ID, path);
    }

    public static ResourceLocation modGeoLoc(String type, String path) {
        if (path == null) {
            return null;
        }

        return ResourceLocation.fromNamespaceAndPath(Cobblewhistle.MOD_ID, "geo/" + type + "/" + path + ".geo.json");
    }

    public static ResourceLocation modTextureLoc(String type, String path) {
        if (path == null) {
            return null;
        }

        return ResourceLocation.fromNamespaceAndPath(Cobblewhistle.MOD_ID, "textures/" + type + "/" + path + ".png");
    }

    public static ResourceLocation modAnimateLoc(String type, String path) {
        if (path == null) {
            return null;
        }

        return ResourceLocation.fromNamespaceAndPath(Cobblewhistle.MOD_ID, "animations/" + type + "/" + path + ".animation.json");
    }

    public static ResourceLocation modBlockGeoLoc(String name) {
        return modGeoLoc("geo", name);
    }

    public static ResourceLocation modBlockTextureLoc(String name) {
        return modTextureLoc("block", name);
    }

    public static ResourceLocation modBlockAnimateLoc(String name) {
        return modTextureLoc("block", "/block/" + name);
    }

    public static ResourceKey<Block> blockKey(String name) {
        return ResourceKey.create(Registries.BLOCK, modLoc(name));
    }

    public static ResourceKey<Item> itemKey(String name) {
        return ResourceKey.create(Registries.ITEM, modLoc(name));
    }

    public static ResourceKey<SoundEvent> soundKey(String name) {
        return ResourceKey.create(Registries.SOUND_EVENT, modLoc(name));
    }

    public static ResourceKey<Registry<CreativeModeTab>> groupKey(String name) {
        return ResourceKey.create(Registries.CREATIVE_MODE_TAB.registryKey(), modLoc(name));
    }
}
