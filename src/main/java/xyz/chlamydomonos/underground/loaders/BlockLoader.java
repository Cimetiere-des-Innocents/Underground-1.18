package xyz.chlamydomonos.underground.loaders;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.chlamydomonos.underground.Underground;
import xyz.chlamydomonos.underground.blocks.GroundExplosionBlock;

public class BlockLoader
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Underground.MODID);
    public static final RegistryObject<Block> GROUND_EXPLOSION = BLOCKS.register("ground_explosion", GroundExplosionBlock::new);

    public static void registerAll()
    {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
