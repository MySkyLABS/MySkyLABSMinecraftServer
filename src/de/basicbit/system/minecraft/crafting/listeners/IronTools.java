package de.basicbit.system.minecraft.crafting.listeners;

import java.util.ArrayList;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.skyblock.SkyBlock;

@SuppressWarnings("all")
public class IronTools extends Listener {
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public static void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        ItemStack is = p.getItemInHand();

        if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName()) {
            String name = is.getItemMeta().getDisplayName();

            if (name.startsWith("§r§a§r")) {
                
                Block block = e.getBlock();
                is = block.getState().getData().toItemStack(1);
                int id = is.getTypeId();
                ArrayList<Integer> blockIds = new ArrayList<Integer>();
                blockIds.add(17);
                blockIds.add(187);
                blockIds.add(186);
                blockIds.add(185);
                blockIds.add(184);
                blockIds.add(183);
                blockIds.add(323);
                blockIds.add(192);
                blockIds.add(191);
                blockIds.add(190);
                blockIds.add(189);
                blockIds.add(188);
                blockIds.add(85);
                blockIds.add(58);
                blockIds.add(126);
                blockIds.add(164);
                blockIds.add(163);
                blockIds.add(136);
                blockIds.add(135);
                blockIds.add(134);
                blockIds.add(162);
                blockIds.add(53);
                blockIds.add(91);
                blockIds.add(103);
                blockIds.add(5);
                blockIds.add(427);
                blockIds.add(428);
                blockIds.add(429);
                blockIds.add(430);
                blockIds.add(431);
                blockIds.add(96);

                //Magische-Iron-Axt
                World w = p.getWorld();
                if (blockIds.contains(id)) {
                    if (SkyBlock.isTrusted(w, p) || isInTeam(p) || isOwnerOfWorld(w, p)) {
                        giveItemsToPlayer(p, new ArrayList<ItemStack>(block.getDrops()));
                        e.setCancelled(true);
                    
                        block.setTypeId(0);
                    }
                }
            } else if (name.startsWith("§r§g§r")) {
                Block block = e.getBlock();
                is = block.getState().getData().toItemStack(1);
                int id = is.getTypeId();
                ArrayList<Integer> blockIds = new ArrayList<Integer>();
                blockIds.add(1);
                blockIds.add(4);
                blockIds.add(14);
                blockIds.add(15);
                blockIds.add(16);
                blockIds.add(21);
                blockIds.add(48);
                blockIds.add(49);
                blockIds.add(87);
                blockIds.add(56);
                blockIds.add(73);
                blockIds.add(74);
                blockIds.add(153);
                blockIds.add(129);
                blockIds.add(44);
                blockIds.add(45);
                blockIds.add(57);
                blockIds.add(24);
                blockIds.add(42);
                blockIds.add(41);
                blockIds.add(22);
                blockIds.add(116);
                blockIds.add(27);
                blockIds.add(157);
                blockIds.add(66);
                blockIds.add(28);
                blockIds.add(152);
                blockIds.add(121);
                blockIds.add(114);
                blockIds.add(112);
                blockIds.add(113);
                blockIds.add(145);
                blockIds.add(97);
                blockIds.add(182);
                blockIds.add(180);
                blockIds.add(172);
                blockIds.add(128);
                blockIds.add(133);
                blockIds.add(173);
                blockIds.add(139);
                blockIds.add(179);
                blockIds.add(168);
                blockIds.add(159);
                blockIds.add(156);
                blockIds.add(155);
                blockIds.add(108);
                blockIds.add(109);
                blockIds.add(98);
                blockIds.add(67);
                blockIds.add(49);

                //Magische-Iron-Spitzhacke
                World w = p.getWorld();
                if (blockIds.contains(id)) {
                    if (SkyBlock.isTrusted(w, p) || isInTeam(p) || isOwnerOfWorld(w, p)) {
                        giveItemsToPlayer(p, new ArrayList<ItemStack>(block.getDrops()));
                        e.setCancelled(true);
                    
                        block.setTypeId(0);
                    }
                }
            } else if (name.startsWith("§r§h§r")) {
                Block block = e.getBlock();
                is = block.getState().getData().toItemStack(1);
                int id = is.getTypeId();
                ArrayList<Integer> blockIds = new ArrayList<Integer>();
                blockIds.add(2);
                blockIds.add(110);
                blockIds.add(13);
                blockIds.add(12);
                blockIds.add(88);
                blockIds.add(82);
                blockIds.add(3);

                //Magische-Iron-Schaufel
                World w = p.getWorld();
                if (blockIds.contains(id)) {
                    if (SkyBlock.isTrusted(w, p) || isInTeam(p) || isOwnerOfWorld(w, p)) {
                        giveItemsToPlayer(p, new ArrayList<ItemStack>(block.getDrops()));
                        e.setCancelled(true);
                    
                        block.setTypeId(0);
                    }
                }
            }
        }
    }
}