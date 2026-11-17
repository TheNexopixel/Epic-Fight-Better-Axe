package nexo.better_axe.skill.compat;

import java.util.function.Function;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import nexo.better_axe.EpicFightBetterAxeMain;
import nexo.better_axe.gameasset.animation.BetterAxeAnimations;
import nexo.better_axe.skill.BetterAxeSkills;
import nexo.better_axe.world.capabilities.item.BetterAxeWeaponCategories;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.compat.ICompatModule;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

public class EFXCompat implements ICompatModule {
    private static final ResourceLocation AXE = ResourceLocation.fromNamespaceAndPath("epicfight", "axe");

    private static void register(WeaponCapabilityPresetRegistryEvent event) {
        Function<Item, CapabilityItem.Builder> base = event.getTypeEntry().get(AXE);
        Function<Item, CapabilityItem.Builder> patched = item -> patch((WeaponCapability.Builder)base.apply(item));
        event.getTypeEntry().put(AXE, patched);
        event.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(EpicFightBetterAxeMain.MODID, "da_axe"), patched);
        event.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(EpicFightBetterAxeMain.MODID, "efx_da_axe"), patched);
    }

    private static WeaponCapability.Builder patch(WeaponCapability.Builder builder) {
        return builder
                .category(BetterAxeWeaponCategories.DA_AXE)
                .weaponCombinationPredicator((entityPatch) -> EpicFightCapabilities.getItemStackCapability(entityPatch.getOriginal()
                        .getOffhandItem()).getWeaponCategory() == BetterAxeWeaponCategories.DA_AXE)
                .styleProvider((pp) ->
                        pp.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == BetterAxeWeaponCategories.DA_AXE
                                ? CapabilityItem.Styles.TWO_HAND : CapabilityItem.Styles.ONE_HAND)
                .collider(ColliderPreset.TOOLS)
                .hitParticle(EpicFightParticles.HIT_BLADE.get())
                .swingSound(EpicFightSounds.WHOOSH.get())
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .canBePlacedOffhand(true)
                .innateSkill(CapabilityItem.Styles.TWO_HAND, ip -> BetterAxeSkills.SKULL_CRACKER)

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
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN);
    }

    @Override
    public void onModEventBus(IEventBus eventBus) {
        eventBus.addListener(EventPriority.LOWEST, EFXCompat::register);
    }

    @Override
    public void onForgeEventBus(IEventBus eventBus) { }

    @Override
    public void onModEventBusClient(IEventBus eventBus) { }

    @Override
    public void onForgeEventBusClient(IEventBus eventBus) { }
}
