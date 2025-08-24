package com.cursee.disenchanting_table.core.world.level.block;

import com.cursee.disenchanting_table.DisEnchantingTableClient;
import com.cursee.disenchanting_table.core.registry.ModBlockEntities;
import com.cursee.disenchanting_table.core.world.level.block.entity.DisEnchantingTableBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class DisEnchantingTableBlock extends BaseEntityBlock {

    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 12, 16);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final MapCodec<DisEnchantingTableBlock> CODEC = simpleCodec(DisEnchantingTableBlock::new);

    public DisEnchantingTableBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        else if (level.getBlockEntity(pos) instanceof MenuProvider menuProvider) player.openMenu(menuProvider);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {

        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {

        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {

        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {

        return new DisEnchantingTableBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {

        return createTileEntityTicker(level, blockEntityType, ModBlockEntities.DISENCHANTING_TABLE);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {

        if (!DisEnchantingTableClient.render_block_particles) return;

        for(int i = 0; i < 3; ++i) {

            int xMod = random.nextInt(2) * 2 - 1;
            int zMod = random.nextInt(2) * 2 - 1;

            double xPos = (double)pos.getX() + (double)0.5F + (double)0.25F * (double)xMod;
            double yPos = (double) ((float)pos.getY() + random.nextFloat());
            double zPos = (double)pos.getZ() + (double)0.5F + (double)0.25F * (double)zMod;

            double xSpeed = (double) (random.nextFloat() * (float)xMod);
            double ySpeed = ((double)random.nextFloat() - (double)0.5F) * (double)0.125F;
            double zSpeed = (double) (random.nextFloat() * (float)zMod);

            level.addParticle(ParticleTypes.PORTAL, xPos, yPos, zPos, xSpeed, ySpeed, zSpeed);
        }
    }

    protected static <T extends BlockEntity> BlockEntityTicker<T> createTileEntityTicker(Level level, BlockEntityType<T> serverType, BlockEntityType<? extends DisEnchantingTableBlockEntity> clientType) {

        return level instanceof ServerLevel serverLevel ? createTickerHelper(serverType, clientType, (levelX, blockPos, blockState, tileEntity) -> DisEnchantingTableBlockEntity.serverTick(serverLevel, blockPos, blockState, tileEntity)) : null;
    }

    // todo add analogue/redstone output signal?
}
