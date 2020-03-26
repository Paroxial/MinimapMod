package com.paroxial.minimap.handler;

import com.paroxial.minimap.MinimapMod;
import com.paroxial.minimap.chunk.ChunkData;
import com.paroxial.minimap.gui.MapSettingsGui;
import com.paroxial.minimap.util.WorldUtils;
import lombok.RequiredArgsConstructor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;

@RequiredArgsConstructor
public class WorldHandler {
    private final MinimapMod instance;
    private int currentTick;

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_COMMA) {
            Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().displayGuiScreen(instance.getGui()));
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        if (event.side != Side.CLIENT) {
            return;
        }

        // Update map every 5 ticks
        if (++currentTick == 5) {
            currentTick = 0;
            updateMapPosition();
        }
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        instance.getChunkManager().clearAllData();
    }

    private void updateMapPosition() {
        BlockPos playerPosition = Minecraft.getMinecraft().thePlayer.getPosition();
        int radius = (int) instance.getMapRenderer().getMap().getRadius();

        for (int z = playerPosition.getZ() - radius; z < playerPosition.getZ() + radius; z++) {
            for (int x = playerPosition.getX() - radius; x < playerPosition.getX() + radius; x++) {
                Chunk chunk = Minecraft.getMinecraft().theWorld.getChunkFromBlockCoords(new BlockPos(x, playerPosition.getY(), z));
                ChunkData data = instance.getChunkManager().getChunkData(chunk);

                int color;

                if (data != null) {
                    color = data.getBlockColor(x, z);
                } else {
                    BlockPos block = WorldUtils.getTopBlock(Minecraft.getMinecraft().theWorld, new BlockPos(x, playerPosition.getY() + radius, z));
                    IBlockState state = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(block.getX(), block.getY() - 1, block.getZ()));
                    color = state.getBlock().getMapColor(state).colorValue;
                }

                instance.getMapBuffer().setRGB(x - playerPosition.getX() + radius, z - playerPosition.getZ() + radius, color);
            }
        }

        instance.getMapRenderer().getMapTexture().updateTexture(instance.getMapBuffer());
    }
}
