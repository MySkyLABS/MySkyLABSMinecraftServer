package de.basicbit.system.minecraft.commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroup;
import com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroupClient;

import org.bukkit.entity.Player;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.ChatMessageBuilder;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.DiscordBot;
import de.basicbit.system.minecraft.MySkyLABS;
import de.basicbit.system.minecraft.PlayerReport;
import de.basicbit.system.minecraft.tasks.TaskManager;
import de.basicbit.system.teamspeak.Teamspeak;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;

public class Report extends Command {

    private static ArrayList<Integer> reportGroupIds = createListFromObjects(85, 58, 48, 45, 79);
    private static ArrayList<ServerGroup> reportGroups = createListFromObjects();
    private static TS3Api ts = Teamspeak.getTeamSpeakAPI();
    private static final long reportChannelID = 741380439352016896L;
    
    public Report() {
        if (!MySkyLABS.debugMode) {
            for (ServerGroup group : ts.getServerGroups()) {
                if (reportGroupIds.contains(group.getId())) {
                    reportGroups.add(group);
                }
            }
        }
    }

    @Override
    public String getUsage(Player p) {
        return "<Spieler> ";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("report");
        names.add("melden");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return true;
    }

    @Override
    public String getDescription(Player p) {
        return "Meldet andere Spieler.";
    }

    @SuppressWarnings("deprecation")
    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length < 2) {
            return CommandResult.InvalidUsage;
        }

        Player cheater = getPlayer(args[0]);

        if (cheater == null) {
            return CommandResult.PlayerNotOnline;
        }

        UUID id = p.getUniqueId();

        if (GlobalValues.reportBlocked.contains(id)) {
            
            sendMessage(p, "§cBitte warte noch etwas, bis du diesen Command wieder verwendest.");

            return CommandResult.None;
        } else {
            GlobalValues.reportBlocked.add(id);
        }

        TaskManager.runAsyncTaskLater(new Runnable() {

            @Override
            public void run() {
                if (GlobalValues.reportBlocked.contains(id)) {
                    GlobalValues.reportBlocked.remove(id);
                }
            }
            
        }, 3600);

        String reason = allArgs.substring(args[0].length() + 1, allArgs.length()).trim();
        int reportId = GlobalValues.magicValue ^ (++GlobalValues.playerReportCounter);

        String hexID = intToHex(reportId).toUpperCase();
        
        boolean foundPlayer = false;
        for (Player player : getPlayers()) {
            if (isSupporter(player)) {
                foundPlayer = true;
                //hab das geändert da man das nicht sehen muss hab backup falls du/ihr das anders siehst/seht Crepper710
                ChatMessageBuilder cmb = new ChatMessageBuilder(true);
                cmb.addText("§eNeuer Report", "§eID§7: " + hexID, "");
                cmb.newLine();
                cmb.addText("§a§lReport bearbeiten!", "§eAkzeptiere den Report", "/reportaccept " + hexID);
                sendChatBaseComponent(player, cmb);
            }
        }

        Date date = new Date(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder();
        int month = date.getMonth() + 1;
        int day = date.getDate();
        int hours = date.getHours();
        int mins = date.getMinutes();

        sb.append(day < 10 ? "0" + day : day)
                .append('.')
                .append(month < 10 ? "0" + month : month)
                .append('.')
                .append(date.getYear() + 1900)
                .append(' ')
                .append(hours < 10 ? "0" + hours : hours)
                .append(':')
                .append(mins < 10 ? "0" + mins : mins);
        
        String dateString = sb.toString();

        if (!foundPlayer) {
            for (ServerGroup group : reportGroups) {
                for (ServerGroupClient client : ts.getServerGroupClients(group)) {
                    String uid = client.getUniqueIdentifier();
                    if (ts.isClientOnline(uid)) {
                        int clientId = ts.getClientByUId(uid).getId();
                        //hier das gleiche wie oben schon
                        ts.sendPrivateMessage(clientId, "[color=#FF2222]Neuer Report ID:" + hexID + "[/color]");
                    }
                }
            }
        }
        
        EmbedBuilder eb = new EmbedBuilder();
        
        eb.setColor(new Color(255, 0, 0)).setTitle("Report").addField("ID", hexID, true).addField("Gemeldeter", cheater.getName(), false).addField("Melder", p.getName(), false).addField("Grund", reason, false).addField("Zeitpunkt", dateString, false).addField("Status", "Nicht bearbeitet", false);
        
        JDA jda = DiscordBot.getJda();
        
        Message m = jda.getTextChannelById(reportChannelID).sendMessage(eb.build()).complete();

        GlobalValues.playerReports.put(reportId, new PlayerReport(cheater.getUniqueId(), p.getUniqueId(), reason, System.currentTimeMillis(), m.getIdLong()));

        return CommandResult.None;
    }
}