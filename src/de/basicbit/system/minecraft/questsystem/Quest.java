package de.basicbit.system.minecraft.questsystem;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Listener;

public abstract class Quest extends Listener {
    
    private int id;

    public abstract String getText();
    public abstract void confirm(Player p);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        String description = getText();

        if (description.contains("\n")) {
            return description.split("\n")[0];
        } else {
            return description;
        }
    }

    public ArrayList<String> getLore() {
        ArrayList<String> lore = new ArrayList<String>();
        String description = getText();
        
        if (description.contains("\n")) {
            boolean first = true;
            for (String line : description.split("\n")) {
                if (first) {
                    first = !first;
                } else {
                    lore.add(line);
                }
            }
        }

        return lore;
    }
}