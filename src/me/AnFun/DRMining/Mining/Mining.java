package me.AnFun.DRMining.Mining;

import me.AnFun.DRMining.Main;
import net.minecraft.server.v1_12_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Mining implements Listener {

    public Mining() {
        new BukkitRunnable(){
            @Override
            public void run() {
                if (!oreList.isEmpty()) {
                    while (!oreList.isEmpty()&&oreList.get(0).getTimer() <= System.currentTimeMillis()) {
                        Ore ore = oreList.remove(0);
                        Location location = ore.location;
                        location.getBlock().setType(getOre(ore.tier));
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(),5, 1);
    }

    private List<Ore> oreList = new ArrayList<Ore>();

    public enum Tier {
        T1,T2,T3,T4,T5;
    }
    private class Ore {
        Tier tier;
        long time;
        Location location;

        public Ore(Tier tier, long time, Location location) {
            this.tier = tier;
            this.time = time;
            this.location = location;
        }

        public long getTimer() {
            return time;
        }
    }

    Tier getTier(Block block) {
        switch (block.getType()){
            case GOLD_ORE:
                return Tier.T5;
            case DIAMOND_ORE:
                return Tier.T4;
            case IRON_ORE:
                return Tier.T3;
            case EMERALD_ORE:
                return Tier.T2;
            case COAL_ORE:
                return Tier.T1;
        }
        return null;
    }

    Material getOre (Tier tier) {
        switch (tier) {
            case T1:
                return Material.COAL_ORE;
            case T2:
                return Material.EMERALD_ORE;
            case T3:
                return Material.IRON_ORE;
            case T4:
                return Material.DIAMOND_ORE;
            case T5:
                return Material.GOLD_ORE;
            default:
                return Material.STONE;
        }
    }

    private long getTime(Tier tier) {
        long time = System.currentTimeMillis();
        long T1Time = 1;
        long T2Time = 2;
        long T3Time = 3;
        long T4Time = 4;
        long T5Time = 5;

        switch (tier) {
            case T1:
                return time+T1Time*1000;
            case T2:
                return time+T2Time*1000;
            case T3:
                return time+T3Time*1000;
            case T4:
                return time+T4Time*1000;
            case T5:
                return time+T5Time*1000;
            default:
                return 0;
        }
    }

    private void addToOreList(Ore ore) {
        oreList.add(ore);
        oreList.sort(Comparator.comparingLong(Ore::getTimer));
        Bukkit.broadcastMessage("Ores Mined:");
        for (Ore o : oreList) {
            Bukkit.broadcastMessage(o.tier.toString());
            Bukkit.broadcastMessage(""+((o.time-System.currentTimeMillis())/1000)+ " Seconds Remaining");
        }
    }


    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
        Tier tier = getTier(event.getBlock());
        if (tier == null) {
            return;
        }
        Location location = event.getBlock().getLocation();
        long time = getTime(tier);
        Ore ore = new Ore(tier,time,location);
        location.getBlock().setType(Material.STONE);
        addToOreList(ore);
    }

}
