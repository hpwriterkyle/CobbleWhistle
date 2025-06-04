package top.vrilhyc.mods.cobblewhistle;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import top.vrilhyc.mods.cobblewhistle.item.ModItemGroups;
import top.vrilhyc.mods.cobblewhistle.item.WhistleItems;
import top.vrilhyc.mods.cobblewhistle.sound.WhistleSoundEvents;

public class Cobblewhistle implements ModInitializer {
    public static final String MOD_ID = "cobblewhistle";
    public static PokemonItemManager pokemonItemManager;
    @Override
    public void onInitialize() {
        pokemonItemManager = new PokemonItemManager();
        WhistleItems.register();
        ModItemGroups.register();
        WhistleSoundEvents.register();
        ServerPlayConnectionEvents.JOIN.register((serverGamePacketListener, packetSender, minecraftServer) -> {
            ItemCooldownManager.getOrCreate(serverGamePacketListener.getPlayer()).recount();
        });
        ServerPlayConnectionEvents.DISCONNECT.register((serverGamePacketListener, minecraftServer) -> ItemCooldownManager.getOrCreate(serverGamePacketListener.getPlayer()).setStartTime(System.currentTimeMillis()));
    }
}
