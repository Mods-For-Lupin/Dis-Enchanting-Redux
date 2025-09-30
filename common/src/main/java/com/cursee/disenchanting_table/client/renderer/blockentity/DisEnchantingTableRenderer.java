package com.cursee.disenchanting_table.client.renderer.blockentity;

import com.cursee.disenchanting_table.DisEnchantingTableServer;
import com.cursee.disenchanting_table.client.renderer.blockentity.DisEnchantingTableRenderer.TableRenderState;
import com.cursee.disenchanting_table.core.world.block.DisEnchantingTableBlock;
import com.cursee.disenchanting_table.core.world.block.entity.DisEnchantingTableBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class DisEnchantingTableRenderer implements BlockEntityRenderer<DisEnchantingTableBlockEntity, TableRenderState> {

    private static final ItemStack MANUAL_STACK = new ItemStack(Items.ENCHANTED_BOOK);
    private final ItemModelResolver itemModelResolver;

    public DisEnchantingTableRenderer(BlockEntityRendererProvider.Context context) {
        // itemRenderer = context.getItemRenderer();
        itemModelResolver = context.itemModelResolver();
    }

//    @Override
//    public void render(DisEnchantingTableBlockEntity disEnchantingTableBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1, Vec3 vec3) {
//
//    }

    @Override
    public TableRenderState createRenderState() {
        return new TableRenderState();
    }

    @Override
    public void extractRenderState(DisEnchantingTableBlockEntity campfireBlockEntity,
        TableRenderState campfireRenderState, float f, Vec3 vec3,
        @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {

        BlockEntityRenderer.super.extractRenderState(campfireBlockEntity, campfireRenderState, f, vec3, crumblingOverlay);
        campfireRenderState.facing = campfireBlockEntity.getBlockState().getValue(
            BlockStateProperties.HORIZONTAL_FACING);
        int i = (int)campfireBlockEntity.getBlockPos().asLong();

        campfireRenderState.items = new ArrayList();
        ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
        this.itemModelResolver.updateForTopItem(itemStackRenderState, (ItemStack)campfireBlockEntity.getItem(0), ItemDisplayContext.FIXED, campfireBlockEntity.getLevel(), (ItemOwner)null, i + 0);
        campfireRenderState.items.add(itemStackRenderState);

        campfireRenderState.manual = new ArrayList();
        ItemStackRenderState manualStackState = new ItemStackRenderState();
        this.itemModelResolver.updateForTopItem(manualStackState, MANUAL_STACK, ItemDisplayContext.FIXED, campfireBlockEntity.getLevel(), (ItemOwner)null, i + 0);
        campfireRenderState.manual.add(manualStackState);
    }

    @Override
    public void submit(TableRenderState campfireRenderState, PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {

        Direction direction = campfireRenderState.facing;
        List<ItemStackRenderState> list = campfireRenderState.items;

        ItemStackRenderState itemStackRenderState = (ItemStackRenderState)list.getFirst();

        if (!DisEnchantingTableServer.automatic_disenchanting) {
            itemStackRenderState = campfireRenderState.manual.getFirst();
        }

        if (!itemStackRenderState.isEmpty()) {


            poseStack.pushPose();

//            poseStack.translate(0.5F, 0.44921875F, 0.5F);
//            Direction facing = Direction.from2DDataValue((direction.get2DDataValue()) % 4);
//            float f = -facing.toYRot();
//            poseStack.mulPose(Axis.YP.rotationDegrees(f));
//            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
//            poseStack.translate(-0.3125F, -0.3125F, 0.0F);
//            poseStack.scale(0.375F, 0.375F, 0.375F);

            poseStack.translate(0.5F, 0.75F, 0.5F);
            poseStack.scale(0.5F, 0.5F, 0.5F);
            // Direction facing = (Direction)table.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
            Direction facing = Direction.from2DDataValue((direction.get2DDataValue()) % 4);
            poseStack.mulPose(Axis.YP.rotationDegrees(facing != Direction.EAST && facing != Direction.WEST ? facing.getOpposite().toYRot() : facing.toYRot()));
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));

            itemStackRenderState.submit(poseStack, submitNodeCollector, campfireRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);

            poseStack.popPose();


        }
    }

    @Override
    public boolean shouldRender(DisEnchantingTableBlockEntity blockEntity, Vec3 cameraPos) {
        return true;
    }

//    private static int getLightLevel(Level level, BlockPos pos) {
//        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
//        int sLight = level.getBrightness(LightLayer.SKY, pos);
//        return LightTexture.pack(bLight, sLight);
//    }

    public static class TableRenderState extends BlockEntityRenderState {
        public List<ItemStackRenderState> manual = Collections.emptyList();
        public List<ItemStackRenderState> items = Collections.emptyList();
        public Direction facing;

        public TableRenderState() {
            this.facing = Direction.NORTH;
        }
    }
}
