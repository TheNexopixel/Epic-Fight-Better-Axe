package nexo.better_axe.skill.compat;

import com.hm.efn.gameasset.animations.EFNSkillAnimations;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import nexo.better_axe.world.capabilities.item.BetterAxeWeaponCategories;
import yesman.epicfight.api.client.forgeevent.WeaponCategoryIconRegisterEvent;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.compat.ICompatModule;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.guard.GuardSkill;

import java.util.List;

public class NightfallCompat implements ICompatModule {


    public static void onEnhancedParry(SkillBuildEvent.ModRegistryWorker.SkillCreateEvent<GuardSkill.Builder> event) {
        if (event.getRegistryName().equals(ResourceLocation.fromNamespaceAndPath("efn", "efn_parry"))) {
            GuardSkill.Builder builder = event.getSkillBuilder();
            builder.addGuardMotion(
                    BetterAxeWeaponCategories.DA_AXE,
                    (i, p) -> Animations.SWORD_DUAL_GUARD_HIT
            ).addGuardBreakMotion(BetterAxeWeaponCategories.DA_AXE,
                    (i, p) -> Animations.BIPED_COMMON_NEUTRALIZED)


                    .addAdvancedGuardMotion(BetterAxeWeaponCategories.DA_AXE, ((capabilityItem, pp) ->
                            List.of(EFNSkillAnimations.EFN_GUARD_ACTIVE_HIT1, EFNSkillAnimations.EFN_GUARD_ACTIVE_HIT2, EFNSkillAnimations.EFN_GUARD_ACTIVE_HIT3)))


            ;

        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void onIconCreate(WeaponCategoryIconRegisterEvent icon){

        icon.registerCategory(BetterAxeWeaponCategories.DA_AXE, new ItemStack(Items.DIAMOND_AXE));
    }





    @Override
    public void onModEventBus(IEventBus eventBus) {
        eventBus.addGenericListener(GuardSkill.Builder.class,
                EventPriority.NORMAL,
                NightfallCompat::onEnhancedParry);
    }

    @Override
    public void onForgeEventBus(IEventBus eventBus) {

    }

    @Override
    public void onModEventBusClient(IEventBus eventBus) {
        eventBus.addListener(NightfallCompat::onIconCreate);
    }

    @Override
    public void onForgeEventBusClient(IEventBus eventBus) {

    }
}
