package me.AnFun.DRMining.Generator;

import me.AnFun.DRMining.Mining.Mining;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;
import net.minecraft.server.v1_12_R1.NBTTagString;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Generator {

    public static ItemStack generateItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack generatePickaxe(int level,int exp) {
        ItemStack pickaxe = null;
        NBTTagList pickaxeTags = new NBTTagList();

        if (level>=100) {
            pickaxe = generateItem(Material.GOLD_PICKAXE, ChatColor.YELLOW.toString() + "Grandmaster Pickaxe", ChatColor.GRAY.toString() + "Level: " + ChatColor.YELLOW.toString() + 100,ChatColor.GRAY +""+exp+" / " + Mining.getExpNeeded(100), ChatColor.GRAY.toString() + "EXP: " + ChatColor.GREEN.toString() + "||||||||||||||||||||||||||||||||||||||||||||||||||", ChatColor.GRAY.toString() + ChatColor.ITALIC + "A pickaxe made out of reinforced gold.");
            pickaxe.addEnchantment(Enchantment.MENDING,1);
            pickaxeTags.add(new NBTTagString("minecraft:coal_ore"));
            pickaxeTags.add(new NBTTagString("minecraft:emerald_ore"));
            pickaxeTags.add(new NBTTagString("minecraft:iron_ore"));
            pickaxeTags.add(new NBTTagString("minecraft:diamond_ore"));
            pickaxeTags.add(new NBTTagString("minecraft:gold_ore"));
        }
        else if (level<=0) {
            pickaxe = generateItem(Material.WOOD_PICKAXE, ChatColor.WHITE.toString() + "Novice Pickaxe", ChatColor.GRAY.toString() + "Level: " + ChatColor.WHITE.toString() + 1,ChatColor.GRAY +""+exp+" / " + Mining.getExpNeeded(1), ChatColor.GRAY.toString() + "EXP: " + ChatColor.RED.toString() + "||||||||||||||||||||||||||||||||||||||||||||||||||", ChatColor.GRAY.toString() + ChatColor.ITALIC + "A pickaxe made out of sturdy wood.");
            pickaxeTags.add(new NBTTagString("minecraft:coal_ore"));
        }
        else {
            switch (level/20) {
                case 4:
                    pickaxe = generateItem(Material.GOLD_PICKAXE, ChatColor.YELLOW.toString() + "Master Pickaxe", ChatColor.GRAY.toString() + "Level: " + ChatColor.YELLOW.toString() + level,ChatColor.GRAY +""+exp+" / " + Mining.getExpNeeded(level), ChatColor.GRAY.toString() + "EXP: " + Mining.setPickGUI(Mining.calculatePickGUI(exp,Mining.getExpNeeded(level))), ChatColor.GRAY.toString() + ChatColor.ITALIC + "A pickaxe made out of reinforced gold.");
                    pickaxeTags.add(new NBTTagString("minecraft:coal_ore"));
                    pickaxeTags.add(new NBTTagString("minecraft:emerald_ore"));
                    pickaxeTags.add(new NBTTagString("minecraft:iron_ore"));
                    pickaxeTags.add(new NBTTagString("minecraft:diamond_ore"));
                    pickaxeTags.add(new NBTTagString("minecraft:gold_ore"));
                    break;
                case 3:
                    pickaxe = generateItem(Material.DIAMOND_PICKAXE, ChatColor.LIGHT_PURPLE.toString() + "Supreme Pickaxe", ChatColor.GRAY.toString() + "Level: " + ChatColor.LIGHT_PURPLE.toString() + level,ChatColor.GRAY +""+exp+" / " + Mining.getExpNeeded(60), ChatColor.GRAY.toString() + "EXP: " + Mining.setPickGUI(Mining.calculatePickGUI(exp,Mining.getExpNeeded(level))), ChatColor.GRAY.toString() + ChatColor.ITALIC + "A pickaxe made out of hardened diamond.");
                    pickaxeTags.add(new NBTTagString("minecraft:coal_ore"));
                    pickaxeTags.add(new NBTTagString("minecraft:emerald_ore"));
                    pickaxeTags.add(new NBTTagString("minecraft:iron_ore"));
                    pickaxeTags.add(new NBTTagString("minecraft:diamond_ore"));
                    break;
                case 2:
                    pickaxe = generateItem(Material.IRON_PICKAXE, ChatColor.AQUA.toString() + "Expert Pickaxe", ChatColor.GRAY.toString() + "Level: " + ChatColor.AQUA.toString() + level,ChatColor.GRAY +""+exp+" / " + Mining.getExpNeeded(level), ChatColor.GRAY.toString() + "EXP: " + Mining.setPickGUI(Mining.calculatePickGUI(exp,Mining.getExpNeeded(level))), ChatColor.GRAY.toString() + ChatColor.ITALIC + "A pickaxe made out of forged iron.");
                    pickaxeTags.add(new NBTTagString("minecraft:coal_ore"));
                    pickaxeTags.add(new NBTTagString("minecraft:emerald_ore"));
                    pickaxeTags.add(new NBTTagString("minecraft:iron_ore"));
                    break;
                case 1:
                    pickaxe = generateItem(Material.STONE_PICKAXE, ChatColor.GREEN.toString() + "Apprentice Pickaxe", ChatColor.GRAY.toString() + "Level: " + ChatColor.GREEN.toString() + level,ChatColor.GRAY +""+exp+" / " + Mining.getExpNeeded(level), ChatColor.GRAY.toString() + "EXP: " + Mining.setPickGUI(Mining.calculatePickGUI(exp,Mining.getExpNeeded(level))), ChatColor.GRAY.toString() + ChatColor.ITALIC + "A pickaxe made out of cave stone.");
                    pickaxeTags.add(new NBTTagString("minecraft:coal_ore"));
                    pickaxeTags.add(new NBTTagString("minecraft:emerald_ore"));
                    break;
                case 0:
                    pickaxe = generateItem(Material.WOOD_PICKAXE, ChatColor.WHITE.toString() + "Novice Pickaxe", ChatColor.GRAY.toString() + "Level: " + ChatColor.WHITE.toString() + level,ChatColor.GRAY +""+exp+" / " + Mining.getExpNeeded(level), ChatColor.GRAY.toString() + "EXP: " + Mining.setPickGUI(Mining.calculatePickGUI(exp,Mining.getExpNeeded(level))), ChatColor.GRAY.toString() + ChatColor.ITALIC + "A pickaxe made out of sturdy wood.");
                    pickaxeTags.add(new NBTTagString("minecraft:coal_ore"));
                    break;
            }
        }

        //picks can only break those blocks in gamemode 2, survival can break all blocks
        net.minecraft.server.v1_12_R1.ItemStack stack = CraftItemStack.asNMSCopy(pickaxe);
        NBTTagCompound tag = stack.hasTag() ? stack.getTag() : new NBTTagCompound();
        assert tag != null;
        tag.set("CanDestroy", pickaxeTags);
        stack.setTag(tag);
        return CraftItemStack.asBukkitCopy(stack);
    }

    public static int generateRandom(int min, int max) {
        return min + ThreadLocalRandom.current().nextInt(1 + max - min);
    }

}
