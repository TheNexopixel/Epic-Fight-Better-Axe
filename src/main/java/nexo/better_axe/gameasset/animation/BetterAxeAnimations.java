package nexo.better_axe.gameasset.animation;

import net.minecraft.world.InteractionHand;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.damagesource.StunType;

public class BetterAxeAnimations {

    public static AnimationManager.AnimationAccessor<StaticAnimation> AXE_IDLE;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AXE_ATTACK1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AXE_ATTACK2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AXE_ATTACK3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AXE_ATTACK4;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> AXE_DASH;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> AXE_AIRSLASH;

    public static AnimationManager.AnimationAccessor<StaticAnimation> DUAL_AXE_IDLE;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_AXE_ATTACK1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_AXE_ATTACK2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_AXE_ATTACK3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_AXE_ATTACK4;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> DUAL_AXE_DASH;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> DUAL_AXE_AIRSLASH;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SKULL_CRACKER;


    public static void build(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> biped = Armatures.BIPED;

        AXE_IDLE = builder.nextAccessor("biped/living/axe_idle", ac ->
                new StaticAnimation(0.12F, true, ac, biped));

        AXE_ATTACK1 = builder.nextAccessor("biped/combat/axe_attack1", (accessor) ->
                new BasicAttackAnimation(0.12F, 0.21F, 0.37F, 0.58F, 0.62F, null, biped.get().toolR, accessor, biped)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true));

        AXE_ATTACK2 = builder.nextAccessor("biped/combat/axe_attack2", (accessor) ->
                new BasicAttackAnimation(0.12F, 0.21F, 0.37F, 0.58F, 0.62F, null, biped.get().toolR, accessor, biped)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true));

        AXE_ATTACK3 = builder.nextAccessor("biped/combat/axe_attack3", (accessor) ->
                new BasicAttackAnimation(0.12F, 0.21F, 0.42F, 0.63F, 0.65F, null, biped.get().toolR, accessor, biped)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true));

        AXE_ATTACK4 = builder.nextAccessor("biped/combat/axe_attack4", (accessor) ->
                new BasicAttackAnimation(0.12F, 0.21F, 0.46F, 0.68F, 0.9F, null, biped.get().toolR, accessor, biped)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.1F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE,EpicFightParticles.BLADE_RUSH_SKILL)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true));

        AXE_DASH = builder.nextAccessor("biped/combat/axe_dash", (accessor) ->
                new DashAttackAnimation(0.12F, 0.4F, 0.43F, 0.58F, 0.8F, null, biped.get().toolR, accessor, biped)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.1F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true));

        AXE_AIRSLASH = builder.nextAccessor("biped/combat/axe_airslash", (accessor) ->
                new AirSlashAnimation(0.12F, 0.2F, 0.54F, 0.8F, null, biped.get().toolR, accessor, biped)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0f,0.25f))
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true));



// ======================================== DUAL MOVESET ==============================================================

        DUAL_AXE_IDLE = builder.nextAccessor("biped/living/dual_axe_idle", ac ->
                new StaticAnimation(0.12F, true, ac, biped));


        DUAL_AXE_ATTACK1 = builder.nextAccessor("biped/combat/dual_axe_attack1", (accessor) ->
                new BasicAttackAnimation(0.12F, accessor, biped,
                        new AttackAnimation.Phase(0.0f, 0.20f, 0.4f, 0.55f, 1.2f, 0.6f, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        ,

                        new AttackAnimation.Phase(0.8f, 0.2f, 0.9f, 1.05f, 1.2f, 5.51f, InteractionHand.OFF_HAND, biped.get().toolL, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE,StunType.LONG)
                )

                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true));

        DUAL_AXE_ATTACK2 = builder.nextAccessor("biped/combat/dual_axe_attack2", (accessor) ->
                new BasicAttackAnimation(0.12F, accessor, biped,
                        new AttackAnimation.Phase(0.0f, 0.20f, 0.4f, 0.55f, 1.2f, 0.6f, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        ,

                        new AttackAnimation.Phase(0.8f, 0.2f, 0.8f, 0.95f, 1.2f, 5.51f, InteractionHand.OFF_HAND, biped.get().toolL, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE,StunType.LONG)
                )

                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true));

        DUAL_AXE_ATTACK3 = builder.nextAccessor("biped/combat/dual_axe_attack3", (accessor) ->
                new BasicAttackAnimation(0.12F, accessor, biped,
                        new AttackAnimation.Phase(0.0f, 0.20f, 0.19f, 0.3f, 0.6f, 0.3f, InteractionHand.OFF_HAND, biped.get().toolL, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        ,

                        new AttackAnimation.Phase(0.3f, 0.2f, 0.3f, 0.44f, 0.6f, 5.51f, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE,StunType.LONG)
                )

                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true));

        DUAL_AXE_ATTACK4 = builder.nextAccessor("biped/combat/dual_axe_attack4", (accessor) ->
                new BasicAttackAnimation(0.12F, accessor, biped,
                        new AttackAnimation.Phase(0.0f, 0.2f, 0.35f, 0.5f, 0.8f, 3.51f,
                                InteractionHand.MAIN_HAND,AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null),
                                AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.1F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE,StunType.LONG)
                )

                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
        );

        DUAL_AXE_DASH = builder.nextAccessor("biped/combat/dual_axe_dash", (accessor) ->
                new DashAttackAnimation(0.12F, accessor, biped,
                        new AttackAnimation.Phase(0.0f, 0.2f, 0.3f, 0.45f, 1.2f, 0.6f,
                                InteractionHand.MAIN_HAND,AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null),
                                AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE,StunType.LONG),

                        new AttackAnimation.Phase(0.6f, 0.2f, 0.69f, 0.8f, 1.2f, 3.51f,
                                InteractionHand.MAIN_HAND,AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null),
                                AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE,StunType.LONG)

                )

                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.02F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
        );
        DUAL_AXE_AIRSLASH = builder.nextAccessor("biped/combat/dual_axe_airslash", (accessor) ->
                new AirSlashAnimation(0.12F, accessor, biped,
                        new AttackAnimation.Phase(0.0f, 0.2f, 0.35f, 0.5f, 1.2f, 5.6f,
                                InteractionHand.MAIN_HAND,AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null),
                                AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE,StunType.LONG)

                )

                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.022F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0f,0.3f))
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
        );

        SKULL_CRACKER = builder.nextAccessor("biped/skill/skull_cracker", (accessor) ->
                new AttackAnimation(0.12F, accessor, biped,
                        new AttackAnimation.Phase(0.0f, 0.2f, 0.24f, 0.39f, 1.3f, 0.6f, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                                ,

                        new AttackAnimation.Phase(0.7f, 0.2f, 0.7f, 1.0f, 1.3f, 8.6f,
                                InteractionHand.MAIN_HAND,AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null),
                                AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(14.7F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(30F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.adder(4F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))

                )

                        .addProperty(AnimationProperty.AttackAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                        .addProperty(AnimationProperty.AttackAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.AttackAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0f,0.9f))
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
        );







    }
}
