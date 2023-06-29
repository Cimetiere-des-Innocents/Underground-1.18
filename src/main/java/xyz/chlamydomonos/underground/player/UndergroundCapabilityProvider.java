package xyz.chlamydomonos.underground.player;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.chlamydomonos.underground.api.UndergroundAPI;
import xyz.chlamydomonos.underground.api.player.IUndergroundCapability;

public class UndergroundCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag>
{
    private IUndergroundCapability capability;
    private final Player player;
    public UndergroundCapabilityProvider(Player player)
    {
        this.player = player;
    }
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
    {
        return cap == UndergroundAPI.CAPABILITY ? LazyOptional.of(this::getOrCreate).cast() : LazyOptional.empty();
    }

    @NotNull IUndergroundCapability getOrCreate()
    {
        if(capability == null)
            capability = new UndergroundCapability(player, 600);
        return capability;
    }

    @Override
    public CompoundTag serializeNBT()
    {
        return getOrCreate().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt)
    {
        getOrCreate().deserializeNBT(nbt);
    }
}
