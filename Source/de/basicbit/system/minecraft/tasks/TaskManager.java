package de.basicbit.system.minecraft.tasks;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import de.basicbit.system.minecraft.MySkyLABS;
import de.basicbit.system.minecraft.Utils;
import net.minecraft.server.v1_8_R1.MinecraftServer;

@SuppressWarnings("deprecation")
public class TaskManager extends Utils {

	private static ArrayList<LoopTask> loops = new ArrayList<LoopTask>();

	public static LoopTask[] getLoops() {
		return loops.toArray(new LoopTask[loops.size()]);
	}

	public static void runSyncTask(String name, Runnable r) {
		runSyncTaskLater(name, r, 0);
	}

	public static void runSyncTaskLater(String name, Runnable r, int ticks) {
		Bukkit.getScheduler().runTaskLater(MySkyLABS.getSystemPlugin(), new Runnable(){
		
			@Override
			public void run() {
				long startTime = System.currentTimeMillis();

				try {
					r.run();
				} catch (Exception e) {
					e.printStackTrace();
				}

				long duration = (int)(System.currentTimeMillis() - startTime);
		
				if (duration >= getMinimalLagMillis()) {
					log("§cLag: §e" + duration + "ms");
					log("§cAusgelöst in: §e" + name);
				}
			}
		}, ticks);
	}

	public static void runSyncLoop(String name, Runnable r, int loopTicks) {
		runSyncLoopLater(name, r, loopTicks, 0);
	}

	public static void runSyncLoopLater(String name, Runnable r, int loopTicks, int ticks) {
		LoopTask loop = new LoopTask(name, true) {
		
			@Override
			protected void onRun() throws Exception {
				r.run();
			}
		};

		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(MySkyLABS.getSystemPlugin(), loop, ticks, loopTicks);
		loop.setIntervalTicks(loopTicks);
		loop.setId(id);

		log("Create sync loop: [name: \"" + name + "\", id: " + id + ", intervalTicks: " + loopTicks + ", waitTicks: " + ticks + "]");

		loops.add(loop);
	}

	public static void runAsyncTask(Runnable r) {
		runAsyncTaskLater(r, 0);
	}

	public static void runAsyncTaskLater(Runnable r, int ticks) {
		Bukkit.getScheduler().runTaskLaterAsynchronously(MySkyLABS.getSystemPlugin(), new Runnable() {

			@Override
			public void run() {
				try {
					r.run();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}, ticks);
	}

	public static void printStackTraceIfSync() {
		if (isSync()) {
			try {
				throw new Exception("SYNC");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean isSync() {
		return Thread.currentThread() == MinecraftServer.getServer().primaryThread;
	}

	public static void runAsyncLoop(String name, Runnable r, int loopTicks) {
		runAsyncLoopLater(name, r, loopTicks, 0);
	}

	public static void runAsyncLoopLater(String name, Runnable r, int loopTicks, int ticks) {
		LoopTask loop = new LoopTask(name, false) {
		
			@Override
			protected void onRun() throws Exception {
				r.run();
			}
		};

		int id = Bukkit.getScheduler().scheduleAsyncRepeatingTask(MySkyLABS.getSystemPlugin(), loop, ticks, loopTicks);
		loop.setIntervalTicks(loopTicks);
		loop.setId(id);

		log("Create async loop: [name: \"" + name + "\", id: " + id + ", intervalTicks: " + loopTicks + ", waitTicks: " + ticks + "]");

		loops.add(loop);
	}

	public static long getMinimalLagMillis() {
		return 100;
	}
}
