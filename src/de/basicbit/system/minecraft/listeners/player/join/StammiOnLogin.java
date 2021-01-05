package de.basicbit.system.minecraft.listeners.player.join;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;

import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.minecraft.Group;
import de.basicbit.system.minecraft.Listener;

public class StammiOnLogin extends Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        int playHours = p.getStatistic(Statistic.PLAY_ONE_TICK) / 72000;
        if (playHours >= 50) {
            if (UserData.get(p, UserValue.playerGroup).equals(Group.MEMBER.toString())) {
                UserData.set(p, UserValue.playerGroup, Group.STAMMI.toString());
            }
        }
    }
}