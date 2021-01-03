package de.basicbit.system.minecraft.usershop;

import java.util.UUID;

import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import de.basicbit.system.minecraft.Gender;
import de.basicbit.system.minecraft.Group;
import de.basicbit.system.minecraft.Utils;
import net.minecraft.server.v1_8_R1.NBTTagCompound;

@SuppressWarnings("deprecation")
public class UserShopItem {
    private int id = 0;
    private int count = 1;
    private short subId = 0;
    private String tag;

    private UUID ownerId = UUID.randomUUID();
    private String name = "";
    private Group group = Group.MEMBER;
    private Gender gender = Gender.MAN;
    private int cost = 1;
    private int slot = 1;

    private UserShopItem() { }

    @Override
    public int hashCode() {
        return ownerId.hashCode() * 3 * slot * 5 * cost * 7;
    }

    public UserShopItem(ItemStack is, UUID ownerId, Group group, String name, int cost, int slot, Gender gender) {
        id = is.getTypeId();
        count = is.getAmount();
        subId = is.getDurability();

        tag = "";

        net.minecraft.server.v1_8_R1.ItemStack nmsIs = CraftItemStack.asNMSCopy(is);
        if (nmsIs != null) {
            NBTTagCompound nbtTag = nmsIs.getTag();
            if (nbtTag != null) {
                tag = nbtTag.toString();
            }
        }

        this.ownerId = ownerId;
        this.group = group;
        this.name = name;
        this.gender = gender;

        this.cost = cost;
        this.slot = slot;
    }

    public static UserShopItem parse(String str) {
        try {
            UserShopItem result = new UserShopItem();
            String[] data = Utils.decodeBase64(str).split("§;");
            result.id = Integer.parseInt(data[0]);
            result.count = Integer.parseInt(data[1]);
            result.subId = Short.parseShort(data[2]);
            result.ownerId = Utils.getUUIDFromTrimmed(data[3]);
            result.cost = Integer.parseInt(data[4]);
            result.slot = Integer.parseInt(data[5]);
            result.name = data[6];
            result.group = Group.valueOf(data[7]);
            result.gender = Gender.valueOf(data[8]);

            if (data.length == 9) {
                result.tag = "";
            } else {
                result.tag = data[9];
            }

            return result;
        } catch (Exception e) {
            return new UserShopItem();
        }
    }

    public String getData() {
        return Utils.encodeBase64(id + "§;" + count + "§;" + subId + "§;" + Utils.toTrimmed(ownerId) + "§;" + cost + "§;" + slot + "§;" + name + "§;" + group + "§;" + gender.toString() + "§;" + tag);
    }

    public ItemStack getItemStack() {
        ItemStack is = new ItemStack(id, count, subId);

        if (hasTag()) {
            net.minecraft.server.v1_8_R1.ItemStack nmsIs = CraftItemStack.asNMSCopy(is);
            nmsIs.setTag(Utils.parseNbt(tag));
            is = CraftItemStack.asBukkitCopy(nmsIs);
        }

        return is;
    }

    public Gender getOwnerGender() {
        return gender;
    }

    public String getChatName() {
        return Utils.getChatName(group, gender, name);
    }

    public String getOwnerName() {
        return name;
    }

    public Group getOwnerGroup() {
        return group;
    }

    public boolean hasTag() {
        return tag != null && tag != "";
    }

    public NBTTagCompound getTag() {
        return Utils.parseNbt(tag);
    }

    public int getCost() {
        return cost;
    }

    public int getSlot() {
        return slot;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setCost(int i) {
        cost = i;
        update();
    }

    private void update() {
        UserShopOffers.setOffer(this);
    }
}