package de.basicbit.system.minecraft.geometry;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;

import net.minecraft.server.v1_8_R1.AxisAlignedBB;

public class Size3D {
    private double x;
    private double y;
    private double z;

    public Size3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Size3D getFromHitBox(Entity e) {
        AxisAlignedBB axisAlignedBB = ((CraftEntity)e).getHandle().getBoundingBox();
        return new Size3D(
            axisAlignedBB.d - axisAlignedBB.a,
            axisAlignedBB.e - axisAlignedBB.b,
            axisAlignedBB.f - axisAlignedBB.c
        );
    }

    public double getXSize() {
        return x;
    }

    public double getYSize() {
        return y;
    }

    public double getZSize() {
        return z;
    }

    public float getXSizeFloat() {
        return (float)x;
    }

    public float getYSizeFloat() {
        return (float)y;
    }

    public float getZSizeFloat() {
        return (float)z;
    }

    public void setXSize(double d) {
        x = d;
    }

    public void setYSize(double d) {
        y = d;
    }

    public void setZSize(double d) {
        z = d;
    }
}