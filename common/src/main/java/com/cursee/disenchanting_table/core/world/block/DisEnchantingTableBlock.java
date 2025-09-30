package com.cursee.disenchanting_table.core.world.block;

import com.cursee.disenchanting_table.DisEnchantingTable;
import com.cursee.disenchanting_table.DisEnchantingTableClient;
import com.cursee.disenchanting_table.core.registry.ModBlockEntities;
import com.cursee.disenchanting_table.core.world.block.entity.DisEnchantingTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DisEnchantingTableBlock extends Block implements EntityBlock {

  private static final VoxelShape SHAPE = Block.box(0.0F, 0.0F, 0.0F,
      16.0F, 12.0F, 16.0F);

  public DisEnchantingTableBlock() {
    super(Properties.ofFullCopy(Blocks.ENCHANTING_TABLE).setId(ResourceKey.create(Registries.BLOCK,
        DisEnchantingTable.identifier("disenchanting_table"))));
    this.registerDefaultState(this.stateDefinition.any().setValue(
        BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
  }

  protected static InteractionResult interact(Level level, BlockPos pos, Player player) {
    if (level.isClientSide()) {
      return InteractionResult.SUCCESS;
    } else {
      BlockEntity var4 = level.getBlockEntity(pos);
      if (var4 instanceof MenuProvider menuProvider) {
        player.openMenu(menuProvider);
      }

      return InteractionResult.SUCCESS;
    }
  }

  protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(
      BlockEntityType<A> serverType, BlockEntityType<E> clientType,
      BlockEntityTicker<? super E> ticker) {
    return clientType == serverType ? (BlockEntityTicker<A>) ticker : null;
  }

  protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
      Player player, BlockHitResult hitResult) {
    return interact(level, pos, player);
  }

  protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level,
      BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
    return interact(level, pos, player);
  }

  public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
    return new DisEnchantingTableBlockEntity(blockPos, blockState);
  }

  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
      BlockEntityType<T> blockEntityType) {
    return level.isClientSide() ? null : createTickerHelper(blockEntityType,
        ModBlockEntities.DISENCHANTING_TABLE,
        DisEnchantingTableBlockEntity::tick);
  }

  protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos,
      CollisionContext context) {
    return SHAPE;
  }

  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(BlockStateProperties.HORIZONTAL_FACING);
  }

  public BlockState getStateForPlacement(BlockPlaceContext context) {
    return this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING,
        context.getHorizontalDirection().getOpposite());
  }

  public BlockState rotate(BlockState state, Rotation rotation) {
    return state.setValue(BlockStateProperties.HORIZONTAL_FACING,
        rotation.rotate(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
  }

  public BlockState mirror(BlockState state, Mirror mirror) {
    return state.rotate(
        mirror.getRotation(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
  }

  public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
    if (DisEnchantingTableClient.render_block_particles) {
      for (int i = 0; i < 3; ++i) {
        int xMod = random.nextInt(2) * 2 - 1;
        int zMod = random.nextInt(2) * 2 - 1;
        double xPos = (double) pos.getX() + (double) 0.5F + (double) 0.25F * (double) xMod;
        double yPos = (float) pos.getY() + random.nextFloat();
        double zPos = (double) pos.getZ() + (double) 0.5F + (double) 0.25F * (double) zMod;
        double xSpeed = random.nextFloat() * (float) xMod;
        double ySpeed = ((double) random.nextFloat() - (double) 0.5F) * (double) 0.125F;
        double zSpeed = random.nextFloat() * (float) zMod;
        level.addParticle(ParticleTypes.PORTAL, xPos, yPos, zPos, xSpeed, ySpeed, zSpeed);
      }

    }
  }
}
