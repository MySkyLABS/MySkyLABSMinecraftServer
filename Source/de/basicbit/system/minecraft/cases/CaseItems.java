package de.basicbit.system.minecraft.cases;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.ListenerSystem;
import de.basicbit.system.minecraft.geometry.Point3D;
import de.basicbit.system.minecraft.skyblock.SkyBlock;
import de.basicbit.system.minecraft.tasks.TaskManager;
import net.minecraft.server.v1_8_R1.EnumParticle;
import net.minecraft.server.v1_8_R1.PacketPlayOutWorldParticles;

public class CaseItems extends Listener {

    public static final ArrayList<Integer> blockIds;

    static {

        blockIds = new ArrayList<Integer>();
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

    }

    public static void init() {
        ListenerSystem.register(new CaseItems());

        TaskManager.runAsyncLoop("CaseItemUpdateTickAsync", new Runnable() {

            @Override
            public void run() {
                for (Player p : getPlayers()) {
                    updateTickAsync(p);
                }
            }

        }, 1);

        TaskManager.runAsyncLoop("CaseItemUpdate", new Runnable() {

            @Override
            public void run() {
                for (Player p : getPlayers()) {
                    update(p);
                }
            }

        }, 20);
    }

    public static void update(Player p) {
        PlayerInventory inv = p.getInventory();
        ItemStack leggings = inv.getLeggings();
        ItemStack boots = inv.getBoots();
        ItemStack chestplate = inv.getChestplate();
        ItemStack helmet = inv.getHelmet();

        if (boots != null && boots.hasItemMeta() && boots.getItemMeta().hasDisplayName()) {
            String name = boots.getItemMeta().getDisplayName();
            if (name.startsWith("§r§2§r")) {
                TaskManager.runSyncTask("CaseItemsAddEffect", new Runnable() {

                    @Override
                    public void run() {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 60, 0), true);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 0), true);
                    }
                });
            }
        }

        if (leggings != null && leggings.hasItemMeta() && leggings.getItemMeta().hasDisplayName()) {
            String name = leggings.getItemMeta().getDisplayName();
            if (name.startsWith("§r§3§r")) {
                TaskManager.runSyncTask("CaseItemsAddEffect", new Runnable() {

                    @Override
                    public void run() {
                        p.setFireTicks(0);
                    }
                });
            }
        }

        if (helmet != null && helmet.hasItemMeta() && helmet.getItemMeta().hasDisplayName()) {
            String name = helmet.getItemMeta().getDisplayName();
            if (name.startsWith("§r§d§r")) {
                TaskManager.runSyncTask("CaseItemsAddEffect", new Runnable() {

                    @Override
                    public void run() {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 60, 0), true);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 60, 0), true);
                    }
                });
            }
        }

        if (chestplate != null && chestplate.hasItemMeta() && chestplate.getItemMeta().hasDisplayName()) {
            String name = chestplate.getItemMeta().getDisplayName();
            if (name.startsWith("§r§c§r")) {
                TaskManager.runSyncTask("CaseItemsAddEffect", new Runnable() {

                    @Override
                    public void run() {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 60, 0), true);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 1), true);
                    }
                });
            }
        }
    }

    @SuppressWarnings("all")
    public static void updateTickAsync(Player p) {
        PlayerInventory inv = p.getInventory();
        ItemStack leggings = inv.getLeggings();
        ItemStack hand = inv.getItem(inv.getHeldItemSlot());
        ItemStack boots = inv.getBoots();

        if (boots != null && boots.hasItemMeta() && boots.getItemMeta().hasDisplayName()) {
            String name = boots.getItemMeta().getDisplayName();
            if (name.startsWith("§r§k§r")) {
                Location loc = p.getLocation();
                World w = p.getWorld();

                Block underThePlayer = loc.getBlock().getRelative(0, -1, 0);

                if (isSkyblockWorld(w) && (SkyBlock.isTrusted(w, p) || isOwnerOfWorld(w, p) || isInTeam(p))
                        && !isVanish(p)) {
                    for (Block block : getBlocksByRadius(underThePlayer, 5)) {
                        if (block != null && block.getType() == Material.STATIONARY_WATER) {
                            TaskManager.runSyncTask("RagnaronIceBootBlockSet", new Runnable() {

                                @Override
                                public void run() {
                                    block.setType(Material.ICE);
                                }

                            });
                        }
                    }
                }
            }
        }

        if (leggings != null && leggings.hasItemMeta() && leggings.getItemMeta().hasDisplayName()) {
            String name = leggings.getItemMeta().getDisplayName();
            if (name.startsWith("§r§3§r")) {
                Location location = p.getLocation();
                float x = (float) location.getX();
                float y = (float) location.getY();
                float z = (float) location.getZ();

                for (Entity entity : getNearbyEntities(p, 20)) {
                    if (entity instanceof Player) {
                        Player t = (Player) entity;
                        sendPacket(t, new PacketPlayOutWorldParticles(EnumParticle.CLOUD, true, x, y + 2.75f, z, 0.3f,
                                0.125f, 0.3f, 0f, 8));
                        sendPacket(t, new PacketPlayOutWorldParticles(EnumParticle.BLOCK_DUST, true, x, y + 2.7f, z,
                                0.25f, 0f, 0.25f, 0f, 8, 9));
                    }
                }

                sendPacket(p, new PacketPlayOutWorldParticles(EnumParticle.CLOUD, true, x, y + 2.75f, z, 0.3f, 0.125f,
                        0.3f, 0f, 8));
                sendPacket(p, new PacketPlayOutWorldParticles(EnumParticle.BLOCK_DUST, true, x, y + 2.7f, z, 0.25f, 0f,
                        0.25f, 0f, 8, 9));
            }
        }

        if (hand != null && hand.hasItemMeta() && hand.getItemMeta().hasDisplayName()) {
            String name = hand.getItemMeta().getDisplayName();
            if (name.startsWith("§0§0§r") && p.isBlocking()) {
                double radius = 1 + ((20 - p.getHealth()) * 0.175);
                ArrayList<Point3D> points = new Point3D(p.getLocation().add(0, 1, 0)).getBulletPoints(radius, 6);

                for (Point3D point : points) {
                    for (Entity entity : getNearbyEntities(p, 20)) {
                        if (entity instanceof Player) {
                            Player t = (Player) entity;
                            sendPacket(t, new PacketPlayOutWorldParticles(EnumParticle.LAVA, true, point.getXFloat(),
                                    point.getYFloat(), point.getZFloat(), 0f, 0f, 0f, 0f, 0));
                        }
                        if (entity.getLocation().distance(p.getLocation()) <= radius) {
                            entity.setVelocity(
                                    p.getLocation().subtract(entity.getLocation()).toVector().multiply(-0.4));
                            entity.setFireTicks(140);

                            if (entity instanceof Player) {
                                Boolean pvp = false;
                                try {
                                    pvp = UserData.getBoolean(getUUIDFromSkyBlockWorld(p.getWorld()), UserValue
                                            .valueOf("island" + getNumberFromSkyBlockWorld(p.getWorld()) + "UsersPvp"));
                                } catch (Exception ex) {
                                }
                                if (pvp || isInSkyPvP(p)) {
                                    ((Player) entity).damage(2);
                                }
                            } else if (entity instanceof LivingEntity) {
                                ((LivingEntity) entity).damage(2);
                            }
                        }
                    }

                    sendPacket(p, new PacketPlayOutWorldParticles(EnumParticle.LAVA, true, point.getXFloat(),
                            point.getYFloat(), point.getZFloat(), 0f, 0f, 0f, 0f, 0));
                }
            } else if (name.startsWith("§r§i§r")) {
                Location location = p.getLocation();
                float x = (float) location.getX();
                float y = (float) location.getY();
                float z = (float) location.getZ();

                for (Entity entity : getNearbyEntities(p, 20)) {
                    if (entity instanceof Player) {
                        Player t = (Player) entity;
                        sendPacket(t, new PacketPlayOutWorldParticles(EnumParticle.FLAME, true, x, y + 1f, z, 0.4f,
                                0.8f, 0.4f, 0f, 1));
                    }
                }

                sendPacket(p, new PacketPlayOutWorldParticles(EnumParticle.FLAME, true, x, y + 1f, z, 0.4f, 0.8f, 0.4f,
                        0f, 1));
            }
        }

        if (boots != null && boots.hasItemMeta() && boots.getItemMeta().hasDisplayName()) {
            String name = boots.getItemMeta().getDisplayName();
            if (name.startsWith("§r§m§r") && boots.getItemMeta() instanceof LeatherArmorMeta) {
                LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) boots.getItemMeta();
                Location location = p.getLocation();
                float x = (float) location.getX();
                float y = (float) location.getY();
                float z = (float) location.getZ();
                Color color = leatherArmorMeta.getColor();
                float r = 255.F;
                float g = 204.F;
                float b = 255.F;

                for (Entity entity : getNearbyEntities(p, 20)) {
                    if (entity instanceof Player) {
                        Player t = (Player) entity;

                        Point3D center = new Point3D(x, y, z);
                        for (int yaw = 0; yaw < 360; yaw += 36) {
                            Point3D point = center.getDirectionPoint(yaw, 0, 0.3);
                            sendPacket(t, new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true,
                                    point.getXFloat(), point.getYFloat(), point.getZFloat(), r, g, b, 1, 0));
                        }
                    }
                }

                Point3D center = new Point3D(x, y, z);
                for (int yaw = 0; yaw < 360; yaw += 36) {
                    Point3D point = center.getDirectionPoint(yaw, 0, 0.3);
                    sendPacket(p, new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true, point.getXFloat(),
                            point.getYFloat(), point.getZFloat(), r, g, b, 1, 0));
                }
            }
        }
    }

    public static ArrayList<Entity> getNearbyEntities(Entity e, double distance) {
        ArrayList<Entity> result = new ArrayList<Entity>();
        World w = e.getWorld();

        for (Entity en : w.getEntities()) {
            if (en.getEntityId() != e.getEntityId() && en.getLocation().distance(e.getLocation()) <= distance) {
                result.add(en);
            }
        }

        return result;
    }

    @EventHandler
    public static void onDamage(EntityDamageByEntityEvent e) {
        Entity en = e.getEntity();
        Entity d = e.getDamager();

        if (d instanceof HumanEntity && en instanceof LivingEntity) {
            HumanEntity h = (HumanEntity) d;
            LivingEntity l = (LivingEntity) en;
            ItemStack is = h.getItemInHand();

            if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName()) {
                String name = is.getItemMeta().getDisplayName();
                World w = h.getWorld();
                Boolean pvp = false;

                try {
                    pvp = UserData.getBoolean(getUUIDFromSkyBlockWorld(w),
                            UserValue.valueOf("island" + getNumberFromSkyBlockWorld(w) + "UsersPvP"));
                } catch (Exception ex) {}
                if (name.startsWith("§r§0§r") && pvp) { // Schattendolch
                    l.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1), true);
                } else if (name.startsWith("§r§1§r") && pvp) {
                    l.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1), true);
                } else if (name.startsWith("§r§9§r") && pvp) {
                    l.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200, 0), true);
                    l.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200, 0), true);
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.LOWEST)
    public static void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            ItemStack is = p.getItemInHand();
            if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName()) {
                String name = is.getItemMeta().getDisplayName();

                if (name.startsWith("§r§4§r")) {
                    BlockFace blockFace = e.getBlockFace();
                    GlobalValues.blockFaces.put(p.getUniqueId(), blockFace);
                }
            }
        } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            ItemStack is = p.getItemInHand();
            if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName()) {
                String name = is.getItemMeta().getDisplayName();

                if (name.startsWith("§r§b§r")) {
                    World w = p.getWorld();

                    if (isInSkyPvP(p) || isSkyblockWorld(w)) {

                        boolean used = false;

                        for (Player t : w.getPlayers()) {
                            if (t.getLocation().distance(p.getLocation()) <= 10
                                    && !p.getUniqueId().equals(t.getUniqueId())) {
                                used = true;

                                sendMessage(t, "§6Puhh... Das stinkt!");
                                t.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 0), true);
                            }
                        }

                        if (used) {
                            sendMessage(p, "§6Puhh... Das stinkt!");

                            int count = is.getAmount();

                            if (count != 1) {
                                is.setAmount(count - 1);
                                p.setItemInHand(is);
                            } else {
                                p.setItemInHand(new ItemStack(0));
                            }
                        } else {
                            sendMessage(p, "§cEs sind keine Spieler in deiner Nähe.");
                        }
                    } else {
                        sendMessage(p, "§cDu darfts dieses Item hier nicht verwenden.");
                    }
                } else if (name.startsWith("§r§5§r")) {
                    UUID id = p.getUniqueId();

                    int uses = 0;

                    if (GlobalValues.warpStickCooldowns.containsKey(id)) {
                        uses = GlobalValues.warpStickCooldowns.get(id);
                    }

                    if (uses < 1) {
                        p.setVelocity(p.getLocation().getDirection().multiply(1.3));
                        GlobalValues.warpStickCooldowns.put(id, uses + 1);
                        p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0F, 1.0F);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDamageEvent(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        if (entity instanceof Player) {
            Player p = (Player) entity;
            if (e.getCause() == DamageCause.FALL) {
                if (GlobalValues.warpStickCooldowns.containsKey(p.getUniqueId())) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerChangePos(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        double vy = p.getVelocity().getY();
        if (vy < 0.42D && 0.419 < vy) {
            GlobalValues.warpStickCooldowns.put(p.getUniqueId(), -1);
        }
        if (p.isOnGround()) {
            if (GlobalValues.warpStickCooldowns.containsKey(p.getUniqueId())) {
                GlobalValues.warpStickCooldowns.remove(p.getUniqueId());
            }
        }
    }

    @SuppressWarnings("all")
    @EventHandler(priority = EventPriority.HIGHEST)
    public static void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        ItemStack is = p.getItemInHand();
        if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName()) {
            String name = is.getItemMeta().getDisplayName();

            if (name.startsWith("§r§4§r")) { // smogmaas arbeiterpicke
                UUID id = p.getUniqueId();
                if (GlobalValues.blockFaces.containsKey(id)) {
                    BlockFace blockFace = GlobalValues.blockFaces.get(id);
                    World w = p.getWorld();
                    Block b = e.getBlock();
                    int x = b.getX();
                    int y = b.getY();
                    int z = b.getZ();

                    if (!isSkyblockWorld(w)) {
                        return;
                    } else if (!SkyBlock.isTrusted(w, p) && !isInTeam(p) && !isOwnerOfWorld(w, p)) {
                        return;
                    }

                    if (blockFace == BlockFace.DOWN || blockFace == BlockFace.UP) {
                        breakBlock(w, x + 1, y, z);
                        breakBlock(w, x - 1, y, z);

                        breakBlock(w, x, y, z + 1);
                        breakBlock(w, x, y, z - 1);

                        breakBlock(w, x + 1, y, z + 1);
                        breakBlock(w, x - 1, y, z - 1);

                        breakBlock(w, x - 1, y, z + 1);
                        breakBlock(w, x + 1, y, z - 1);
                    } else if (blockFace == BlockFace.NORTH || blockFace == BlockFace.SOUTH) {
                        breakBlock(w, x + 1, y, z);
                        breakBlock(w, x - 1, y, z);

                        breakBlock(w, x, y + 1, z);
                        breakBlock(w, x, y - 1, z);

                        breakBlock(w, x + 1, y + 1, z);
                        breakBlock(w, x - 1, y - 1, z);

                        breakBlock(w, x - 1, y + 1, z);
                        breakBlock(w, x + 1, y - 1, z);
                    } else if (blockFace == BlockFace.EAST || blockFace == BlockFace.WEST) {
                        breakBlock(w, x, y, z + 1);
                        breakBlock(w, x, y, z - 1);

                        breakBlock(w, x, y + 1, z);
                        breakBlock(w, x, y - 1, z);

                        breakBlock(w, x, y + 1, z + 1);
                        breakBlock(w, x, y - 1, z - 1);

                        breakBlock(w, x, y + 1, z - 1);
                        breakBlock(w, x, y - 1, z + 1);
                    }
                }
            } else if (name.startsWith("§r§j§r")) { // Cobalt's Farmersense
                Block b = e.getBlock();
                Material m = b.getType();
                World w = b.getWorld();
                if (m.equals(Material.POTATO) || m.equals(Material.CARROT) || m.equals(Material.CROPS)
                        || m.equals(Material.NETHER_WARTS) || m.equals(Material.NETHER_WARTS)) {
                    int luck = 5; // min. 1, max. 5
                    int multiplier = new Random(System.nanoTime()).nextInt(luck) + 1;
                    if (b.getData() == 7) {
                        e.setCancelled(true);
                        for (ItemStack drop : b.getDrops()) {
                            for (int i = 0; i < multiplier; i++) {
                                w.dropItemNaturally(new Location(w, b.getX() + 0.5, b.getY() + 0.5, b.getZ() + 0.5),
                                        drop);
                            }
                        }
                        b.setData((byte) 0);
                    } else if (b.getData() == 3 && m.equals(Material.NETHER_WARTS)) {
                        e.setCancelled(true);
                        w.dropItemNaturally(new Location(w, b.getX() + 0.5, b.getY() + 0.5, b.getZ() + 0.5),
                                new ItemStack(372, multiplier));
                        b.setData((byte) 0);
                    } else {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    public static void breakBlock(World w, int x, int y, int z) {
        Block b = w.getBlockAt(x, y, z);
        int id = b.getTypeId();

        if (blockIds.contains(id)) {
            b.breakNaturally();
        }
    }

    @EventHandler
    public static void playerFish(PlayerFishEvent e) {
        Player p = e.getPlayer();
        ItemStack is = p.getItemInHand();
        if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName()) {
            String name = is.getItemMeta().getDisplayName();
            if (name.startsWith("§r§f§r")) {
                if (e.getState().equals(PlayerFishEvent.State.CAUGHT_ENTITY)
                        || e.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
                    e.setExpToDrop(e.getExpToDrop() * 3);
                }
            }
        }
    }
}