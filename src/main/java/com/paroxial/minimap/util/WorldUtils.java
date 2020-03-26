package com.paroxial.minimap.util;

import lombok.experimental.UtilityClass;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

@UtilityClass
public class WorldUtils {
    public BlockPos getTopBlock(World world, BlockPos startingPos) {
        Chunk chunk = world.getChunkFromBlockCoords(startingPos);
        BlockPos posA;
        BlockPos posB;

        for (posA = new BlockPos(startingPos.getX(), chunk.getTopFilledSegment() + 16, startingPos.getZ());
             posA.getY() >= 0; posA = posB) {
            posB = posA.down();
            Block block = chunk.getBlock(posB);

            if (block.getMaterial().blocksMovement() || block.getMaterial() == Material.lava
                    || block.getMaterial() == Material.water) {
                break;
            }
        }

        return posA;
    }
}
