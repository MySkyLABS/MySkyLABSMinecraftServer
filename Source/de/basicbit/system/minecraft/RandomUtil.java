package de.basicbit.system.minecraft;

import com.sk89q.worldedit.util.command.argument.ArgumentException;

public class RandomUtil {
    
    private static final int multiplier = 0x5DE8DA7E;

    private int seed;
    private int current;

    public RandomUtil(int seed) throws ArgumentException {
        if (seed == 0) {
            seed = Integer.MAX_VALUE / 2;
        }

        this.seed = seed * multiplier;
        current = this.seed * this.seed;
    }

    public int nextInt(int repeats) {
        return current * (seed * repeats);
    }

    public int nextInt(int repeats, int size) {
        return nextInt(repeats) % size;
    }
}