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

    //private volatile boolean running;
    private boolean running = false;
    private int time = getConfig().getInt("Time");

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
        Bukkit.getConsoleSender().sendMessage("§5getTime1");
        return time;
    }

    public void setTime(int time) {
        Bukkit.getConsoleSender().sendMessage("§5setTime1");
        this.time = time;
        Bukkit.getConsoleSender().sendMessage("§5setTime2");
    }

    // ActionBar
    public void Stopped() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§4§l" + getConfig().getString("Time")));
        }
    }

    // Timer
    /*public void TimerRunnable() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (isRunning()) {
                        setTime(getTime() + 1);
                        try {
                            getConfig().set("Time", getTime());
                            getConfig().save(getFile());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(getTimeFormat()));
                    } else {
                        Bukkit.getConsoleSender().sendMessage("§cLäuft nicht...");
                        Stopped();
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 20, 20);
    }*/

    public void TimerRunnable() {
        new BukkitRunnable() {
            @Override
            public void run() {

                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.sendMessage("§cRunnable §f" + isRunning());
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

        while (seconds > 60L) {
            seconds -= 60L;
            minutes++;
        }
        while (minutes > 60L) {
            minutes -= 60L;
            hours++;
        }
        while (hours > 24L) {
            hours -= 24L;
            days++;
        }
        while (days > 7L) {
            days -= 7L;
            weeks++;
        }
        while (weeks > 7L) {
            days -= 7L;
        }
        if (weeks != 0L) {
            return "§c" + weeks + "§cw §c" + days + "§cd §c" + hours + "§ch §c" + minutes + "§cm §c" + seconds + "§cs";
        }
        if (days != 0L) {
            return "§c" + days + "§cd §c" + hours + "§ch §c" + minutes + "§cm §c" + seconds + "§cs";
        }
        if (hours != 0L) {
            return "§c" + hours + "§ch §c" + minutes + "§cm §c" + seconds + "§cs";
        }
        if (minutes != 0L) {
            return "§c" + minutes + "§cm §c" + seconds + "§cs";
        }
        if (seconds != 0L) {
            return "§c" + seconds + "§cs";
        }
        return "§c" + hours + "§ch §c" + minutes + "§cm §c" + seconds + "§cs";
    }
}