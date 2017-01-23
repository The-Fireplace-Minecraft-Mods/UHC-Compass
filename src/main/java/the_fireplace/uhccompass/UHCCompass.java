package the_fireplace.uhccompass;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import the_fireplace.uhccompass.config.ConfigValues;

/**
 * @author The_Fireplace
 */
@Mod(modid=UHCCompass.MODID, name=UHCCompass.MODNAME, guiFactory = "the_fireplace.uhccompass.config.UHCCompassGuiFactory", clientSideOnly = true, canBeDeactivated = true)
public class UHCCompass {
    public static final String MODID = "uhccompass";
    public static final String MODNAME = "UHC-Compass";

    public static Configuration config;
    public static Property COMPASSX_PROPERTY;
    public static Property COMPASSY_PROPERTY;
    public static Property TARGETX_PROPERTY;
    public static Property TARGETZ_PROPERTY;
    public static Property ALIGNMENT_PROPERTY;

    public static void syncConfig(){
        ConfigValues.COMPASSX = COMPASSX_PROPERTY.getInt();
        ConfigValues.COMPASSY = COMPASSY_PROPERTY.getInt();
        ConfigValues.TARGETX = TARGETX_PROPERTY.getInt();
        ConfigValues.TARGETZ = TARGETZ_PROPERTY.getInt();
        ConfigValues.ALIGNMENT = Justification.valueOf(ALIGNMENT_PROPERTY.getString());

        if(config.hasChanged())
            config.save();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        COMPASSX_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.COMPASSX_NAME, ConfigValues.COMPASSX_DEFAULT, I18n.format(ConfigValues.COMPASSX_NAME+".tooltip"));
        COMPASSY_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.COMPASSY_NAME, ConfigValues.COMPASSY_DEFAULT, I18n.format(ConfigValues.COMPASSY_NAME+".tooltip"));
        TARGETX_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.TARGETX_NAME, ConfigValues.TARGETX_DEFAULT, I18n.format(ConfigValues.TARGETX_NAME+".tooltip"));
        TARGETZ_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.TARGETZ_NAME, ConfigValues.TARGETZ_DEFAULT, I18n.format(ConfigValues.TARGETZ_NAME+".tooltip"));
        ALIGNMENT_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.ALIGNMENT_NAME, ConfigValues.ALIGNMENT_DEFAULT.name(), I18n.format(ConfigValues.ALIGNMENT_NAME+".tooltip"));
        syncConfig();

        MinecraftForge.EVENT_BUS.register(new ClientEvents());
    }
}
