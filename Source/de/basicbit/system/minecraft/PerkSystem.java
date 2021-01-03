package de.basicbit.system.minecraft;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.basicbit.system.minecraft.tasks.TaskManager;

@SuppressWarnings("deprecation")
public class PerkSystem extends Listener {

    public static enum Perk {
        Haste1, Haste2, Haste3, Strength, Regeneration1, Regeneration2, Water, Night, Fire, Speed, NoFall, NoFood;
    }

    private static HashMap<Perk, ItemStack> perkItems = new HashMap<Perk, ItemStack>();

    public static void updatePerks() {
        for (final Player p : Bukkit.getOnlinePlayers()) {
            checkPerks(p);
        }
    }

    public static ItemStack getPerkItem(final Perk p) {
        return perkItems.get(p);
    }

    public static ArrayList<ItemStack> getPerkItems() {
        final ArrayList<ItemStack> items = new ArrayList<ItemStack>();

        for (final Perk p : Perk.values()) {
            items.add(getPerkItem(p));
        }

        return items;
    }

    public static void checkPerks(final Player p) {

        final ArrayList<Effect> l = new ArrayList<Effect>();
        final ArrayList<PotionEffectType> saveTypes = new ArrayList<PotionEffectType>();

        if (hasPerk(p, Perk.Water)) {

            if (p.getEyeLocation().getBlock().getType().equals(Material.STATIONARY_WATER)
                    || p.getEyeLocation().getBlock().getType().equals(Material.WATER)) {
                l.add(new Effect(PotionEffectType.NIGHT_VISION, 0));
                l.add(new Effect(PotionEffectType.WATER_BREATHING, 0));
            }
        }

        if (hasPerk(p, Perk.NoFood)) {
            p.setFoodLevel(40);
        }

        if (hasPerk(p, Perk.Speed)) {
            l.add(new Effect(PotionEffectType.SPEED, 0));
        }

        if (hasPerk(p, Perk.Night)) {
            l.add(new Effect(PotionEffectType.NIGHT_VISION, 0));
        }

        if (hasPerk(p, Perk.Regeneration2)) {
            l.add(new Effect(PotionEffectType.REGENERATION, 1));
        } else if (hasPerk(p, Perk.Regeneration1)) {
            l.add(new Effect(PotionEffectType.REGENERATION, 0));
        }

        if (hasPerk(p, Perk.Haste3)) {
            l.add(new Effect(PotionEffectType.FAST_DIGGING, 2));
        } else if (hasPerk(p, Perk.Haste2)) {
            l.add(new Effect(PotionEffectType.FAST_DIGGING, 1));
        } else if (hasPerk(p, Perk.Haste1)) {
            l.add(new Effect(PotionEffectType.FAST_DIGGING, 0));
        }

        if (hasPerk(p, Perk.Strength)) {
            l.add(new Effect(PotionEffectType.INCREASE_DAMAGE, 0));
        }

        for (final Effect ef : l) {
            saveTypes.add(ef.type);
        }

        TaskManager.runSyncTask("PerkEffectEdit", new Runnable() {
        
            @Override
            public void run() {
                addEffects(p, l);

                for (final PotionEffect pe : p.getActivePotionEffects()) {

                    if (pe.getDuration() > Integer.MAX_VALUE / 2) {

                        if (!saveTypes.contains(pe.getType())) {
                            p.removePotionEffect(pe.getType());
                        }
                    }
                }
            }
        });
    }

    @EventHandler
    public void onDamage(final EntityDamageByEntityEvent e) {

        if (e.getDamager() instanceof Player) {

            final Player p = (Player) e.getDamager();

            if (hasPerk(p, Perk.Fire)) {
                e.getEntity().setFireTicks(100);
            }
        }
    }

    @EventHandler
    public void onDamage(final EntityDamageEvent e) {

        if (e.getEntity() instanceof Player) {

            final Player p = (Player) e.getEntity();

            if (e.getCause().equals(DamageCause.LAVA) || e.getCause().equals(DamageCause.FIRE) || e.getCause().equals(DamageCause.FIRE_TICK)) {

                if (hasPerk(p, Perk.Fire)) {

                    p.setFireTicks(0);
                    e.setCancelled(true);
                }

                return;
            }

            if (e.getCause().equals(DamageCause.FALL)) {

                if (hasPerk(p, Perk.NoFall)) {

                    e.setCancelled(true);
                }
            }
        }
    }

    public static void addEffect(final Player p, final Effect e) {

        for (final PotionEffect pe : p.getActivePotionEffects()) {

            if (pe.getType().equals(e.type)) {

                if (pe.getAmplifier() != e.lvl) {
                    p.removePotionEffect(e.type);
                }

                continue;
            }
        }

        p.addPotionEffect(new PotionEffect(e.type, Integer.MAX_VALUE, e.lvl), true);
    }

    public static void addEffects(final Player p, final ArrayList<Effect> effects) {
        for (final Effect e : effects) {
            addEffect(p, e);
        }
    }

    public static void init() {

        TaskManager.runAsyncLoopLater("PerkSystem", new Runnable(){
        
            @Override
            public void run() {
                updatePerks();
            }
        }, 60, 20);

        log("Creating perks...");

        ItemStack is = new ItemStack(Material.MAGMA_CREAM, 1, (short) 0);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§c§lDie Todeskugel des Nether");
        ArrayList<String> l = new ArrayList<String>();
        l.add("§r");
        l.add("§e- Entfernt Feuer- und Lavaschaden");
        l.add("§e- Erzeugt Feuerschaden beim angreifen");
        l.add("§r");
        im.setLore(l);
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
        perkItems.put(Perk.Fire, is);

        is = new ItemStack(Material.PRISMARINE_CRYSTALS, 1, (short) 0);
        im = is.getItemMeta();
        im.setDisplayName("§3§lTraurige Wasserperlen");
        l = new ArrayList<String>();
        l.add("§r");
        l.add("§e- Ermöglicht das Atmen unter Wasser");
        l.add("§e- Ermöglicht das Sehen unter Wasser");
        l.add("§r");
        im.setLore(l);
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
        perkItems.put(Perk.Water, is);

        is = new ItemStack(Material.DIAMOND, 1, (short) 0);
        im = is.getItemMeta();
        im.setDisplayName("§f§lLeuchtener Stein");
        l = new ArrayList<String>();
        l.add("§r");
        l.add("§e- Nachtsicht");
        l.add("§r");
        im.setLore(l);
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
        perkItems.put(Perk.Night, is);

        is = new ItemStack(Material.SUGAR, 1, (short) 0);
        im = is.getItemMeta();
        im.setDisplayName("§b§lVerschollener Geschwindigkeitsstein");
        l = new ArrayList<String>();
        l.add("§r");
        l.add("§e- Geschwindigkeit I");
        l.add("§r");
        im.setLore(l);
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
        perkItems.put(Perk.Speed, is);

        is = new ItemStack(Material.FEATHER, 1, (short) 0);
        im = is.getItemMeta();
        im.setDisplayName("§2§lFeder der Schwerkraft");
        l = new ArrayList<String>();
        l.add("§r");
        l.add("§e- Kein Fallschaden");
        l.add("§r");
        im.setLore(l);
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
        perkItems.put(Perk.NoFall, is);

        is = new ItemStack(351, 1, (short) 1);
        im = is.getItemMeta();
        im.setDisplayName("§c§lRegenerationsstaub I");
        l = new ArrayList<String>();
        l.add("§r");
        l.add("§e- Regeneration I");
        l.add("§r");
        im.setLore(l);
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
        perkItems.put(Perk.Regeneration1, is);

        is = new ItemStack(351, 1, (short) 1);
        im = is.getItemMeta();
        im.setDisplayName("§c§lRegenerationsstaub II");
        l = new ArrayList<String>();
        l.add("§r");
        l.add("§e- Regeneration II");
        l.add("§r");
        im.setLore(l);
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
        perkItems.put(Perk.Regeneration2, is);

        is = new ItemStack(Material.EMERALD, 1, (short) 0);
        im = is.getItemMeta();
        im.setDisplayName("§c§lEiligkeitsstein I");
        l = new ArrayList<String>();
        l.add("§r");
        l.add("§e- Eile I");
        l.add("§r");
        im.setLore(l);
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
        perkItems.put(Perk.Haste1, is);

        is = new ItemStack(Material.EMERALD, 1, (short) 0);
        im = is.getItemMeta();
        im.setDisplayName("§c§lEiligkeitsstein II");
        l = new ArrayList<String>();
        l.add("§r");
        l.add("§e- Eile II");
        l.add("§r");
        im.setLore(l);
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
        perkItems.put(Perk.Haste2, is);

        is = new ItemStack(Material.EMERALD, 1, (short) 0);
        im = is.getItemMeta();
        im.setDisplayName("§c§lEiligkeitsstein III");
        l = new ArrayList<String>();
        l.add("§r");
        l.add("§e- Eile III");
        l.add("§r");
        im.setLore(l);
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
        perkItems.put(Perk.Haste3, is);

        is = new ItemStack(351, 1, (short) 6);
        im = is.getItemMeta();
        im.setDisplayName("§6§lMitnehmbare Muskelmasse");
        l = new ArrayList<String>();
        l.add("§r");
        l.add("§e- Stärke I");
        l.add("§r");
        im.setLore(l);
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
        perkItems.put(Perk.Strength, is);

        is = new ItemStack(351, 1, (short) 5);
        im = is.getItemMeta();
        im.setDisplayName("§d§lSättigungsstein");
        l = new ArrayList<String>();
        l.add("§r");
        l.add("§e- Kein Hunger");
        l.add("§r");
        im.setLore(l);
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
        perkItems.put(Perk.NoFood, is);

        ListenerSystem.register(new PerkSystem());
    }

    public static Perk checkItem(final ItemStack is) {

        if (is == null) {
            return null;
        }

        Perk p = null;

        for (final Perk pe : Perk.values()) {

            if (perkItems.containsKey(pe)) {

                final ItemStack is2 = perkItems.get(pe);

                final String name1 = is.getItemMeta().getDisplayName();
                final String name2 = is2.getItemMeta().getDisplayName();

                if (name1 == null || name2 == null) {
                    continue;
                }

                final boolean b1 = name1.equalsIgnoreCase(name2);
                final boolean b2 = is.getType().equals(is2.getType());

                if (b1 && b2) {
                    p = pe;
                }
            }
        }

        return p;
    }

    public static boolean hasPerk(final Player pl, final Perk p) {
		final ItemStack perkItem = PerkSystem.getPerkItem(p);

		for (final ItemStack is : pl.getInventory().getContents()) {
			if (is != null) {
				if (is.getType().equals(perkItem.getType())) {
					if (is.getDurability() == perkItem.getDurability()) {
						if (is.getItemMeta() != null) {
							if (perkItem.getItemMeta() != null) {
								if (is.getItemMeta().getDisplayName() != null) {
									if (perkItem.getItemMeta().getDisplayName() != null) {
                                        if (perkItem.getItemMeta().getDisplayName().contentEquals(is.getItemMeta().getDisplayName())) {
											return true;
										}
									}
								}
							}
						}
					}
				}
			}
		}

		return false;
	}
}
