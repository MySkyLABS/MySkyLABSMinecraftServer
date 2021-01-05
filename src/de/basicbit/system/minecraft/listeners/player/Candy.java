package de.basicbit.system.minecraft.listeners.player;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.basicbit.system.minecraft.Listener;

@SuppressWarnings("deprecation")
public class Candy extends Listener {

    public static int getRandomPotionEffect() {

        int[] intArray = { 1, 3, 5, 8, 10, 11, 12, 13, 14, 16, 21, 22, 23 };

        int effect = new Random().nextInt(intArray.length);
        return intArray[effect];
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action action = e.getAction();

        if ((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) && e.hasItem()) {
            ItemStack is = e.getItem();

            if (is.getTypeId() == 397 && is.hasItemMeta()) {
                int count = is.getAmount();
                ItemMeta im = is.getItemMeta();

                if (im.hasDisplayName() // Cake
                        && im.getDisplayName().contentEquals(getCakeItemStack(1).getItemMeta().getDisplayName())) {
                            e.setCancelled(true);
                    if (count == 1) {
                        if (p.getFoodLevel() >= 20) {
                            sendMessage(p, "§cDu bist bereits satt.");
                            sendMessage(p, "§cBitte verschwende nicht deine Kuchen. >:(");
                        } else {
                            sendMessage(p, "§aGuten Hunger!");

                            p.setFoodLevel(p.getFoodLevel() + 14);

                            p.setItemInHand(null);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 30 * 20, 0), true);
                        }
                    } else {
                        if (p.getFoodLevel() >= 20) {
                            sendMessage(p, "§cDu bist bereits satt.");
                            sendMessage(p, "§cBitte verschwende nicht deine Kuchen. >:(");
                        } else {
                            sendMessage(p, "§aGuten Hunger!");

                            p.setFoodLevel(p.getFoodLevel() + 14);

                            is.setAmount(count - 1);
                            p.setItemInHand(is);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 30 * 20, 0), true);
                        }
                    }
                } else if (im.hasDisplayName() // Cupcake
                        && im.getDisplayName().contentEquals(getCupCakeItemStack(1).getItemMeta().getDisplayName())) {
                            e.setCancelled(true);
                    if (count == 1) {
                        if (p.getFoodLevel() >= 20) {
                            sendMessage(p, "§cDu bist bereits satt.");
                            sendMessage(p, "§cBitte verschwende nicht deine Cupcakes. >:(");
                        } else {
                            sendMessage(p, "§aGuten Hunger!");

                            p.setFoodLevel(p.getFoodLevel() + 14);

                            p.setItemInHand(null);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 10 * 20, 0), true);
                        }
                    } else {
                        if (p.getFoodLevel() >= 20) {
                            sendMessage(p, "§cDu bist bereits satt.");
                            sendMessage(p, "§cBitte verschwende nicht deine Cupcakes. >:(");
                        } else {
                            sendMessage(p, "§aGuten Hunger!");

                            p.setFoodLevel(p.getFoodLevel() + 14);

                            is.setAmount(count - 1);
                            p.setItemInHand(is);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 10 * 20, 0), true);
                        }
                    }
                } else if (im.hasDisplayName() // Donut
                        && im.getDisplayName().contentEquals(getDonutItemStack(1).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    if (count == 1) {
                        if (p.getFoodLevel() >= 20) {
                            sendMessage(p, "§cDu bist bereits satt.");
                            sendMessage(p, "§cBitte verschwende nicht deine Donuts. >:(");
                        } else {
                            sendMessage(p, "§aGuten Hunger!");

                            p.setFoodLevel(p.getFoodLevel() + 12);
                            p.setItemInHand(null);
                            p.addPotionEffect(
                                    new PotionEffect(PotionEffectType.getById(getRandomPotionEffect()), 10 * 20, 0),
                                    true);
                        }
                    } else {
                        if (p.getFoodLevel() >= 20) {
                            sendMessage(p, "§cDu bist bereits satt.");
                            sendMessage(p, "§cBitte verschwende nicht deine Donuts. >:(");
                        } else {
                            sendMessage(p, "§aGuten Hunger!");

                            p.setFoodLevel(p.getFoodLevel() + 12);

                            is.setAmount(count - 1);
                            p.setItemInHand(is);
                            p.addPotionEffect(
                                    new PotionEffect(PotionEffectType.getById(getRandomPotionEffect()), 10 * 20, 0),
                                    true);
                        }
                    }
                } else if (im.hasDisplayName() // Pancake
                        && im.getDisplayName().contentEquals(getPancakeItemStack(1).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    if (count == 1) {
                        if (p.getFoodLevel() >= 20) {
                            sendMessage(p, "§cDu bist bereits satt.");
                            sendMessage(p, "§cBitte verschwende nicht deine Pancakes. >:(");
                        } else {
                            sendMessage(p, "§aGuten Hunger!");

                            p.setFoodLevel(p.getFoodLevel() + 15);

                            p.setItemInHand(null);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 15 * 20, 0), true);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 15 * 20, 0), true);
                        }
                    } else {
                        if (p.getFoodLevel() >= 20) {
                            sendMessage(p, "§cDu bist bereits satt.");
                            sendMessage(p, "§cBitte verschwende nicht deine Pancakes. >:(");
                        } else {
                            sendMessage(p, "§aGuten Hunger!");

                            p.setFoodLevel(p.getFoodLevel() + 15);

                            is.setAmount(count - 1);
                            p.setItemInHand(is);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 15 * 20, 0), true);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 15 * 20, 0), true);
                        }
                    }
                } else if (im.hasDisplayName() // Cookie
                        && im.getDisplayName().contentEquals(getCookieItemStack(1).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    if (count == 1) {
                        if (p.getFoodLevel() >= 20) {
                            sendMessage(p, "§cDu bist bereits satt.");
                            sendMessage(p, "§cBitte verschwende nicht deine Kekse. >:(");
                        } else {
                            p.setFoodLevel(40);
                            p.setItemInHand(null);

                            if (!isVanish(p)) {
                                sendMessageToAllPlayers(getChatName(p) + "§e krümelt.");
                            }
                        }
                    } else {
                        if (p.getFoodLevel() >= 20) {
                            sendMessage(p, "§cDu bist bereits satt.");
                            sendMessage(p, "§cBitte verschwende nicht deine Kekse. >:(");
                        } else {
                            p.setFoodLevel(40);

                            is.setAmount(count - 1);
                            p.setItemInHand(is);

                            if (!isVanish(p)) {
                                sendMessageToAllPlayers(getChatName(p) + "§e krümelt.");
                            }
                        }
                    }
                } else if (im.hasDisplayName() // KidSurprise
                        && im.getDisplayName()
                                .contentEquals(getKidSurpriseItemStack(1).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    if (count == 1) {
                        if (isInventoryFull(p)) {
                            sendMessage(p, "§cDu hast nicht genug Platz im Inventar.");
                        } else {
                            sendMessage(p, "§aGlückwunsch!");

                            p.setItemInHand(null);
                            giveItemToPlayer(p, getChocolateItemStack(1));
                            int random = (int) (Math.random() * (64 - 1 + 1) + 1);
                            giveItemToPlayer(p, getMajoItemStack(random));
                        }
                    } else {
                        if (isInventoryFull(p)) {
                            sendMessage(p, "§cDu hast nicht genug Platz im Inventar.");
                        } else {
                            sendMessage(p, "§aGlückwunsch!");

                            is.setAmount(count - 1);
                            p.setItemInHand(is);
                            giveItemToPlayer(p, getChocolateItemStack(1));
                            int random = (int) (Math.random() * (64 - 1 + 1) + 1);
                            giveItemToPlayer(p, getMajoItemStack(random));
                        }
                    }
                } else if (im.hasDisplayName() // Chocolate
                        && im.getDisplayName().contentEquals(getChocolateItemStack(1).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    if (count == 1) {
                        if (p.getFoodLevel() >= 20) {
                            sendMessage(p, "§cDu hast nicht genug Platz im Inventar.");
                        } else {
                            sendMessage(p, "§aGuten Hunger!");

                            p.setFoodLevel(p.getFoodLevel() + 18);
                            p.setItemInHand(null);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 5 * 20, 0), true);
                        }
                    } else {
                        if (p.getFoodLevel() >= 20) {
                            sendMessage(p, "§cDu hast nicht genug Platz im Inventar.");
                        } else {
                            sendMessage(p, "§aGuten Hunger!");

                            p.setFoodLevel(p.getFoodLevel() + 8);

                            is.setAmount(count - 1);
                            p.setItemInHand(is);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 5 * 20, 0), true);
                        }
                    }
                } else if (im.hasDisplayName() // Honey
                        && im.getDisplayName().contentEquals(getHoneyItemStack(1).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    if (count == 1) {
                        if (p.getFoodLevel() >= 20) {
                            sendMessage(p, "§cDu bist bereits satt.");
                            sendMessage(p, "§cBitte verschwende nicht deinen Honig. >:(");
                        } else {
                            sendMessage(p, "§aGuten Hunger!");

                            p.setFoodLevel(p.getFoodLevel() + 18);
                            p.setItemInHand(null);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10 * 20, 1), true);
                        }
                    } else {
                        if (p.getFoodLevel() >= 20) {
                            sendMessage(p, "§cDu bist bereits satt.");
                            sendMessage(p, "§cBitte verschwende nicht deinen Honig. >:(");
                        } else {
                            sendMessage(p, "§aGuten Hunger!");

                            p.setFoodLevel(p.getFoodLevel() + 6);

                            is.setAmount(count - 1);
                            p.setItemInHand(is);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10 * 20, 1), true);
                        }
                    }
                } else if (im.hasDisplayName() // Popcorn
                        && im.getDisplayName().contentEquals(getPopcornItemStack(1).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    if (count == 1) {
                        if (p.getFoodLevel() >= 20) {
                            sendMessage(p, "§cDu bist bereits satt.");
                            sendMessage(p, "§cBitte verschwende nicht dein Popkorn. >:(");
                        } else {
                            sendMessage(p, "§aGuten Hunger!");

                            p.setFoodLevel(p.getFoodLevel() + 4);
                            p.setItemInHand(null);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 10 * 20, 0), true);
                        }
                    } else {
                        if (p.getFoodLevel() >= 20) {
                            sendMessage(p, "§cDu bist bereits satt.");
                            sendMessage(p, "§cBitte verschwende nicht dein Popkorn. >:(");
                        } else {
                            sendMessage(p, "§aGuten Hunger!");

                            p.setFoodLevel(p.getFoodLevel() + 4);

                            is.setAmount(count - 1);
                            p.setItemInHand(is);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 10 * 20, 0), true);
                        }
                    }
                } else if (im.hasDisplayName() // Magic Mushroom
                        && im.getDisplayName().contentEquals(getMushroomItemStack(1).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    if (count == 1) {
                        if (p.getFoodLevel() >= 20) {
                            sendMessage(p, "§cDu bist bereits satt.");
                            sendMessage(p, "§cBitte verschwende nicht deine Pilze. >:(");
                        } else {
                            sendMessage(p, "§aGuten Hunger!");

                            p.setFoodLevel(p.getFoodLevel() + 8);
                            p.setItemInHand(null);

                            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 15 * 20, 0), true);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 15 * 20, 2), true);
                        }
                    } else {
                        if (p.getFoodLevel() >= 20) {
                            sendMessage(p, "§cDu bist bereits satt.");
                            sendMessage(p, "§cBitte verschwende nicht deine Pilze. >:(");
                        } else {
                            sendMessage(p, "§aGuten Hunger!");

                            p.setFoodLevel(p.getFoodLevel() + 8);

                            is.setAmount(count - 1);
                            p.setItemInHand(is);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 15 * 20, 0), true);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 15 * 20, 2), true);
                        }
                    }
                } else if (im.hasDisplayName() // Cola
                        && im.getDisplayName().contentEquals(getColaItemStack(1).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    if (count == 1) {
                        if (p.getFoodLevel() >= 20) {
                            sendMessage(p, "§cDu bist bereits satt.");
                            sendMessage(p, "§cBitte verschwende nicht deine Cola. >:(");
                        } else {
                            sendMessage(p, "§aGuten Hunger!");

                            p.setFoodLevel(p.getFoodLevel() + 14);
                            p.setItemInHand(null);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30 * 20, 0), true);
                        }
                    } else {
                        if (p.getFoodLevel() >= 20) {
                            sendMessage(p, "§cDu bist bereits satt.");
                            sendMessage(p, "§cBitte verschwende nicht deine Cola. >:(");
                        } else {
                            sendMessage(p, "§aGuten Durst!");

                            p.setFoodLevel(p.getFoodLevel() + 14);

                            is.setAmount(count - 1);
                            p.setItemInHand(is);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30 * 20, 0), true);
                        }
                    }
                }
            }
        }
    }
}