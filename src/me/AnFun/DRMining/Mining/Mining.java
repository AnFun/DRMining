package me.AnFun.DRMining.Mining;

import me.AnFun.DRMining.Generator.Generator;
import me.AnFun.DRMining.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Mining implements Listener {

    private List<Ore> oreList = new ArrayList<>();

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

    private static class Ore {
        Tier tier;
        long time;
        Location location;

        Ore(Tier tier, long time, Location location) {
            this.tier = tier;
            this.time = time;
            this.location = location;
        }

        long getTimer() {
            return time;
        }
    }

    //pre value will never be greater than 50 and less than 0
    public static String setPickGUI(int value) {
        StringBuilder ret = new StringBuilder(ChatColor.GREEN.toString());
        for (int i = 0; i < value; i++) {
            ret.append("|");
        }
        ret.append(ChatColor.RED.toString());
        for (int i = 0; i <50-value; i++) {
            ret.append("|");
        }
        return ret.toString();
    }

    public static int calculatePickGUI(int minExp, int maxExp) {
        return (int)(((float)minExp)/((float)maxExp)*50);
    }

    public static int getExpNeeded(int level) {

        if (level == 1) {
            return 176;
        }

        if (level == 100) {
            return 0;
        }

        int prevLevel = level - 1;
        return (int) (Math.pow(prevLevel, 2) + (prevLevel * 20) + 150 + (prevLevel * 4) + getExpNeeded(prevLevel));
    }

    //pre: pick will always be a pick
    private int getLevel(ItemStack pick) {
        try {
            return Integer.parseInt(ChatColor.stripColor(pick.getItemMeta().getLore().get(0).split(": ")[1].trim()));
        }
        catch (NumberFormatException e) {
            System.out.println("Error: NumberFormatException found in getLevel, should not be possible as it is only being called on a pick.");
            return -1;
        }
    }
    //pre: pick will always be a pick
    private int[] getExps(ItemStack pick) {
        try {
            return new int[]{Integer.parseInt(ChatColor.stripColor(pick.getItemMeta().getLore().get(1).split("/")[0].trim())), Integer.parseInt(ChatColor.stripColor(pick.getItemMeta().getLore().get(1).split("/")[1].trim()))};
        }
        catch (NumberFormatException e) {
            System.out.println("Error: NumberFormatException found in getExps, should not be possible as it is only being called on a pick.");
            return new int[]{-1,-1};
        }
    }

    private int oreExp(Tier tier) {
        switch (tier) {
            case T1:
                return Generator.generateRandom(90,125);
            case T2:
                return Generator.generateRandom(275,310);
            case T3:
                return Generator.generateRandom(480,540);
            case T4:
                return Generator.generateRandom(820,860);
            case T5:
                return Generator.generateRandom(1025,1080);
            default:
                return 0;
        }
    }

    private Tier getTier(Block block) {
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

    private Material getOre(Tier tier) {
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

    private ItemStack addOre(Tier tier) {
        switch (tier) {
            case T1:
                return Generator.generateItem(Material.COAL_ORE, ChatColor.WHITE.toString() + "Coal Ore", ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "A chunk of coal ore.");
            case T2:
                return Generator.generateItem(Material.EMERALD_ORE, ChatColor.GREEN.toString() + "Emerald Ore", ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "An unrefined piece of emerald ore.");
            case T3:
                return Generator.generateItem(Material.IRON_ORE, ChatColor.AQUA.toString() + "Iron Ore", ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "A piece of raw iron.");
            case T4:
                return Generator.generateItem(Material.DIAMOND_ORE,  ChatColor.LIGHT_PURPLE.toString() + "Diamond Ore", ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "A sharp chunk of diamond ore.");
            case T5:
                return Generator.generateItem(Material.GOLD_ORE, ChatColor.YELLOW.toString() + "Gold Ore", ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "A sparkling piece of gold ore.");
        }
        return null;
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
    }



    private void playerOreEvent(Player player, Ore ore) {
        Tier tier = ore.tier;
        ItemStack pick = player.getInventory().getItemInMainHand();
        int oreTier = 0;
        try {
            oreTier = Integer.parseInt(tier.toString().substring(1));
        }
        catch (NumberFormatException e){
            System.out.println("enum somehow doesnt end with a number, should never happen");
        }
        int pickTier = -1;

        //there should never be a non lore'd pickaxe in dr
        switch (pick.getType()) {
            case GOLD_PICKAXE:
                pickTier = 5;
                break;
            case DIAMOND_PICKAXE:
                pickTier = 4;
                break;
            case IRON_PICKAXE:
                pickTier = 3;
                break;
            case STONE_PICKAXE:
                pickTier = 2;
                break;
            case WOOD_PICKAXE:
                pickTier = 1;
                break;
        }

        if (pickTier>=oreTier) {
            player.getInventory().addItem(addOre(tier));
            expToPlayer(player,pick,tier,pickTier,oreTier);
            addToOreList(ore);
            ore.location.getBlock().setType(Material.STONE);
        }
    }

    //pre pick will always be a proper pick
    private void expToPlayer(Player player,ItemStack pick, Tier tier,int pickTier, int oreTier) {
        int level = getLevel(pick);
        int expAdd = oreExp(tier);
        if (pickTier>oreTier) {
            expAdd/=2;
        }
        if (level==100) {
            return;
        }
        int[] exp = getExps(pick);
        exp[0] += expAdd;
        if (exp[0]>=exp[1]) {
            //minexp will never be double maxexp unless the exp from ore is set to a ridiculous value. just dont do that for the love of god
            miningDebug(player,pick,expAdd,MiningRewards.LEVEL_UP,tier);
            exp[0]-=exp[1];
            level++;
        }
        pick = Generator.generatePickaxe(level,exp[0]);
        miningDebug(player,pick,expAdd,MiningRewards.ORE_MINED,tier);
        player.getInventory().setItemInMainHand(pick);
    }



    //pre: pick will be a pick
    private void miningDebug(Player player,ItemStack pick,int expAmount, MiningRewards miningRewards,Tier tier) {
        switch (miningRewards) {
            case LEVEL_UP:
                player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "         " + "PICKAXE LEVEL UP! " + ChatColor.YELLOW + ChatColor.UNDERLINE + (getLevel(pick) - 1) + ChatColor.BOLD + " -> " + ChatColor.YELLOW + ChatColor.UNDERLINE + getLevel(pick));
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 1F);
                break;
            case ORE_MINED:
                player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "          +" + ChatColor.YELLOW + expAmount + ChatColor.BOLD + " EXP" + ChatColor.YELLOW + ChatColor.GRAY + " [" + getExps(pick)[0] + ChatColor.BOLD + "/" + ChatColor.GRAY + getExps(pick)[1] + " EXP]");
                break;
            case PICK_CHANGE:
                break;
            default:
                break;
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
        Ore ore = new Ore(tier, time, location);

        playerOreEvent(event.getPlayer(),ore);
    }

    /*---------------------------
    ENUMS
    ---------------------------*/

    public enum Tier {
        T1,T2,T3,T4,T5
    }

    private enum MiningRewards {
        LEVEL_UP,ORE_MINED,PICK_CHANGE
    }

}
