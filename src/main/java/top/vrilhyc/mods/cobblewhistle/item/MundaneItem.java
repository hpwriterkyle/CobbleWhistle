package top.vrilhyc.mods.cobblewhistle.item;

import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.joml.Vector3d;
import top.vrilhyc.mods.cobblewhistle.ItemCooldownManager;
import top.vrilhyc.mods.cobblewhistle.menu.PokemonSelectMenu;
import top.vrilhyc.mods.cobblewhistle.sound.WhistleSoundEvents;
import top.vrilhyc.mods.cobblewhistle.util.Utils;

import java.util.Comparator;
import java.util.List;

public class MundaneItem extends Item {
    protected String name;

    public MundaneItem(String name, Properties properties) {
        super(properties);
        this.name = name;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA,CustomData.EMPTY);
        CompoundTag tag = data.copyTag();
        if(tag.contains("cobblewhistle")){
            Species species = PokemonSpecies.INSTANCE.getByName(tag.getString("cobblewhistle").toLowerCase());
            tooltipComponents.add(Component.translatable("itemTooltip.cobblewhistle.used."+name,species.getTranslatedName().getString()));
            return;
        }
        tooltipComponents.add(Component.translatable("itemTooltip.cobblewhistle.unused."+name));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public void onDestroyed(ItemEntity itemEntity) {
        super.onDestroyed(itemEntity);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(context.getLevel().isClientSide){
            return InteractionResult.sidedSuccess(true);
        }
        ServerPlayer player = (ServerPlayer) context.getPlayer();
        CustomData data = context.getItemInHand().getOrDefault(DataComponents.CUSTOM_DATA,CustomData.EMPTY);
        CompoundTag tag = data.copyTag();
        if(!tag.contains("cobblewhistle")) {
            player.server.execute(()->{
//                tag.putString("cobblewhistle",species.getName());
                new PokemonSelectMenu(context.getItemInHand()).open(player);
                WhistleSoundEvents.play(player,1.0F);
            });
      //      context.getItemInHand().applyComponents(DataComponentMap.builder().addAll(context.getItemInHand().getComponents()).set(DataComponents.CUSTOM_DATA,CustomData.EMPTY).build());
            return InteractionResult.CONSUME;
        }
        if(ItemCooldownManager.getOrCreate(player).isOnCooldown(WhistleItems.WHISTLE)){
            Utils.displayMessage(player,Component.translatable("message.cobblewhistle.cooldown"));
            return InteractionResult.CONSUME;
        }
        String species = tag.getString("cobblewhistle");
        player.server.execute(()->{
            Species pokemonSpecies = PokemonSpecies.INSTANCE.getByName(species.toLowerCase());
            spawnPokemon(player,pokemonSpecies);
            Utils.displayMessage(player,Component.translatable("message.cobblewhistle.spawn",pokemonSpecies.getTranslatedName().getString()));
        });
        ItemCooldownManager.getOrCreate(player).start().recount();
//        context.getItemInHand().
//        if(Cobblebucketitems.getWorldBucketManager().hasUsedBucket()){
//            player.displayClientMessage(Component.translatable("message.cobblebucketitems.using",Cobblebucketitems.getWorldBucketManager().getGlobalGroup().getLeftTime()/1000L).withStyle(),false);
//            return InteractionResult.FAIL;
//        }
//        getGroup().setUpOnGlobal(player);
//        BlockPos pos = player.getOnPos();
//        try {
//            new Star(new Vector3d(player.xOld,player.yOld,player.zOld)).show(player.serverLevel());
//        } catch (CloneNotSupportedException e) {
//            throw new RuntimeException(e);
//        }
//        player.displayClientMessage(Component.translatable("message.cobblebucketitems.used").withStyle(),false);
//        //TODO MORE
//        if(player.gameMode.isSurvival()) {
//            ItemStack is = player.getInventory().getItem(player.getInventory().selected);
//            is.setCount(is.getCount()-1);
//        }
        return InteractionResult.CONSUME;
    }

    public PokemonEntity spawnPokemon(ServerPlayer player,Species pokemonSpecies){
        PokemonEntity entity = PokemonProperties.Companion.parse(pokemonSpecies.getName().toLowerCase()).createEntity(player.serverLevel());
        entity.teleportTo(player.xOld,player.yOld,player.zOld);
        player.serverLevel().addFreshEntity(entity);
        return entity;
    }
}
