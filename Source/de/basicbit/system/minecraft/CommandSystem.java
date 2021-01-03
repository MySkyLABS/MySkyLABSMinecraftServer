package de.basicbit.system.minecraft;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import de.basicbit.system.ClassFinder;
import de.basicbit.system.minecraft.tasks.TaskManager;

public class CommandSystem extends Utils {

    private static ArrayList<Command> commands = new ArrayList<Command>();

    public static void init() {
		try {
			for (Class<?> c : ClassFinder.findClasses("de.basicbit.system.minecraft.commands")) {
				if (!c.getName().contains("$")) {
                    try {
                        register((Command) c.getConstructors()[0].newInstance());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }

    public static List<Command> getCommands() {
        return commands;
    }

    public static void register(Command cmd) {
        commands.add(cmd);
		log("Register command: " + cmd.getClass().getName());
    }

    public static boolean onCommand(Player p, String cmd, String[] args, String allArgs) {
        for (Command command : commands) {
            if (command.hasPermissions(p) && command.getNames().contains(cmd.toLowerCase())) {

                if (command.isAsync()) {
                    TaskManager.runAsyncTask(new Runnable() {
                    
                        @Override
                        public void run() {
                            CommandResult result = command.onCommand(p, args, allArgs);
                            String msg = result.getMessage();
            
                            if (msg != null) {
                                sendMessage(p, msg);
                            } else if (result == CommandResult.InvalidUsage) {
                                sendMessage(p, "§cDu hast den Command falsch eingegeben.");
                                sendMessage(p, "§cBitte benutze: §e/" + cmd.toUpperCase().toCharArray()[0] + cmd.toLowerCase().substring(1) + " " + command.getUsage(p));
                            }
                        }
                    });
                } else {
                    CommandResult result = command.onCommand(p, args, allArgs);
                    String msg = result.getMessage();
    
                    if (msg != null) {
                        sendMessage(p, msg);
                    } else if (result == CommandResult.InvalidUsage) {
                        sendMessage(p, "§cDu hast den Command falsch eingegeben.");
                        sendMessage(p, "§cBitte benutze: §e/" + cmd.toUpperCase().toCharArray()[0] + cmd.toLowerCase().substring(1) + " " + command.getUsage(p));
                    }
                }

                return true;
            } else {
                continue;
            }
        }

        sendMessage(p, "§cDer Command §e/" + (cmd.length() == 0 ? "" : cmd.toUpperCase().toCharArray()[0] + cmd.toLowerCase().substring(1)) + "§c wurde nicht gefunden.");

        return !canPlayerRunVanillaCommands(p);
    }
}
