/**
    Copyright (C) 2017 by jabelar

    This file is part of jabelar's Minecraft Forge modding examples; as such,
    you can redistribute it and/or modify it under the terms of the GNU
    General Public License as published by the Free Software Foundation,
    either version 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    For a copy of the GNU General Public License see <http://www.gnu.org/licenses/>.
*/

package opcode_.austinmod;

import opcode_.austinmod.client.gui.GuiHandler;
import opcode_.austinmod.proxy.IProxy;
import opcode_.austinmod.utilities.Utilities;
import opcode_.austinmod.worldgen.structures.villages.MapGenVillageCloud;
import opcode_.austinmod.worldgen.structures.villages.VillageHouseCloud;
import opcode_.austinmod.init.ModAdvancements;
import opcode_.austinmod.init.ModBiomes;
import opcode_.austinmod.init.ModBlocks;
import opcode_.austinmod.init.ModConfig;
import opcode_.austinmod.init.ModFluids;
import opcode_.austinmod.init.ModItems;
import opcode_.austinmod.init.ModNetworking;
import opcode_.austinmod.init.ModProfessions;
import opcode_.austinmod.init.ModTileEntities;
import opcode_.austinmod.init.ModTriggers;
import opcode_.austinmod.init.ModWorldGen;

import net.minecraft.stats.StatBasic;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

// TODO: Auto-generated Javadoc
/**
 * This is the main file for the mod, as it has the mod annotation
 */
@Mod(   modid = MainMod.MODID,
        name = MainMod.MODNAME,
        version = MainMod.MODVERSION,
        guiFactory = "opcode_." + MainMod.MODID + ".client.gui.GuiFactory",
        acceptedMinecraftVersions = "[1.12]",
        updateJSON = "https://raw.githubusercontent.com/nu-musketeers/austin-mod/master/src/main/resources/versionChecker.json"
        )
public class MainMod
{
    public static final String MODID = "austinmod";
    public static final String MODNAME = "Austin's Mod";
    public static final String MODVERSION = "1.0.0";
    public static final String MODDESCRIPTION = "Describe your mod here";
    public static final String MODAUTHOR = "opcode_";
    public static final String MODCREDITS = "";
    public static final String MODURL = "";
    public static final String MODLOGO = "modconfigraphic.png";

    // instantiate advancements and stats
    public static StatBasic deconstructedItemsStat;

    static
    {
        FluidRegistry.enableUniversalBucket();
    }

    // instantiate the mod
    @Instance(MODID)
    public static MainMod instance;

    // Says where the client and server 'proxy' code is loaded.
    @SidedProxy(clientSide = "opcode_.austinmod.proxy.ClientProxy",
            serverSide = "opcode_.austinmod.proxy.ServerProxy")
    public static IProxy proxy;

    /**
     * Pre-Initialization FML Life Cycle event handling method which is automatically
     * called by Forge. It must be annotated as an event handler.
     *
     * @param event the event
     */
    @EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the GameRegistry."
    public void preInit(FMLPreInitializationEvent event)
    {
        // DEBUG
        System.out.println("preInit() " + event.getModMetadata().name);

        Utilities.setModInfo(event);
        ModConfig.initConfig(event); // load configuration before doing anything else that may be controlled by it.
        // register stuff
        ModTileEntities.registerTileEntities();
        ModFluids.registerFluids();
        ModNetworking.registerSimpleNetworking();
        ModWorldGen.registerDimensions();
        // VillagerRegistry.instance().registerVillagerId(10);
        // VillagerRegistry.instance().registerVillageTradeHandler(10, new VillageTradeHandlerMagicBeans());
        // VillagerRegistry.getRegisteredVillagers();

        proxy.preInit(event);
    }

    /**
     * Initialization FML Life Cycle event handling method which is automatically
     * called by Forge. It must be annotated as an event handler.
     *
     * @param event the event
     */
    @EventHandler
    // Do your mod setup. Build whatever data structures you care about.
    // Register network handlers
    public void init(FMLInitializationEvent event)
    {
        // DEBUG
        System.out.println("init()");
        
        // DEBUG
        System.out.println("Registering gui handler");
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        ModAdvancements.registerAdvancements();
        ModWorldGen.registerWorldGenerators();
        ModBiomes.initBiomeManagerAndDictionary();
        ModBlocks.registerOreDictionaryEntries();
        ModItems.registerOreDictionaryEntries();
        MapGenStructureIO.registerStructure(MapGenVillageCloud.Start.class, MODID+":cloud_village");
        MapGenStructureIO.registerStructureComponent(VillageHouseCloud.class, MODID+":cloud_house");
        VillagerRegistry.instance().registerVillageCreationHandler(ModWorldGen.CLOUD_VILLAGE_HANDLER);
        ModProfessions.associateCareersAndTrades();
        ModTriggers.registerTriggers();
        
        MinecraftForge.TERRAIN_GEN_BUS.register(new TerrainGenEventHandler());
        MinecraftForge.ORE_GEN_BUS.register(new OreGenEventHandler());
        
        proxy.init(event);
    }

    /**
     * Post-Initialization FML Life Cycle event handling method which is automatically
     * called by Forge. It must be annotated as an event handler.
     *
     * @param event the event
     */
    @EventHandler
    // postInit "Handle interaction with other mods, complete your setup based on this."
    public void postInit(FMLPostInitializationEvent event)
    {
        // DEBUG
        System.out.println("postInit()");

        proxy.postInit(event);
    }


    /**
     * Fml life cycle.
     *
     * @param event the event
     */
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        // DEBUG
        System.out.println("Server starting");

        proxy.serverStarting(event);
    }
}
