package com.cursee.disenchanting_table.core.world.level.block.entity.base;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.slf4j.Logger;

public abstract class ModBlockEntity extends BlockEntity {

    private static final Logger LOGGER = LogUtils.getLogger();

    public ModBlockEntity(BlockEntityType<? extends BlockEntity> type, BlockPos pos, BlockState state) {

        super(type, pos, state);
    }

    public abstract void writePacketData(ValueOutput output);

    public abstract void readPacketData(ValueInput input);

    @Override
    protected void saveAdditional(ValueOutput output) {
        writePacketData(output);
        super.saveAdditional(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        readPacketData(input);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {

        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {

        CompoundTag tag;

        try (ProblemReporter.ScopedCollector collector = new ProblemReporter.ScopedCollector(this.problemPath(), LOGGER)) {
            TagValueOutput output = TagValueOutput.createWithContext(collector, registries);
            writePacketData(output);
            tag = output.buildResult();
        }

        return tag;
    }
}
