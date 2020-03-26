package com.paroxial.minimap.handler;

import com.paroxial.minimap.MinimapMod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class RenderHandler {
    private final MinimapMod instance;

    public RenderHandler(MinimapMod instance) {
        this.instance = instance;
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (Minecraft.getMinecraft().inGameHasFocus) {
            instance.getMapRenderer().draw();
        }
    }
}
