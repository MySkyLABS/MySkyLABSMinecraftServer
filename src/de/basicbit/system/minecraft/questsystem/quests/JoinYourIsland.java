package de.basicbit.system.minecraft.questsystem.quests;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.questsystem.Quest;

public class JoinYourIsland extends Quest {

    @Override
    public String getText() {
        return "§2§lErstelle eine Insel\n\n§7Gehe zum §2§lSkyBlock§7-NPC oder\n§7gebe §e/Is §7ein um dir\n§7deine Insel zu erstellen.";
    }

    @Override
    public void confirm(Player p) {
        
    }
}