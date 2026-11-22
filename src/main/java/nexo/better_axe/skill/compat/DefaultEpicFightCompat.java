package nexo.better_axe.skill.compat;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nexo.better_axe.EpicFightBetterAxeMain;
import nexo.better_axe.world.capabilities.item.BetterAxeWeaponCategories;
import yesman.epicfight.api.client.forgeevent.WeaponCategoryIconRegisterEvent;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.guard.GuardSkill;

@Mod.EventBusSubscriber(modid = EpicFightBetterAxeMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DefaultEpicFightCompat {

    @SubscribeEvent
    public static void onGuardSkillcreate(SkillBuildEvent.ModRegistryWorker.SkillCreateEvent<GuardSkill.Builder> event) {
        if (event.getRegistryName().equals(ResourceLocation.fromNamespaceAndPath("epicfight", "guard"))) {
            GuardSkill.Builder builder = event.getSkillBuilder();

            builder.addGuardMotion(
                    BetterAxeWeaponCategories.DA_AXE,
                    (i, p) -> Animations.SWORD_DUAL_GUARD_HIT
            ).addGuardBreakMotion(BetterAxeWeaponCategories.DA_AXE,
                    (i, p) -> Animations.BIPED_COMMON_NEUTRALIZED);
        }
    }
    @SubscribeEvent
    public static void onImpactGuardSkillcreate(SkillBuildEvent.ModRegistryWorker.SkillCreateEvent<GuardSkill.Builder> event) {
        if (event.getRegistryName().equals(ResourceLocation.fromNamespaceAndPath("epicfight", "impact_guard"))) {
            GuardSkill.Builder builder = event.getSkillBuilder();

            builder.addGuardMotion(
                    BetterAxeWeaponCategories.DA_AXE,
                    (i, p) -> Animations.SWORD_DUAL_GUARD_HIT
            ).addGuardBreakMotion(BetterAxeWeaponCategories.DA_AXE,
                    (i, p) -> Animations.BIPED_COMMON_NEUTRALIZED);

        }
    }
    @SubscribeEvent
    public static void onParrySkillcreate(SkillBuildEvent.ModRegistryWorker.SkillCreateEvent<GuardSkill.Builder> event) {
        if (event.getRegistryName().equals(ResourceLocation.fromNamespaceAndPath("epicfight", "parrying"))) {
            GuardSkill.Builder builder = event.getSkillBuilder();
            builder.addGuardMotion(
                    BetterAxeWeaponCategories.DA_AXE,
                    (i, p) -> Animations.SWORD_DUAL_GUARD_HIT
            ).addGuardBreakMotion(BetterAxeWeaponCategories.DA_AXE,
                    (i, p) -> Animations.BIPED_COMMON_NEUTRALIZED);

        }
    }
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onIconCreate(WeaponCategoryIconRegisterEvent icon) {
        icon.registerCategory(BetterAxeWeaponCategories.DA_AXE, new ItemStack(Items.DIAMOND_AXE));
    }
}
