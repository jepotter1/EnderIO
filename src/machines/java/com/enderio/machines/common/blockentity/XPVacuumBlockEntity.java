package com.enderio.machines.common.blockentity;

import com.enderio.base.common.init.EIOFluids;
import com.enderio.core.common.network.slot.IntegerNetworkDataSlot;
import com.enderio.machines.common.attachment.IFluidTankUser;
import com.enderio.machines.common.blockentity.base.VacuumMachineBlockEntity;
import com.enderio.machines.common.config.MachinesConfig;
import com.enderio.machines.common.init.MachineBlockEntities;
import com.enderio.machines.common.io.TransferUtil;
import com.enderio.machines.common.io.fluid.MachineFluidHandler;
import com.enderio.machines.common.io.fluid.MachineFluidTank;
import com.enderio.machines.common.io.fluid.MachineTankLayout;
import com.enderio.machines.common.io.fluid.TankAccess;
import com.enderio.machines.common.menu.XPVacuumMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;

import static com.enderio.base.common.util.ExperienceUtil.EXP_TO_FLUID;

public class XPVacuumBlockEntity extends VacuumMachineBlockEntity<ExperienceOrb> implements IFluidTankUser {

    private final MachineFluidHandler fluidHandler;
    private static final TankAccess TANK = new TankAccess();
    public XPVacuumBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(MachineBlockEntities.XP_VACUUM.get(), pWorldPosition, pBlockState, ExperienceOrb.class);
        fluidHandler = createFluidTank();

        // Sync fluid level.
        addDataSlot(new IntegerNetworkDataSlot(() -> TANK.getFluidAmount(this), i -> TANK.setFluid(this, new FluidStack(EIOFluids.XP_JUICE.getSource(), i))
        ));
    }

    @Override
    public String getColor() {
        return MachinesConfig.CLIENT.BLOCKS.XP_VACUUM_RANGE_COLOR.get();
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new XPVacuumMenu(this, inventory, containerId);
    }


    @Override
    public void handleEntity(ExperienceOrb xpe) {
        int filled = TANK.fill(this, new FluidStack(EIOFluids.XP_JUICE.getSource(), xpe.getValue() * EXP_TO_FLUID), FluidAction.EXECUTE);
        if (filled == xpe.getValue() * EXP_TO_FLUID) {
            xpe.discard();
        } else {
            xpe.value -= filled / ((float) EXP_TO_FLUID);
        }
    }

    // region Fluid Storage

    @Override
    public MachineTankLayout getTankLayout() {
        return new MachineTankLayout.Builder().tank(TANK, Integer.MAX_VALUE).build();
    }

    @Override
    public MachineFluidHandler createFluidTank() {
        return new MachineFluidHandler(getIOConfig(), getTankLayout()) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                setChanged();
            }
        };
    }

    public MachineFluidTank getFluidTank() {
        return TANK.getTank(this);
    }

    @Override
    public MachineFluidHandler getFluidHandler() {
        return fluidHandler;
    }

    // endregion

    // region Serialization

    @Override
    public void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        saveTank(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        loadTank(pTag);
    }

    // endregion

    /**
     * Move fluids to and fro via the given side.
     */
    private void moveFluids(Direction side) {
        IFluidHandler selfHandler = getSelfCapability(Capabilities.FluidHandler.BLOCK, side);
        IFluidHandler otherHandler = getNeighbouringCapability(Capabilities.FluidHandler.BLOCK, side);
        if (selfHandler == null || otherHandler == null) {
            return;
        }

        TransferUtil.distributeFluids(getIOConfig().getMode(side), selfHandler, otherHandler);
    }

    @Override
    public void moveResource(Direction direction) {
        super.moveResource(direction);
        moveFluids(direction);
    }
}
