package de.basicbit.system.minecraft.skypvp.listeners;

import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.events.PlayerSkyPvPDeathEvent;

public class KillItems extends Listener {

    @EventHandler
    public void onDeath(PlayerSkyPvPDeathEvent e) {
        Player k = e.getKiller();
        Player p = e.getPlayer();
        PlayerInventory inv = p.getInventory();

        PlayerDeathEvent deathEvent = e.getEvent();
        deathEvent.setKeepInventory(true);

        ArrayList<ItemStack> drops = createListFromObjects(ArrayUtils.addAll(inv.getContents(), inv.getArmorContents()));

        inv.clear();
        inv.setArmorContents(new ItemStack[4]);

        if (isInSkyPvP(k)) {
            giveItemsToPlayer(k, drops);

            sendMessage(k, "Du hast seine Items erhalten.");
        }
    }
}