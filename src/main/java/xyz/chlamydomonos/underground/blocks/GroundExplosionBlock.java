package xyz.chlamydomonos.underground.blocks;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.ticks.TickPriority;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.chlamydomonos.underground.api.UndergroundAPI;
import xyz.chlamydomonos.underground.tileentities.GroundExplosionTileEntity;

import java.util.Random;

public class GroundExplosionBlock extends Block implements EntityBlock
{
    public GroundExplosionBlock()
    {
        super(Properties.of(Material.EXPLOSIVE).strength(10.0f, 0.000001f));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void tick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull Random pRandom)
    {
        if (pLevel.isClientSide) return;

        var tempTileEntity = pLevel.getBlockEntity(pPos);
        if (!(tempTileEntity instanceof GroundExplosionTileEntity tileEntity)) return;
        if (tileEntity.isExploding())
        {
            pLevel.explode(tileEntity.getPlayer(), UndergroundAPI.ON_GROUND, null, pPos.getX(), pPos.getY(), pPos.getZ(), 5, true, Explosion.BlockInteraction.BREAK);
            pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 3);
            return;
        }

        if (tileEntity.getCountDown() > 0)
        {
            tileEntity.setExploding(true);
            pLevel.scheduleTick(pPos, this, tileEntity.getCountDown(), TickPriority.EXTREMELY_HIGH);
        }
        tryExpand(pLevel, pPos, tileEntity.getBlocksToCreate(), pRandom);
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion)
    {
        var tileEntity = level.getBlockEntity(pos);
        if (tileEntity instanceof GroundExplosionTileEntity)
            ((GroundExplosionTileEntity) tileEntity).setExploding(true);
        if (!level.isClientSide)
        {
            level.scheduleTick(pos, this, 2, TickPriority.EXTREMELY_HIGH);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState)
    {
        return new GroundExplosionTileEntity(pPos, pState);
    }

    private void tryExpand(@NotNull ServerLevel level, @NotNull BlockPos pos, int blocksToExpand, @NotNull Random random)
    {
        boolean[] canExpand = new boolean[5];
        canExpand[0] = level.getBlockState(pos.north()).getBlock() != this;
        canExpand[1] = level.getBlockState(pos.south()).getBlock() != this;
        canExpand[2] = level.getBlockState(pos.west()).getBlock() != this;
        canExpand[3] = level.getBlockState(pos.east()).getBlock() != this;

        canExpand[4] = level.getMinBuildHeight() < pos.getY();

        if (!canExpand[0] && !canExpand[1] && !canExpand[2] && !canExpand[3] && !canExpand[4]) return;

        int[] toExpand = new int[5];
        for (int i = 0; i < 5; i++)
            toExpand[i] = 0;

        for (int i = 0; i < blocksToExpand; i++)
        {
            int temp = random.nextInt(5);
            if (canExpand[temp]) toExpand[temp]++;
            else i--;
        }

        if (toExpand[0] > 0) expand(level, pos, pos.north(), toExpand[0]);
        if (toExpand[1] > 0) expand(level, pos, pos.south(), toExpand[1]);
        if (toExpand[2] > 0) expand(level, pos, pos.west(), toExpand[2]);
        if (toExpand[3] > 0) expand(level, pos, pos.east(), toExpand[3]);
        if (toExpand[4] > 0) expand(level, pos, pos.below(), toExpand[4]);
    }

    private void expand(@NotNull ServerLevel level, @NotNull BlockPos thisPos, @NotNull BlockPos expandPos, int blocksToExpand)
    {
        level.setBlockAndUpdate(expandPos, this.defaultBlockState());
        var tempTileEntity = level.getBlockEntity(expandPos);
        if (!(tempTileEntity instanceof GroundExplosionTileEntity tileEntity))
        {
            LogUtils.getLogger().debug("BlockEntity is not GroundExplosionTileEntity");
            return;
        }

        tempTileEntity = level.getBlockEntity(thisPos);
        if (!(tempTileEntity instanceof GroundExplosionTileEntity thisTileEntity))
        {
            LogUtils.getLogger().debug("BlockEntity is not GroundExplosionTileEntity");
            return;
        }

        tileEntity.setExploding(false);
        tileEntity.setPlayer(thisTileEntity.getPlayer());
        tileEntity.setBlocksToCreate(blocksToExpand);

        level.scheduleTick(expandPos, this, 2, TickPriority.EXTREMELY_HIGH);
    }

    public void createBlock(@NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Player player, int blocksToCreate)
    {
        level.setBlockAndUpdate(pos, this.defaultBlockState());
        var tempTileEntity = level.getBlockEntity(pos);
        if (!(tempTileEntity instanceof GroundExplosionTileEntity tileEntity))
        {
            LogUtils.getLogger().debug("BlockEntity is not GroundExplosionTileEntity");
            return;
        }

        tileEntity.setExploding(false);
        tileEntity.setPlayer(player);
        tileEntity.setBlocksToCreate(blocksToCreate);
        tileEntity.setCountDown(60);

        level.scheduleTick(pos, this, 2, TickPriority.EXTREMELY_HIGH);
    }
}
