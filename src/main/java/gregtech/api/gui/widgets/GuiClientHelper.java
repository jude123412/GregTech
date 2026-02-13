package gregtech.api.gui.widgets;

import gregtech.api.gui.GuiGhostCircuitSelector;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Consumer;

@SideOnly(Side.CLIENT)
public class GuiClientHelper {

    public static void openGhostCircuitSelector(
                                                gregtech.api.gui.impl.ModularUIGui gui,
                                                int currentValue,
                                                Consumer<Integer> callback) {
        Minecraft.getMinecraft().displayGuiScreen(
                new GuiGhostCircuitSelector(gui, currentValue, callback));
    }
}
