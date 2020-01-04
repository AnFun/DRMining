package me.AnFun.DRMining.Utils;

import net.minecraft.server.v1_12_R1.*;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {

    public static NBTTagCompound setNBTTagCompound(String key, NBTBase value) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.set(key, value);
        return tag;
    }

    public static NBTTagCompound setNBTTagCompound(String key, String value) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.set(key, new NBTTagString(value));
        return tag;
    }

    public static NBTTagCompound setNBTTagCompound(String key, int value) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.set(key, new NBTTagInt(value));
        return tag;
    }

    public static NBTTagCompound setNBTTagCompound(String key, double value) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.set(key, new NBTTagDouble(value));
        return tag;
    }

    public static Object getNBTTagCompound(NBTTagCompound tag, String key) {
         return tag.get(key);
    }

}
