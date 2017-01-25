package the_fireplace.uhccompass.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL12;
import the_fireplace.uhccompass.UHCCompass;
import the_fireplace.uhccompass.config.ConfigValues;

import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

/**
 * @author The_Fireplace
 */
public class RenderEvents {
    @SubscribeEvent
    public void guiRender(TickEvent.RenderTickEvent t){
        Minecraft mc = Minecraft.getMinecraft();
        if(mc.inGameHasFocus && !mc.gameSettings.showDebugInfo) {
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
                    xPos = width/2+ ConfigValues.COMPASSX;
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

            RenderHelper.disableStandardItemLighting();
            glDisable(GL12.GL_RESCALE_NORMAL);
            glEnable(32826);
            GlStateManager.popMatrix();
        }
    }
}
