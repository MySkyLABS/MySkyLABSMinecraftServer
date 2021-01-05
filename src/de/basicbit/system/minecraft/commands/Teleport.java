package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.Group;

public class Teleport extends Command {

    @Override
    public String getUsage(Player p) {
        return getGroup(p) == Group.TEST_SUPPORTER ? "<Spieler>" : "<Spieler> [Spieler]";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("teleport");
        names.add("tp");
        return names;
    }

    @Override
    public String getDescription(Player p) {
        return getGroup(p) == Group.TEST_SUPPORTER ? "Du kannst dich teleportieren." : "Teleportiert einen Spieler.";
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isInTeam(p);
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 1) { // Ein Wort eingegeben
            String name = args[0];//argument1 und mach daraus ein Spieler
            Player t = getPlayer(name);
            
            if (t != null) { // Prüfe ob Spieler Online
                p.teleport(t.getLocation());// setzte deine Koordinaten zu Spieler Koordinaten 
                sendMessage(p, "Du hast dich zu " + getChatName(t) + " teleportiert.");// Meldung
            } else {
                return CommandResult.PlayerNotOnline;// Meldung Spieler ist nicht Online
            }
            return CommandResult.None;// Funktion verlassen
            
        } else if (args.length == 2) { // Zwei Wörter eingegeben
            if (!isSupporter(p) && !isBuilder(p) && !isContent(p)) {//Prüfe ob Rang des Ausführenden T-Sup ist
                return CommandResult.InvalidPermissions;// Befehl falsch eingegeben//Funktion beenden
            } else { //Ausführender ist nicht T-Sup
                String player1 = args[0];
                String player2 = args[1];
                Player t = getPlayer(player1);
                Player s = getPlayer(player2);//Hol dir beide Argumente und mach daraus spieler
                if (t != null && s != null) {//prüfe ob beide Spieler online sind
                    t.teleport(s.getLocation());//setzt Spieler1 Koordinaten zu Spieler2 Koordinaten
                    sendMessage(p, "Du hast " + getChatName(t) + " zu " + getChatName(s) + " teleportiert.");//Meldung du hast Spieler1 zu Spieler2 teleportiert
                } else {
                    return CommandResult.PlayerNotOnline;//Meldung Spieler sind nicht Online
                }
                
            }

            return CommandResult.None;
        }

        return CommandResult.InvalidUsage;// Befehl falsch eingegeben
    }
}
