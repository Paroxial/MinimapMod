package com.paroxial.minimap;

import com.paroxial.minimap.chunk.ChunkManager;
import com.paroxial.minimap.gui.MapSettingsGui;
import com.paroxial.minimap.handler.RenderHandler;
import com.paroxial.minimap.handler.WorldHandler;
import com.paroxial.minimap.model.Minimap;
import com.paroxial.minimap.render.MapRenderer;
import java.awt.image.BufferedImage;
import lombok.Getter;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Getter
@Mod(modid = "minimap", name = "Minimap Mod", version = "1.0.0")
public class MinimapMod {
    // Map handling
    private BufferedImage mapBuffer;
    private MapRenderer mapRenderer;
    private ChunkManager chunkManager;

    // GUI handling
    private MapSettingsGui gui;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // Initialize minimap and map renderer
        mapRenderer = new MapRenderer(this, new Minimap());
        mapBuffer = new BufferedImage((int) mapRenderer.getMap().getRadius() * 2,
                (int) mapRenderer.getMap().getRadius() * 2, BufferedImage.TYPE_INT_RGB);
        chunkManager = new ChunkManager();

        gui = new MapSettingsGui(this);

        // Register event handlers
        MinecraftForge.EVENT_BUS.register(new WorldHandler(this));
        MinecraftForge.EVENT_BUS.register(new RenderHandler(this));
    }
}
