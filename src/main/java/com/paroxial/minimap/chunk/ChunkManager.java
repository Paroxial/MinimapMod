package com.paroxial.minimap.chunk;

import gnu.trove.map.hash.TLongObjectHashMap;
import net.minecraft.world.chunk.Chunk;

public class ChunkManager {
    private final TLongObjectHashMap<ChunkData> chunkData = new TLongObjectHashMap<>();

    // Turns (x, z) coordinates into a hashed key
    public static long hashCoordinates(int x, int z) {
        return (((long) x) << 32) | (z & 0xFFFFFFFFL);
    }

    public ChunkData addChunk(Chunk chunk) {
        long hash = hashCoordinates(chunk.xPosition, chunk.zPosition);

        ChunkData data = getChunkData(chunk);

        if (data == null) {
            data = new ChunkData();
            chunkData.put(hash, data);
        }

        return data;
    }

    public void removeChunk(Chunk chunk) {
        chunkData.remove(hashCoordinates(chunk.xPosition, chunk.zPosition));
    }

    public void clearAllData() {
        chunkData.clear();
    }

    public ChunkData getChunkData(Chunk chunk) {
        return chunkData.get(hashCoordinates(chunk.xPosition, chunk.zPosition));
    }
}
