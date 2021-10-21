// Copyright© by Fin

package Commands;

import API.TimerAPI;
import Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.io.IOException;

public class TimerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof ConsoleCommandSender) {
            Bukkit.getConsoleSender().sendMessage("§4Du musst ein Spieler sein!");
            return true;
        }

        if (args.length == 0 || args.length > 2) {
            sender.sendMessage(TimerAPI.usage);
            return true;
        }

        TimerAPI timerapi = Main.getInstance().getTimer();

        switch (args[0].toLowerCase()) {

            case "resume": {
                if (timerapi.isRunning()) {
                    sender.sendMessage("§4Der Timer läuft bereits");
                } else {
                    timerapi.setRunning(true);
                    sender.sendMessage("§aDer Timer wurde gestartet");
                }
                break;
            }

            case "pause": {
                if (!timerapi.isRunning()) {
                    sender.sendMessage("§4Der Timer läuft nicht");
                } else {
                    timerapi.setRunning(false);
                    sender.sendMessage("§4Der Timer wurde pausiert");
                }
                break;
            }

            case "set": {
                if (args.length != 2) {
                    sender.sendMessage("Verwendung: /timer <Zeit in s>");
                    return true;
                }

                try {
                    timerapi.setRunning(false);
                    timerapi.setTime(Integer.parseInt(args[1]));
                    timerapi.SaveTime();
                    sender.sendMessage("§4Die Zeit wurde auf " + timerapi.getTimeFormat() + " §4gesetzt");
                } catch (NumberFormatException e) {
                    sender.sendMessage("§4Zeit muss eine Zahl sein");
                }
                break;
            }

            case "reset": {
                if (timerapi.getTime() > 0) {
                    timerapi.setRunning(false);
                    timerapi.setTime(0);
                    timerapi.SaveTime();
                    sender.sendMessage("§4Der Timer wurde zurückgesetzt");
                } else {
                    sender.sendMessage("§4Der Timer ist bereits zurückgesetzt");
                }
                break;
            }
            default:
                sender.sendMessage(TimerAPI.usage);
        }

        return false;
    }
}