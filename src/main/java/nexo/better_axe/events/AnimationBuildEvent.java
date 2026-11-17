package nexo.better_axe.events;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nexo.better_axe.EpicFightBetterAxeMain;
import nexo.better_axe.gameasset.animation.AnimationBuilder;
import yesman.epicfight.api.animation.AnimationManager;

@Mod.EventBusSubscriber(modid = EpicFightBetterAxeMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AnimationBuildEvent {
    @SubscribeEvent
    public static void registerAnimation(AnimationManager.AnimationRegistryEvent event) {
        event.newBuilder(EpicFightBetterAxeMain.MODID, AnimationBuilder::BobtheAninimationBuilder);
    }
}
