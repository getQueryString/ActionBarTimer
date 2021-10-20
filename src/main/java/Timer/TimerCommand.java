// Copyright© by Fin

package Timer;

import API.TimerAPI;
import Main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;

public class TimerCommand implements CommandExecutor {

    TimerAPI timerapi = new TimerAPI().getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length != 1) {
            sender.sendMessage("Verwendung: /timer <resume, pause, reset>, set <Zeit>");
            return true;
        }

        switch (args[0].toLowerCase()) {

            case "resume":
                if (timerapi.isRunning()) {
                    sender.sendMessage("§4Der Timer läuft bereits §f" + timerapi.isRunning());
                } else {
                    timerapi.setRunning(true);
                    sender.sendMessage("§aDer Timer wurde gestartet §f" + timerapi.isRunning());
                }
                break;

            case "pause":
                if (!timerapi.isRunning()) {
                    sender.sendMessage("§4Der Timer läuft nicht §f" + timerapi.isRunning());
                } else {
                    timerapi.setRunning(false);
                    sender.sendMessage("§4Der Timer wurde pausiert §f" + timerapi.isRunning());
                }
                break;

            /*case "set":
                try {
                    timerapi.setTime(Integer.parseInt(args[2]));
                    sender.sendMessage("§4Die Zeit wurde auf §4§l" + args[2] + " §4gesetzt");
                } catch (NumberFormatException e) {
                    sender.sendMessage("§4Die Zeit muss in Sekunden angegeben werden");
                }
                break;*/

            case "reset":
                if (timerapi.getTime() >= 0) {
                    timerapi.setRunning(false);
                    timerapi.setTime(0);
                    try {
                        timerapi.getConfig().set("Time", timerapi.getTime());
                        timerapi.getConfig().save(timerapi.getFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    sender.sendMessage("§4Der Timer wurde zurückgesetzt §f" + timerapi.isRunning());
                } else {
                    sender.sendMessage("§4Timer ist bereits auf 0 §f" + timerapi.isRunning());
                }
                break;
        }

        return false;
    }
}