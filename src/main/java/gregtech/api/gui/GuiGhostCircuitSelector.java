package gregtech.api.gui;

import gregtech.api.recipes.ingredients.IntCircuitIngredient;

import gregtech.api.util.LocalizationUtils;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.function.Consumer;

public class GuiGhostCircuitSelector extends GuiScreen {

    private final GuiScreen parent;

    private final Consumer<Integer> onSelect;

    private static final ResourceLocation GT_BACKGROUND =
            new ResourceLocation("gregtech", "textures/gui/base/ghost_circuit_background.png");

    int guiLeft = 0;
    int guiTop = 0;

    int startX = 0;
    int startY = 0;

    int previewX = 0;
    int previewY = 0;

    int currentValue;

    public GuiGhostCircuitSelector(GuiScreen parent, int currentValue, Consumer<Integer> onSelect) {
        this.onSelect = onSelect;
        this.parent = parent;
        this.currentValue = currentValue;
    }

    @Override
    public void initGui() {
        guiLeft = (width - 166) / 2;
        guiTop = (height - 120) / 2;

        startX = guiLeft + 6;
        startY = guiTop + 44;

        previewX = guiLeft + (166 - 18) / 2 - 1;
        previewY = guiTop + 15;

        int value = 0;
        int buttonSize = 16;
        int spacing = 1;

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 9; col++) {

                ItemStack circuit = IntCircuitIngredient.getIntegratedCircuit(value);

                this.buttonList.add(new GuiGhostCircuitButton(
                        value,
                        startX + col * (buttonSize + spacing),
                        startY + row * (buttonSize + spacing),

                        circuit
                ));

                value++;
                if (value >= 33) break;
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        mc.getTextureManager().bindTexture(GT_BACKGROUND);

        // Draw GTCEu-style panel
        drawModalRectWithCustomSizedTexture(
                guiLeft, guiTop,
                0, 0,
                166,
                120,
                166,
                120
        );

        String title = LocalizationUtils.format("metaitem.circuit.integrated.gui");
        fontRenderer.drawString(title, guiLeft + 5, guiTop + 5, 0x404040);

        if (currentValue != -1) {
            ItemStack current = IntCircuitIngredient.getIntegratedCircuit(currentValue);
            RenderHelper.enableGUIStandardItemLighting();
            mc.getRenderItem().renderItemAndEffectIntoGUI(current, previewX + 1, previewY + 1);
            RenderHelper.disableStandardItemLighting();
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        // ESC or Inventory key
        if (keyCode == 1 || keyCode == mc.gameSettings.keyBindInventory.getKeyCode()) {
            mc.displayGuiScreen(parent);
            return;
        }

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        onSelect.accept(button.id);
        this.currentValue = button.id;
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();

        // If the parent is still valid, reopen it
        if (parent != null && mc.currentScreen == null) {
            mc.displayGuiScreen(parent);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
