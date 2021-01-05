package de.basicbit.system.minecraft.listeners.player.join;

import java.io.IOException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import de.basicbit.system.database.UserValue;
import de.basicbit.system.GlobalValues;
import de.basicbit.system.database.UserData;
import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.MoneyLogger;

public class PlayerJoinSetup extends Listener {

    @EventHandler
    public static void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        GlobalValues.playerMode.put(p.getUniqueId(), 0);

        UserData.set(p, UserValue.ip, p.getAddress().getAddress().getHostAddress());
        
        if (UserData.getInt(p, UserValue.dayBonusDay) <= 0) {
            UserData.set(p, UserValue.dayBonusDay, 1);
        }
        
        if (UserData.getInt(p, UserValue.coinsBankInvest) <= 0) {
            UserData.set(p, UserValue.coinsBankInvest, 1);
        }

        try {
            MoneyLogger.write(p, Integer.parseInt(UserData.get(p, UserValue.coins).toString()), "[HAS]" + "HasCoins");
            MoneyLogger.write(p, Integer.parseInt(UserData.get(p, UserValue.coinsBank).toString()), "[HAS]" + "HasBankCoins");
            log(p.getName() + ": " + "HasCoins" + " [HAS] " + UserData.get(p, UserValue.coins) + " Coins");
            log(p.getName() + ": " + "HasBankCoins" + " [HAS] " + UserData.get(p, UserValue.coinsBank) + " BankCoins");
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}