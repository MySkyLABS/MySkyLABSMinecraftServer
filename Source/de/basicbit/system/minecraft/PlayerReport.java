package de.basicbit.system.minecraft;

import java.util.UUID;

public class PlayerReport {

    private final UUID reporter;
    private final UUID cheater;
    private final String reason;
    private final long timestamp;
    private final long messageId;
    
    public PlayerReport(UUID cheater, UUID reporter, String reason, long timestamp, long messageId) {
        this.cheater = cheater;
        this.reporter = reporter;
        this.reason = reason;
        this.timestamp = timestamp;
        this.messageId = messageId;
    }

    public UUID getCheater() {
        return cheater;
    }

    public UUID getReporter() {
        return reporter;
    }

    public String getReason() {
        return reason;
    }

    public long getTimestamp() {
        return timestamp;
    }
    
    public long getMessageId() {
		return messageId;
	}
}