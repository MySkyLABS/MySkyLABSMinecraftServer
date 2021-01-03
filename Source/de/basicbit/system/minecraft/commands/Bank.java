package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

import de.basicbit.system.database.UserData;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Bank extends Command {

    @Override
    public String getUsage(Player p) {
		return "Get <Spieler>; <Set/Add/Remove> <Spieler> <Coins>; Invest <Set/Get> <Spieler> [Level] ";
		//Get <Spieler>|Set <Spieler> <Coins>|Add <Spieler> <Coins>|Remove <Spieler> <Coins>|Invest Set <Spieler> <Level>|Invest Get <Spieler>
	}

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("bank");
        return names;
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
    public String getDescription(Player p) {
        return "Verwaltet die Bank anderer Spieler.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length >= 2 && args.length <= 4) {
            if (args[0].equalsIgnoreCase("get") && args.length == 2) {
                UUID id = UserData.getUUIDFromName(args[1]);

				int i = getBankCoins(id);
				sendMessage(p, getChatName(id) + "§7 hat §e" + (i == 1 ? "ein Coin" : 
					(i == Integer.MAX_VALUE ? "unendlich viele Coins" : i + " Coins")) + "§7 auf der Bank.");

                return CommandResult.None;
            } else if (args[0].equalsIgnoreCase("set")) {
                if (args.length == 3) {
					UUID id = UserData.getUUIDFromName(args[1]);
					String coins = args[2];
					
					if (coins.contentEquals("*")) {
						setBankCoins(id, Integer.MAX_VALUE, " BankNpcSetCoins");
					} else {
						try {
							int c = Integer.parseInt(coins);

							if (c < 0 || c > 1000000000) {
								return CommandResult.InvalidNumber;
							} else {
								setBankCoins(id, c, " BankNpcSetCoins");
							}
						} catch (Exception e) {
							return CommandResult.InvalidNumber;
						}
					}

					int i = getBankCoins(id);
					sendMessage(p, getChatName(id) + "§a hat nun §e" + (i == 1 ? "ein Coin" : 
						(i == Integer.MAX_VALUE ? "unendlich viele Coins" : i + " Coins")) + "§a auf der Bank.");
				} else {
					return CommandResult.InvalidUsage;
				}
                return CommandResult.None;
            } else if (args[0].equalsIgnoreCase("add")) {
				if (args.length == 3) {
					UUID id = UserData.getUUIDFromName(args[1]);
					String coins = args[2];

					if (coins.contentEquals("*")) {
						setBankCoins(id, Integer.MAX_VALUE, " BankNpcSetCoins");
					} else {
						try {
							int c = Integer.parseInt(coins);

							if (c < 0 || c > 1000000000) {
								return CommandResult.InvalidNumber;
							} else {
								if (c + getBankCoins(id) > 1000000000) {
									setBankCoins(id, 1000000000, " BankNpcSetCoins");
								} else {
									addBankCoins(id, c, " BankNpcSetCoins");
								}
							}

						} catch (Exception e) {
							return CommandResult.InvalidNumber;
						}
					}

					int i = getBankCoins(id);
					sendMessage(p, getChatName(id) + "§a hat nun §e" + (i == 1 ? "ein Coin" : 
						(i == Integer.MAX_VALUE ? "unendlich viele Coins" : i + " Coins")) + "§a auf der Bank.");
				} else {
					return CommandResult.InvalidUsage;
				}
                return CommandResult.None;
            } else if (args[0].equalsIgnoreCase("remove")) {
                if (args.length == 3) {
					UUID id = UserData.getUUIDFromName(args[1]);
					String coins = args[2];

					if (coins.contentEquals("*")) {
						setBankCoins(id, 0, " BankNpcSetCoins");
					} else {
						try {
							int c = Integer.parseInt(coins);

							if (c < 0 || c > 1000000000) {
								return CommandResult.InvalidNumber;
							} else {
								if (getBankCoins(id) - c < 0) {
									setBankCoins(id, 0, " BankNpcSetCoins");
								} else {
									removeBankCoins(id, c, " BankNpcSetCoins");
								}
							}

						} catch (Exception e) {
							return CommandResult.InvalidNumber;
						}
					}

					int i = getBankCoins(id);
					sendMessage(p, getChatName(id) + "§a hat nun §e" + (i == 1 ? "ein Coin"	: 
						(i == Integer.MAX_VALUE ? "unendlich viele Coins" : i + " Coins")) + "§a auf der Bank.");
				

				} else {
					return CommandResult.InvalidUsage;
				}

                return CommandResult.None;
            } else if (args[0].equalsIgnoreCase("invest")) {
                if (args[1].equalsIgnoreCase("set")) {
					
					UUID id = UserData.getUUIDFromName(args[2]);

                    try {
                        int level = Integer.parseInt(args[3]);

                        setBankInvest(id, level, "BankInvestLevel");
                        sendMessage(p, getChatName(id) + "§a hat jetzt ein Banklevel von §e" + level + "§a.");
                    } catch (Exception e) {
                        return CommandResult.InvalidNumber;
                    }

                    return CommandResult.None;
                } else if (args[1].equalsIgnoreCase("get")) {
					UUID id = UserData.getUUIDFromName(args[2]);

                    sendMessage(p, getChatName(id) + "§7 hat ein Bank Level von §e" + getBankInvest(id) + "§7.");

                    return CommandResult.None;
				}
				return CommandResult.InvalidUsage;
            }

            return CommandResult.InvalidUsage;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}