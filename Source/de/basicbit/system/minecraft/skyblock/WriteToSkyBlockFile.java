package de.basicbit.system.minecraft.skyblock;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import org.bukkit.Location;

import de.basicbit.system.minecraft.Utils;

public class WriteToSkyBlockFile {

    public static void write(String coords, int number, UUID id) throws IOException, IllegalArgumentException {
        File folder = new File(Utils.toTrimmed(id) + "#" + number);
        File file = new File("./" + folder + "/holograms.txt");

        if (folder.exists()) {
            if (!file.exists()) {
                file.createNewFile();
            }
            Files.write(Paths.get("./" + folder + "/holograms.txt"), coords.getBytes(), StandardOpenOption.APPEND);
        }
    }

    public static void remove(UUID id, int number, Location loc) throws IOException {
        File folder = new File(Utils.toTrimmed(id) + "#" + number);
        File inputFile = new File("./" + folder + "/holograms.txt");
        File tempFile = new File("./" + folder + "/holograms.txt.temp");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        String lineToRemove = x + ";" + y + ";" + z;
        String currentLine;

        while ((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine.trim();
            if (!trimmedLine.equals(lineToRemove)) {
                writer.write(currentLine + "\n");
            }
        }

        writer.close();
        reader.close();
        inputFile.delete();
        tempFile.renameTo(inputFile);
    }
}
