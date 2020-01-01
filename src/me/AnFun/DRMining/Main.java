package me.AnFun.DRMining;

import me.AnFun.DRMining.Mining.Mining;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {


    private static Main instance;



    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(new Mining(), this);
    }

    public static Main getInstance() { return instance;}

    @Override
    public void onDisable() {

    }

}
