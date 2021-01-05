package de.basicbit.system.minecraft.questsystem;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.ListenerSystem;
import de.basicbit.system.minecraft.Utils;
import de.basicbit.system.minecraft.questsystem.quests.JoinYourIsland;

@SuppressWarnings("all")
public class QuestSystem {

    private static ArrayList<Quest> quests = new ArrayList<Quest>();

    public static void init() {
        addQuest(new JoinYourIsland());
    }

    public static void addQuest(Quest quest) {
        quest.setId(quests.size());
        Utils.log("Adding quest: " + quest.getName() + " (" + quest.getId() + ")");

        ListenerSystem.register(quest);
        quests.add(quest);
    }

    public static void openMenu(Player p) {

    }

    public static Quest get(Player p) {
        return null;
    }

    private static byte toByte(boolean[] booleans) {

        if (booleans.length != 8) {
            return 0;
        }

        return (byte)((booleans[0] ? 1 << 7 : 0) + (booleans[1] ? 1 << 6 : 0) + (booleans[2] ? 1 << 5 : 0) +
            (booleans[3] ? 1 << 4 : 0) + (booleans[4] ? 1 << 3 : 0) + (booleans[5] ? 1 << 2 : 0) +
            (booleans[6] ? 1 << 1 : 0) + (booleans[7] ? 1 : 0));
    }

    private static byte[] toByteArray(boolean[] booleans) {
        if (booleans.length % 8 == 0) {
            return new byte[0];
        }

        int byteCount = booleans.length / 8;
        byte[] bytes = new byte[byteCount];

        for (int i = 0; i < byteCount; i++) {

        }

        java.util.Random random;

        return null;
    }
}