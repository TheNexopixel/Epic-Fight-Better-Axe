package nexo.dual_axe.skill;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nexo.dual_axe.EpicFightBetterAxeMain;
import nexo.dual_axe.gameasset.animation.BetterAxeAnimations;
import nexo.dual_axe.world.capabilities.item.BetterAxeWeaponCategories;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Set;

@Mod.EventBusSubscriber(
        modid = EpicFightBetterAxeMain.MODID,
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class BetterAxeSkills {

    public static Skill AXE_MAN;
    public static Skill SKULL_CRACKER;

    @SubscribeEvent
    public static void buildSkillEvent(SkillBuildEvent build) {
        SkillBuildEvent.ModRegistryWorker modRegistry = build.createRegistryWorker(EpicFightBetterAxeMain.MODID);

        AXE_MAN = modRegistry.build("axe_master", AxemanSkill::new,
                AxemanSkill.createAxemanBuilder()
                        .addAvailableWeaponCategory(BetterAxeWeaponCategories.DA_AXE)
                        .setCategory(SkillCategories.PASSIVE));

        WeaponInnateSkill skullcracker = modRegistry.build("skull_cracker", SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder()
                .setAnimations(BetterAxeAnimations.SKULL_CRACKER)
                .setCategory(SkillCategories.WEAPON_INNATE));
        skullcracker.newProperty()

                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(6.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(40.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT
                        .create())).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.EXECUTION))
                .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.FINISHER))
                .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.GUARD_PUNCTURE))
                .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
        ;
        SKULL_CRACKER = skullcracker;

    }

}
