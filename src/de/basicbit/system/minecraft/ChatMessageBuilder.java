package de.basicbit.system.minecraft;

import java.util.ArrayList;

public class ChatMessageBuilder {
    
    private ArrayList<String> msgs = new ArrayList<String>();
    private boolean usePrefixes = true;

    public ChatMessageBuilder(boolean usePrefixes) {
        this.usePrefixes = usePrefixes;
    }

    public void addText(String text, String hover, String cmd) {
        if (hover.isEmpty() && cmd.isEmpty()) {
            msgs.add("{'text':'" + (msgs.size() == 0 ? MySkyLABS.prefix : "") + text + "'}");
        } else if (cmd.isEmpty()) {
            addTextWithHoverEvent(text, hover);
        } else if (hover.isEmpty()) {
            addTextWithClickEvent(text, cmd);
        } else {
            addTextWithHoverClickEvent(text, hover, cmd);
        }
    }

    public void newLine() {
        msgs.add("{'text':'\n'}");
    }

    private void addTextWithHoverEvent(String text, String hover) {
        msgs.add(("{'text':'" + (msgs.size() == 0 ? MySkyLABS.prefix : "") + text + "','hoverEvent':{'action':'show_text','value':'" + hover + "'}}").replace("\\", "\\\\").replace("\\'", "\\\\'"));
    }

    private void addTextWithClickEvent(String text, String cmd) {
        msgs.add(("{'text':'" + (msgs.size() == 0 ? MySkyLABS.prefix : "") + text + "','clickEvent':{'action':'run_command','value':'" + cmd + "'}}").replace("\\", "\\\\").replace("\\'", "\\\\'"));
    }

    private void addTextWithHoverClickEvent(String text, String hover, String cmd) {
        msgs.add(("{'text':'" + (msgs.size() == 0 ? MySkyLABS.prefix : "") + text + "','hoverEvent':{'action':'show_text','value':'" + hover + "'},'clickEvent':{'action':'run_command','value':'" + cmd + "'}}").replace("\\", "\\\\").replace("\\'", "\\\\'"));
    }

    @Override
    public String toString() {
        if (usePrefixes) {
            return "[" + String.join(",", msgs).replace("\n", "\n" + MySkyLABS.prefix) + "]";
        } else {
            return "[" + String.join(",", msgs) + "]";
        }
    }
}