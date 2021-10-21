// Copyright© by Fin

package API;

import Main.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

public class TimerAPI {

    private static TimerAPI instance;

    private File file = new File("plugins/TimerSaved/config.yml");
    private YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    private boolean running = false;
    private int time = getConfig().getInt("Time");

    public static String usage = "Verwendung: /timer <resume, pause, reset, set <Zeit in s>>";
    private String color = null;

    // Main
    public TimerAPI() {
        instance = this;
    }

    public TimerAPI getInstance() {
        return instance;
    }

    // File management
    public File getFile() {
        return file;
    }

    public YamlConfiguration getConfig() {
        return cfg;
    }

    // time management
    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    // ActionBar
    public void Stopped() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(getTimeFormat()));
        }
    }

    public void SaveTime() {
        try {
            getConfig().set("Time", getTime());
            getConfig().save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Timer
    public void TimerRunnable() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (isRunning()) {
                        setTime(getTime() + 1);
                        SaveTime();
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(getTimeFormat()));
                    } else {
                        Stopped();
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 20, 20);
    }

    // time format
    public String getTimeFormat() {
        long seconds = getConfig().getInt("Time");
        long minutes = 0L;
        long hours = 0L;
        long days = 0L;
        long weeks = 0L;
        long years = 0;

        if (isRunning()) color = "§c";
        else color = "§4§l";

        while (seconds >= 60L) {
            seconds -= 60L;
            minutes++;
        }
        while (minutes >= 60L) {
            minutes -= 60L;
            hours++;
        }
        while (hours >= 24L) {
            hours -= 24L;
            days++;
        }
        while (days >= 7L) {
            days -= 7L;
            weeks++;
        }
        if (years != 0L) {
            return color + years + "y " + weeks + "w " + days + "d " + hours + "h " + minutes + "m " + seconds + "s";
        }
        if (weeks != 0L) {
            return color + weeks + "w " + days + "d " + hours + "h " + minutes + "m " + seconds + "s";
        }
        if (days != 0L) {
            return color + days + "d " + hours + "h " + minutes + "m " + seconds + "s";
        }
        if (hours != 0L) {
            return color + hours + "h " + minutes + "m " + seconds + "s";
        }
        if (minutes != 0L) {
            return color + minutes + "m " + seconds + "s";
        }
        if (seconds != 0L) {
            return color + seconds + "s";
        }
        return "§4§l" + hours + "§4§lh " + minutes + "§4§lm " + seconds + "§4§ls";
    }
}