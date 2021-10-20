// Copyright© by Fin

package Timer;

import API.TimerAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;

public class TimerCommand implements CommandExecutor {

    TimerAPI timerapi = new TimerAPI();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length != 1) {
            sender.sendMessage("Verwendung: /timer resume, /timer pause, /timer reset");
            return true;
        }

        switch (args[0].toLowerCase()) {

            case "resume":
                if (timerapi.isRunning()) {
                    sender.sendMessage("§4Der Timer läuft bereits");
                } else {
                    timerapi.setRunning(true);
                    sender.sendMessage("§aDer Timer wurde gestartet");
                }
                break;


            case "pause":
                if (!timerapi.isRunning()) {
                    sender.sendMessage("§4Der Timer läuft nicht");
                } else {
                    timerapi.setRunning(false);
                    timerapi.Stopped();
                    sender.sendMessage("§4Der Timer wurde pausiert");
                }
                break;

            case "reset":
                if (timerapi.getTime() > 0) {
                    timerapi.Stopped();
                    timerapi.setRunning(false);
                    timerapi.setTime(0);
                    Bukkit.getConsoleSender().sendMessage("§c0");
                    try {
                        Bukkit.getConsoleSender().sendMessage("§c1");
                        timerapi.getConfig().set("Time", timerapi.getTime());
                        timerapi.getConfig().save(timerapi.getFile());
                        Bukkit.getConsoleSender().sendMessage("§c2");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    sender.sendMessage("§4Der Timer wurde zurückgesetzt");
                } else {
                    sender.sendMessage("§4Timer ist bereits auf 0");
                }
                break;
        }

        return false;
    }
}