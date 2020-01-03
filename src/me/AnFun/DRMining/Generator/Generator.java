package me.AnFun.DRMining.Generator;

import me.AnFun.DRMining.Durability.Durability;
import me.AnFun.DRMining.Mining.Mining;
import net.minecraft.server.v1_12_R1.*;
import org.apache.commons.lang.time.DateUtils;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    //pre: amount <= 64
    public static ItemStack generateGems(int amount) {
        ItemStack gems = generateItem(Material.EMERALD,ChatColor.WHITE.toString() + "Gem", ChatColor.GRAY.toString() + "The currency of Andalucia");
        gems.setAmount(amount);
        return gems;
    }

    public static ItemStack generateOrb(Tier tier) {
        ItemStack orb = new ItemStack(Material.FIREWORK_CHARGE);
        ChatColor orbChat = ChatColor.WHITE;
        Color orbColor = Color.BLACK;
        switch (tier) {
            case T2:
                orbChat = ChatColor.GREEN;
                orbColor = Color.GREEN;
                break;
            case T3:
                orbChat = ChatColor.AQUA;
                orbColor = Color.AQUA;
                break;
            case T4:
                orbChat = ChatColor.LIGHT_PURPLE;
                orbColor = Color.PURPLE;
                break;
            case T5:
                orbChat = ChatColor.YELLOW;
                orbColor = Color.YELLOW;
                break;
        }
        ItemMeta meta = orb.getItemMeta();
        FireworkMeta fMeta = (FireworkMeta) orb.getItemMeta();
        Builder fe = FireworkEffect.builder();
        fe.withColor(orbColor);
        fMeta.addEffect(fe.build());
        orb.setItemMeta(fMeta);
        List<String> lore = new ArrayList<>();
        meta.setDisplayName(orbChat.toString() + "Orb of Alteration");
        lore.add(ChatColor.GRAY.toString() + "Place on " + orbChat.toString() + tier.toString() + ChatColor.GRAY.toString() + " equipment to " + orbChat.toString() + "randomize" + ChatColor.GRAY.toString() + " all bonus stats.");
        orb.setItemMeta(meta);
        meta.setLore(lore);

        return orb;
    }

    public static ItemStack generatePickaxe (ItemStack pickaxe,int level, int exp, boolean dura) {
        String[] enchantStrings = Mining.getMiningEnchantStrings();
        int[] enchantValues = Mining.getMiningEnchantValues(pickaxe);
        ItemStack newPick = generatePickaxe(level,exp);
        ItemMeta meta = newPick.getItemMeta();
        List<String> lore = meta.getLore();
        if (dura) lore = Durability.setDura(newPick,Durability.getDura(pickaxe.getItemMeta().getLore()));
        meta.setDisplayName(newPick.getItemMeta().getDisplayName());
        for (int i = 0; i < enchantStrings.length; i++) {
            if (enchantValues[i]>0) {
                lore.add(lore.size()-1,ChatColor.RED.toString()+enchantStrings[i]+enchantValues[i]+"%");
            }
        }
        meta.setLore(lore);
        newPick.setItemMeta(meta);
        return newPick;
    }

    public static ItemStack generatePickaxe(int level,int exp) {
        ItemStack pickaxe = null;
        if (level>=100) {
            pickaxe = generateItem(Material.GOLD_PICKAXE, ChatColor.YELLOW.toString() + "Grandmaster Pickaxe", ChatColor.GRAY.toString() + "Level: " + ChatColor.YELLOW.toString() + 100,ChatColor.GRAY +""+exp+" / " + Mining.getExpNeeded(100), ChatColor.GRAY.toString() + "EXP: " + ChatColor.GREEN.toString() + "||||||||||||||||||||||||||||||||||||||||||||||||||", ChatColor.GRAY.toString() + ChatColor.ITALIC + "A pickaxe made out of reinforced gold.");
            pickaxe.addEnchantment(Enchantment.MENDING,1);
        }
        else if (level<=0) {
            pickaxe = generateItem(Material.WOOD_PICKAXE, ChatColor.WHITE.toString() + "Novice Pickaxe", ChatColor.GRAY.toString() + "Level: " + ChatColor.WHITE.toString() + 1,ChatColor.GRAY +""+exp+" / " + Mining.getExpNeeded(1), ChatColor.GRAY.toString() + "EXP: " + ChatColor.RED.toString() + "||||||||||||||||||||||||||||||||||||||||||||||||||",ChatColor.RED.toString() + "MINING SUCCESS: 50%",ChatColor.RED.toString() + "GEM FIND: 50%",ChatColor.RED.toString() + "DOUBLE ORE: 50%", ChatColor.GRAY.toString() + ChatColor.ITALIC + "A pickaxe made out of sturdy wood.");
        }
        else {
            switch (level/20) {
                case 4:
                    pickaxe = generateItem(Material.GOLD_PICKAXE, ChatColor.YELLOW.toString() + "Master Pickaxe", ChatColor.GRAY.toString() + "Level: " + ChatColor.YELLOW.toString() + level,ChatColor.GRAY +""+exp+" / " + Mining.getExpNeeded(level), ChatColor.GRAY.toString() + "EXP: " + Mining.setPickGUI(Mining.calculatePickGUI(exp,Mining.getExpNeeded(level))), ChatColor.GRAY.toString() + ChatColor.ITALIC + "A pickaxe made out of reinforced gold.");
                    break;
                case 3:
                    pickaxe = generateItem(Material.DIAMOND_PICKAXE, ChatColor.LIGHT_PURPLE.toString() + "Supreme Pickaxe", ChatColor.GRAY.toString() + "Level: " + ChatColor.LIGHT_PURPLE.toString() + level,ChatColor.GRAY +""+exp+" / " + Mining.getExpNeeded(60), ChatColor.GRAY.toString() + "EXP: " + Mining.setPickGUI(Mining.calculatePickGUI(exp,Mining.getExpNeeded(level))), ChatColor.GRAY.toString() + ChatColor.ITALIC + "A pickaxe made out of hardened diamond.");
                    break;
                case 2:
                    pickaxe = generateItem(Material.IRON_PICKAXE, ChatColor.AQUA.toString() + "Expert Pickaxe", ChatColor.GRAY.toString() + "Level: " + ChatColor.AQUA.toString() + level,ChatColor.GRAY +""+exp+" / " + Mining.getExpNeeded(level), ChatColor.GRAY.toString() + "EXP: " + Mining.setPickGUI(Mining.calculatePickGUI(exp,Mining.getExpNeeded(level))), ChatColor.GRAY.toString() + ChatColor.ITALIC + "A pickaxe made out of forged iron.");
                    break;
                case 1:
                    pickaxe = generateItem(Material.STONE_PICKAXE, ChatColor.GREEN.toString() + "Apprentice Pickaxe", ChatColor.GRAY.toString() + "Level: " + ChatColor.GREEN.toString() + level,ChatColor.GRAY +""+exp+" / " + Mining.getExpNeeded(level), ChatColor.GRAY.toString() + "EXP: " + Mining.setPickGUI(Mining.calculatePickGUI(exp,Mining.getExpNeeded(level))), ChatColor.GRAY.toString() + ChatColor.ITALIC + "A pickaxe made out of cave stone.");
                    break;
                case 0:
                    pickaxe = generateItem(Material.WOOD_PICKAXE, ChatColor.WHITE.toString() + "Novice Pickaxe", ChatColor.GRAY.toString() + "Level: " + ChatColor.WHITE.toString() + level,ChatColor.GRAY +""+exp+" / " + Mining.getExpNeeded(level), ChatColor.GRAY.toString() + "EXP: " + Mining.setPickGUI(Mining.calculatePickGUI(exp,Mining.getExpNeeded(level))), ChatColor.GRAY.toString() + ChatColor.ITALIC + "A pickaxe made out of sturdy wood.");
                    break;
            }
        }

        return setFlags(pickaxe,getMiningTags(pickaxe),"CanDestroy");
    }

    public static int generateRandom(int min, int max) {
        return min + ThreadLocalRandom.current().nextInt(1 + max - min);
    }
    //pre will be a pick
    private static NBTTagList getMiningTags(ItemStack pick) {
        NBTTagList pickaxeTags = new NBTTagList();
        switch(pick.getType()) {
            case WOOD_PICKAXE:
                pickaxeTags.add(new NBTTagString("minecraft:coal_ore"));
                break;
            case STONE_PICKAXE:
                pickaxeTags.add(new NBTTagString("minecraft:coal_ore"));
                pickaxeTags.add(new NBTTagString("minecraft:emerald_ore"));
                break;
            case IRON_PICKAXE:
                pickaxeTags.add(new NBTTagString("minecraft:coal_ore"));
                pickaxeTags.add(new NBTTagString("minecraft:emerald_ore"));
                pickaxeTags.add(new NBTTagString("minecraft:iron_ore"));
                break;
            case DIAMOND_PICKAXE:
                pickaxeTags.add(new NBTTagString("minecraft:coal_ore"));
                pickaxeTags.add(new NBTTagString("minecraft:emerald_ore"));
                pickaxeTags.add(new NBTTagString("minecraft:iron_ore"));
                pickaxeTags.add(new NBTTagString("minecraft:diamond_ore"));
                break;
            case GOLD_PICKAXE:
                pickaxeTags.add(new NBTTagString("minecraft:coal_ore"));
                pickaxeTags.add(new NBTTagString("minecraft:emerald_ore"));
                pickaxeTags.add(new NBTTagString("minecraft:iron_ore"));
                pickaxeTags.add(new NBTTagString("minecraft:diamond_ore"));
                pickaxeTags.add(new NBTTagString("minecraft:gold_ore"));
                break;
        }
        return pickaxeTags;
    }

    private static ItemStack setFlags(ItemStack item, NBTTagList tags, String setTag) {
        net.minecraft.server.v1_12_R1.ItemStack stack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = stack.hasTag() ? stack.getTag() : new NBTTagCompound();
        assert tag != null;
        tag.set(setTag, tags);
        stack.setTag(tag);
        return CraftItemStack.asBukkitCopy(stack);
    }

    public enum Tier {
        T1,T2,T3,T4,T5
    }


    public enum GearTypes {
        WEAPON,ARMOR
    }

}
