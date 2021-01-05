package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Info extends Command {

    @Override
    public String getUsage(Player p) {
        return "<Spieler>";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("info");
        return names;
    }

    @Override
    public String getDescription(Player p) {
        return "Ermöglicht das Einsehen der Datenbankeinträge von Spielern.";
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isInTeam(p);
    }

    @SuppressWarnings("all")
    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) throws NullPointerException {
        if (args.length == 1) {

            UUID id = UserData.getUUIDFromName(args[0]);
            if (id == null) {
                return CommandResult.PlayerNotInDataBase;
            }
            
            String ip = UserData.get(id, UserValue.ip);
            String sex = "";
            String enlistedBy;

            try {
                enlistedBy = getChatName(toUUID(UserData.get(id, UserValue.enlistedBy)));
            } catch (Exception e) {
                enlistedBy = "?";
            }

            if (UserData.get(id, UserValue.isWomen).equals("0")) {
                sex = "Mann";
            } else {
                sex = "Frau";
            }

            sendMessage(p, "§eName: " + getChatName(id));
            sendMessage(p, "§eRang: §7" + UserData.get(id, UserValue.playerGroup));

            if (isAdmin(p)) {
                sendMessage(p, "§eIP-Adresse: §7" + UserData.get(id, UserValue.ip));
            }

            sendMessage(p, "§eUUID: §7" + id);
            sendMessage(p, "§eCoins: §7" + UserData.get(id, UserValue.coins));
            sendMessage(p, "§eBank: §7" + UserData.get(id, UserValue.coinsBank));
            sendMessage(p, "§eInvest-Level: §7" + UserData.get(id, UserValue.coinsBankInvest));
            sendMessage(p, "§eTagesbonus: §7" + UserData.get(id, UserValue.dayBonusDay));
            sendMessage(p, "§eAngeworben von: §7" + enlistedBy);
            sendMessage(p, "§eGeschlecht: §7" + sex);

            return CommandResult.None;
        }

        return CommandResult.InvalidUsage;
    }

    @Override
    public boolean isAsync() {
        return true;
    }
}