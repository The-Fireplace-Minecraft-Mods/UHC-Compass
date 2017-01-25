package the_fireplace.uhccompass.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL12;
import the_fireplace.uhccompass.KeyHandler;
import the_fireplace.uhccompass.UHCCompass;
import the_fireplace.uhccompass.config.ConfigValues;
import the_fireplace.uhccompass.config.XJust;
import the_fireplace.uhccompass.config.YJust;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

/**
 * @author The_Fireplace
 */
public class GuiCompassMoving extends GuiScreen {
    public static final ResourceLocation compass_texture = new ResourceLocation("textures/items/compass_16.png");
    private boolean mouseDown = false;
    private static final int mouseOffset = 8;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        super.drawScreen(mouseX, mouseY, partialTicks);

        ScaledResolution res = new ScaledResolution(mc);
        int width = res.getScaledWidth();
        int height = res.getScaledHeight();

        int xPos = 0;
        int yPos = 0;

        switch(ConfigValues.XALIGNMENT){
            case LEFT:
                xPos = ConfigValues.COMPASSX;
                break;
            case RIGHT:
                xPos = width-ConfigValues.COMPASSX;
                break;
            case CENTER:
                xPos = width/2+ConfigValues.COMPASSX;
        }

        switch(ConfigValues.YALIGNMENT){
            case TOP:
                yPos = ConfigValues.COMPASSY;
                break;
            case BOTTOM:
                yPos = height-ConfigValues.COMPASSY;
                break;
            case CENTER:
                yPos = height/2+ConfigValues.COMPASSY;
        }

        glEnable(32826);
        GlStateManager.pushMatrix();

        RenderHelper.enableGUIStandardItemLighting();

        mc.getRenderItem().renderItemIntoGUI(new ItemStack(UHCCompass.uhccompass), xPos, yPos);

        if(mouseDown){
            GlStateManager.enableBlend();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
            Minecraft.getMinecraft().getTextureManager().bindTexture(compass_texture);
            drawModalRectWithCustomSizedTexture(mouseX-mouseOffset, mouseY-mouseOffset, 0, 0, 16, 16, 16, 16);
            GlStateManager.resetColor();
            GlStateManager.disableBlend();
        }

        RenderHelper.disableStandardItemLighting();
        glDisable(GL12.GL_RESCALE_NORMAL);
        glEnable(32826);
        GlStateManager.popMatrix();
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        ScaledResolution res = new ScaledResolution(mc);
        int width = res.getScaledWidth();
        int height = res.getScaledHeight();
        int screenX = mouseX-mouseOffset;
        int screenY = mouseY-mouseOffset;
        int finalX;
        int finalY;
        XJust xval;
        YJust yval;
        if(screenX < width/3)
            xval=XJust.LEFT;
        else if(screenX > width/3*2)
            xval=XJust.RIGHT;
        else
            xval=XJust.CENTER;
        if(screenY < height/3)
            yval=YJust.TOP;
        else if(screenY > height/3*2)
            yval=YJust.BOTTOM;
        else
            yval=YJust.CENTER;

        switch(xval){
            case LEFT:default:
                finalX=screenX;
                break;
            case CENTER:
                finalX=screenX-width/2;
                break;
            case RIGHT:
                finalX=width-screenX;
        }
        switch(yval){
            case TOP:default:
                finalY=screenY;
                break;
            case CENTER:
                finalY=screenY-height/2;
                break;
            case BOTTOM:
                finalY=height-screenY;
        }
        UHCCompass.COMPASSX_PROPERTY.set(finalX);
        UHCCompass.COMPASSY_PROPERTY.set(finalY);
        UHCCompass.XALIGNMENT_PROPERTY.set(xval.name());
        UHCCompass.YALIGNMENT_PROPERTY.set(yval.name());
        UHCCompass.syncConfig();
        mouseDown = false;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        mouseDown = true;
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (keyCode == mc.gameSettings.keyBindInventory.getKeyCode() || keyCode == Keyboard.KEY_RETURN || keyCode == UHCCompass.instance.keyHandler.getKeyCode(KeyHandler.MOVECOMPASS))
        {
            this.mc.displayGuiScreen(null);

            if (this.mc.currentScreen == null)
            {
                this.mc.setIngameFocus();
            }
        }
        super.keyTyped(typedChar, keyCode);
    }
}
