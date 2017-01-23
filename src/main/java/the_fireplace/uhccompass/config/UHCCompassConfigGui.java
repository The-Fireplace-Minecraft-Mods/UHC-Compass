package the_fireplace.uhccompass.config;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import the_fireplace.uhccompass.UHCCompass;

public class UHCCompassConfigGui extends GuiConfig {
    public UHCCompassConfigGui(GuiScreen parentScreen) {
        super(parentScreen, new ConfigElement(UHCCompass.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), UHCCompass.MODID, false,
                false, GuiConfig.getAbridgedConfigPath(UHCCompass.config.toString()));
    }
}