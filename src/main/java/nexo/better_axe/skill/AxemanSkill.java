package nexo.better_axe.skill;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import nexo.better_axe.world.capabilities.item.BetterAxeWeaponCategories;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.api.client.forgeevent.WeaponCategoryIconRegisterEvent;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.skill.passive.SwordmasterSkill;
import yesman.epicfight.world.capabilities.entitypatch.EntityDecorations.AnimationPropertyModifier;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;
// import ...BetterAxeWeaponCategories;  <- Paket aus deinem Addon

public class AxemanSkill extends PassiveSkill {
    private static final UUID EVENT_UUID = UUID.fromString("0b6f3c52-8d1a-4e47-9a35-c2e71f4b9d08");
    private static final UUID STAMINA_UUID = UUID.fromString("7e2a9d14-5c83-4b60-a1f7-3d58b0c6e921");
    private static final UUID REGEN_UUID = UUID.fromString("c19f4a73-2b6e-4d85-8e10-5a7d3f92b64c");

    private static final ResourceLocation TRAIL_MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath("better_axe", "axeman_trail_modifier");
    private static final ResourceLocation TRAIL_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("better_axe", "textures/particle/axeman_swing_trail.png");

    public static class Builder extends SkillBuilder<AxemanSkill> {
        protected final Set<WeaponCategory> availableWeaponCategories = Sets.newHashSet();

        public Builder addAvailableWeaponCategory(WeaponCategory... wc) {
            this.availableWeaponCategories.addAll(Arrays.asList(wc));
            return this;
        }
    }

    public static AxemanSkill.Builder createAxemanBuilder() {
        return new AxemanSkill.Builder()
                .addAvailableWeaponCategory(BetterAxeWeaponCategories.DA_AXE)
                .addAvailableWeaponCategory(WeaponCategories.AXE)
                .setCategory(SkillCategories.PASSIVE)
                .setResource(Resource.NONE);
    }

    private float speedBonus = 30.0F;
    private float speedMalus = 20.0F;
    private double maxStaminaBonus = 6.0D;
    private double staminaRegenBonus = 0.2D;

    private final Set<WeaponCategory> availableWeaponCategories;

    public AxemanSkill(AxemanSkill.Builder builder) {
        super(builder);
        this.availableWeaponCategories = ImmutableSet.copyOf(builder.availableWeaponCategories);
    }

    @Override
    public void setParams(CompoundTag parameters) {
        super.setParams(parameters);
        this.speedBonus = parameters.contains("speed_bonus") ? parameters.getFloat("speed_bonus") : 20.0F;
        this.speedMalus = parameters.contains("speed_malus") ? parameters.getFloat("speed_malus") : 20.0F;
        this.maxStaminaBonus = parameters.contains("max_stamina_bonus") ? parameters.getDouble("max_stamina_bonus") : 6.0D;
        this.staminaRegenBonus = parameters.contains("stamina_regen_bonus") ? parameters.getDouble("stamina_regen_bonus") : 0.2D;
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);

        container.getExecutor().getEventListener().addEventListener(
                EventType.MODIFY_ATTACK_SPEED_EVENT, EVENT_UUID, event -> {
                    WeaponCategory category = event.getItemCapability().getWeaponCategory();
                    float speed = event.getAttackSpeed();

                    if (this.availableWeaponCategories.contains(category)) {
                        event.setAttackSpeed(speed * (1.0F + this.speedBonus * 0.01F));
                    } else if (category != WeaponCategories.FIST
                            && !coveredBySwordmaster(container.getExecutor(), category)) {
                        event.setAttackSpeed(speed * (1.0F - this.speedMalus * 0.01F));
                    }
                });
    }

    @Override
    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);

        container.getExecutor().getEventListener().removeListener(EventType.MODIFY_ATTACK_SPEED_EVENT, EVENT_UUID);

        if (!container.getExecutor().isLogicalClient()) {
            Player player = container.getExecutor().getOriginal();
            setModifier(player, EpicFightAttributes.MAX_STAMINA.get(), STAMINA_UUID, "", 0.0D, false);
            setModifier(player, EpicFightAttributes.STAMINA_REGEN.get(), REGEN_UUID, "", 0.0D, false);
        }
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        if (container.getExecutor().isLogicalClient()) return;

        PlayerPatch<?> patch = container.getExecutor();
        Player player = patch.getOriginal();
        WeaponCategory held = patch.getAdvancedHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory();
        boolean axe = this.availableWeaponCategories.contains(held);

        setModifier(player, EpicFightAttributes.MAX_STAMINA.get(), STAMINA_UUID,
                "Axeman max stamina", this.maxStaminaBonus, axe);
        setModifier(player, EpicFightAttributes.STAMINA_REGEN.get(), REGEN_UUID,
                "Axeman stamina regen", this.staminaRegenBonus, axe);
    }

    private static void setModifier(Player player, Attribute attribute, UUID id, String name,
                                    double amount, boolean active) {
        AttributeInstance instance = player.getAttribute(attribute);
        if (instance == null) return;

        boolean present = instance.getModifier(id) != null;
        if (active && !present) {
            instance.addTransientModifier(
                    new AttributeModifier(id, name, amount, AttributeModifier.Operation.ADDITION));
        } else if (!active && present) {
            instance.removeModifier(id);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onInitiateClient(SkillContainer container) {
        container.getExecutor().getEntityDecorations().addTrailInfoModifier(TRAIL_MODIFIER_ID,
                new AnimationPropertyModifier<>() {
                    @Override
                    public TrailInfo getModifiedValue(TrailInfo val, CapabilityItem object) {
                        if (AxemanSkill.this.availableWeaponCategories.contains(object.getWeaponCategory())) {
                            TrailInfo.Builder builder = val.unpackAsBuilder();
                            builder.lifetime(val.trailLifetime() + 2);
                            builder.blockLight(val.blockLight() + 10);
                            if (val.texturePath().equals(TrailInfo.GENERIC_TRAIL_TEXTURE)) {
                                builder.texture(TRAIL_TEXTURE);
                            }
                            return builder.create();
                        }
                        return val;
                    }

                    @Override
                    public boolean shouldRemove() {
                        return container.getExecutor().getSkill(AxemanSkill.this) == null;
                    }
                });
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public List<Object> getTooltipArgsOfScreen(List<Object> list) {
        list.add(String.format("%.0f", this.speedBonus));
        list.add(String.format("%.0f", this.speedMalus));
        list.add(String.format("%.0f", this.maxStaminaBonus));
        list.add(String.format("%.1f", this.staminaRegenBonus));

        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (WeaponCategory category : this.availableWeaponCategories) {
            sb.append(WeaponCategory.ENUM_MANAGER.toTranslated(category));
            if (i < this.availableWeaponCategories.size() - 1) sb.append(", ");
            i++;
        }
        list.add(sb.toString());
        return list;
    }
    private static boolean coveredBySwordmaster(PlayerPatch<?> patch, WeaponCategory category) {
        boolean[] covered = {false};
        patch.getSkillCapability().listSkillContainers().forEach(skillContainer -> {
            if (skillContainer.getSkill() instanceof SwordmasterSkill swordmaster
                    && swordmaster.getAvailableWeaponCategories().contains(category)) {
                covered[0] = true;
            }
        });
        return covered[0];
    }
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onIconCreate(WeaponCategoryIconRegisterEvent icon) {

        icon.registerCategory(BetterAxeWeaponCategories.DA_AXE,new ItemStack(Items.DIAMOND_AXE));
    }
    @Override
    public Set<WeaponCategory> getAvailableWeaponCategories() {
        return this.availableWeaponCategories;
    }
}