package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Heal extends Command {

	@Override
	public String getUsage(Player p) {
		return "[Spieler]";
	}

	@Override
	public ArrayList<String> getNames() {
		ArrayList<String> names = new ArrayList<String>();
		names.add("heal");
		return names;
	}

	@Override
	public boolean hasPermissions(Player p) {
		return isModerator(p);
	}

    @Override
    public String getDescription(Player p) {
        return "Heilt dich oder einen anderen Spieler.";
    }

	@Override
	public CommandResult onCommand(Player p, String[] args, String allArgs) {
		if (args.length == 0) {
			p.setHealth(p.getMaxHealth());
			p.setFoodLevel(40);
			p.setFireTicks(0);
			
			sendMessage(p, "§aDu wurdest geheilt.");
			return CommandResult.None;
		} else if (args.length == 1) {

			Player t = getPlayer(args[0]);
			
			if (t == null) {
				return CommandResult.PlayerNotOnline;
			}
			
			t.setHealth(p.getMaxHealth());
			t.setFoodLevel(40);
			t.setFireTicks(0);
			
			sendMessage(p, getChatName(t) + "§a wurde geheilt.");
			
			return CommandResult.None;
		} else {
			return CommandResult.InvalidUsage;
		}
	}
}
