package com.cursee.disenchanting_table.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public final class S2CBlockEntityUpdatePacket {
  public static void sendToClients(BlockEntity blockEntity) {
    Level var2 = blockEntity.getLevel();
    if (var2 instanceof ServerLevel level) {
      Packet<?> packet = blockEntity.getUpdatePacket();
      if (packet != null) {
        BlockPos pos = blockEntity.getBlockPos();
        level.getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false).forEach((e) -> e.connection.send(packet));
      }
    }
  }
}
