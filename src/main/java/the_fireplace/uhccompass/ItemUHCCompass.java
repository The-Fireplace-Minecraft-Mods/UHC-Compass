package the_fireplace.uhccompass;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import the_fireplace.uhccompass.config.ConfigValues;

import javax.annotation.Nullable;

public class ItemUHCCompass extends Item
{
    public ItemUHCCompass()
    {
        this.addPropertyOverride(new ResourceLocation("angle"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            double rotation;
            @SideOnly(Side.CLIENT)
            double rota;
            @SideOnly(Side.CLIENT)
            long lastUpdateTick;
            @SideOnly(Side.CLIENT)
            @Override
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                Entity entity = Minecraft.getMinecraft().player;

                if (worldIn == null)
                    worldIn = entity.world;

                double d0;

                double d1 = (double)entity.rotationYaw;
                d1 = d1 % 360.0D;
                double d2 = this.getSpawnToAngle(worldIn, entity);
                d0 = Math.PI - ((d1 - 90.0D) * 0.01745329238474369D - d2);

                float f = (float)(d0 / (Math.PI * 2D));
                return MathHelper.positiveModulo(f, 1.0F);
            }
            @SideOnly(Side.CLIENT)
            private double getSpawnToAngle(World p_185092_1_, Entity p_185092_2_)
            {
                BlockPos blockpos = new BlockPos(ConfigValues.TARGETX, 1, ConfigValues.TARGETZ);
                return Math.atan2((double)blockpos.getZ() - p_185092_2_.posZ, (double)blockpos.getX() - p_185092_2_.posX);
            }
        });
    }
}