package com.cursee.disenchanting_table.client.renderer.blockentity;

import com.cursee.disenchanting_table.core.world.level.block.entity.DisEnchantingTableBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.phys.Vec3;

public class DisEnchantingTableRenderer implements BlockEntityRenderer<DisEnchantingTableBlockEntity> {

    private final ItemRenderer itemRenderer;

    public DisEnchantingTableRenderer(BlockEntityRendererProvider.Context context) {
        itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(DisEnchantingTableBlockEntity disEnchantingTableBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1, Vec3 vec3) {

    }
}
