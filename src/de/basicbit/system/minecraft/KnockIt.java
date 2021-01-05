package de.basicbit.system.minecraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fish;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import de.basicbit.system.GlobalValues;
import net.minecraft.server.v1_8_R1.NBTTagCompound;

@SuppressWarnings("deprecation")
public class KnockIt extends Listener {

    public static HashMap<UUID, UUID> lastHit = new HashMap<UUID, UUID>();
    public static HashMap<UUID, Integer> voodooCounter = new HashMap<UUID, Integer>();

    public static String knockItDiedMsg = "§cDu bist gestorben!";
    public static String knockItCoinsToLow = "§cDu hast nicht genug Coins :(";
    public static String knockItStackIsFull = "§cDu hast nicht genug Platz im Inventar.";
    public static String knockItItemIsInInv = "§cDu hast dieses Item bereits.";

    @EventHandler
    public void onDamage(final EntityDamageEvent e) {

        if (e.getEntity() instanceof Player) {

            final Player p = (Player) e.getEntity();

            if (isInWorld(p, "KnockIt")) {

                if (e.getCause().equals(DamageCause.FALL) || p.getLocation().getY() > 40) {
                    e.setCancelled(true);
                }

                e.setDamage(0);

            }

        }

    }

    @EventHandler
    public void onDamage(final EntityDamageByEntityEvent e) {

        if (!isInWorld(e.getEntity(), "KnockIt")) {
            return;
        }

        if (e.getEntity() instanceof Player) {

            final Player o = (Player) e.getEntity();
            Player d = null;

            if (e.getDamager() instanceof Player) {

                d = (Player) e.getDamager();
                ItemStack is = d.getInventory().getItem(5);

                if (is != null) {

                    if (is.getType() != Material.AIR) {

                        if (d.getInventory().getHeldItemSlot() == 5 && d.getLocation().getY() <= 40) {

                            if (!voodooCounter.containsKey(d.getUniqueId())) {

                                is = new ItemStack(Material.BLAZE_ROD);
                                final net.minecraft.server.v1_8_R1.ItemStack mis = CraftItemStack.asNMSCopy(is);
                                final NBTTagCompound tag = new NBTTagCompound();
                                tag.setBoolean("Unbreakable", true);
                                mis.setTag(tag);
                                is = CraftItemStack.asBukkitCopy(mis);
                                final ItemMeta im = is.getItemMeta();
                                im.setDisplayName("§dV§5O§1O§9D§3O§bO§e-Stab §6[§e40 Coins§6]");
                                is.setItemMeta(im);
                                is.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
                                d.getInventory().setItem(5, is);

                                voodooCounter.put(d.getUniqueId(), 1);

                            } else {

                                final int i = voodooCounter.get(d.getUniqueId()) + 1;

                                if (i == 3) {

                                    d.getInventory().getItem(5).addUnsafeEnchantment(Enchantment.KNOCKBACK, 10);

                                }

                                voodooCounter.put(d.getUniqueId(), i);

                            }

                            final String a = "§3§l§k█";
                            final String b = "§7§l§k█";
                            String c = "";

                            for (int i = 0; i < voodooCounter.get(d.getUniqueId()); i++) {
                                c = c + a;
                            }

                            for (int i = 0; i < 3 - voodooCounter.get(d.getUniqueId()); i++) {
                                c = c + b;
                            }

                            sendTitle(d, "", c);

                            if (voodooCounter.get(d.getUniqueId()) == 3) {
                                voodooCounter.remove(d.getUniqueId());
                            }

                        }

                    }

                }

            } else if (e.getDamager() instanceof Projectile) {

                if (e.getDamager() instanceof FishHook) {
                    ProjectileSource ps = ((FishHook)e.getDamager()).getShooter();
                    if (ps instanceof Player) {
                        if (((Player)ps).getUniqueId().equals(o.getUniqueId())) {
                            e.setCancelled(true);
                            return;
                        }
                    }
                }

                d = (Player) (((Projectile) e.getDamager()).getShooter());

                if (d.getLocation().getY() > 40) {
                    e.setCancelled(true);
                } else if (e.getDamager() instanceof Snowball) {

                    o.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 50, 255), true);
                    o.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20, 255), true);

                    e.setCancelled(true);

                }

            } else {
                return;
            }

            if (isInWorld(o, "KnockIt") && isInWorld(d, "KnockIt")) {

                if (lastHit.containsKey(o.getUniqueId()))
                    lastHit.remove(o.getUniqueId());

                lastHit.put(o.getUniqueId(), d.getUniqueId());
            }

        }

    }

    @EventHandler
    public void onMove(final PlayerMoveEvent e) {

        final Player p = e.getPlayer();

        if (p.getGameMode() == GameMode.SPECTATOR || p.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        if (isInWorld(p, "KnockIt")) {

            p.setFoodLevel(40);

            if (p.getLocation().getBlock().getType().equals(Material.WOOD_PLATE)) {

                p.setVelocity(p.getLocation().getDirection().multiply(2).setY(1));
                p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 1, 1);

            }

            if (p.getLocation().getBlock().getType().equals(Material.GOLD_PLATE)) {

                final Vector v = p.getLocation().getDirection().setY(1.5);
                p.setVelocity(new Vector(v.getX(), v.getY(), v.getZ()));
                p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 1, 1);

            }

            if (p.getLocation().getBlock().getType().equals(Material.IRON_PLATE)) {

                p.setVelocity(p.getLocation().getDirection().setY(0.8));
                p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 1, 1);

            }

            boolean f = true;

            if ((p.getInventory().getItem(1) == null || p.getInventory().getItem(1).getType() != Material.FEATHER)
                    && !GlobalValues.flyingPlayersKnockIt.contains(p.getUniqueId())) {
                f = false;
            }

            p.setAllowFlight(f);

            if (p.getLocation().getBlockY() <= 10) {

                onJoin(p);
                onDeath(p);
            }

            if (p.isFlying() && !GlobalValues.flyingPlayersKnockIt.contains(p.getUniqueId())) {

                boost(p);

                final ItemStack is = p.getInventory().getItem(1);

                if (is != null) {
                    final int c = is.getAmount() - 1;

                    if (c == 0) {
                        p.getInventory().setItem(1, null);
                    } else {
                        is.setAmount(c);
                    }
                }
            }
        }
    }

    public void boost(final Player p) {

        p.setAllowFlight(false);
        boostPlayerJump(p);
        p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 1, 1);

    }

    public void onDeath(final Player p) {

        if (voodooCounter.containsKey(p.getUniqueId())) {
            voodooCounter.remove(p.getUniqueId());
        }

        Player k = null;

        if (lastHit.containsKey(p.getUniqueId())) {

            k = Bukkit.getPlayer(lastHit.get(p.getUniqueId()));
            lastHit.remove(p.getUniqueId());

        }

        if (k == null) {
            if (!isVanish(p)) {
                sendMessageInWorld(p.getWorld(), getPlayerDiedMessage(p));
            }
            sendMessage(p, knockItDiedMsg + " (-2 Coins)");
            removeCoins(p, 2, " ­KnockItRemoveCoins");
        } else {
            removeCoins(p, 2, " KnockItRemoveCoins");
            addCoins(k, 4, " KnockItAddCoins");
            if (!isVanish(p) && !isVanish(k)) {
                sendMessageInWorld(p.getWorld(), getPlayerKilledByMessage(p, k));
                sendMessage(p, getKilledByMessage(k) + " (-2 Coins)");
                sendMessage(k, getKillMessage(p) + " (+4 Coins)");
            }
        }

    }

    public static String getKillMessage(final Player p) {
        return "§aDu hast " + getChatName(p) + "§a getötet!";
    }

    public static String getKilledByMessage(final Player p) {
        return "§cDu wurdest von " + getChatName(p) + "§c getötet!";
    }

    public static String getPlayerDiedMessage(final Player p) {
        return getChatName(p) + "§c ist gestorben!";
    }

    public static String getPlayerKilledByMessage(final Player p, final Player t) {
        return getChatName(p) + "§7 wurde von " + getChatName(t) + "§7 getötet.";
    }

    @EventHandler
    public void onWorldChange(final PlayerChangedWorldEvent e) {
        final Player p = e.getPlayer();

        if (isInWorld(p, "KnockIt")) {
            onJoin(p);
        }
    }

    public static void onJoin(final Player p) {
        p.teleport(new Location(Bukkit.getWorld("KnockIt"), 0.5, 46.5, 0.5));
        
        p.setFoodLevel(20);
        p.setHealth(20);

        p.setLevel(0);
        p.setExp(0);
        
        for (PotionEffect potionEffect: p.getActivePotionEffects()) {
            p.removePotionEffect(potionEffect.getType());
        }

        createInvetory(p);
    }

    public static void createInvetory(final Player p) {
        final PlayerInventory inv = p.getInventory();
        inv.setArmorContents(new ItemStack[4]);
        inv.clear();

        ItemStack is = new ItemStack(Material.STICK);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§bStock I");
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
        inv.setItem(0, is);

        is = new ItemStack(Material.CHEST);
        im = is.getItemMeta();
        im.setDisplayName("§3§lShop");
        is.setItemMeta(im);
        inv.setItem(8, is);
    }

    public void boostPlayerJump(final Player p) {

        final Vector v = p.getLocation().getDirection().multiply(1.3).setY(0.1);

        p.setVelocity(v);

    }

    @EventHandler
    public void onClick(final InventoryClickEvent e) {

        final Player p = (Player) e.getWhoClicked();

        if (isInWorld(p, "KnockIt")) {

            e.setCancelled(true);

            if (p.getOpenInventory().getTitle().equalsIgnoreCase("§3§lShop")) {

                final Inventory inv = e.getInventory();

                if (e.getSlot() == 0) {

                    if (inv.getItem(e.getSlot()).getItemMeta().getDisplayName().equalsIgnoreCase(
                            p.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName())) {

                        sendMessage(p, knockItItemIsInInv);

                        return;
                    } else {

                        final int db = 20;
                        if (getCoins(p) < db) {

                            sendMessage(p, knockItCoinsToLow);

                            return;
                        } else
                            removeCoins(p, db, " ­KnockItRemoveCoins");

                        sendMessage(p,
                                getKnockItItemBuyMsg(inv.getItem(e.getSlot()).getItemMeta().getDisplayName()));

                    }

                    p.getInventory().setItem(0, inv.getItem(e.getSlot()));

                } else if (e.getSlot() == 1) {

                    if (p.getInventory().getItem(1) == null) {

                        if (e.isShiftClick()) {

                            final int db = 50;
                            if (getCoins(p) < db) {

                                sendMessage(p, knockItCoinsToLow);

                                return;
                            } else
                                removeCoins(p, db, " ­KnockItRemoveCoins");

                        } else {

                            final int db = 4;
                            if (getCoins(p) < db) {

                                sendMessage(p, knockItCoinsToLow);

                                return;
                            } else
                                removeCoins(p, db, " ­KnockItRemoveCoins");

                        }

                        p.getInventory().setItem(1, inv.getItem(1).clone());
                        if (e.isShiftClick())
                            p.getInventory().getItem(1).setAmount(p.getInventory().getItem(1).getAmount() + 4);
                        sendMessage(p,
                                getKnockItItemBuyMsg(inv.getItem(e.getSlot()).getItemMeta().getDisplayName()));

                    } else if (p.getInventory().getItem(1).getType().equals(Material.AIR)) {

                        if (e.isShiftClick()) {

                            final int db = 50;
                            if (getCoins(p) < db) {

                                sendMessage(p, knockItCoinsToLow);

                                return;
                            } else
                                removeCoins(p, db, " ­KnockItRemoveCoins");

                        } else {

                            final int db = 4;
                            if (getCoins(p) < db) {

                                sendMessage(p, knockItCoinsToLow);

                                return;
                            } else
                                removeCoins(p, db, " ­KnockItRemoveCoins");

                        }

                        p.getInventory().setItem(1, inv.getItem(1).clone());
                        if (e.isShiftClick())
                            p.getInventory().getItem(1).setAmount(p.getInventory().getItem(1).getAmount() + 4);
                        sendMessage(p,
                                getKnockItItemBuyMsg(inv.getItem(e.getSlot()).getItemMeta().getDisplayName()));

                    } else {

                        final ItemStack is = p.getInventory().getItem(1).clone();
                        final int nn = is.getAmount() + (e.isShiftClick() ? 5 : 1);
                        if (nn > 64) {

                            sendMessage(p, knockItStackIsFull);

                        } else {

                            if (e.isShiftClick()) {

                                final int db = 50;
                                if (getCoins(p) < db) {

                                    sendMessage(p, knockItCoinsToLow);

                                    return;
                                } else
                                    removeCoins(p, db, " ­KnockItRemoveCoins");

                            } else {

                                final int db = 4;
                                if (getCoins(p) < db) {

                                    sendMessage(p, knockItCoinsToLow);

                                    return;
                                } else
                                    removeCoins(p, db, " ­KnockItRemoveCoins");

                            }

                            is.setAmount(nn);
                            p.getInventory().setItem(1, is);
                            sendMessage(p,
                                    getKnockItItemBuyMsg(inv.getItem(e.getSlot()).getItemMeta().getDisplayName()));

                        }

                        return;
                    }

                } else if (e.getSlot() == 2) {

                    final int db = 40;

                    if (p.getInventory().getItem(e.getSlot()) == null) {

                        if (getCoins(p) < db) {

                            sendMessage(p, knockItCoinsToLow);

                            return;
                        } else
                            removeCoins(p, db, " ­KnockItRemoveCoins");

                        p.getInventory().setItem(2, inv.getItem(e.getSlot()));
                        sendMessage(p,
                                getKnockItItemBuyMsg(inv.getItem(e.getSlot()).getItemMeta().getDisplayName()));

                    } else if (p.getInventory().getItem(e.getSlot()).getType() == Material.AIR) {

                        if (getCoins(p) < db) {

                            sendMessage(p, knockItCoinsToLow);

                            return;
                        } else
                            removeCoins(p, db, " ­KnockItRemoveCoins");

                        p.getInventory().setItem(2, inv.getItem(e.getSlot()));
                        sendMessage(p,
                                getKnockItItemBuyMsg(inv.getItem(e.getSlot()).getItemMeta().getDisplayName()));

                    } else if (inv.getItem(e.getSlot()).getItemMeta().getDisplayName().equalsIgnoreCase(
                            p.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName())) {

                        sendMessage(p, knockItItemIsInInv);

                    }

                } else if (e.getSlot() == 3) {

                    if (p.getInventory().getItem(3) == null) {

                        if (e.isShiftClick()) {

                            final int db = 10;
                            if (getCoins(p) < db) {

                                sendMessage(p, knockItCoinsToLow);

                                return;
                            } else
                                removeCoins(p, db, " ­KnockItRemoveCoins");

                        } else {

                            final int db = 1;
                            if (getCoins(p) < db) {

                                sendMessage(p, knockItCoinsToLow);

                                return;
                            } else
                                removeCoins(p, db, " ­KnockItRemoveCoins");

                        }

                        p.getInventory().setItem(3, inv.getItem(3).clone());
                        if (e.isShiftClick())
                            p.getInventory().getItem(3).setAmount(p.getInventory().getItem(3).getAmount() + 4);
                        sendMessage(p,
                                getKnockItItemBuyMsg(inv.getItem(e.getSlot()).getItemMeta().getDisplayName()));

                    } else if (p.getInventory().getItem(3).getType().equals(Material.AIR)) {

                        if (e.isShiftClick()) {

                            final int db = 10;
                            if (getCoins(p) < db) {

                                sendMessage(p, knockItCoinsToLow);

                                return;
                            } else
                                removeCoins(p, db, " ­KnockItRemoveCoins");

                        } else {

                            final int db = 1;
                            if (getCoins(p) < db) {

                                sendMessage(p, knockItCoinsToLow);

                                return;
                            } else
                                removeCoins(p, db, " ­KnockItRemoveCoins");

                        }

                        p.getInventory().setItem(3, inv.getItem(3).clone());
                        if (e.isShiftClick())
                            p.getInventory().getItem(3).setAmount(p.getInventory().getItem(3).getAmount() + 4);
                        sendMessage(p,
                                getKnockItItemBuyMsg(inv.getItem(e.getSlot()).getItemMeta().getDisplayName()));

                    } else {

                        final ItemStack is = p.getInventory().getItem(3).clone();
                        final int nn = is.getAmount() + (e.isShiftClick() ? 5 : 1);
                        if (nn > 64) {

                            sendMessage(p, knockItStackIsFull);

                        } else {

                            if (e.isShiftClick()) {

                                final int db = 10;
                                if (getCoins(p) < db) {

                                    sendMessage(p, knockItCoinsToLow);

                                    return;
                                } else
                                    removeCoins(p, db, " ­KnockItRemoveCoins");

                            } else {

                                final int db = 1;
                                if (getCoins(p) < db) {

                                    sendMessage(p, knockItCoinsToLow);

                                    return;
                                } else
                                    removeCoins(p, db, " ­KnockItRemoveCoins");

                            }

                            is.setAmount(nn);
                            p.getInventory().setItem(3, is);
                            sendMessage(p,
                                    getKnockItItemBuyMsg(inv.getItem(e.getSlot()).getItemMeta().getDisplayName()));

                        }

                        return;
                    }

                } else if (e.getSlot() == 4) {

                    final int db = 40;

                    if (p.getInventory().getItem(e.getSlot()) == null) {

                        if (getCoins(p) < db) {

                            sendMessage(p, knockItCoinsToLow);

                            return;
                        } else
                            removeCoins(p, db, " ­KnockItRemoveCoins");

                        p.getInventory().setItem(4, inv.getItem(e.getSlot()));
                        sendMessage(p,
                                getKnockItItemBuyMsg(inv.getItem(e.getSlot()).getItemMeta().getDisplayName()));

                    } else if (p.getInventory().getItem(e.getSlot()).getType() == Material.AIR) {

                        if (getCoins(p) < db) {

                            sendMessage(p, knockItCoinsToLow);

                            return;
                        } else
                            removeCoins(p, db, " ­KnockItRemoveCoins");

                        p.getInventory().setItem(4, inv.getItem(e.getSlot()));
                        sendMessage(p,
                                getKnockItItemBuyMsg(inv.getItem(e.getSlot()).getItemMeta().getDisplayName()));

                    } else if (inv.getItem(e.getSlot()).getItemMeta().getDisplayName().equalsIgnoreCase(
                            p.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName())) {

                        sendMessage(p, knockItItemIsInInv);

                    }

                } else if (e.getSlot() == 5) {

                    final int db = 40;

                    if (p.getInventory().getItem(e.getSlot()) == null) {

                        if (getCoins(p) < db) {

                            sendMessage(p, knockItCoinsToLow);

                            return;
                        } else
                            removeCoins(p, db, " ­KnockItRemoveCoins");

                        p.getInventory().setItem(5, inv.getItem(e.getSlot()));
                        sendMessage(p,
                                getKnockItItemBuyMsg(inv.getItem(e.getSlot()).getItemMeta().getDisplayName()));

                    } else if (p.getInventory().getItem(e.getSlot()).getType() == Material.AIR) {

                        if (getCoins(p) < db) {

                            sendMessage(p, knockItCoinsToLow);

                            return;
                        } else
                            removeCoins(p, db, " ­KnockItRemoveCoins");

                        p.getInventory().setItem(5, inv.getItem(e.getSlot()));
                        sendMessage(p,
                                getKnockItItemBuyMsg(inv.getItem(e.getSlot()).getItemMeta().getDisplayName()));

                    } else if (inv.getItem(e.getSlot()).getItemMeta().getDisplayName().equalsIgnoreCase(
                            p.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName())) {

                        sendMessage(p, knockItItemIsInInv);

                    }

                } else if (e.getSlot() == 6) {

                    if (p.getInventory().getItem(6) == null) {

                        if (e.isShiftClick()) {

                            final int db = 250;
                            if (getCoins(p) < db) {

                                sendMessage(p, knockItCoinsToLow);

                                return;
                            } else
                                removeCoins(p, db, " ­KnockItRemoveCoins");

                        } else {

                            final int db = 30;
                            if (getCoins(p) < db) {

                                sendMessage(p, knockItCoinsToLow);

                                return;
                            } else
                                removeCoins(p, db, " ­KnockItRemoveCoins");

                        }

                        p.getInventory().setItem(6, inv.getItem(6).clone());
                        if (e.isShiftClick())
                            p.getInventory().getItem(6).setAmount(p.getInventory().getItem(6).getAmount() + 4);
                        sendMessage(p,
                                getKnockItItemBuyMsg(inv.getItem(e.getSlot()).getItemMeta().getDisplayName()));

                    } else if (p.getInventory().getItem(6).getType().equals(Material.AIR)) {

                        if (e.isShiftClick()) {

                            final int db = 250;
                            if (getCoins(p) < db) {

                                sendMessage(p, knockItCoinsToLow);

                                return;
                            } else
                                removeCoins(p, db, " ­KnockItRemoveCoins");

                        } else {

                            final int db = 50;
                            if (getCoins(p) < db) {

                                sendMessage(p, knockItCoinsToLow);

                                return;
                            } else
                                removeCoins(p, db, " ­KnockItRemoveCoins");

                        }

                        p.getInventory().setItem(6, inv.getItem(6).clone());
                        if (e.isShiftClick())
                            p.getInventory().getItem(6).setAmount(p.getInventory().getItem(6).getAmount() + 4);
                        sendMessage(p,
                                getKnockItItemBuyMsg(inv.getItem(e.getSlot()).getItemMeta().getDisplayName()));

                    } else {

                        final ItemStack is = p.getInventory().getItem(6).clone();
                        final int nn = is.getAmount() + (e.isShiftClick() ? 5 : 1);
                        if (nn > 64) {

                            sendMessage(p, knockItStackIsFull);

                        } else {

                            if (e.isShiftClick()) {

                                final int db = 250;
                                if (getCoins(p) < db) {

                                    sendMessage(p, knockItCoinsToLow);

                                    return;
                                } else
                                    removeCoins(p, db, " ­KnockItRemoveCoins");

                            } else {

                                final int db = 50;
                                if (getCoins(p) < db) {

                                    sendMessage(p, knockItCoinsToLow);

                                    return;
                                } else
                                    removeCoins(p, db, " ­KnockItRemoveCoins");

                            }

                            is.setAmount(nn);
                            p.getInventory().setItem(6, is);
                            sendMessage(p,
                                    getKnockItItemBuyMsg(inv.getItem(e.getSlot()).getItemMeta().getDisplayName()));

                        }

                        return;
                    }

                }

            }

        }

    }

    public static String getKnockItItemBuyMsg(final String itemName) {
        return "§aDu hast dir das Item \"" + itemName + "\"§a gekauft.";
    }

    @EventHandler
    public void onHit(final ProjectileHitEvent e) {

        if (!e.getEntity().getWorld().getName().contentEquals("KnockIt"))
            return;

        if (e.getEntity() instanceof Arrow) {

            e.getEntity().remove();

        }

    }

    @EventHandler
    public void onBlock(final BlockBreakEvent e) {

        if (e.getBlock().getWorld().getName().contentEquals("KnockIt")) {

            e.setCancelled(true);

        }

    }

    @EventHandler
    public void onPlayerFish(final PlayerFishEvent e) {
        final Player p = e.getPlayer();

        if (!isInWorld(p, "KnockIt"))
            return;

        if (e.getCaught() != null) {

            if (e.getCaught() instanceof Player) {

                final Player ta = ((Player) e.getCaught());

                final Location lc = ta.getLocation();
                final Location to = p.getLocation();
                lc.setY(lc.getY() + 0.8D);
                ta.teleport(lc);
                final double g = -0.08D;
                final double d = to.distance(lc);
                final double t = d;
                final double v_x = (1.0D + 0.07D * t) * (to.getX() - lc.getX()) / t;
                final double v_y = (1.0D + 0.03D * t) * (to.getY() - lc.getY()) / t - 0.5D * g * t;
                final double v_z = (1.0D + 0.07D * t) * (to.getZ() - lc.getZ()) / t;
                final Vector v = p.getVelocity();
                v.setX(v_x);
                v.setY(v_y);
                v.setZ(v_z);
                ta.setVelocity(v.multiply(0.6));
                ta.playSound(ta.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0F, 1.0F);
                p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0F, 1.0F);

                return;
            }

        }

        final Fish h = e.getHook();

        if (h.getLocation().getY() > 97)
            return;

        if (((e.getState().equals(PlayerFishEvent.State.IN_GROUND))
                || (e.getState().equals(PlayerFishEvent.State.CAUGHT_ENTITY))
                || (e.getState().equals(PlayerFishEvent.State.FAILED_ATTEMPT)))
                && (Bukkit.getWorld(e.getPlayer().getWorld().getName())
                        .getBlockAt(h.getLocation().getBlockX(), h.getLocation().getBlockY() - 1,
                                h.getLocation().getBlockZ())
                        .getType() != Material.AIR)
                && (Bukkit
                        .getWorld(e.getPlayer().getWorld().getName()).getBlockAt(h.getLocation().getBlockX(),
                                h.getLocation().getBlockY() - 1, h.getLocation().getBlockZ())
                        .getType() != Material.STATIONARY_WATER)) {

            final Location lc = p.getLocation();
            final Location to = e.getHook().getLocation();
            lc.setY(lc.getY() + 0.8D);
            p.teleport(lc);
            final double g = -0.08D;
            final double d = to.distance(lc);
            final double t = d;
            final double v_x = (1.0D + 0.07D * t) * (to.getX() - lc.getX()) / t;
            final double v_y = (1.0D + 0.03D * t) * (to.getY() - lc.getY()) / t - 0.5D * g * t;
            final double v_z = (1.0D + 0.07D * t) * (to.getZ() - lc.getZ()) / t;
            final Vector v = p.getVelocity();
            v.setX(v_x);
            v.setY(v_y);
            v.setZ(v_z);
            p.setVelocity(v);
            p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0F, 1.0F);

        }
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent e) {

        final Player p = e.getPlayer();

        if (isInWorld(p, "KnockIt") && e.getAction() != Action.PHYSICAL) {

            if (p.getInventory().getHeldItemSlot() == 8) {

                e.setCancelled(true);

                final Inventory inv = Bukkit.createInventory(null, 9, "§3§lShop");

                ItemStack is = new ItemStack(Material.STICK);
                ItemMeta im = is.getItemMeta();
                im.setDisplayName("§bStock II §6[§e20 Coins§6]");
                is.setItemMeta(im);
                is.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
                inv.setItem(0, is);

                is = new ItemStack(Material.FEATHER);
                im = is.getItemMeta();
                im.setDisplayName("§f§lFeder §6[§e4 Coins§6]");
                final ArrayList<String> l = new ArrayList<String>();
                l.add("§2- Erlaubt dir Doppelsprünge!");
                im.setLore(l);
                is.setItemMeta(im);
                inv.setItem(1, is);

                is = new ItemStack(Material.BOW);
                net.minecraft.server.v1_8_R1.ItemStack mis = CraftItemStack.asNMSCopy(is);
                NBTTagCompound tag = new NBTTagCompound();
                tag.setBoolean("Unbreakable", true);
                mis.setTag(tag);
                is = CraftItemStack.asBukkitCopy(mis);
                im = is.getItemMeta();
                im.setDisplayName("§6§lBogen §6[§e40 Coins§6]");
                is.setItemMeta(im);
                is.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
                inv.setItem(2, is);

                is = new ItemStack(Material.ARROW);
                im = is.getItemMeta();
                im.setDisplayName("§e§lPfeil §6[§e1 Coins§6]");
                is.setItemMeta(im);
                inv.setItem(3, is);

                is = new ItemStack(Material.FISHING_ROD);
                mis = CraftItemStack.asNMSCopy(is);
                tag = new NBTTagCompound();
                tag.setBoolean("Unbreakable", true);
                mis.setTag(tag);
                is = CraftItemStack.asBukkitCopy(mis);
                im = is.getItemMeta();
                im.setDisplayName("§d§lEnterhaken §6[§e40 Coins§6]");
                is.setItemMeta(im);
                inv.setItem(4, is);

                is = new ItemStack(Material.BLAZE_ROD);
                mis = CraftItemStack.asNMSCopy(is);
                tag = new NBTTagCompound();
                tag.setBoolean("Unbreakable", true);
                mis.setTag(tag);
                is = CraftItemStack.asBukkitCopy(mis);
                im = is.getItemMeta();
                im.setDisplayName("§dV§5O§1O§9D§3O§bO§e-Stab §6[§e40 Coins§6]");
                is.setItemMeta(im);
                is.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
                inv.setItem(5, is);

                is = new ItemStack(Material.SNOW_BALL);
                im = is.getItemMeta();
                im.setDisplayName("§f§lBlack§3§lIn§f§lWhite §6[§e30 Coins§6]");
                is.setItemMeta(im);
                inv.setItem(6, is);

                is = new ItemStack(Material.BARRIER);
                im = is.getItemMeta();
                im.setDisplayName("§r");
                is.setItemMeta(im);
                inv.setItem(7, is);
                inv.setItem(8, is);

                p.openInventory(inv);

            }

        }

    }

    @EventHandler
    public void onDrop(final PlayerDropItemEvent e) {

        final Player p = e.getPlayer();

        if (isInWorld(p, "KnockIt")) {
            e.setCancelled(true);
        }

    }

    public static void init() {

        final World w = Bukkit.getWorld("KnockIt");
        w.setMonsterSpawnLimit(0);
        w.setAmbientSpawnLimit(0);
        w.setAnimalSpawnLimit(0);
        w.setWaterAnimalSpawnLimit(0);

        for (final Entity e : w.getEntities()) {
            if (!(e instanceof Player)) {
                e.remove();
            }
        }

        ListenerSystem.register(new KnockIt());
    }
}