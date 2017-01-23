package the_fireplace.uhccompass;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author The_Fireplace
 */
public class ClientEvents {
    public static final ResourceLocation compass = new ResourceLocation("minecraft:textures/items/compass.png");
    //32 compass states
    @SubscribeEvent
    public void guiRender(TickEvent.RenderTickEvent t){
        Minecraft mc = Minecraft.getMinecraft();
        if(mc.inGameHasFocus && !mc.gameSettings.showDebugInfo) {
            ScaledResolution res = new ScaledResolution(mc);
            int width = res.getScaledWidth();
            int height = res.getScaledHeight();

            
        }
    }
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if(eventArgs.modID.equals(UHCCompass.MODID))
            UHCCompass.syncConfig();
    }
}
