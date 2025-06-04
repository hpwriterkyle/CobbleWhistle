package top.vrilhyc.mods.cobblewhistle;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.item.PokemonItem;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PokemonItemManager {
    protected Map<String,String> messages = new HashMap<>();

    public ItemStack fromPokemon(Pokemon pokemon){
        ItemStack is = PokemonItem.from(pokemon);
        return is;
    }

    public ItemStack fromPokemon(Species species){
        ItemStack is = PokemonItem.from(species.create(1));
        return is;
    }

    public Pokemon fromItemStack(ItemStack pokemonItem){
        return CobblemonItems.POKEMON_MODEL.asPokemon(pokemonItem);
    }
}
