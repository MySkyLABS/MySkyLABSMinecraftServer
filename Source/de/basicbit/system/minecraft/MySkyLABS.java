package de.basicbit.system.minecraft;

import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.plugin.java.JavaPlugin;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.JavaListeners;
import de.basicbit.system.PluginClass;
import de.basicbit.system.database.Database;
import de.basicbit.system.database.UserData;
import de.basicbit.system.database.Var;
import de.basicbit.system.minecraft.cases.CaseSystem;
import de.basicbit.system.minecraft.crafting.CraftingSystem;
import de.basicbit.system.minecraft.crafting.listeners.ColorShoesListener;
import de.basicbit.system.minecraft.gui.Gui;
import de.basicbit.system.minecraft.npc.NPC;
import de.basicbit.system.minecraft.skyblock.SkyBlock;
import de.basicbit.system.minecraft.skypvp.SkyPvP;
import de.basicbit.system.minecraft.usershop.UserShop;

public class MySkyLABS extends Utils {

	public static String name = "§3MySkyLABS";
	public static String prefix = "§6[" + name + "§6] §7";
	public static JavaPlugin plugin = PluginClass.instance;
	public static boolean disableLog = false;
	public static boolean debugMode = false;

	public static void onPreStart() {
		ListenerSystem.preInit();
		log(" \n--- JavaListeners -------------------------------------------------------------------\n ");
		JavaListeners.registerAllJavaListeners();

		log(" \n--- DiscordBot ----------------------------------------------------------------------\n ");
		DiscordBot.init();
	}

	public static void onStart() {
		log(" \n--- UserData ------------------------------------------------------------------------\n ");
		UserData.init();

		log(" \n--- ListenerSystem ------------------------------------------------------------------\n ");
		ListenerSystem.init();

		log(" \n--- CommandSystem -------------------------------------------------------------------\n ");
		CommandSystem.init();

		log(" \n--- PlayerListSystem ----------------------------------------------------------------\n ");
		PlayerListSystem.init();

		log(" \n--- ScoreboardSystem ----------------------------------------------------------------\n ");
		ScoreboardSystem.init();

		log(" \n--- CaseSystem ----------------------------------------------------------------------\n ");
		CaseSystem.init();

		log(" \n--- CraftingSystem ------------------------------------------------------------------\n ");
		CraftingSystem.init();

		log(" \n--- PerkSystem ----------------------------------------------------------------------\n ");
		PerkSystem.init();

		log(" \n--- ItemClearTimer ------------------------------------------------------------------\n ");
		ItemClearTimer.init();

		log(" \n--- SkyBlock ------------------------------------------------------------------------\n ");
		SkyBlock.init();

		log(" \n--- BonusTimer ----------------------------------------------------------------------\n ");
		BonusTimer.init();

		log(" \n--- Troll ---------------------------------------------------------------------------\n ");
		Troll.init();

		log(" \n--- SkyPvP --------------------------------------------------------------------------\n ");
		SkyPvP.init();

		log(" \n--- Gui -----------------------------------------------------------------------------\n ");
		Gui.init();

		log(" \n--- UserShop ------------------------------------------------------------------------\n ");
		UserShop.init();

		log(" \n--- ServerTimeBroadcast -------------------------------------------------------------\n ");
		ServerTimeBroadcast.init();

		log(" \n--- ReallifeTime --------------------------------------------------------------------\n ");
		ReallifeTime.init();

		log(" \n--- UpdateRestartTimer --------------------------------------------------------------\n ");
		UpdateRestartTimer.init();

		log(" \n--- AntiLagSystem -------------------------------------------------------------------\n ");
		AntiLagSystem.init();

		log(" \n--- ColorShoes ----------------------------------------------------------------------\n ");
		ColorShoesListener.init();

		//log(" \n--- ColorHelmet ---------------------------------------------------------------------\n ");
		//ColorHelmetListener.init();

		//log(" \n--- CustomCrafting ------------------------------------------------------------------\n ");
		//CustomCrafting.init();
		
		log(" \n-------------------------------------------------------------------------------------\n ");

	}

	public static void onPostStart() {
		log(" \n--- SaveReloadSystem ----------------------------------------------------------------\n ");
		SaveReloadSystem.init();

		log(" \n--- HologramSystem ------------------------------------------------------------------\n ");
		HologramSystem.init();

		log(" \n--- NPC -----------------------------------------------------------------------------\n ");
		NPC.init();

		log(" \n--- KnockIt -------------------------------------------------------------------------\n ");
		KnockIt.init();

		log(" \n--- WorldMobCleaner -----------------------------------------------------------------\n ");
		WorldMobCleaner.init();

		log(" \n-------------------------------------------------------------------------------------\n ");

		//TODO: ts3server://myskylabs.de?port=9987&nickname=BasicBit
	}
	
	public static void onExit() {			
		for (Player p : getPlayers()) {
			Wolf wolf = GlobalValues.dogPets.get(p.getUniqueId());
			if (wolf != null && !wolf.isDead()) {
				wolf.remove();
				GlobalValues.dogPets.remove(p.getUniqueId(), wolf);
			}
		}

		NPC.destroyAll();
		UserShop.close();

		Database.setVar(Var.debugMode, debugMode ? "1" : "0");
	}
	
	public static JavaPlugin getSystemPlugin() {
		return plugin;
	}
}
