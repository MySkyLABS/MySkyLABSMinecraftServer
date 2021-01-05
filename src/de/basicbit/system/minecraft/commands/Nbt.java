package de.basicbit.system.minecraft.commands;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import net.minecraft.server.v1_8_R1.NBTTagByte;
import net.minecraft.server.v1_8_R1.NBTTagCompound;
import net.minecraft.server.v1_8_R1.NBTTagDouble;
import net.minecraft.server.v1_8_R1.NBTTagFloat;
import net.minecraft.server.v1_8_R1.NBTTagInt;
import net.minecraft.server.v1_8_R1.NBTTagList;
import net.minecraft.server.v1_8_R1.NBTTagLong;
import net.minecraft.server.v1_8_R1.NBTTagShort;
import net.minecraft.server.v1_8_R1.NBTTagString;

public class Nbt extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("nbt");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isDev(p);
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length != 0) {
            return CommandResult.InvalidUsage;
        }

        ItemStack is = p.getItemInHand();

        if (is == null) {
            sendMessage(p, "§cDu musst ein Item in die Hand nehmen.");
            return CommandResult.None;
        }

        try {
            net.minecraft.server.v1_8_R1.ItemStack nmsIs = CraftItemStack.asNMSCopy(is);
            NBTTagCompound tag = nmsIs.getTag();
            
            sendMessage(p, "§b§lTag:");
            for (String line : getTree(tag, 0)) {
                sendMessage(p, line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CommandResult.None;
    }

    @Override
    public String getDescription(Player p) {
        return "Gibt dir die NBT-Daten von dem Item in deiner Hand.";
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
}