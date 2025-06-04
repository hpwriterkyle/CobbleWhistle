package top.vrilhyc.mods.cobblewhistle.sound;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import top.vrilhyc.mods.cobblewhistle.IdHelper;

public class WhistleSoundEvents {
    public static final Holder<SoundEvent> WHISTLE_SELECT_SOUND = WhistleSoundEvents.registerCopperHornSounds(Type.SELECT);

    public static void register(){
    }

    private static Holder<SoundEvent> registerCopperHornSounds(Type type) {
        return WhistleSoundEvents.registerReference("whistle_" + type.value);
    }

    private static Holder<SoundEvent> registerReference(String id) {
        var soundKey = IdHelper.modLoc(id);
        return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, soundKey, SoundEvent.createVariableRangeEvent(soundKey));
    }

    public static void play(ServerPlayer player,float volume){
        ServerLevel world = player.serverLevel();
        System.out.println("asdasdas");
      //  world.playSound(null,player.getOnPos(), SoundEvents.BEE_DEATH, SoundSource.BLOCKS, 1f, 1f);
        world.playSound(null,player.getOnPos(), WHISTLE_SELECT_SOUND.value(), SoundSource.PLAYERS,volume, 1.0f);
    }

    private enum Type {
        SELECT("select");

        public final String value;

        Type(String value) {
            this.value = value;
        }
    }
}