package de.basicbit.system.minecraft;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.entity.Player;

public class ExceptionListener extends OutputStream {

    private PrintStream stream;
    private String line = "";

    public static void init() {
        System.setErr(new PrintStream(new ExceptionListener(System.err)));
    }

    public ExceptionListener(PrintStream errorStream) {
        this.stream = errorStream;
    }

    @Override
    public void write(int b) throws IOException {
        if (b == '\n' || b == '\r') {
            if (line != "") {
                stream.println(line);

                if (line.startsWith("\t")) {
                    if (line.startsWith("\tat ")) {
                        line = line.substring(4).trim();

                        if (line.startsWith("java.base/")) {
                            line = line.substring(10);
                        }

                        int indexOfSource = line.indexOf("(");
                        String source = line.substring(indexOfSource + 1, line.indexOf(")"));
                        String file = null;
                        int lineIndex = -1;

                        if (source.contains(":")) {
                            file = source.substring(0, source.indexOf(":")).trim();
                            lineIndex = Integer.parseInt(source.substring(source.indexOf(":") + 1));
                        }

                        line = line.substring(0, indexOfSource);
                        String fullStackEntry = line;
                        line = line.substring(indexOf(Pattern.compile("(\\W|\\.)[A-Z]"), line) + 1);
                        String classLocation = fullStackEntry.substring(0, indexOf(Pattern.compile("(\\W|\\.)[A-Z]"), fullStackEntry) + 1);
                        String jar = "Unknown";

                        try {
                            jar = getRelativeFromAbsolutePath(Class.forName(classLocation + file.substring(0, file.length() - 5)).getProtectionDomain().getCodeSource().getLocation().toString().substring(6));
                        } catch (Exception e) { }

                        String hoverText = "§7Source: " + (file == null ? "Unknown" : "src/" + classLocation.replace(".", "/") + file) + "\n§7Jar: " + jar + "\n§7Line: " + (lineIndex != -1 ? lineIndex + "" : "?");
                        line = "[{'text':'§c\tat '},{'text':'§c" + line + "','hoverEvent':{'action':'show_text','value':'" + hoverText + "'}}]";
                    } else {
                        line = "{'text':'§c" + line + "'}";
                    }
                } else {
                    boolean causedBy = false;
                    if (line.startsWith("Caused by: ")) {
                        line = line.substring(11);
                        causedBy = true;
                    }

                    boolean hasMessage = line.contains(":");
                    String[] splittedLine = line.split(":");
                    String message = hasMessage ? splittedLine[1].trim() : "The exception contains no message.";
                    String fullClassName = hasMessage ? splittedLine[0].trim() : line.trim();
                    String simpleClassName = fullClassName.contains(".") ? fullClassName.substring(fullClassName.lastIndexOf(".") + 1) : fullClassName;
                    String hoverText = "§7Message: §c" + message + "\n§7Class: " + fullClassName.substring(0, fullClassName.length() - simpleClassName.length()) + "§e" + simpleClassName;

                    line = "[" + (causedBy ? "{'text':'§cCaused by: '}," : "") + "{'text':'§e" + simpleClassName + "','hoverEvent':{'action':'show_text','value':'" + hoverText + "'}}]";
                }

                line = line.replace("\t", "    ");

                for (Player p : Utils.getPlayers()) {
                    if (MySkyLABS.debugMode || Utils.isDev(p)) {
                        Utils.sendChatBaseComponent(p, line);
                    }
                }
            }
            line = "";
        } else {
            line += (char)b;
        }
    }
    
    private static int indexOf(Pattern pattern, String s) {
        Matcher matcher = pattern.matcher(s);
        return matcher.find() ? matcher.start() : 0;
    }

    private static String getRelativeFromAbsolutePath(String absolute) {
        return new File("./").toURI().relativize(new File(absolute).toURI()).getPath();
    }
}