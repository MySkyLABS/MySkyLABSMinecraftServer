package de.basicbit.system.minecraft.geometry;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

public class Point3D {
    private double x;
    private double y;
    private double z;

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D(Location location) {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    public Point3D(Block b) {
        Location location = b.getLocation();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    public Vector toVector() {
        return new Vector(x, y, z);
    }

    public Location toLocation(World world) {
        return toVector().toLocation(world);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getXFloat() {
        return (float)x;
    }

    public float getYFloat() {
        return (float)y;
    }

    public float getZFloat() {
        return (float)z;
    }

    public int getXInt() {
        return (int)x;
    }

    public int getYInt() {
        return (int)y;
    }

    public int getZInt() {
        return (int)z;
    }

    public Rotation getRotation(Point3D rotationPoint) {
        double distX = x - rotationPoint.getX();
        double distY = y - rotationPoint.getY();
        double distZ = z - rotationPoint.getZ();
		double var1 = Math.sqrt(distX * distX + distZ * distZ);
        double yaw = (Math.atan2(distZ, distX) / Math.PI * 180.) - 90;
		double pitch = -(Math.atan2(distY, var1) / Math.PI * 180.);
        return new Rotation(yaw, pitch);
    }

    public void setX(double d) {
        x = d;
    }

    public void setY(double d) {
        y = d;
    }

    public void setZ(double d) {
        z = d;
    }

    public double distance(Point3D point) {
        return Math.sqrt(NumberConversions.square(this.x - point.x) + NumberConversions.square(this.y - point.y) + NumberConversions.square(this.z - point.z));
    }

    public Point3D add(double x, double y, double z) {
        return new Point3D(this.x + x, this.y + y, this.z + z);
    }

    public Point3D multiply(Point3D point) {
        return multiply(point.x, point.y, point.z);
    }

    public Point3D multiply(double x, double y, double z) {
        return new Point3D(this.x * x, this.y * y, this.z * z);
    }

    public Point3D multiply(double value) {
        return multiply(value, value, value);
    }

    public Point3D add(Point3D point) {
        return new Point3D(x + point.x, y + point.y, z + point.z);
    }

    public Point3D clone() {
        return new Point3D(x, y, z);
    }

    public Block getBlock(World w) {
        return w.getBlockAt((int) x, (int) y, (int) z);
    }
    
    public ArrayList<Point3D> getBulletPoints(double radius, double precision) {
        ArrayList<Point3D> result = new ArrayList<Point3D>();

        double increment = 180 / precision;
        
        for (double yaw = 0; yaw < 360; yaw += increment) {
            for (double pitch = -90; pitch < 90; pitch += increment) {
            	result.add(getDirectionPoint(yaw, pitch, radius));
            }
        }

        return result;
    }
    
    public Point3D getDirectionPoint(double yaw, double pitch, double radius) {
    	double radiansYaw = Math.toRadians(yaw);
    	double radiansPitch = Math.toRadians(pitch);
    	
        return new Point3D(
            (float)(x - (Math.cos(radiansPitch) * Math.sin(radiansYaw) * radius)),
            (float)(y - (Math.sin(radiansPitch) * radius)),
            (float)(z + (Math.cos(radiansPitch) * Math.cos(radiansYaw) * radius))
        );
    }
    
    public Point3D getDirectionPoint(Rotation rotation, double radius) {
    	return getDirectionPoint(rotation.getYaw(), rotation.getPitch(), radius);
    }
}