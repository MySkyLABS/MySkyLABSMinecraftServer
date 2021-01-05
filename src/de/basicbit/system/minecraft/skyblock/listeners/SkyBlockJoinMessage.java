package de.basicbit.system.minecraft.skyblock.listeners;

import java.util.UUID;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.skyblock.SkyBlock;

public class SkyBlockJoinMessage extends Listener {
    
    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        World w = p.getWorld();
        
        if (!isVanish(p)) {
            if (isSkyblockWorld(w)) {
                UUID id = getUUIDFromSkyBlockWorld(w);
                int number = getNumberFromSkyBlockWorld(w);
                Player t = getPlayer(id);

                if (t != null) {
                    if (!SkyBlock.isTrusted(id, number, p) && !isOwnerOfWorld(w, p)) {
                        sendMessage(t, getChatName(p) + "§7 hat deine " + (number + 1) + ". Welt betreten.");
                    }
                }
            }
        }
    }
}