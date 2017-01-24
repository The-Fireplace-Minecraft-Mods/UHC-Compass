package the_fireplace.uhccompass.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import the_fireplace.uhccompass.config.ConfigValues;

public class TextureUHCCompass extends TextureAtlasSprite
{
    /** Current uhccompass heading in radians */
    public double currentAngle;
    /** Speed and direction of uhccompass rotation */
    public double angleDelta;

    public TextureUHCCompass()
    {
        super("items/compass");
    }

    @Override
    public void updateAnimation()
    {
        Minecraft minecraft = Minecraft.getMinecraft();

        if (minecraft.theWorld != null && minecraft.thePlayer != null)
        {
            this.updateCompass(minecraft.theWorld, minecraft.thePlayer.posX, minecraft.thePlayer.posZ, (double)minecraft.thePlayer.rotationYaw, false, false);
        }
        else
        {
            this.updateCompass((World)null, 0.0D, 0.0D, 0.0D, true, false);
        }
    }

    /**
     * Updates the uhccompass based on the given x,z coords and camera direction
     */
    public void updateCompass(World worldIn, double xPos, double zPos, double rotationYaw, boolean p_94241_8_, boolean p_94241_9_)
    {
        if (!this.framesTextureData.isEmpty())
        {
            double d0 = 0.0D;

            if (worldIn != null && !p_94241_8_)
            {
                BlockPos blockpos = new BlockPos(ConfigValues.TARGETX, 0, ConfigValues.TARGETZ);
                double d1 = (double)blockpos.getX() - xPos;
                double d2 = (double)blockpos.getZ() - zPos;
                rotationYaw = rotationYaw % 360.0D;
                d0 = -((rotationYaw - 90.0D) * Math.PI / 180.0D - Math.atan2(d2, d1));

                if (!worldIn.provider.isSurfaceWorld())
                {
                    d0 = Math.random() * Math.PI * 2.0D;
                }
            }

            if (p_94241_9_)
            {
                this.currentAngle = d0;
            }
            else
            {
                double d3;

                for (d3 = d0 - this.currentAngle; d3 < -Math.PI; d3 += (Math.PI * 2D))
                {
                    ;
                }

                while (d3 >= Math.PI)
                {
                    d3 -= (Math.PI * 2D);
                }

                d3 = MathHelper.clamp_double(d3, -1.0D, 1.0D);
                this.angleDelta += d3 * 0.1D;
                this.angleDelta *= 0.8D;
                this.currentAngle += this.angleDelta;
            }

            int i;

            for (i = (int)((this.currentAngle / (Math.PI * 2D) + 1.0D) * (double)this.framesTextureData.size()) % this.framesTextureData.size(); i < 0; i = (i + this.framesTextureData.size()) % this.framesTextureData.size())
            {
                ;
            }

            if (i != this.frameCounter)
            {
                this.frameCounter = i;
                TextureUtil.uploadTextureMipmap(this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
            }
        }
    }
}