package de.basicbit.system.minecraft.commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.bukkit.entity.Player;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.DiscordBot;
import de.basicbit.system.minecraft.Gender;
import de.basicbit.system.minecraft.Group;
import de.basicbit.system.minecraft.PlayerReport;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import de.basicbit.system.minecraft.ChatMessageBuilder;

public class ReportAccept extends Command {
	
	private static final long reportChannelID = 741380439352016896L;

    @Override
    public String getUsage(Player p) {
        return "<ID>";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("reportaccept");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isSupporter(p);
    }

    @Override
    public String getDescription(Player p) {
        return "Nimmt ein Report an.";
    }

    @Override
    @SuppressWarnings("deprecation")
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 1) {
            int id = hexToInt(args[0]);
            
            if (id == 0) {
                sendMessage(p, "§cDu hast eine ungültige ID Verwendet.");
                return CommandResult.None;
            }

            if (GlobalValues.playerReports.containsKey(id)) {
                PlayerReport playerReport = GlobalValues.playerReports.get(id);
                UUID uuid = playerReport.getReporter();
                Gender gender = getGender(uuid);
                Group group = getGroup(uuid);

                String reporterName = UserData.get(uuid, UserValue.playerName);

                String reporterChatName = getChatName(group, gender, reporterName);

                uuid = playerReport.getCheater();
                gender = getGender(uuid);
                group = getGroup(uuid);

                String cheaterName = UserData.get(uuid, UserValue.playerName);

                String cheaterChatName = getChatName(group, gender, cheaterName);

                Date date = new Date(playerReport.getTimestamp());
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

                ChatMessageBuilder cmb = new ChatMessageBuilder(true);
                cmb.addText("§eID§7: " + args[0], "", "");
                cmb.newLine();
                cmb.addText("§eMelder§7: " + reporterChatName, "", "");
                cmb.newLine();
                cmb.addText("§eGemeldeter§7: " + cheaterChatName, "", "");
                cmb.newLine();
                cmb.addText("§eGrund§7: " + playerReport.getReason(), "", "");
                cmb.newLine();
                cmb.addText("§eMeldungszeit§7: " + sb.toString(), "", "");
                sendChatBaseComponent(p, cmb);
                GlobalValues.playerReports.remove(id);
                
                EmbedBuilder eb = new EmbedBuilder();
                
                eb.setColor(new Color(0, 255, 0)).setTitle("Report").addField("ID", args[0], true).addField("Gemeldeter", cheaterName, false).addField("Melder", reporterName, false).addField("Grund", playerReport.getReason(), false).addField("Zeitpunkt", sb.toString(), false).addField("Status", "Bearbeitet von " + p.getName() , false);
                
                JDA jda = DiscordBot.getJda();
                
                jda.getTextChannelById(reportChannelID).editMessageById(playerReport.getMessageId(), eb.build()).complete();
                
                Player reporter = getPlayer(playerReport.getReporter());
                if (reporter != null) {
                	sendMessage(reporter, "§7Dein Report wird bearbeitet.");
                }
            } else {
                sendMessage(p, "§cDieser Report wird bereits bearbeitet.");
            }
        }

        return CommandResult.None;
    }
}