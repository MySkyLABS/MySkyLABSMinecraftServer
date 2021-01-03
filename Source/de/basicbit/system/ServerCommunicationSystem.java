package de.basicbit.system;

import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;

import de.basicbit.system.minecraft.Utils;
import de.basicbit.system.minecraft.events.ServerCommunicationSystemMessageEvent;
import de.basicbit.system.minecraft.tasks.TaskManager;

public class ServerCommunicationSystem extends Utils {

    private static Socket socket;

    public static void init() {
        try {
            socket = new Socket("127.0.0.1", 25567);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        TaskManager.runAsyncLoop("ServerCommunicationSystem", new Runnable() {
        
            @Override
            public void run() {
                try {
                    final InputStream input = socket.getInputStream();
                    
                    if (input.available() >= 4) {
                        byte[] buffer = new byte[4];
                        input.read(buffer, 0, 4);
                        final int length = getInt(buffer, 0);

                        buffer = new byte[length];
                        input.read(buffer, 0, buffer.length);

                        final String cmd = new String(buffer, StandardCharsets.UTF_8);
                        log("ServerCommunicationSystem: " + cmd);

                        onCommand(cmd);
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1);
    }

    public static void onCommand(final String cmd) {
        TaskManager.runSyncTask("ServerCommunicationSystemMessageEventCall", new Runnable() {
        
            @Override
            public void run() {
                Bukkit.getPluginManager().callEvent(new ServerCommunicationSystemMessageEvent(cmd));
            }
        });
    }

    public static void sendCommand(final String cmd) {
        final byte[] msgBuffer = cmd.getBytes(StandardCharsets.UTF_8);
        final byte[] lengthBuffer = getBytes(msgBuffer.length);
        final byte[] buffer = ArrayUtils.addAll(lengthBuffer, msgBuffer);
        
        try {
            socket.getOutputStream().write(buffer);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] getBytes(final int value) {
        final ByteBuffer buffer = ByteBuffer.allocate(4).order(ByteOrder.nativeOrder());
        buffer.putInt(value);
        return buffer.array();
    }

    public static int getInt(final byte[] bytes, final int index) {
        return (int) ((int) (0xff & bytes[index]) << 32 | (int) (0xff & bytes[index + 1]) << 40 | (int) (0xff & bytes[index + 2]) << 48 | (int) (0xff & bytes[index + 3]) << 56);
    }
}