package top.vrilhyc.mods.cobblewhistle.menu;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.pokemon.Species;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import org.apache.commons.lang3.stream.IntStreams;
import top.vrilhyc.mods.cobblewhistle.Cobblewhistle;
import top.vrilhyc.mods.cobblewhistle.util.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PokemonSelectMenu {
    private ItemStack item;
    protected GooeyPage page;

    public PokemonSelectMenu(ItemStack item){
        this.item = item;
    }

    private ChestTemplate panel(){
        return ChestTemplate.builder(3).fillFromList(buttons()).build();
    }

    private List<Button> buttons(){
        List<Species> list = PokemonSpecies.INSTANCE.getSpecies().stream().sorted(Comparator.comparing(a->Math.random(),Comparator.naturalOrder())).limit(10).toList();
        List<Button> buttons = IntStreams.range(17).mapToObj(a-> (Button)GooeyButton.of(ItemStack.EMPTY)).toList();
        List<Button> buttonss = new ArrayList<>();
        buttonss.addAll(buttons);
        buttonss.addAll(list.stream().map(d->{
            ItemStack is = Cobblewhistle.pokemonItemManager.fromPokemon(d);
            return GooeyButton.builder().display(is).onClick(a->{
                item.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE,true);
                setPokemon(d);
                UIManager.closeUI(a.getPlayer());
                Utils.displayMessage(a.getPlayer(), Component.translatable("message.cobblewhistle.select",d.getTranslatedName().getString()));
            }
            ).build();
        }).toList());
        return buttonss.stream().sorted(Comparator.comparing(a->Math.random(),Comparator.naturalOrder())).toList();
    }

    public void open(ServerPlayer player){
        page = GooeyPage.builder().template(panel()).build();
        UIManager.openUIForcefully(player,page);
    }

    public void setPokemon(Species species){
        CustomData data = item.getOrDefault(DataComponents.CUSTOM_DATA,CustomData.EMPTY);
        CompoundTag tag = data.copyTag();
        tag.putString("cobblewhistle",species.getName());
        CustomData.set(DataComponents.CUSTOM_DATA,item,tag);
    }
}
