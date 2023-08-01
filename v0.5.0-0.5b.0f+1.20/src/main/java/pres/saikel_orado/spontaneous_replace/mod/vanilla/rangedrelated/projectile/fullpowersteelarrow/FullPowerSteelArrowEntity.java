package pres.saikel_orado.spontaneous_replace.mod.vanilla.rangedrelated.projectile.fullpowersteelarrow;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

import static pres.saikel_orado.spontaneous_replace.mod.data.SRElements.Vanilla.RangedRelated.FULL_POWER_STEEL_ARROW;
import static pres.saikel_orado.spontaneous_replace.mod.data.SRElements.Vanilla.RangedRelated.FULL_POWER_STEEL_ARROW_ENTITY;


/**
 * <b style="color:FFC800"><font size="+2">FullPowerSteelArrowEntity：全威力钢箭实体</font></b>
 * <p><i><b style="color:FFC800"><font size="+1">设置有关全威力钢箭的所有特性</font></b></i></p>
 * <style="color:FFC800">
 *
 * @author 刘 Saikel Orado 又称 “游戏极客-Saikel”
 * <p>Saikel Orado Liu aka ”GameGeek-Saikel“</p>
 * @version 4.0
 * | 创建于 2023/5/1 21:01
 */
public class FullPowerSteelArrowEntity extends PersistentProjectileEntity {
    // region 构建全威力钢箭实体
    public FullPowerSteelArrowEntity(EntityType<? extends FullPowerSteelArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    public FullPowerSteelArrowEntity(World world, LivingEntity owner) {
        super(FULL_POWER_STEEL_ARROW_ENTITY, owner, world);
    }

    public FullPowerSteelArrowEntity(World world, double x, double y, double z) {
        super(FULL_POWER_STEEL_ARROW_ENTITY, x, y, z, world);
    }
    // endregion

    /**
     * 添加粒子效果
     */
    @Override
    public void tick() {
        if (getWorld().isClient && !this.inGround) {
            getWorld().addParticle(ParticleTypes.INSTANT_EFFECT, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
        super.tick();
    }

    /**
     * 作为物品堆栈
     */
    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(FULL_POWER_STEEL_ARROW);
    }

    /**
     * 设置全威力钢箭的破盾效果
     */
    @Override
    protected void onHit(LivingEntity target) {
        super.onHit(target);
    }
}