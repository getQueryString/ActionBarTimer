// Copyright© by Fin + https://www.youtube.com/watch?v=KxjsEzA_fU0

package Main;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Main extends JavaPlugin implements CommandExecutor {

    private static Main instance;

    public boolean running = false;
    public int time = 0;

    public File file = new File("plugins/TimerSaved/config.yml");
    public YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    public Main() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("Timer")).setExecutor(this);
        TimerRunnable();
        Bukkit.getConsoleSender().sendMessage("§5Plugin fertig geladen");
    }

    @Override
    public void onDisable() {
    }

    public String getTime() {
        long seconds = cfg.getInt("Time");
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
            return "§a" + weeks + " §cWoche(n) §a" + days + " §cTag(e) §a" + hours + " §cStunde(n) §a" + minutes + " §cMinute(n) §a" + seconds + " §cSekunde(n)";
        }
        if (days != 0L) {
            return "§a" + days + " §cTag(e) §a" + hours + " §cStunde(n) §a" + minutes + " §cMinute(n) §a" + seconds + " §cSekunde(n)";
        }
        if (hours != 0L) {
            return "§a" + hours + " §cStunde(n) §a" + minutes + " §cMinute(n) §a" + seconds + " §cSekunde(n)";
        }
        if (minutes != 0L) {
            return "§a" + minutes + " §cMinute(n) §a" + seconds + " §cSekunde(n)";
        }
        if (seconds != 0L) {
            return "§a" + seconds + " §cSekunde(n)";
        }
        return "§a" + weeks + " §cWoche(n) §a" + days + " §cTag(e) §a" + hours + " §cStunde(n) §a" + minutes + " §cMinute(n) §a" + seconds + " §cSekunde(n)";
    }

    public void TimerRunnable() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (running == true) {
                        time = time + 1;
                        try {
                            cfg.set("Time", time);
                            cfg.save(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§c§l" + cfg.getString("Time")+ "s"));
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§c§l" + getTime()));
                    } else {
                        Stopped();
                    }
                }
            }
        }.runTaskTimer(instance, 20, 20);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0 || args.length >= 2) {
            sender.sendMessage("Verwendung: /Timer resume, /Timer pause, /Timer reset");
            return true;
        }

        switch (args[0]) {

            case "resume":
                if (running == true) {
                    sender.sendMessage("§4Der Timer läuft bereits");
                } else {
                    running = true;
                    sender.sendMessage("§aDer Timer wurde gestartet");
                }
                break;


            case "pause":
                if (running == false) {
                    sender.sendMessage("§4Der Timer läuft nicht.");
                } else {
                    running = false;
                    Stopped();
                    sender.sendMessage("§4Der Timer wurde pausiert.");
                }
                break;

            case "reset":
                if (time > 0) {
                    running = false;
                    time = 0;
                    try {
                        cfg.set("Time", time);
                        cfg.save(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stopped();
                    sender.sendMessage("§4Der Timer wurde zurückgesetzt.");
                } else {
                    sender.sendMessage("§4Timer ist bereits auf 0.");
                }
                break;
        }

        return false;
    }

    public void Stopped() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§4" + cfg.getString("Time") + "s"));
        }
    }
}