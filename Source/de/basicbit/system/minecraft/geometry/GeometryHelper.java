package de.basicbit.system.minecraft.geometry;

public class GeometryHelper {
    
    public static boolean isNumberBetween(double number, double p1, double p2) {
        if (p1 == p2) {
            return number == p1;
        }

        if (p1 > p2) {
            p1 += p2; 
            p2 = p1 - p2; 
            p1 -= p2;
        }

        return number >= p1 && number <= p2;
    }
}