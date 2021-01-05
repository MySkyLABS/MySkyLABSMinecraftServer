package de.basicbit.system.minecraft;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.io.Files;

import org.bukkit.entity.Player;

public class MoneyLogger {

    private static final SimpleDateFormat logFormat = new SimpleDateFormat("[dd-MM-yyyy HH:mm:ss]");
    private static final int LOG_CAP = 0xFF;
    private static final String LINE_SEPARATOR = System.lineSeparator();

    public static void log(String message, Player p) throws IOException, IllegalArgumentException {
        PrintWriter out = new PrintWriter(new FileWriter("./logs/" + p.getUniqueId() + "/Kontoauszug.log", true), true);
        out.print(message);
        out.close();
    }

    public static void write(Player p, int c, String reason) throws IllegalArgumentException, IOException {
        File folder = new File("./logs/" + Utils.toTrimmed(p.getUniqueId()));
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File logFile = new File("./logs/" + Utils.toTrimmed(p.getUniqueId()) + "/money.log");
        List<String> lines = null;
        
        if (logFile.exists()) {
            lines = Files.readLines(logFile, StandardCharsets.US_ASCII);
        } else {
            lines = new ArrayList<>();
        }

        while (lines.size() >= LOG_CAP) {
            lines.remove(0);
        }

        String date = logFormat.format(new Date());
        String coins = c + " Coins";
        String newLine = (date + reason + ": " + coins + "\n");
        lines.add(newLine);
        Files.write(lines.stream().collect(Collectors.joining(LINE_SEPARATOR)), logFile, StandardCharsets.US_ASCII);
    }
}