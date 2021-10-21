// Copyright© by Fin

package Listener;

import API.TimerAPI;
import Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class onKick implements Listener {

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        TimerAPI timerapi = Main.getInstance().getTimer();
        Player p = e.getPlayer();
        if (timerapi.isRunning()) {
            if (p.hasPermission("timer.command")) {
                timerapi.setRunning(false);
                Bukkit.getConsoleSender().sendMessage("§4Timer wurde pausiert");
            }
        }
    }
}