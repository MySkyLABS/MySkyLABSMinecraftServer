package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Pay extends Command {

    @Override
    public String getUsage(Player p) {
        return "<Spieler> <Coins>";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("pay");
        return names;
    }

    @Override
    public String getDescription(Player p) {
        return "Mit diesem Command kannst du anderen Coins geben.";
    }

    @Override
    public boolean hasPermissions(Player p) {
        return true;
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 2) {

            Player t = getPlayer(args[0]);

            if (t == null) {
                return CommandResult.PlayerNotOnline;
            }

            if (p.getUniqueId().equals(t.getUniqueId())) {
                sendMessage(p, "§cWas soll denn das werden? xD");
                return CommandResult.None;
            }

            int coins = -1;

            try {
                coins = Integer.parseInt(args[1]);
            } catch (Exception e) { }

            if (coins <= 0) {
                return CommandResult.InvalidNumber;
            }

            if (hasCoins(p, coins)) {
                removeCoins(p, coins, " PayCommand sendMoney­");
                sendMessage(p, "§aDu hast " + getChatName(t) + "§e " + (coins == 1 ? "ein Coin" : 
                (coins == Integer.MAX_VALUE ? "unendlich viele Coins" : coins + " Coins")) + "§a gezahlt.");

                addCoins(t, coins, " PayCommand recieveMoney­");
                sendMessage(t, "§aDu hast §e" + (coins == 1 ? "ein Coin" : 
                (coins == Integer.MAX_VALUE ? "unendlich viele Coins" : coins + " Coins")) + "§a von " + getChatName(p) + "§a erhalten.");
            } else {
                return CommandResult.NotEnoughCoins;
            }

            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}