package de.basicbit.system.minecraft.skyblock.worldgenerator;

import org.bukkit.plugin.java.JavaPlugin;

public class PluginClass extends JavaPlugin {

    /*

    @Override
    public void onEnable() {
        MySkyLABS.plugin = this;

        WorldGenerator.init();

        ServerCommunicationSystem.init();
        ServerCommunicationSystem.sendCommand("connected server-worldgenerator");

        ListenerSystem.register(new Listener() {
            @EventHandler
            public void onCommunicationMessage(ServerCommunicationSystemMessageEvent e) {
                String msg = e.getMessage().toLowerCase();
        
                if (msg.startsWith("map")) {
                    if (msg.startsWith("map create ")) {
                        String[] args = msg.substring(11).split(";");
                        UUID joiningUser = Utils.getUUIDFromTrimmed(args[0]);
                        String worldName = args[1];
        
                        SkyBlock.createWorld(joiningUser, Utils.getUUIDFromSkyBlockWorldName(worldName), Utils.getNumberFromSkyBlockWorldName(worldName), true);
                        
                        TaskManager.runSyncTaskLater("WorldGeneratorPluginClassMvRemoveWorld", new Runnable() {
        
                            @Override
                            public void run() {
                                SkyBlock.mvRemoveWorld(worldName);
                            }
        
                        }, 95);
                        
                        TaskManager.runSyncTaskLater("WorldGeneratorPluginClassMapCreatedMessage", new Runnable() {
        
                            @Override
                            public void run() {
                                ServerCommunicationSystem.sendCommand("map created " + Utils.toTrimmed(joiningUser) + ";" + worldName);
                            }
        
                        }, 100);
                    }

                    if (msg.startsWith("map clear")) {
                        for (World w : Bukkit.getWorlds()) {
                            if (!w.getName().contentEquals("world")) {
                                SkyBlock.mvRemoveWorld(w.getName());
                                SkyBlock.deleteFolder(new File("./" + w.getName()).toPath());
                            }
                        }
                    }
                }
            }
        });
    }

    */
}