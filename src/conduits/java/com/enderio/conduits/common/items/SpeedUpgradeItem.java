package com.enderio.conduits.common.items;

import com.enderio.api.capability.IConduitUpgrade;
import com.enderio.conduits.common.init.ConduitComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;

public class SpeedUpgradeItem extends Item {

    public static final ICapabilityProvider<ItemStack, Void, IConduitUpgrade> ITEM_SPEED_UPGRADE_PROVIDER
        = (stack, v) -> stack.get(ConduitComponents.ITEM_SPEED_UPGRADE);

    public static final ICapabilityProvider<ItemStack, Void, IConduitUpgrade> FLUID_SPEED_UPGRADE_PROVIDER
        = (stack, v) -> stack.get(ConduitComponents.FLUID_SPEED_UPGRADE);


    public SpeedUpgradeItem(Properties pProperties) {
        super(pProperties);
    }
}
