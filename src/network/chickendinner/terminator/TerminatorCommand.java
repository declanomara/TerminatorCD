package network.chickendinner.terminator;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TerminatorCommand implements CommandExecutor {
    // This method is called, when somebody uses our command
    private final Terminator plugin;

    TerminatorCommand(Terminator plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args[0].equals("add"))
        {
            Player target = Bukkit.getPlayer(args[1]);
            plugin.terminators.add(target);
            Bukkit.broadcastMessage("Added " + args[1] + " to terminator list");
            return true;
        }

        else if (args[0].equals("remove"))
        {
            Player target = Bukkit.getPlayer(args[1]);
            plugin.terminators.remove(target);
            Bukkit.broadcastMessage("Removed " + args[1] + " from terminator list");
            return true;
        }

        else if (args[0].equals("toggle"))
        {
            plugin.hunt = !plugin.hunt;
            Bukkit.broadcastMessage("Player hunting is now set to " + plugin.hunt);
            return true;
        }

        else if (args[0].equals("countdown"))
        {
            int seconds = Integer.parseInt(args[1]);
            Countdown task = new Countdown(plugin, seconds);
            task.runTaskTimer(plugin, 0, 20);
            Bukkit.broadcastMessage("Terminator will be released in " + seconds + " seconds.");
            return true;
        }

        else
        {
            Bukkit.broadcastMessage("Unknown usage");

        }

        return false;
    }
}
