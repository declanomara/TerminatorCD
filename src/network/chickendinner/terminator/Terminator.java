package network.chickendinner.terminator;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class Terminator extends JavaPlugin {
    public ArrayList<Player> terminators = new ArrayList<Player>();
    public boolean hunt = false;
    public int interval = 20; // 20TPS (Every second, not lag resistant);

    @Override
    public void onEnable(){
        // Add commands
        this.getCommand("terminator").setExecutor(new TerminatorCommand(this));

        // Event listener
        getServer().getPluginManager().registerEvents(new TerminatorListener(), this);

        // Run loop
        TerminatorTick task = new TerminatorTick(this);
        task.runTaskTimer(this, 0, interval); //Wait 0 ticks before executing for the first time. Wait {interval} tick(s) between each consecutive execution


    }

    @Override
    public void onDisable(){
        //Fired when the server stops and disables all plugins
    }


    public void setupGame()
    {
        for(Player p: getServer().getOnlinePlayers())
        {
            for(PotionEffect effect : p.getActivePotionEffects()){
                p.removePotionEffect(effect.getType());
            }

            if(terminators.contains(p))
            {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
                p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 9999999, 255));
                p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999999, 255));
                p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 9999999, 255));
            }

            else
            {
                p.getInventory().clear();
            }

        }
    }
}
