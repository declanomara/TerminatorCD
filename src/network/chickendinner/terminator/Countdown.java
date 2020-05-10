package network.chickendinner.terminator;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown extends BukkitRunnable {
    private final Terminator plugin;
    private int secondsRemaining;

    Countdown(Terminator plugin, int seconds) {
        this.plugin = plugin;
        this.secondsRemaining = seconds;
    }

    @Override
    public void run() {
        secondsRemaining--;
        if(secondsRemaining % 60 == 0)
        {
            Bukkit.broadcastMessage(secondsRemaining/60 + " minutes remaining before the hunt begins");
        }

        if(secondsRemaining <= 15)
        {
            Bukkit.broadcastMessage(secondsRemaining + " seconds remaining before the hunt begins");
        }

        if(secondsRemaining <= 0)
        {
            Bukkit.broadcastMessage("The hunt has begun! Good luck prey...");
            plugin.hunt = true;
            this.cancel();
        }

    }
}
