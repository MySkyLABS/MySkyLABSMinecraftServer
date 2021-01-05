package de.basicbit.system.minecraft.dynamic;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DynamicObject {
    private HashMap<Integer, Object> entries = new HashMap<Integer, Object>();

    public DynamicObject(FieldAndObject... fieldAndObjects) {
        for (FieldAndObject fieldAndObject : fieldAndObjects) {
            set(fieldAndObject.field, fieldAndObject.obj);
        }
    }

    public boolean contains(int key) {
        return entries.containsKey(key);
    }

    public void set(int key, Object obj) {
        entries.put(key, obj);
    }

    public Object get(int key) {
        return entries.get(key);
    }

    public int getInt(int key) {
        return (int) get(key);
    }

    public ItemStack getItemStack(int key) {
        return (ItemStack) get(key);
    }

    public Player getPlayer(int key) {
        return (Player) get(key);
    }

    public boolean getBoolean(int key) {
        if (contains(key)) {
            return (boolean) get(key);
        } else {
            return false;
        }
    }

    public long getLong(int key) {
        return (long) get(key);
    }

    public double getDouble(int key) {
        return (double) get(key);
    }

    public float getFloat(int key) {
        return (float) get(key);
    }

    public String getString(int key) {
        return (String) get(key);
    }

    public short getShort(int key) {
        return (short) get(key);
    }

    public byte getByte(int key) {
        return (byte) get(key);
    }

    public UUID getUUID(int key) {
        return (UUID) get(key);
    }
}