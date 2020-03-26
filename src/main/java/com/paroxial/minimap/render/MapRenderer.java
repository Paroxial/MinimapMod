package com.paroxial.minimap.render;

import com.paroxial.minimap.MinimapMod;
import com.paroxial.minimap.model.Minimap;
import com.paroxial.minimap.render.texture.WrappedMapTexture;
import com.paroxial.minimap.util.GLUtils;
import java.awt.image.BufferedImage;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import org.lwjgl.opengl.GL11;

public class MapRenderer {
    private final MinimapMod instance;

    @Getter
    private final Minimap map;

    @Getter
    private final WrappedMapTexture mapTexture;

    public MapRenderer(MinimapMod instance, Minimap map) {
        this.instance = instance;
        this.map = map;
        this.mapTexture = new WrappedMapTexture(new DynamicTexture(new BufferedImage((int) map.getRadius() * 2,
                (int) map.getRadius() * 2, BufferedImage.TYPE_INT_RGB)));
    }

    public void draw() {
        GL11.glPushMatrix();

        // Translate away from the top-left corner
        GL11.glTranslatef(map.getCornerDistance(), map.getCornerDistance(), 1);

        drawMap();
        drawMarker();
        drawDirections();

        GlStateManager.popMatrix();
    }

    private void drawMap() {
        GL11.glColor3f(1, 1, 1);
        GL11.glPushMatrix();

        if (map.isRotating()) {
            double mapAngle = -Minecraft.getMinecraft().thePlayer.rotationYaw + 180.0;
            GLUtils.rotateFixed(map.getRadius(), map.getRadius(), mapAngle);
        }

        GLUtils.enableCircleStencil(map.getRadius(), map.getRadius(), map.getRadius());

        TextureManager manager = Minecraft.getMinecraft().getTextureManager();
        manager.bindTexture(instance.getMapRenderer().getMapTexture().getTextureLocation());
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, 80, 80, 80, 80);

        GLUtils.disableStencil();

        GL11.glColor3f(0.2F, 0.2F, 0.2F);
        GLUtils.drawCircle(map.getRadius(), map.getRadius(), map.getRadius(), 2);

        GL11.glPopMatrix();
    }

    private void drawMarker() {
        GL11.glColor3f(0, 0, 0);
        GL11.glPushMatrix();

        if (!map.isRotating()) {
            double markerAngle = Minecraft.getMinecraft().thePlayer.rotationYaw + 180.0;
            GLUtils.rotateFixed(map.getRadius(), map.getRadius(), markerAngle);
        }

        GLUtils.drawTriangle(map.getRadius() - (map.getMarkerSize() / 2.0F), map.getRadius() + (map.getMarkerSize() / 2.0F) + 2F,
                map.getRadius() + (map.getMarkerSize() / 2.0F), map.getRadius() + (map.getMarkerSize() / 2.0F) + 2.0F,
                map.getRadius(), map.getRadius() - map.getMarkerSize() + 2.0F);

        GL11.glPopMatrix();
    }

    private void drawDirections() {
        double directionAngle = map.isRotating() ? -Minecraft.getMinecraft().thePlayer.rotationYaw : -180.0;

        drawDirection("N", directionAngle + 90.0F);
        drawDirection("S", directionAngle - 90.0F);
        drawDirection("E", directionAngle + 180.0F);
        drawDirection("W", directionAngle);
    }

    private void drawDirection(String indicator, double angle) {
        GLUtils.drawText((Math.cos(Math.toRadians(angle)) * map.getRadius()) + (map.getRadius()),
                (Math.sin(Math.toRadians(angle)) * map.getRadius()) + map.getRadius() - 3,
                indicator, 0xFFFFFF);
    }
}
