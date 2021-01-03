package de.basicbit.system.minecraft.geometry;

import org.bukkit.Location;

public class Rotation {
    private double yaw;
    private double pitch;

    public Rotation(double yaw, double pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Rotation(Location location) {
        this.yaw = (double)location.getYaw();
        this.pitch = (double)location.getPitch();
    }

    public double getYaw() {
        return yaw;
    }

    public double getPitch() {
        return pitch;
    }

    public float getYawFloat() {
        return (float)yaw;
    }

    public float getPitchFloat() {
        return (float)pitch;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    public Rotation add(Rotation rotation) {
        return new Rotation(this.yaw + rotation.yaw, this.pitch + rotation.pitch);
    }
}
