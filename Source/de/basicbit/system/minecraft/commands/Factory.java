package de.basicbit.system.minecraft.commands;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import net.minecraft.server.v1_8_R1.MojangsonParser;
import net.minecraft.server.v1_8_R1.NBTTagCompound;
import net.minecraft.server.v1_8_R1.NBTTagByte;
import net.minecraft.server.v1_8_R1.NBTTagDouble;
import net.minecraft.server.v1_8_R1.NBTTagFloat;
import net.minecraft.server.v1_8_R1.NBTTagInt;
import net.minecraft.server.v1_8_R1.NBTTagList;
import net.minecraft.server.v1_8_R1.NBTTagLong;
import net.minecraft.server.v1_8_R1.NBTTagShort;
import net.minecraft.server.v1_8_R1.NBTTagString;

public class Factory extends Command {

    @Override
    public String getUsage(Player p) {
        return "test";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("factory");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isDev(p);
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        NBTTagCompound tag = MojangsonParser.parse(
                "{NoGravity:1b,Small:1,Invisible:1,Invulnerable:1,NoBasePlate:1,ArmorItems:[{},{},{},{id:stone,Count:1}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,0f,0f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}");
        ArrayList<String> tree;
        try {
            tree = getTree(tag, 0);
            for (String s : tree) {
                sendMessage(p, s);
            }
        } catch (Exception e) {
            sendMessage(p, e.getClass().getName() + ":");
            sendMessage(p, e.getMessage());
            for (StackTraceElement ste : e.getStackTrace()) {
                sendMessage(p, ste.getClassName());
            }
        }
        return null;
    }

    public static ArrayList<String> getTree(NBTTagCompound tag, int i) throws Exception {
        ArrayList<String> lines = new ArrayList<String>();

        Field field = NBTTagCompound.class.getDeclaredField("map");
        field.setAccessible(true);
        Map<?, ?> map = (Map<?, ?>) field.get(tag);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("§7|");
        for (int j = 0; j < i; j++) {
            stringBuilder.append(" |");
        }

        String offset = stringBuilder.toString();

        for (Object key : map.keySet()) {
            String name = (String) key;
            Object obj = map.get(key);

            if (obj instanceof NBTTagString) {
                lines.add(offset + " §e" + name + ": \"" + ((NBTTagString) obj).a_().replace("\\", "\\\\").replace("\"", "\\\"") + "§e\"");
                continue;
            }

            if (obj instanceof NBTTagInt) {
                lines.add(offset + " §e" + name + ": " + ((NBTTagInt) obj).d() + " (int)");
                continue;
            }

            if (obj instanceof NBTTagByte) {
                lines.add(offset + " §e" + name + ": " + ((NBTTagByte) obj).f() + " (byte)");
                continue;
            }

            if (obj instanceof NBTTagShort) {
                lines.add(offset + " §e" + name + ": " + ((NBTTagShort) obj).e() + " (short)");
                continue;
            }

            if (obj instanceof NBTTagDouble) {
                lines.add(offset + " §e" + name + ": " + ((NBTTagDouble) obj).g() + " (double)");
                continue;
            }

            if (obj instanceof NBTTagLong) {
                lines.add(offset + " §e" + name + ": " + ((NBTTagLong) obj).c() + " (long)");
                continue;
            }

            if (obj instanceof NBTTagFloat) {
                lines.add(offset + " §e" + name + ": " + ((NBTTagFloat) obj).h() + " (float)");
                continue;
            }

            if (obj instanceof NBTTagCompound) {
                lines.add(offset + " §e" + name + ":");
                lines.addAll(getTree((NBTTagCompound) obj, i + 1));
                continue;
            }

            if (obj instanceof NBTTagList) {
                NBTTagList nbtTagList = (NBTTagList) obj;
                int size = nbtTagList.size();

                lines.add(offset + " §e" + name + ": " + size);

                for (int j = 0; j < size; j++) {
                    NBTTagCompound nbtTagCompound = nbtTagList.get(j);
                    lines.add(offset + " | §e" + j + ":");
                    lines.addAll(getTree(nbtTagCompound, i + 2));
                }

                continue;
            }

            lines.add("§e" + offset + " " + name + ": " + obj.getClass().getSimpleName());
        }
        
        return lines;
    }

    @Override
    public String getDescription(Player p) {
        return "test";
    }
}