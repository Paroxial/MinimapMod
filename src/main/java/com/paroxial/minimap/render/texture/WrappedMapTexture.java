package com.paroxial.minimap.render.texture;

import java.awt.image.BufferedImage;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class WrappedMapTexture {
    private final int[] mapTextureData;

    @Getter
    private DynamicTexture texture;
    @Getter
    private ResourceLocation textureLocation;

    public WrappedMapTexture(DynamicTexture texture) {
        TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
        this.texture = texture;
        this.mapTextureData = this.texture.getTextureData();
        this.textureLocation = textureManager.getDynamicTextureLocation("minimap", this.texture);
    }

    public void updateTexture(BufferedImage image) {
        texture = new DynamicTexture(image);

//        DataBufferInt intBuffer = (DataBufferInt) image.getRaster().getDataBuffer();
//        int[] imageData = intBuffer.getData();
//
//        for (int i = 0; i < mapTextureData.length; i++) {
//            mapTextureData[i] = imageData[i];
//        }
//
//        texture.updateDynamicTexture();

        this.textureLocation = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("minimap", this.texture);
    }
}
