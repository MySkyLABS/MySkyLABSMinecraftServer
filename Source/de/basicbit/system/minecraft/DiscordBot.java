package de.basicbit.system.minecraft;

import javax.security.auth.login.LoginException;

import de.basicbit.system.GlobalValues;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class DiscordBot extends Utils {
	
	private static JDA jda;
	private static final long errorChannel = 756878933445705848L;
	
	public static void init() {
        try {
			jda = JDABuilder.createDefault(GlobalValues.discordToken).build();
			jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.playing("MySkyLABS.de"));
		} catch (LoginException e) {
			e.printStackTrace();
		}
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        	jda.shutdownNow();
        }));
    }
	
	public static JDA getJda() {
		return jda;
	}

	public static void logError(Throwable error) {
		if (error.getLocalizedMessage().trim().equals("SYNC")) {
			return;
		}
		EmbedBuilder embedBuilder = new EmbedBuilder();
		String fullError = error.getClass().getSimpleName() + ": " + error.getLocalizedMessage();
		embedBuilder.setColor(0xFFFF0000).setTitle(fullError);
		StackTraceElement[] stackTrace = error.getStackTrace();
		for (StackTraceElement element : stackTrace) {
			embedBuilder.addField(getStackTraceElementHead(element), getStackTraceElementBody(element), false);
		}
		Throwable[] throwables = error.getSuppressed();
		if (throwables != null) {
			for (Throwable throwable : throwables) {
				embedBuilder.addBlankField(false);
				logError(throwable, "Suppressed: ", embedBuilder);
			}
		}
		Throwable cause = error.getCause();
		if (cause != null) {
			embedBuilder.addBlankField(false);
			logError(cause, "Caused by: ", embedBuilder);
		}
		jda.getTextChannelById(errorChannel).sendMessage(embedBuilder.build()).queue();
	}

	private static void logError(Throwable error, String prefix, EmbedBuilder builder) {
		String reason = error.getClass().getSimpleName() + ": " + error.getLocalizedMessage();
		builder.addField(prefix + reason, "", true);
		StackTraceElement[] stackTrace = error.getStackTrace();
		for (StackTraceElement element : stackTrace) {
			builder.addField(getStackTraceElementHead(element), getStackTraceElementBody(element), false);
		}
		Throwable[] throwables = error.getSuppressed();
		if (throwables != null) {
			for (Throwable throwable : throwables) {
				builder.addBlankField(false);
				logError(throwable, "Suppressed: ", builder);
			}
		}
		Throwable cause = error.getCause();
		if (cause != null) {
			builder.addBlankField(false);
			logError(cause, "Caused by: ", builder);
		}
	}

	private static String getStackTraceElementHead(StackTraceElement stackTraceElement) {
		return stackTraceElement.getClassName();
	}

	private static String getStackTraceElementBody(StackTraceElement stackTraceElement) {
		return stackTraceElement.getMethodName() + (stackTraceElement.isNativeMethod() ? "(Native Method)" : ((stackTraceElement.getFileName() != null && stackTraceElement.getLineNumber() >= 0) ? ("(" + stackTraceElement.getFileName() + ":" +stackTraceElement.getLineNumber() + ")") : ((stackTraceElement.getFileName() != null) ? ("(" + stackTraceElement.getFileName() + ")") : "(Unknown Source)")));
	}
}
