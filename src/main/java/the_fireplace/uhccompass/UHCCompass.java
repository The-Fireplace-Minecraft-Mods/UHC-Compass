package the_fireplace.uhccompass;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
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
    public static Property XALIGNMENT_PROPERTY;
    public static Property YALIGNMENT_PROPERTY;

    public static final Item uhccompass = new Item().setRegistryName("uhccompass").setUnlocalizedName("uhccompass");

    public static void syncConfig(){
        ConfigValues.COMPASSX = COMPASSX_PROPERTY.getInt();
        ConfigValues.COMPASSY = COMPASSY_PROPERTY.getInt();
        ConfigValues.TARGETX = TARGETX_PROPERTY.getInt();
        ConfigValues.TARGETZ = TARGETZ_PROPERTY.getInt();
        ConfigValues.XALIGNMENT = XJust.valueOf(XALIGNMENT_PROPERTY.getString());
        ConfigValues.YALIGNMENT = YJust.valueOf(YALIGNMENT_PROPERTY.getString());

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
        XALIGNMENT_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.XALIGNMENT_NAME, ConfigValues.XALIGNMENT_DEFAULT.name(), I18n.format(ConfigValues.XALIGNMENT_NAME+".tooltip"));
        YALIGNMENT_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.YALIGNMENT_NAME, ConfigValues.YALIGNMENT_DEFAULT.name(), I18n.format(ConfigValues.YALIGNMENT_NAME+".tooltip"));
        syncConfig();

        GameRegistry.registerItem(uhccompass);
        ModelLoader.setCustomModelResourceLocation(uhccompass, 0, new ModelResourceLocation(MODID+":uhccompass", "inventory"));

        MinecraftForge.EVENT_BUS.register(new ClientEvents());
    }
}
