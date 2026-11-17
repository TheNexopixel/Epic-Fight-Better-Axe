package nexo.better_axe.world.capabilities.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nexo.better_axe.EpicFightBetterAxeMain;
import nexo.better_axe.gameasset.animation.BetterAxeAnimations;
import nexo.better_axe.skill.BetterAxeSkills;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.function.Function;

@Mod.EventBusSubscriber(modid = EpicFightBetterAxeMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class BetterAxeWeaponCapabilities {

    public static final Function<Item, CapabilityItem.Builder> DA_AXE = (item) ->
            WeaponCapability.builder()
                    .category(BetterAxeWeaponCategories.DA_AXE)
                    .styleProvider((pp) -> CapabilityItem.Styles.ONE_HAND)
                    .weaponCombinationPredicator((entityPatch) -> EpicFightCapabilities.getItemStackCapability(entityPatch.getOriginal().getOffhandItem()).getWeaponCategory() == BetterAxeWeaponCategories.DA_AXE)
                    .styleProvider((pp) ->
                            pp.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == BetterAxeWeaponCategories.DA_AXE ? CapabilityItem.Styles.TWO_HAND : CapabilityItem.Styles.ONE_HAND)
                    .collider(ColliderPreset.TOOLS)
                    .hitParticle(EpicFightParticles.HIT_BLADE.get())
                    .swingSound(EpicFightSounds.WHOOSH.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .canBePlacedOffhand(true)
                    .newStyleCombo(CapabilityItem.Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
                    .innateSkill(CapabilityItem.Styles.ONE_HAND, ip -> EpicFightSkills.GUILLOTINE_AXE)
                    .innateSkill(CapabilityItem.Styles.TWO_HAND, ip -> BetterAxeSkills.SKULL_CRACKER)
                    .newStyleCombo(CapabilityItem.Styles.ONE_HAND,
                            BetterAxeAnimations.AXE_ATTACK1,
                            BetterAxeAnimations.AXE_ATTACK2,
                            BetterAxeAnimations.AXE_ATTACK3,
                            BetterAxeAnimations.AXE_ATTACK4,
                            BetterAxeAnimations.AXE_DASH,
                            BetterAxeAnimations.AXE_AIRSLASH)

                    .newStyleCombo(CapabilityItem.Styles.TWO_HAND,
                            BetterAxeAnimations.DUAL_AXE_ATTACK1,
                            BetterAxeAnimations.DUAL_AXE_ATTACK2,
                            BetterAxeAnimations.DUAL_AXE_ATTACK3,
                            BetterAxeAnimations.DUAL_AXE_ATTACK4,
                            BetterAxeAnimations.DUAL_AXE_DASH,
                            BetterAxeAnimations.DUAL_AXE_AIRSLASH)

                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, BetterAxeAnimations.DUAL_AXE_IDLE)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN)

                    .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.IDLE, BetterAxeAnimations.AXE_IDLE)
                    .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD);


    @SubscribeEvent
    public static void WeaponMovesetRegister(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(EpicFightBetterAxeMain.MODID, "da_axe"), DA_AXE);
    }
}
