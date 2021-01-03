package de.basicbit.system.minecraft;

import java.util.Calendar;

import de.basicbit.system.minecraft.tasks.TaskManager;

public class UpdateRestartTimer extends Utils {

    public static void init() {
        TaskManager.runAsyncLoop("UpdateRestartTimer", new Runnable() {

            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();

                if (calendar.get(Calendar.HOUR_OF_DAY) == 4 && calendar.get(Calendar.MINUTE) == 50) {
                    sendMessageToAllPlayers("§4§lDER SERVER STARTET IN 10 MINUTEN NEU!");
                    safeBackup();

                    TaskManager.runAsyncTaskLater(new Runnable() {
                        @Override
                        public void run() {
                            sendMessageToAllPlayers("§4§lDER SERVER STARTET IN 5 MINUTEN NEU!");
                        }
                    }, ((20 * 60 * 10) - (20 * 60 * 5)));

                    TaskManager.runAsyncTaskLater(new Runnable() {
                        @Override
                        public void run() {
                            sendMessageToAllPlayers("§4§lDER SERVER STARTET IN 1 MINUTE NEU!");
                        }
                    }, ((20 * 60 * 10) - (20 * 60)));

                    TaskManager.runAsyncTaskLater(new Runnable() {
                        @Override
                        public void run() {
                            sendMessageToAllPlayers("§4§lDER SERVER STARTET IN 10 SEKUNDEN NEU!");
                        }
                    }, ((20 * 60 * 10) - (20 * 10)));

                    TaskManager.runAsyncTaskLater(new Runnable() {
                        @Override
                        public void run() {
                            sendMessageToAllPlayers("§4§lDER SERVER STARTET IN 9 SEKUNDEN NEU!");
                        }
                    }, ((20 * 60 * 10) - (20 * 9)));

                    TaskManager.runAsyncTaskLater(new Runnable() {
                        @Override
                        public void run() {
                            sendMessageToAllPlayers("§4§lDER SERVER STARTET IN 8 SEKUNDEN NEU!");
                        }
                    }, ((20 * 60 * 10) - (20 * 8)));

                    TaskManager.runAsyncTaskLater(new Runnable() {
                        @Override
                        public void run() {
                            sendMessageToAllPlayers("§4§lDER SERVER STARTET IN 7 SEKUNDEN NEU!");
                        }
                    }, ((20 * 60 * 10) - (20 * 7)));

                    TaskManager.runAsyncTaskLater(new Runnable() {
                        @Override
                        public void run() {
                            sendMessageToAllPlayers("§4§lDER SERVER STARTET IN 6 SEKUNDEN NEU!");
                        }
                    }, ((20 * 60 * 10) - (20 * 6)));

                    TaskManager.runAsyncTaskLater(new Runnable() {
                        @Override
                        public void run() {
                            sendMessageToAllPlayers("§4§lDER SERVER STARTET IN 5 SEKUNDEN NEU!");
                        }
                    }, ((20 * 60 * 10) - (20 * 5)));

                    TaskManager.runAsyncTaskLater(new Runnable() {
                        @Override
                        public void run() {
                            sendMessageToAllPlayers("§4§lDER SERVER STARTET IN 4 SEKUNDEN NEU!");
                        }
                    }, ((20 * 60 * 10) - (20 * 4)));

                    TaskManager.runAsyncTaskLater(new Runnable() {
                        @Override
                        public void run() {
                            sendMessageToAllPlayers("§4§lDER SERVER STARTET IN 3 SEKUNDEN NEU!");
                        }
                    }, ((20 * 60 * 10) - (20 * 3)));

                    TaskManager.runAsyncTaskLater(new Runnable() {
                        @Override
                        public void run() {
                            sendMessageToAllPlayers("§4§lDER SERVER STARTET IN 2 SEKUNDEN NEU!");
                        }
                    }, ((20 * 60 * 10) - (20 * 2)));

                    TaskManager.runAsyncTaskLater(new Runnable() {
                        @Override
                        public void run() {
                            sendMessageToAllPlayers("§4§lDER SERVER STARTET IN EINER SEKUNDE NEU!");
                        }
                    }, ((20 * 60 * 10) - (20 * 1)));

                    TaskManager.runSyncTaskLater("RestartUpdate", new Runnable() {
                        @Override
                        public void run() {
                            Utils.stopServer();
                        }
                    }, ((20 * 60 * 10)));
                }
            }
        }, 1100);
    }        
}