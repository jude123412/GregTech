package gregtech.api.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

public class GuiGhostCircuitButton extends GuiButton {

    private final ItemStack stack;

    public GuiGhostCircuitButton(int id, int x, int y, ItemStack stack) {
        super(id, x, y, 16, 16, "");
        this.stack = stack;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (!this.visible) return;

        // Draw normal button background
        mc.getTextureManager().bindTexture(Gui.ICONS);
        GlStateManager.color(1f, 1f, 1f, 1f);
        drawTexturedModalRect(x, y, 0, 0, width, height);

        // Draw item icon
        RenderHelper.enableGUIStandardItemLighting();
        mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x + 1, y + 1);
        RenderHelper.disableStandardItemLighting();
    }
}
