// Copyright© by Fin + https://www.youtube.com/watch?v=KxjsEzA_fU0

package Main;

import API.TimerAPI;
import Commands.TimerCommand;
import Listener.onKick;
import Listener.onQuit;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin implements CommandExecutor {

    private static Main instance;

    private TimerAPI timerapi;

    public Main() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // Commands
        Objects.requireNonNull(getCommand("timer")).setExecutor(new TimerCommand());

        // Events
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new onQuit(), this);
        pm.registerEvents(new onKick(), this);

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

    public TimerAPI getTimer() {
        return timerapi;
    }
}