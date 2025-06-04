package top.vrilhyc.mods.cobblewhistle;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ServerItemCooldowns;
import top.vrilhyc.mods.cobblewhistle.item.WhistleItems;

public class WhistleCooldown extends ServerItemCooldowns {
    protected long startTime = 0;
    protected long endTime = 0;
    protected Item item;

    public WhistleCooldown(ServerPlayer player,Item item) {
        super(player);
        this.item = item;
    }

    @Override
    protected void onCooldownStarted(Item item, int ticks) {
        setStartTime(System.currentTimeMillis());
        long time = endTime-startTime;
        super.onCooldownStarted(item,(int)(time/50));
    }

    @Override
    public boolean isOnCooldown(Item item) {
        onCooldownStarted(item,0);
        return System.currentTimeMillis()<endTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void recount(){
        isOnCooldown(WhistleItems.WHISTLE);
    }

    public WhistleCooldown start(){
        startTime = System.currentTimeMillis();
        endTime = startTime+5*1000L;
        return this;
    }
}
