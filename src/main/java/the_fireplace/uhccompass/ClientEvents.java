package the_fireplace.uhccompass;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL12;
import the_fireplace.uhccompass.config.ConfigValues;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author The_Fireplace
 */
public class ClientEvents {
    private static RenderItem itemRender = null;
    @SubscribeEvent
    public void guiRender(TickEvent.RenderTickEvent t){
        Minecraft mc = Minecraft.getMinecraft();
        if(mc.inGameHasFocus && !mc.gameSettings.showDebugInfo) {
            if(itemRender == null){
                itemRender = mc.getRenderItem();
            }
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
            glPushMatrix();

            RenderHelper.enableGUIStandardItemLighting();

            itemRender.renderItemIntoGUI(new ItemStack(UHCCompass.uhccompass), xPos, yPos);

            RenderHelper.disableStandardItemLighting();
            glDisable(GL12.GL_RESCALE_NORMAL);
            glEnable(32826);
            glPopMatrix();
        }
    }
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if(eventArgs.modID.equals(UHCCompass.MODID))
            UHCCompass.syncConfig();
    }

    @SubscribeEvent
    public void texStitch(TextureStitchEvent.Pre event){
        event.map.setTextureEntry("uhccompass:builtin/uhccompass", new TextureUHCCompass());
    }
}
