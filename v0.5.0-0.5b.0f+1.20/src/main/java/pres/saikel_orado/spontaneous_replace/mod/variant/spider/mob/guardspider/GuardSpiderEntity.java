package pres.saikel_orado.spontaneous_replace.mod.variant.spider.mob.guardspider;

import pres.saikel_orado.spontaneous_replace.mod.variant.spider.data.SRSpiderEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.world.World;

/**
 * <b style="color:FFC800"><font size="+2">GuardSpiderEntity：蜘蛛卫兵实体类</font></b>
 * <p><i><b style="color:FFC800"><font size="+1">蜘蛛卫兵的所有实体属性，逻辑控制</font></b></i></p>
 * <style="color:FFC800">
 *
 * @author 刘 Saikel Orado 又称 “游戏极客-Saikel”
 * <p>Saikel Orado Liu aka ”GameGeek-Saikel“</p>
 * @version 2.0
 * | 创建于 2022/12/26 ~ 2023/01/05
 */
public class GuardSpiderEntity extends SRSpiderEntity {
    /**
     * 构建实体
     */
    public GuardSpiderEntity(EntityType<? extends SpiderEntity> entityType, World world) {
        super(entityType, world);
        setExpPoint(GuardSpiderData.EXP_RADIO);
    }

    /**
     * 设置实体数值
     */
    public static DefaultAttributeContainer.Builder createSpiderAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, GuardSpiderData.HP)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, GuardSpiderData.DAMAGE)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, GuardSpiderData.SPEED_COEFFICIENT)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, GuardSpiderData.KNOCK_BACK_RESISTANCE)
                .add(EntityAttributes.GENERIC_ARMOR, GuardSpiderData.ARMOR);
    }

    /**
     * 重新分配目标逻辑
     */
    @Override
    protected void initGoals() {
        super.initGoals();
        setDefaultAttackGoals();
        setDefaultTargetGoals();
    }
}