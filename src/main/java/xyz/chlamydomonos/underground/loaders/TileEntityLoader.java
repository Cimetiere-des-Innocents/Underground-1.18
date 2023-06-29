package xyz.chlamydomonos.underground.loaders;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.chlamydomonos.underground.Underground;
import xyz.chlamydomonos.underground.tileentities.GroundExplosionTileEntity;

public class TileEntityLoader
{
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Underground.MODID);

    @SuppressWarnings("ConstantConditions")
    public static final RegistryObject<BlockEntityType<?>> GROUND_EXPLOSION = TILE_ENTITIES.register("ground_explosion", () -> BlockEntityType.Builder.of(GroundExplosionTileEntity::new, BlockLoader.GROUND_EXPLOSION.get()).build(null));

    public static void registerAll()
    {
        TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
