// Copyright© by Fin + https://www.youtube.com/watch?v=KxjsEzA_fU0

package Main;

import API.TimerAPI;
import Timer.TimerCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin implements CommandExecutor {

    private static Main instance;

    private TimerAPI timerapi;

    public Main() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("timer")).setExecutor(new TimerCommand());

        timerapi = new TimerAPI();
        if (timerapi.isRunning()) {
            timerapi.setRunning(false);
        }
        timerapi.TimerRunnable();

        Bukkit.getConsoleSender().sendMessage("§5Plugin fertig geladen");
    }

    @Override
    public void onDisable() {
    }

    /*public TimerAPI getTimer() {
        return timerapi;
    }*/
}