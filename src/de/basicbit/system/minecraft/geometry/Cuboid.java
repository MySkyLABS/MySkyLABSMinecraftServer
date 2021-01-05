package de.basicbit.system.minecraft.geometry;

import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;

public class Cuboid {
    private Point3D middle;
    private Size3D size;

    public Cuboid(Point3D middle, Size3D size) {
        this.middle = middle;
        this.size = size;
    }

    public static Cuboid getFromBounds(Entity e) {
        Size3D size = Size3D.getFromHitBox(e);
        Point3D point = new Point3D(e.getLocation());
        if (!(e instanceof ItemFrame)) {
            point.setY(point.getY() + (size.getYSize() / 2));
        }
        return new Cuboid(point, size);
    }

    public static Cuboid getFromItemFrameWithMapBounds(ItemFrame e) {
        Size3D size = Size3D.getFromHitBox(e);
        size.setYSize(1);
        if (size.getXSize() < size.getZSize()) {
            size.setZSize(1);
        } else {
            size.setXSize(1);
        }
        return new Cuboid(new Point3D(e.getLocation()), size);
    }

    public Point3D getMiddle() {
        return middle;
    }

    public void setMiddle(Point3D middle) {
        this.middle = middle;
    }

    public Size3D getSize() {
        return size;
    }

    public void setSize(Size3D size) {
        this.size = size;
    }

    public boolean contains(Point3D point) {
        return GeometryHelper.isNumberBetween(point.getX(), getMiddleX() - (getXSize() / 2), getMiddleX() + (getXSize() / 2))
            && GeometryHelper.isNumberBetween(point.getY(), getMiddleY() - (getYSize() / 2), getMiddleY() + (getYSize() / 2))
            && GeometryHelper.isNumberBetween(point.getZ(), getMiddleZ() - (getZSize() / 2), getMiddleZ() + (getZSize() / 2));
    }

    public double getMiddleX() {
        return middle.getX();
    }

    public double getMiddleY() {
        return middle.getY();
    }

    public double getMiddleZ() {
        return middle.getZ();
    }

    public float getMiddleXFloat() {
        return middle.getXFloat();
    }

    public float getMiddleYFloat() {
        return middle.getYFloat();
    }

    public float getMiddleZFloat() {
        return middle.getZFloat();
    }

    public void setMiddleX(double d) {
        middle.setX(d);
    }

    public void setMiddleY(double d) {
        middle.setY(d);
    }

    public void setMiddleZ(double d) {
        middle.setZ(d);
    }

    public double getXSize() {
        return size.getXSize();
    }

    public double getYSize() {
        return size.getYSize();
    }

    public double getZSize() {
        return size.getZSize();
    }

    public float getXSizeFloat() {
        return size.getXSizeFloat();
    }

    public float getYSizeFloat() {
        return size.getYSizeFloat();
    }

    public float getZSizeFloat() {
        return size.getZSizeFloat();
    }

    public void setXSize(double d) {
        size.setXSize(d);
    }

    public void setYSize(double d) {
        size.setYSize(d);
    }

    public void setZSize(double d) {
        size.setZSize(d);
    }
}