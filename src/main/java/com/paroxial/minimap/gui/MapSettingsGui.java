package com.paroxial.minimap.gui;

import com.paroxial.minimap.MinimapMod;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;


@RequiredArgsConstructor
public class MapSettingsGui extends GuiScreen {
    private final MinimapMod instance;

    @Override
    public void initGui() {
        super.initGui();
        buttonList.add(new GuiButton(0,
                this.width / 2 - 100,
                this.height / 4 + 56, 200, 20,
                "Map Rotation: " + instance.getMapRenderer().getMap().isRotating()));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                instance.getMapRenderer().getMap().setRotating(!instance.getMapRenderer().getMap().isRotating());
                Minecraft.getMinecraft().displayGuiScreen(this);
                break;
        }
    }
}
