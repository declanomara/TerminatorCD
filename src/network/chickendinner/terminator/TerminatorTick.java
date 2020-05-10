package network.chickendinner.terminator;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collection;

public class TerminatorTick extends BukkitRunnable {

    private final Terminator plugin;
    private double tpSpeed = 7;
    private int huntRange = 150;

    TerminatorTick(Terminator plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if(plugin.hunt){moveTerminators();}
    }

    public void moveTerminators()
    {
        Collection<? extends Player> players = plugin.getServer().getOnlinePlayers();

        for(Player terminator : plugin.terminators)
        {
            Location nearestLocation = null;

            for(Player p : players)
            {
                if(plugin.terminators.contains(p)) { continue; }
                if(p.getGameMode() != GameMode.SURVIVAL) { continue; }
                if (nearestLocation == null){ nearestLocation = p.getLocation(); }

                if(terminator.getLocation().distance(p.getLocation()) < terminator.getLocation().distance(nearestLocation))
                { nearestLocation = p.getLocation(); }
            }

            if(nearestLocation == null)
            {
                terminator.sendMessage("No valid target found.");
                continue;
            }

            terminator.sendMessage("Nearest player is "+ (int) terminator.getLocation().distance(nearestLocation) + " blocks away at (" + nearestLocation.getBlockX() + ", " + nearestLocation.getBlockY() + ", " + nearestLocation.getBlockZ() + ")");

            if (terminator.getLocation().distance(nearestLocation) > huntRange)
            {
                Vector tV = new Vector(terminator.getLocation().getX(), terminator.getLocation().getY(), terminator.getLocation().getZ());
                Vector pV = new Vector(nearestLocation.getX(), nearestLocation.getY(), nearestLocation.getZ());

                Vector pt = new Vector(pV.getX() - tV.getX(), pV.getY() - tV.getY(), pV.getZ() - tV.getZ());

                double multiplier = tpSpeed/terminator.getLocation().distance(nearestLocation);

                Vector nV = new Vector(tV.getX() + pt.getX() * multiplier, tV.getY() + pt.getY() * multiplier, tV.getZ() + pt.getZ() * multiplier);

                Location moveLocation = new Location(terminator.getWorld(), nV.getX(), terminator.getWorld().getHighestBlockYAt(nV.getBlockX(), nV.getBlockZ()), nV.getZ());

                terminator.teleport(moveLocation);
                terminator.sendMessage("Moved terminators " + tpSpeed + " closer to nearest players");
            }
        }
    }
}
