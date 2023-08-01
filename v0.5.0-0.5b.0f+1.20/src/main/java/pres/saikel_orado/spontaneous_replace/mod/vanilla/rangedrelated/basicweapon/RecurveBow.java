package pres.saikel_orado.spontaneous_replace.mod.vanilla.rangedrelated.basicweapon;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import pres.saikel_orado.spontaneous_replace.mod.data.SRData;
import pres.saikel_orado.spontaneous_replace.mod.util.SRBow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static pres.saikel_orado.spontaneous_replace.mod.data.SRElements.Vanilla.RangedRelated.RECURVE_BOW;

/**
 * <b style="color:FFC800"><font size="+2">RecurveBow：反曲弓</font></b>
 * <p><i><b style="color:FFC800"><font size="+1">添加一种性能更优良的基础远程武器</font></b></i></p>
 * <style="color:FFC800">
 *
 * @author 刘 Saikel Orado 又称 “游戏极客-Saikel”
 * <p>Saikel Orado Liu aka ”GameGeek-Saikel“</p>
 * @version 4.0
 * | 创建于 2023/4/26 14:36
 */
public class RecurveBow extends SRBow {
    public static final double MAX_PULL_TIME = 0.75;
    public static final double USING_SPEED = SRData.Player.WALK_SPEED / 2;
    public static final double BASIC_DAMAGE_MULTIPLE = 1.5;
    public static final double PULLING_SCALE_MULTIPLE = 0.85;
    public static final float MAX_ARROW_SPEED = 4;
    public static final int RANGE = BowItem.RANGE;
    public static final int RECURVE_BOW_MAX_DAMAGE = Items.BOW.getMaxDamage() * 2;

    /**
     * <p>反曲弓可用弹丸：</p>
     * <p>箭、药箭、光灵箭</p>
     */
    public static final Predicate<ItemStack> RECURVE_BOW_PROJECTILES =
            (stack) -> stack.isOf(Items.ARROW)
                    || stack.isOf(Items.TIPPED_ARROW)
                    || stack.isOf(Items.SPECTRAL_ARROW);
    /**
     * <p>反曲弓可用附魔：</p>
     * <p>无限、火矢、力量、击退、耐久、消失诅咒、经验修补</p>
     */
    public static final List<Enchantment> RECURVE_BOW_ENCHANTMENTS = new ArrayList<>(
            Arrays.asList(Enchantments.INFINITY,
                    Enchantments.FLAME,
                    Enchantments.POWER,
                    Enchantments.PUNCH,
                    Enchantments.UNBREAKING,
                    Enchantments.VANISHING_CURSE,
                    Enchantments.MENDING)
    );

    /**
     * 构建反曲弓
     */
    public RecurveBow(Settings settings) {
        super(settings,
                RECURVE_BOW_PROJECTILES,
                RECURVE_BOW_ENCHANTMENTS,
                USING_SPEED,
                DEFAULT_MAX_USE_TIME,
                MAX_PULL_TIME,
                MAX_ARROW_SPEED,
                BASIC_DAMAGE_MULTIPLE,
                RANGE);
    }

    /**
     * 设置停止使用时的操作，如发射箭等
     */
    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        // 检查用户使用者是否为玩家
        if (user instanceof PlayerEntity playerEntity) {
            // 检查是否在创造模式或者拥有“无限”附魔
            boolean inCreateOrInfinity = playerEntity.getAbilities().creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
            // 获取弹丸
            ItemStack useArrow = user.getProjectileType(stack);
            // 检查玩家是否有箭，如果没有箭但在创造模式或者拥有“无限”附魔则使用箭
            if (!useArrow.isEmpty() || inCreateOrInfinity) {
                if (useArrow.isEmpty())
                    useArrow = new ItemStack(Items.ARROW);
            }
            if (!useArrow.isEmpty() || inCreateOrInfinity) {
                // 获取弹弓已使用游戏刻
                int usedTicks = this.getMaxUseTime(stack) - remainingUseTicks;
                // 获取弹弓拉弓进度
                float pullProgress = getPullProgress(usedTicks);
                // 如果拉弓进度不小于 0.1
                if (!((double) pullProgress < 0.1)) {
                    // 如果在创造模式或者拥有“无限”附魔以及弹丸是默认弹丸
                    boolean inCreateOrInfinityAndDefaultBullet = inCreateOrInfinity && useArrow.isOf(Items.ARROW);
                    if (!world.isClient) {
                        // 创建箭实体
                        ArrowItem arrowItem = (ArrowItem) (useArrow.getItem() instanceof ArrowItem ? useArrow.getItem() : Items.ARROW);
                        PersistentProjectileEntity persistentProjectileEntity = arrowItem.createArrow(world, useArrow, playerEntity);
                        // 设置速度速度
                        persistentProjectileEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, pullProgress * MAX_ARROW_SPEED, 1.0F);
                        // 设置基础伤害增加
                        persistentProjectileEntity.setDamage(persistentProjectileEntity.getDamage() * DAMAGE_MULTIPLE);
                        if (pullProgress == 1.0F)
                            persistentProjectileEntity.setCritical(true);

                        // 设置“力量”效果
                        int powerLevel = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
                        if (powerLevel > 0)
                            persistentProjectileEntity.setDamage(persistentProjectileEntity.getDamage() + (double) powerLevel * 0.5 + 0.5);

                        // 设置“冲击”效果
                        int punchLevel = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack);
                        if (punchLevel > 0)
                            persistentProjectileEntity.setPunch(punchLevel);

                        // 设置“火矢”效果
                        if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0)
                            persistentProjectileEntity.setOnFireFor(100);

                        // 设置工具损伤
                        stack.damage(1, playerEntity, (p) -> p.sendToolBreakStatus(playerEntity.getActiveHand()));
                        if (inCreateOrInfinityAndDefaultBullet || playerEntity.getAbilities().creativeMode && (useArrow.isOf(Items.SPECTRAL_ARROW) || useArrow.isOf(Items.TIPPED_ARROW))) {
                            persistentProjectileEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                        }

                        // 生成弹丸实体
                        world.spawnEntity(persistentProjectileEntity);
                    }

                    // 播放音效
                    world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + pullProgress * 0.5F);
                    if (!inCreateOrInfinityAndDefaultBullet && !playerEntity.getAbilities().creativeMode) {
                        useArrow.decrement(1);
                        if (useArrow.isEmpty()) {
                            playerEntity.getInventory().removeOne(useArrow);
                        }
                    }

                    playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
                }
            }
        }
    }

    /**
     * 在开始使用反曲弓时获取弹丸
     */
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // 获取使用物品
        ItemStack itemStack = new ItemStack(RECURVE_BOW);
        for (ItemStack stack : user.getHandItems()) {
            if (stack.isOf(RECURVE_BOW))
                itemStack = stack;
        }
        // 检查是否在创造模式或者拥有“无限”附魔
        boolean inCreateOrInfinity = user.getAbilities().creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, itemStack) > 0;
        // 获取弹丸
        ItemStack useArrow = user.getProjectileType(itemStack);
        // 检查玩家是否有箭，如果没有箭但在创造模式或者拥有“无限”附魔则使用箭
        if (!useArrow.isEmpty() || inCreateOrInfinity) {
            if (useArrow.isEmpty())
                useArrow = new ItemStack(Items.ARROW);
        }
        setBulletId(itemStack, useArrow);

        return super.use(world, user, hand);
    }

    /**
     * 设置弹丸 ID 的 NBT 以供 JSON 渲染使用
     */
    @Override
    public void setBulletId(ItemStack stack, ItemStack useBullet) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putFloat("Bullet", 0);
        if (useBullet != null) {
            if (useBullet.isOf(Items.ARROW))
                nbtCompound.putFloat("Bullet", 0);
            else if (useBullet.isOf(Items.TIPPED_ARROW))
                nbtCompound.putFloat("Bullet", 0.1F);
            else if (useBullet.isOf(Items.SPECTRAL_ARROW))
                nbtCompound.putFloat("Bullet", 0.2F);
        }
        nbtCompound.getFloat("Bullet");
    }

    /**
     * 获取 NBT 弹丸 ID 以供 JSON 渲染使用
     */
    @Override
    public float getBulletId(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        return nbtCompound != null ? nbtCompound.getFloat("Bullet") : 0;
    }
}