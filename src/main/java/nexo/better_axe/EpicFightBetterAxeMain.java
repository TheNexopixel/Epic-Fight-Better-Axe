package nexo.better_axe;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.resource.PathPackResources;
import nexo.better_axe.skill.compat.EFXCompat;
import yesman.epicfight.compat.ICompatModule;

import java.nio.file.Path;

@Mod(EpicFightBetterAxeMain.MODID)
public class EpicFightBetterAxeMain {

    public static final String MODID = "better_axe";

    public EpicFightBetterAxeMain(FMLJavaModLoadingContext context) {

        IEventBus bus = context.getModEventBus();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        bus.addListener(this::addPackFindersEvent);
/*
        if(ModList.get().isLoaded(WeaponsOfMinecraft.MODID)) {
            bus.addListener(WoMCompat::buildSkillEvent);
            ICompatModule.loadCompatModule(context, WoMCompat.class);
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> bus.addListener(WoMCompat::regIcon));
        }

        if(ModList.get().isLoaded(EFN.MODID)){
            ICompatModule.loadCompatModule(context, NightfallCompat.class);
        }
 */
        if (ModList.get().isLoaded("epicfightx")) {
            ICompatModule.loadCompatModule(context, EFXCompat.class);
        }




    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    public void addPackFindersEvent(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            Path resourcePath = ModList.get().getModFileById(EpicFightBetterAxeMain.MODID).getFile().findResource("packs/da_override");
            PathPackResources pack = new PathPackResources(ModList.get().getModFileById(EpicFightBetterAxeMain.MODID).getFile().getFileName() + ":" + resourcePath, true, resourcePath);
            Pack.ResourcesSupplier resourcesSupplier = (string) -> pack;
            Pack.Info info = Pack.readPackInfo("da_override", resourcesSupplier);

            if (info != null) {
                event.addRepositorySource((source) ->
                        source.accept(Pack.create("da_override", Component.translatable("pack.da_override.title"), false, resourcesSupplier, info, PackType.SERVER_DATA, Pack.Position.TOP, false, PackSource.SERVER)));
            }
        }

    }
}
