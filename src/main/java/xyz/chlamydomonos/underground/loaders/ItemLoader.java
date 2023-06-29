package xyz.chlamydomonos.underground.loaders;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.chlamydomonos.underground.Underground;

public class ItemLoader
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Underground.MODID);
}
