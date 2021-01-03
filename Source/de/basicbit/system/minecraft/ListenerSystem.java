package de.basicbit.system.minecraft;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.UnknownDependencyException;

import de.basicbit.system.ClassFinder;

public class ListenerSystem implements PluginManager {

	private final PluginManager handle;

	public ListenerSystem(PluginManager handle) {
		this.handle = handle;
	}

	public static void preInit() {
		try {
			CraftServer server = (CraftServer) Bukkit.getServer();
			Field pluginManagerField = CraftServer.class.getDeclaredField("pluginManager");
            pluginManagerField.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
			modifiersField.setInt(pluginManagerField, pluginManagerField.getModifiers() & ~Modifier.FINAL);
			pluginManagerField.set(server, new ListenerSystem(Bukkit.getPluginManager()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void init() {
		try {
			ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

			classes.addAll(ClassFinder.findClasses("de.basicbit.system.minecraft.listeners"));
			classes.addAll(ClassFinder.findClasses("de.basicbit.system.minecraft.crafting.listeners"));
			classes.addAll(ClassFinder.findClasses("de.basicbit.system.minecraft.skyblock.listeners"));
			classes.addAll(ClassFinder.findClasses("de.basicbit.system.minecraft.skypvp.listeners"));

			for (Class<?> c : classes) {
				if (!c.getName().contains("$")) {
					try {
						register((Listener) c.getConstructors()[0].newInstance());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void register(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, MySkyLABS.getSystemPlugin());
		Utils.log("Register listener: " + listener.getClass().getName());
	}

	@Override
	public void addPermission(Permission arg0) {
		handle.addPermission(arg0);
	}

	@Override
	public void callEvent(Event event) throws IllegalStateException {
		if (event.isAsynchronous()) {
			if (Thread.holdsLock(this)) {
				throw new IllegalStateException(event.getEventName() + " cannot be triggered asynchronously from inside synchronized code.");
			}

			if (Bukkit.getServer().isPrimaryThread()) {
				throw new IllegalStateException(event.getEventName() + " cannot be triggered asynchronously from primary server thread.");
			}

			this.fireEvent(event);
		} else {
			synchronized (this) {
				this.fireEvent(event);
			}
		}
	}

	private void fireEvent(Event event) {
		HandlerList handlers = event.getHandlers();
		RegisteredListener[] listeners = handlers.getRegisteredListeners();

		for (int var6 = 0; var6 < listeners.length; ++var6) {
			RegisteredListener registration = listeners[var6];
			if (registration.getPlugin().isEnabled()) {
				try {
					registration.callEvent(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void clearPlugins() {
		handle.clearPlugins();
	}

	@Override
	public void disablePlugin(Plugin arg0) {
		handle.disablePlugin(arg0);
	}

	@Override
	public void disablePlugins() {
		handle.disablePlugins();
	}

	@Override
	public void enablePlugin(Plugin arg0) {
		handle.enablePlugin(arg0);
	}

	@Override
	public Set<Permissible> getDefaultPermSubscriptions(boolean arg0) {
		return handle.getDefaultPermSubscriptions(arg0);
	}

	@Override
	public Set<Permission> getDefaultPermissions(boolean arg0) {
		return handle.getDefaultPermissions(arg0);
	}

	@Override
	public Permission getPermission(String arg0) {
		return handle.getPermission(arg0);
	}

	@Override
	public Set<Permissible> getPermissionSubscriptions(String arg0) {
		return handle.getPermissionSubscriptions(arg0);
	}

	@Override
	public Set<Permission> getPermissions() {
		return handle.getPermissions();
	}

	@Override
	public Plugin getPlugin(String arg0) {
		return handle.getPlugin(arg0);
	}

	@Override
	public Plugin[] getPlugins() {
		return handle.getPlugins();
	}

	@Override
	public boolean isPluginEnabled(String arg0) {
		return handle.isPluginEnabled(arg0);
	}

	@Override
	public boolean isPluginEnabled(Plugin arg0) {
		return handle.isPluginEnabled(arg0);
	}

	@Override
	public Plugin loadPlugin(File arg0) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException {
		return handle.loadPlugin(arg0);
	}

	@Override
	public Plugin[] loadPlugins(File arg0) {
		return handle.loadPlugins(arg0);
	}

	@Override
	public void recalculatePermissionDefaults(Permission arg0) {
		handle.recalculatePermissionDefaults(arg0);
	}

	@Override
	public void registerEvent(Class<? extends Event> arg0, org.bukkit.event.Listener arg1, EventPriority arg2, EventExecutor arg3, Plugin arg4) {
		handle.registerEvent(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void registerEvent(Class<? extends Event> arg0, org.bukkit.event.Listener arg1, EventPriority arg2, EventExecutor arg3, Plugin arg4, boolean arg5) {
		handle.registerEvent(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void registerEvents(org.bukkit.event.Listener arg0, Plugin arg1) {
		handle.registerEvents(arg0, arg1);
	}

	@Override
	public void registerInterface(Class<? extends PluginLoader> arg0) throws IllegalArgumentException {
		handle.registerInterface(arg0);
	}

	@Override
	public void removePermission(Permission arg0) {
		handle.removePermission(arg0);
	}

	@Override
	public void removePermission(String arg0) {
		handle.removePermission(arg0);
	}

	@Override
	public void subscribeToDefaultPerms(boolean arg0, Permissible arg1) {
		handle.subscribeToDefaultPerms(arg0, arg1);
	}

	@Override
	public void subscribeToPermission(String arg0, Permissible arg1) {
		handle.subscribeToPermission(arg0, arg1);
	}

	@Override
	public void unsubscribeFromDefaultPerms(boolean arg0, Permissible arg1) {
		handle.unsubscribeFromDefaultPerms(arg0, arg1);
	}

	@Override
	public void unsubscribeFromPermission(String arg0, Permissible arg1) {
		handle.unsubscribeFromPermission(arg0, arg1);
	}

	@Override
	public boolean useTimings() {
		return handle.useTimings();
	}
}
