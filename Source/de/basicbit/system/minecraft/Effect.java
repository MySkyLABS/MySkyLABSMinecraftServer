package de.basicbit.system.minecraft;

import org.bukkit.potion.PotionEffectType;

public class Effect {
    
    public PotionEffectType type;

    public int lvl;

    public Effect(final PotionEffectType type, final int lvl) {
        this.type = type;
        this.lvl = lvl;
    }
}