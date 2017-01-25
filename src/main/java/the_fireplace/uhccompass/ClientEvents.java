package the_fireplace.uhccompass;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author The_Fireplace
 */
public class ClientEvents {
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if(eventArgs.getModID().equals(UHCCompass.MODID))
            UHCCompass.syncConfig();
    }
}
