package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.World;
import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.skyblock.SkyBlock;

public class IsKick extends Command {

    @Override
    public String getUsage(Player p) {
        return "<Player>";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("iskick");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return true;
    }

    @Override
    public String getDescription(Player p) {
        return "Kickt Spieler von der Insel, wo sie drauf sind.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 1) {
            Player t = getPlayer(args[0]);

			if (t == null) {
				return CommandResult.PlayerNotOnline;
            }

            World w = t.getWorld();
            if (isSkyblockWorld(w)) {
                UUID id = getUUIDFromSkyBlockWorld(w);
                int number = getNumberFromSkyBlockWorld(w);

                if (p.getUniqueId().equals(id) || isInTeam(p)) {
                    if (p.getUniqueId().equals(t.getUniqueId())) {
                        sendMessage(p, "§cDu kannst dich nicht selbst kicken.");
                        return CommandResult.None;
                    }

                    if (isInTeam(t)) {
                        return CommandResult.InvalidPermissions;
                    }

                    t.teleport(getSpawn());
                    sendMessage(t, "§cDu wurdest von der Welt gekickt.");
                    sendMessage(p, getChatName(t) + "§a wurde von der Welt gekickt.");
                } else if (SkyBlock.isTrusted(id, number, p)) {
                    if (p.getUniqueId().equals(t.getUniqueId())) {
                        sendMessage(p, "§cDu kannst dich nicht selbst kicken.");
                        return CommandResult.None;
                    }
                    
                    if (isInTeam(t) || isOwnerOfWorld(w, t) || SkyBlock.isTrusted(id, number, t)) {
                        return CommandResult.InvalidPermissions;
                    }

                    t.teleport(getSpawn());
                    sendMessage(t, "§cDu wurdest von der Welt gekickt.");
                    sendMessage(p, getChatName(t) + "§a wurde von der Welt gekickt.");
                } else {
                    return CommandResult.InvalidPermissions;
                }
            } else {
                sendMessage(p, "§cDer Spieler ist auf keiner Welt.");
            }

            return CommandResult.None;
		} else {
            return CommandResult.InvalidUsage;
        }
    }
}