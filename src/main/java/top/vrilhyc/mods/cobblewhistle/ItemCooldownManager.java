package top.vrilhyc.mods.cobblewhistle;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ServerItemCooldowns;
import top.vrilhyc.mods.cobblewhistle.item.WhistleItems;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ItemCooldownManager {
    protected static Map<UUID, WhistleCooldown> cooldownsMap = new HashMap<>();

    public static WhistleCooldown getOrCreate(ServerPlayer player){
        return cooldownsMap.compute(player.getUUID(),(a,b)->{
            if(b==null){
                return new WhistleCooldown(player, WhistleItems.WHISTLE);
            }
            return b;
        });
    }

    public static Optional<WhistleCooldown> getIfPresent(ServerPlayer player){
        return Optional.of(cooldownsMap.get(player.getUUID()));
    }
}
