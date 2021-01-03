package de.basicbit.system.minecraft;

public enum CommandResult {
	PlayerNotOnline("§cDer angegebene Spieler ist nicht online."),
	NotEnoughCoins("§cDu hast nicht genug Coins."),
	InvalidPermissions("§cDu hast nicht genug Recht um dies zu tun."),
	InvalidNumber("§cDer angegebene Parameter ist keine gültige Zahl."),
	PlayerNotInDataBase("§cDer angegebene Spieler ist nicht in der Datenbank."),
	None(null),
	InvalidUsage(null);
	
	private String message;
	
	private CommandResult(String msg) {
		message = msg;
	}
	
	public String getMessage() {
		return message;
	}
}
