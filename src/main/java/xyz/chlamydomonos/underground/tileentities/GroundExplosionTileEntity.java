package xyz.chlamydomonos.underground.tileentities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.chlamydomonos.underground.loaders.TileEntityLoader;

public class GroundExplosionTileEntity extends BlockEntity
{
    private Player player;
    private int blocksToCreate;
    private boolean exploding;
    private int countDown;

    public GroundExplosionTileEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(TileEntityLoader.GROUND_EXPLOSION.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.putUUID("player", player.getUUID());
        tag.putInt("blocks_to_create", blocksToCreate);
        tag.putBoolean("exploding", exploding);
        tag.putInt("count_down", countDown);
    }

    @Override
    public void load(@NotNull CompoundTag tag)
    {
        super.load(tag);
        assert level != null;
        player = level.getPlayerByUUID(tag.getUUID("player"));
        blocksToCreate = tag.getInt("blocks_to_create");
        exploding = tag.getBoolean("exploding");
        countDown = tag.getInt("count_down");
    }

    @Override
    public @NotNull CompoundTag getUpdateTag()
    {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public int getBlocksToCreate()
    {
        return blocksToCreate;
    }

    public void setBlocksToCreate(int blocksToCreate)
    {
        this.blocksToCreate = blocksToCreate;
    }

    public boolean isExploding()
    {
        return exploding;
    }

    public void setExploding(boolean exploding)
    {
        this.exploding = exploding;
    }

    public int getCountDown()
    {
        return countDown;
    }

    public void setCountDown(int countDown)
    {
        this.countDown = countDown;
    }
}
