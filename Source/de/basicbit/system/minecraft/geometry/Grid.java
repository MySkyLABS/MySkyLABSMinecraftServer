package de.basicbit.system.minecraft.geometry;

public class Grid {
    private Rotation rotation = new Rotation(0, 0);
    private Point3D center = new Point3D(0, 0, 0);
    private double scale = 1;

    public Grid(Point3D center, Rotation rotation, double scale) {
        this.center = center;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Grid(Point3D center, Rotation rotation) {
        this.center = center;
        this.rotation = rotation;
    }

    public Grid(Point3D center) {
        this.center = center;
    }

    public Point3D fromGridPoint(Point3D point) {
        return center.getDirectionPoint(rotation.add(point.getRotation(new Point3D(0, 0, 0))), center.distance(center.add(point.multiply(scale))) * -1);
    }

    public Point3D fromGridPoint(double x, double y, double z) {
        return fromGridPoint(new Point3D(x, y, z));
    }
}
