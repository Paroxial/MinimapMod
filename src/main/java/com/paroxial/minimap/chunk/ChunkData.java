package com.paroxial.minimap.chunk;

import gnu.trove.map.hash.TLongIntHashMap;

public class ChunkData {
    private final TLongIntHashMap blockColorMap = new TLongIntHashMap();

    public static long hash(int x, int z) {
        return (((long) x) << 32) | (z & 0xFFFFFFFFL);
    }

    public void setBlockColor(int x, int z, int color) {
        blockColorMap.put(hash(x, z), color);
    }

    public Integer getBlockColor(int x, int z) {
        return blockColorMap.get(hash(x, z));
    }
}
