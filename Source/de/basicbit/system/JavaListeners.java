package de.basicbit.system;

import java.lang.reflect.Method;
import java.util.function.Consumer;

import de.basicbit.system.minecraft.DiscordBot;
import de.basicbit.system.minecraft.Utils;

public class JavaListeners extends Utils {

    public static void registerAllJavaListeners() {
        log("\tRegister Discord Exception Listener");
        JavaListeners.registerExceptionListener(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable t) {
                DiscordBot.logError(t);
            }
        });
    }

    public static void registerExceptionListener(Consumer<Throwable> listener) {
        try {
            Method addListener = Throwable.class.getDeclaredMethod("addListener", Consumer.class);
            addListener.invoke(null, listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
