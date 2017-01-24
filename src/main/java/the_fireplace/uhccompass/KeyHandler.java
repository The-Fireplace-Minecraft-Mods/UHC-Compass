package the_fireplace.uhccompass;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import the_fireplace.uhccompass.render.GuiCompassMoving;
import the_fireplace.uhccompass.render.GuiCoords;

/**
 * @author The_Fireplace
 */
public class KeyHandler {
    //Key Index
    public static final int MOVECOMPASS = 0;
    public static final int QUICKCOORDS = 1;
    //Descriptions, use language file to localize later
    private static final String[] desc =
            {"key.movecompass.desc", "key.quickcoords.desc"};
    //Default Key Values
    private static final int[] keyValues =
            {Keyboard.KEY_EQUALS, Keyboard.KEY_APOSTROPHE};
    private final KeyBinding[] keys;
    public KeyHandler(){
        keys = new KeyBinding[desc.length];
        for(int i = 0; i < desc.length; ++i){
            keys[i] = new KeyBinding(desc[i], keyValues[i], "key.uhccompass.category");
            ClientRegistry.registerKeyBinding(keys[i]);
        }
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event){
        if(keys[MOVECOMPASS].isPressed()){
            Minecraft.getMinecraft().displayGuiScreen(new GuiCompassMoving());
        }
        if(keys[QUICKCOORDS].isPressed()){
            Minecraft.getMinecraft().displayGuiScreen(new GuiCoords());
        }
    }

    public int getKeyCode(int keyBind){
        return keys[keyBind].getKeyCode();
    }
}