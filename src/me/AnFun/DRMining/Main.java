package me.AnFun.DRMining;

import me.AnFun.DRMining.Commands.Commands;
import me.AnFun.DRMining.Mining.Mining;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Commands.onCommand(sender, command.getName(), args);
        return false;
    }

    @Override
    public void onDisable() {

    }

}
