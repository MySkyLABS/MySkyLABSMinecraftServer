package de.basicbit.system.minecraft.usershop;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.entity.Player;

import de.basicbit.system.database.Database;
import de.basicbit.system.database.Var;

public class UserShopOffers {
    private static String offers = "";

    public static void init() {
        offers = Database.getVar(Var.offers);
    }

    public static void close() {
        Database.setVar(Var.offers, offers);
    }

    public static void setOffer(UserShopItem shopItem) {
        removeOffer(shopItem.getOwnerId(), shopItem.getSlot());

        offers += ";" + shopItem.getData();

        if (offers.startsWith(";")) {
            offers = offers.substring(1);
        }
    }

    public static boolean removeOffer(UserShopItem shopItem) {
        if (shopItem == null) {
            return false;
        }

        String oldOffers = offers;
        offers = offers.replace(shopItem.getData(), "").replace(";;", ";");

        if (offers.startsWith(";")) {
            offers = offers.substring(1);
        }

        if (offers.endsWith(";")) {
            offers = offers.substring(0, offers.length() - 1);
        }

        return oldOffers != offers;
    }

    public static boolean removeOffer(UUID id, int slot) {
        return removeOffer(getOffer(id, slot));
    }

    public static boolean removeOffer(Player p, int slot) {
        return removeOffer(p.getUniqueId(), slot);
    }

    public static UserShopItem getOffer(UUID id, int slot) {
        if (offers == "") {
            return null;
        }

        String[] offerArray = offers.split(";");

        for (String offer : offerArray) {
            UserShopItem item = UserShopItem.parse(offer);

            if (item.getOwnerId().equals(id) && item.getSlot() == slot) {
                return item;
            }
        }

        return null;
    }

    public static UserShopItem getOffer(Player p, int slot) {
        return getOffer(p.getUniqueId(), slot);
    }

    public static HashSet<UserShopItem> getOffers() {
        String[] offerArray = offers.split(";");
        HashSet<UserShopItem> result = new HashSet<UserShopItem>();

        if (offers == "") {
            return result;
        }

        for (String offer : offerArray) {
            result.add(UserShopItem.parse(offer));
        }

        return result;
    }
}