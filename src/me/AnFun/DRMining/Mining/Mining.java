package me.AnFun.DRMining.Mining;

import me.AnFun.DRMining.Generator.Generator;
import me.AnFun.DRMining.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Mining implements Listener {
    private List<Ore> oreList = new ArrayList<>();
    private static String[] miningEnchants = new String[]{"MINING SUCCESS: ", "DURABILITY: ", "DOUBLE ORE: ", "TRIPLE ORE: ", "GEM FIND: ", "TREASURE FIND: "};

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
        Generator.Tier tier;
        long time;
        Location location;

        Ore(Generator.Tier tier, long time, Location location) {
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
    private static int getLevel(ItemStack pick) {
        try {
            return Integer.parseInt(ChatColor.stripColor(pick.getItemMeta().getLore().get(0).split(": ")[1].trim()));
        }
        catch (NumberFormatException e) {
            System.out.println("Error: NumberFormatException found in getLevel, should not be possible as it is only being called on a pick.");
            return -1;
        }
    }
    //pre: pick will always be a pick
    private static int[] getExps(ItemStack pick) {
        try {
            return new int[]{Integer.parseInt(ChatColor.stripColor(pick.getItemMeta().getLore().get(1).split("/")[0].trim())), Integer.parseInt(ChatColor.stripColor(pick.getItemMeta().getLore().get(1).split("/")[1].trim()))};
        }
        catch (NumberFormatException e) {
            System.out.println("Error: NumberFormatException found in getExps, should not be possible as it is only being called on a pick.");
            return new int[]{-1,-1};
        }
    }

    private int oreExp(Generator.Tier tier) {
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

    private Generator.Tier getTier(Block block) {
        switch (block.getType()){
            case GOLD_ORE:
                return Generator.Tier.T5;
            case DIAMOND_ORE:
                return Generator.Tier.T4;
            case IRON_ORE:
                return Generator.Tier.T3;
            case EMERALD_ORE:
                return Generator.Tier.T2;
            case COAL_ORE:
                return Generator.Tier.T1;
        }
        return null;
    }

    private Material getOre(Generator.Tier tier) {
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

    private ItemStack addOre(Generator.Tier tier) {
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

    private long getTime(Generator.Tier tier) {
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
        Generator.Tier tier = ore.tier;
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
            miningChance(player,pick,ore,pickTier,oreTier);
        }
    }

    //pre pick will always be a proper pick
    private void expToPlayer(Player player, ItemStack pick, Generator.Tier tier, int pickTier, int oreTier, boolean dura) {
        int level = getLevel(pick);
        int expAdd = oreExp(tier);
        int[] exp = getExps(pick);
        if (pickTier > oreTier) {
            expAdd /= 2;
        }
        if (level == 100) {
            return;
        }
        exp[0] += expAdd;
        if (exp[0] >= exp[1]) {
            //minexp will never be double maxexp unless the exp from ore is set to a ridiculous value. just dont do that for the love of god
            miningDebug(player, pick, expAdd, MiningDebugValues.LEVEL_UP, tier);
            exp[0] -= exp[1];
            level++;
        }
        pick = Generator.generatePickaxe(pick,level,exp[0],dura);
        player.getInventory().setItemInMainHand(pick);
        miningDebug(player,pick,expAdd, MiningDebugValues.ORE_MINED,tier);
        miningEnchants(player,pick,level,exp[0],tier);
    }



    //pre: pick will be a pick
    private void miningDebug(Player player, ItemStack pick, int amount, MiningDebugValues miningDebugValues, Generator.Tier tier) {
        switch (miningDebugValues) {
            case LEVEL_UP:
                player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "         " + "PICKAXE LEVEL UP! " + ChatColor.YELLOW + ChatColor.UNDERLINE + getLevel(pick) + ChatColor.BOLD + " -> " + ChatColor.YELLOW + ChatColor.UNDERLINE + (getLevel(pick)+1));
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 1F);
                break;
            case ORE_MINED:
                player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "          +" + ChatColor.YELLOW + amount + ChatColor.BOLD + " EXP" + ChatColor.YELLOW + ChatColor.GRAY + " [" + getExps(pick)[0] + ChatColor.BOLD + "/" + ChatColor.GRAY + getExps(pick)[1] + " EXP]");
                break;
            case MINE_FAILED:
                player.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "You fail to gather any ore.");
                break;
            case PICK_CHANGE:
                break;
            case GEM_FIND:
                player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "          FOUND " + amount + " GEM(s)" + ChatColor.YELLOW + "");
                break;
            case DOUBLE_ORE:
                player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "          DOUBLE ORE DROP" + ChatColor.YELLOW + " (2x)");
                break;
            case TRIPLE_ORE:
                player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "          TRIPLE ORE DROP" + ChatColor.YELLOW + " (3x)");
                break;
            case DURABILITY:
                player.sendMessage(ChatColor.GREEN + "Your durability resulted in no durability lost");
                break;
            case MINING_SUCCESS:
                player.sendMessage(ChatColor.GREEN + "Your mining sucess resulted in ore");
                break;
            case TREASURE_FIND:
                break;
            default:
                break;
        }
    }

    private void miningChance(Player player, ItemStack pick, Ore ore, int pickTier, int oreTier) {
        boolean success = true;
        boolean dura = true;
        if (getLevel(pick)<100&&pickTier==oreTier) {
            int baseChance = 50;
            int pickChance = baseChance + (getLevel(pick) % 20)*5;
            System.out.println(""+pickChance/100.0);

            double chance = ThreadLocalRandom.current().nextDouble();
            double miningSuccess = getMiningEnchantValues(pick)[0]/100.0;
            if ((pickChance/100.0) < chance) {
                if ((pickChance/100.0)+miningSuccess>=chance) {
                    miningDebug(player,pick,0, MiningDebugValues.MINING_SUCCESS,null);
                }
                else {
                    success = false;
                }
            }
        }

        if (getMiningEnchantValues(pick)[1]/100.0 >= ThreadLocalRandom.current().nextDouble()) {
            dura = false;
        }

        if (success) {
            expToPlayer(player,pick,ore.tier,pickTier,oreTier,dura);
        }
        else {
            miningDebug(player, null, 0, MiningDebugValues.MINE_FAILED,null);
            pick = Generator.generatePickaxe(pick,getLevel(pick),getExps(pick)[0],dura);
            player.getInventory().setItemInMainHand(pick);
        }
        if (!dura) miningDebug(player,null,0, MiningDebugValues.DURABILITY,null);
        addToOreList(ore);
        ore.location.getBlock().setType(Material.STONE);
    }

    public static String[] getMiningEnchantStrings() {
        return miningEnchants;
    }

    public static int[] getMiningEnchantValues(ItemStack pick) {
        int[] enchantValues = new int[6];
        for (String lore : pick.getItemMeta().getLore()) {
            for (int i = 0; i < miningEnchants.length; i++) {
                if (lore.contains(ChatColor.stripColor(miningEnchants[i]))) {
                    enchantValues[i] += Integer.parseInt(ChatColor.stripColor(lore.substring(0, lore.length()-1)).split(": ")[1]);
                }
            }
        }
        return enchantValues;
    }


    private void miningEnchants(Player player, ItemStack pick,int level,int exp, Generator.Tier tier) {
        ItemStack ore = addOre(tier);
        assert ore != null;
        String[] enchantTypes = miningEnchants;
        int[] enchantValues = getMiningEnchantValues(pick);
        for (int i = 0; i < enchantTypes.length; i++) {
            String type = enchantTypes[i];
            int value = enchantValues[i];
            switch (type) {
                case "DOUBLE ORE: ":
                    if (value>0 && value/100.0>=ThreadLocalRandom.current().nextDouble()) {
                        ore.setAmount(2);
                        miningDebug(player,null,0, MiningDebugValues.DOUBLE_ORE,null);
                    }
                    break;
                case "TRIPLE ORE: ":
                    if (value>0 && value/100.0>=ThreadLocalRandom.current().nextDouble()) {
                        ore.setAmount(3);
                        miningDebug(player,null,0, MiningDebugValues.TRIPLE_ORE,null);
                    }
                    break;
                case "GEM FIND: ":
                    if (value>0 && value/100.0>=ThreadLocalRandom.current().nextDouble()) {
                        int gemAmount = 0;
                        switch (tier) {
                            case T1:
                                gemAmount = Generator.generateRandom(10, 20);
                                break;
                            case T2:
                                gemAmount = Generator.generateRandom(25, 35);
                                break;
                            case T3:
                                gemAmount = Generator.generateRandom(40, 55);
                                break;
                            case T4:
                                gemAmount = Generator.generateRandom(80, 100);
                                break;
                            case T5:
                                gemAmount = Generator.generateRandom(140, 160);
                                break;
                        }
                        miningDebug(player,null,gemAmount, MiningDebugValues.GEM_FIND,null);
                        while (gemAmount > 0) {
                            int drop = Math.min(gemAmount,64);
                            player.getWorld().dropItem(player.getLocation(),Generator.generateGems(drop));
                            gemAmount-=drop;
                        }
                    }
                    break;
                case "TREASURE FIND: ":

                    break;
            }
        }
        player.getInventory().addItem(ore);
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
        Generator.Tier tier = getTier(event.getBlock());
        if (tier == null) {
            return;
        }
        Location location = event.getBlock().getLocation();
        long time = getTime(tier);
        Ore ore = new Ore(tier, time, location);
        playerOreEvent(event.getPlayer(),ore);
    }

    @EventHandler
    public void onInteract (PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK && getTier(event.getClickedBlock()) != null) {
            Player player = event.getPlayer();
            player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
            player.removePotionEffect(PotionEffectType.FAST_DIGGING);
            Block ore = event.getClickedBlock();
            int oreTier = 0;
            switch (ore.getType()) {
                case GOLD_ORE:
                    oreTier = 5;
                    break;
                case DIAMOND_ORE:
                    oreTier = 4;
                    break;
                case IRON_ORE:
                    oreTier = 3;
                    break;
                case EMERALD_ORE:
                    oreTier = 2;
                    break;
                case COAL_ORE:
                    oreTier = 1;
                    break;
            }
            int pickTier = -1;

            //there should never be a non lore'd pickaxe in dr
            switch (player.getInventory().getItemInMainHand().getType()) {
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

            if (pickTier != -1 && oreTier != 0 && pickTier <= oreTier) {
                if (pickTier == oreTier && pickTier == 2) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 80, 0));
                }
                else if (pickTier<oreTier) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 400, 0));
                }
                else if (pickTier != 1 && getLevel(player.getInventory().getItemInMainHand()) < 100) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 80, 0));
                }
            }
        }
    }

    /*---------------------------
    ENUMS
    ---------------------------*/

    private enum MiningDebugValues {
        LEVEL_UP,ORE_MINED,MINE_FAILED,PICK_CHANGE,DOUBLE_ORE,TRIPLE_ORE,GEM_FIND, MINING_SUCCESS,DURABILITY,TREASURE_FIND
    }

    public static enum MiningEnchants {
        DOUBLE_ORE, TRIPLE_ORE, GEM_FIND, MINING_SUCCESS, DURABILITY, TREASURE_FIND
    }

}
