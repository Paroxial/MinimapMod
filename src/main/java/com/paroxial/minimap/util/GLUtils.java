package com.paroxial.minimap.util;

import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;

@UtilityClass
public class GLUtils {
    public void preDrawConstants() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    public void postDrawConstants() {
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public void drawTriangle(float x1, float y1, float x2, float y2, float x3, float y3) {
        preDrawConstants();

        // Draw triangle vertices
        GL11.glBegin(GL11.GL_TRIANGLES);

        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x2, y2);
        GL11.glVertex2f(x3, y3);

        GL11.glEnd();

        postDrawConstants();
    }

    public void drawCircle(double x, double y, double radius, double width) {
        preDrawConstants();

        GL11.glBegin(GL11.GL_TRIANGLE_STRIP);

        double doublePi = Math.PI * 2.0;
        double angleChange = doublePi / 180.0;
        double r2 = radius + width;

        for (double angle = -angleChange; angle < doublePi; angle += angleChange) {
            GL11.glVertex2d(x + (radius * Math.cos(-angle)), y + (radius * Math.sin(-angle)));
            GL11.glVertex2d(x + (r2 * Math.cos(-angle)), y + (r2 * Math.sin(-angle)));
        }

        GL11.glEnd();

        postDrawConstants();
    }

    public void drawCircle(double x, double y, double r) {
        preDrawConstants();

        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        GL11.glVertex3d(x, y, 1);

        double doublePi = Math.PI * 2.0;
        double angleChange = doublePi / 180.0;

        for (double angle = -angleChange; angle < doublePi; angle += angleChange) {
            GL11.glVertex3d(x + (r * Math.cos(-angle)), y + (r * Math.sin(-angle)), 1);
        }

        GL11.glEnd();

        postDrawConstants();
    }

    public void setHexColor(int color) {
        float r = (float) ((color >> 16) & 0xFF) / 255.0F;
        float g = (float) ((color >> 8) & 0xFF) / 255.0F;
        float b = (float) ((color) & 0xFF) / 255.0F;
        float a = (float) ((color >> 24) & 0xFF) / 255.0F;

        GL11.glColor4f(r, g, b, a);
    }

    public void drawText(double width, double height, String text, int hex) {
        FontRenderer renderer = Minecraft.getMinecraft().fontRendererObj;
        renderer.drawString(text, (float) width - 2, (float) height, hex, true);
    }

    public void enableCircleStencil(double x, double y, double radius) {
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        GL11.glColorMask(false, false, false, false);
        GL11.glDepthMask(true);
        GL11.glDepthFunc(GL11.GL_ALWAYS);

        setHexColor(0xFFFFFFFF);
        drawCircle(x, y, radius);

        GL11.glColorMask(true, true, true, true);
        GL11.glDepthMask(false);
        GL11.glDepthFunc(GL11.GL_GREATER);
    }

    public void disableStencil() {
        GL11.glDepthMask(true);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }

    public void rotateFixed(double x, double y, double angle) {
        GL11.glTranslated(x, y, 1.0F);
        GL11.glRotated(angle, 0, 0, 1.0F);
        GL11.glTranslated(-x, -y, 1.0F);
    }
}
