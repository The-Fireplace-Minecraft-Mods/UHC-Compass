package the_fireplace.uhccompass.render;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.text.TextComponentTranslation;
import org.lwjgl.input.Keyboard;
import the_fireplace.uhccompass.KeyHandler;
import the_fireplace.uhccompass.UHCCompass;

import java.io.IOException;

/**
 * @author The_Fireplace
 */
public class GuiCoords extends GuiChat {
    private GuiTextField xCoord;
    private GuiTextField zCoord;
    @Override
    public void initGui() {
        super.initGui();
        this.inputField.setCanLoseFocus(true);
        this.inputField.setFocused(false);
        xCoord = new GuiTextField(0, this.fontRendererObj, 2, height/2-20-this.inputField.height, 100, 20);
        xCoord.setFocused(true);
        xCoord.setMaxStringLength(16);
        zCoord = new GuiTextField(1, this.fontRendererObj, 104, height/2-20-this.inputField.height, 100, 20);
        zCoord.setMaxStringLength(16);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        xCoord.drawTextBox();
        zCoord.drawTextBox();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (keyCode == mc.gameSettings.keyBindInventory.getKeyCode() || keyCode == Keyboard.KEY_RETURN || keyCode == UHCCompass.instance.keyHandler.getKeyCode(KeyHandler.QUICKCOORDS))
        {
            try{
                int x = Integer.parseInt(xCoord.getText());
                int z = Integer.parseInt(zCoord.getText());
                UHCCompass.TARGETX_PROPERTY.set(x);
                UHCCompass.TARGETZ_PROPERTY.set(z);
            }catch(Exception e){
                mc.thePlayer.addChatMessage(new TextComponentTranslation("uhccompass.invalidcoords"));
            }
            UHCCompass.syncConfig();

            this.mc.displayGuiScreen(null);

            if (this.mc.currentScreen == null)
            {
                this.mc.setIngameFocus();
            }
        } else if(keyCode == Keyboard.KEY_TAB || keyCode == Keyboard.KEY_SPACE){
            xCoord.setFocused(!xCoord.isFocused());
            zCoord.setFocused(!zCoord.isFocused());
        }else if(Character.isDigit(typedChar) || typedChar == '-' || keyCode == Keyboard.KEY_BACK || keyCode == Keyboard.KEY_LEFT || keyCode == Keyboard.KEY_RIGHT){
            xCoord.textboxKeyTyped(typedChar, keyCode);
            zCoord.textboxKeyTyped(typedChar, keyCode);
        }else if (keyCode == 201)
        {
            this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().getLineCount() - 1);
        }
        else if (keyCode == 209)
        {
            this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().getLineCount() + 1);
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void updateScreen()
    {
        xCoord.updateCursorCounter();
        zCoord.updateCursorCounter();
        super.updateScreen();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        xCoord.mouseClicked(mouseX, mouseY, mouseButton);
        zCoord.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
