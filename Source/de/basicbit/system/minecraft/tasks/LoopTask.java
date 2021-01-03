package de.basicbit.system.minecraft.tasks;

import de.basicbit.system.minecraft.Utils;

public abstract class LoopTask extends Utils implements Runnable {

    private String name;
    private boolean sync;
    private int lastDuration = -1;
    private int interval = -1;
    private int id = -1;

    protected abstract void onRun() throws Exception;

    public LoopTask(String name, boolean sync) {
        this.name = name;
        this.sync = sync;
    }

    public String getName() {
        return name;
    }

    public boolean isSync() {
        return sync;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getLastDuration() {
        return lastDuration;
    }

    public void setIntervalTicks(int i) {
        interval = i;
    }

    public int getIntervalTicks() {
        return interval;
    }

    public int getIntervalMillis() {
        return interval * 50;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();

        try {
            onRun();
        } catch (Exception e) {
            e.printStackTrace();
        }

        lastDuration = (int)(System.currentTimeMillis() - startTime);

        if (lastDuration >= TaskManager.getMinimalLagMillis() && sync) {
            log("§cLag: §e" + lastDuration + "ms");
            log("§cAusgelöst in: §e" + name);
        }
    }
}