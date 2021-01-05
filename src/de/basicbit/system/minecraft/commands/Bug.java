package de.basicbit.system.minecraft.commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.DiscordBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;

public class Bug extends Command {
	
    private static final long reportChannelID = 749646705137418300L;

	@Override
	public String getUsage(Player p) {
		return "<Nachricht>";
	}

	@Override
	public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("bug");
        return names;
	}

	@Override
	public String getDescription(Player p) {
		return "Meldet eine Bug.";
	}

	@Override
	public boolean hasPermissions(Player p) {
		return true;
	}

    @SuppressWarnings("deprecation")
	@Override
	public CommandResult onCommand(Player p, String[] args, String allArgs) {
		if (args.length == 0) {
			return CommandResult.InvalidUsage;
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
        
        EmbedBuilder eb = new EmbedBuilder();
        
        eb.setColor(new Color(255, 165, 0)).setTitle("Bug").addField("Melder", p.getName(), false).addField("Bug", allArgs, false).addField("Zeitpunkt", dateString, false);
        
        JDA jda = DiscordBot.getJda();
        
        jda.getTextChannelById(reportChannelID).sendMessage(eb.build()).complete();

        sendMessage(p, "§aDein Bug wurde erfolgreich den Entwicklern mitgeteilt.");
        
		return CommandResult.None;
	}
	
	
	
}
