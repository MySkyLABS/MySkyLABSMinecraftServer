package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

import de.basicbit.system.database.UserData;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

import static de.basicbit.system.minecraft.CommandResult.*;

public class Coins extends Command {

	@Override
	public String getUsage(Player p) {
		return "<Get> <Spieler>; <Set/Add/Remove> <Coins>";
		//<Get <Spieler>|Set <Spieler> <Coins>|Add <Spieler> <Coins>|Remove <Spieler> <Coins>>
	}

	@Override
	public ArrayList<String> getNames() {
		ArrayList<String> names = new ArrayList<String>();
        names.add("coins");
		return names;
	}

    @Override
    public String getDescription(Player p) {
        return "Verwaltet die Coins anderer Spieler.";
    }

	@Override
	public boolean hasPermissions(Player p) {
		return isAdmin(p);
	}

	@Override
	public boolean isAsync() {
		return true;
	}

	@Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length >= 1 && args.length <= 3) {
            if (args[0].equalsIgnoreCase("get")) {
                if (args.length == 2) {
					UUID id = UserData.getUUIDFromName(args[1]);

					int i = getCoins(id);
					sendMessage(p, getChatName(id) + "§7 hat §e" + (i == 1 ? "ein Coin" : 
						(i == Integer.MAX_VALUE ? "unendlich viele Coins" : i + " Coins")) + "§7 im Besitz.");
                } else {
                    return InvalidUsage;
                }
			} else if (args[0].equalsIgnoreCase("set")) {
				if (args.length == 3) {
					UUID id = UserData.getUUIDFromName(args[1]);
					String coins = args[2];
				
					if (coins.contentEquals("*")) {
						setCoins(id, Integer.MAX_VALUE, " CommandCoinsSetCoins");
					} else {
						try {
							int c = Integer.parseInt(coins);

							if (c < 0 || c > 1000000000) {
								return InvalidNumber;
							} else {
								setCoins(id, c, " CommandCoinsSetCoins");
							}
						} catch (Exception e) {
							return InvalidNumber;
						}
					}

					int i = getCoins(id);
					sendMessage(p, getChatName(id) + "§a hat nun §e" + (i == 1 ? "ein Coin" : 
						(i == Integer.MAX_VALUE ? "unendlich viele Coins" : i + " Coins")) + "§a im Besitz.");
				} else {
					return InvalidUsage;
				}
			} else if (args[0].equalsIgnoreCase("add")) {
				if (args.length == 3) {
					UUID id = UserData.getUUIDFromName(args[1]);
					String coins = args[2];

					if (coins.contentEquals("*")) {
						setCoins(id, Integer.MAX_VALUE, " CommandAddCoins");
					} else {
						try {
							int c = Integer.parseInt(coins);

							if (c < 0 || c > 1000000000) {
								return InvalidNumber;
							} else {
								if (c + getCoins(id) > 1000000000) {
									setCoins(id, 1000000000, " CommandAddCoins");
								} else {
									addCoins(id, c, " CommandAddCoins");
								}
							}

						} catch (Exception e) {
							return InvalidNumber;
						}
					}

					int i = getCoins(id);
					sendMessage(p, getChatName(id) + "§a hat nun §e" + (i == 1 ? "ein Coin" : 
						(i == Integer.MAX_VALUE ? "unendlich viele Coins" : i + " Coins")) + "§a im Besitz.");
				} else {
					return InvalidNumber;
				}
			} else if (args[0].equalsIgnoreCase("remove")) {

				if (args.length == 3) {

					UUID id = UserData.getUUIDFromName(args[1]);
					String coins = args[2];

					if (coins.contentEquals("*")) {
						setCoins(id, 0, " CommandCoinsSetCoins");
					} else {
						try {
							int c = Integer.parseInt(coins);

							if (c < 0 || c > 1000000000) {
								return InvalidNumber;
							} else {
								if (getCoins(id) - c < 0) {
									setCoins(id, 0, " CommandCoinsSetCoins");
								} else {
									removeCoins(id, c, " CommandCoinsRemoveCoins");
								}
							}

						} catch (Exception e) {
							return InvalidNumber;
						}
					}

					int i = getCoins(id);
					sendMessage(p, getChatName(id) + "§a hat nun §e" + (i == 1 ? "ein Coin"	: 
						(i == Integer.MAX_VALUE ? "unendlich viele Coins" : i + " Coins")) + "§a im Besitz.");
				

				} else {
					return InvalidNumber;
				}

            }
            
            return None;
		} else {
            return InvalidUsage;
        }
	}
}
