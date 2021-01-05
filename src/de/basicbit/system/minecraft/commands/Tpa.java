package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.ChatMessageBuilder;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Tpa extends Command {

    @Override
    public String getUsage(Player p) {
        return "<Annehmen/Ablehnen> <Spieler>";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("tpa");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return true;
    }

    @Override
    public String getDescription(Player p) {
        return "Teleportiert dich zu einem Spieler.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 1) {
            if (p instanceof Player) {
                if (args.length == 1) {
                    switch (args[0]) {
                        case "annehmen":
                            if (GlobalValues.tpaRequests.get(p.getName()) != null) {
                                Player t = Bukkit.getPlayer(GlobalValues.tpaRequests.get(p.getName()));
                                if (t != null) {
                                    teleportWithCooldown(t, p.getLocation());
                                    sendMessage(p, getChatName(t) + "§a wird teleportiert!");
                                    GlobalValues.tpaRequests.remove(p.getName());
                                } else {
                                    return CommandResult.PlayerNotOnline;
                                }
                            } else {
                                sendMessage(p, "§cDu hast keine Teleportanfrage");
                            }
                            break;
                        case "ablehnen":
                            if (GlobalValues.tpaRequests.get(p.getName()) != null) {
                                Player t = Bukkit.getPlayer(GlobalValues.tpaRequests.get(p.getName()));
                                if (t != null) {
                                    sendMessage(t, getChatName(p) + "§c hat deine Teleportanfrage abgelehnt");
                                    sendMessage(p, "§cDie Teleportanfrage von " + getChatName(t) + "§c wurde abgelehnt!");
                                    GlobalValues.tpaRequests.remove(p.getName());
                                } else {
                                    return CommandResult.PlayerNotOnline;
                                }
                            } else {
                                sendMessage(p, "§cDu hast keine Teleportanfragen");
                            }
                            break;
                        default:
                            Player t = Bukkit.getPlayer(args[0]);
                            if (t != null) {
                                if (!(t.getName().equals(p.getName()))) {
                                    if (GlobalValues.tpaRequests.get(t.getName()) == null) {
                                        sendMessage(p, "§aDu hast eine Teleportanfrage an " + getChatName(t) + "§a gesendet.");
                                        ChatMessageBuilder builder = new ChatMessageBuilder(true);
                                        builder.addText("§aDu hast eine Teleportanfrage von " + getChatName(p) + " §abekommen!", "", "");
                                        builder.addText(" §8[§a§lAnnehmen§8]", "§aTeleportanfrage annehmen", "/tpa annehmen");
                                        builder.addText(" ", "", "");
                                        builder.addText(" §8[§c§lAblehnen§8]", "§cTeleportanfrage ablehnen", "/tpa ablehnen");
                                        sendChatBaseComponent(t, builder);
                                        GlobalValues.tpaRequests.put(t.getName(), p.getName());
                                    } else {
                                        sendMessage(p, "§cDu hast diesem Spieler bereits eine Teleportanfrage gesendet!");
                                    }
                                } else {
                                    sendMessage(p, "§cDu kannst dir selbst keine Teleportanfrage senden!");
                                }
                            } else {
                                return CommandResult.PlayerNotOnline;
                            }
                            break;
                    }
                } else {
                    return CommandResult.InvalidUsage;
                }
            }
        } else {
            return CommandResult.InvalidUsage;
        }
        return CommandResult.None;
    }
}
