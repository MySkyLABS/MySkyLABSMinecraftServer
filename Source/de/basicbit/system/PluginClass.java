package de.basicbit.system;

import org.bukkit.plugin.java.JavaPlugin;

import de.basicbit.system.database.Database;
import de.basicbit.system.database.Var;
import de.basicbit.system.minecraft.ExceptionListener;
import de.basicbit.system.minecraft.MySkyLABS;
import de.basicbit.system.minecraft.tasks.TaskManager;
import de.basicbit.system.teamspeak.Teamspeak;

public class PluginClass extends JavaPlugin {
	
	public static PluginClass instance;
	
	public PluginClass() {
		instance = this;
	}

	@Override
	public void onLoad() {
		MySkyLABS.onPreStart();
	}
	
	@Override
	public void onEnable() {
		ExceptionListener.init();

		Database.onStart();
		MySkyLABS.debugMode = Integer.parseInt(Database.getVar(Var.debugMode)) != 0;

		ServerCommunicationSystem.init();

		if (MySkyLABS.debugMode) {
			ServerCommunicationSystem.sendCommand("connected DevServer");
		} else {
			ServerCommunicationSystem.sendCommand("connected MainServer");
		}

		if (!MySkyLABS.debugMode) {
			Teamspeak.onStart();
		}

		MySkyLABS.onStart();

		TaskManager.runSyncTask("PostStart", new Runnable() {
			@Override
			public void run() {
				MySkyLABS.onPostStart();
			}
		});
	}

	@Override
	public void onDisable() {
		MySkyLABS.onExit();
		Database.onExit();

		if (!MySkyLABS.debugMode) {
			Teamspeak.onExit();
		}
	}
}