package me.AnFun.DRMining.Durability;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Durability {

    public static List<String> setDura(ItemStack item, int[] dura) {
       List <String> lore = item.getItemMeta().getLore();
        dura[0]--;
        if (dura[0]<=0) {
            lore.add(ChatColor.DARK_RED.toString()+ChatColor.ITALIC.toString()+"Broken");
        }
        else {
            lore.set(lore.size()-1,ChatColor.GRAY.toString()+"Durability: "+dura[0]+" / "+dura[1]);
        }
        return lore;
    }
    //pre: lore will have dura
   public static int[] getDura(List<String> lore) {
        for (String s : lore) {
            if (ChatColor.stripColor(s.toLowerCase()).contains("durability: ")) {
                return new int[] {Integer.parseInt(ChatColor.stripColor(lore.get(lore.size() - 1).split(":")[1].split("/")[0].trim())), Integer.parseInt(ChatColor.stripColor(lore.get(lore.size() - 1).split(":")[1].split("/")[1].trim()))};
            }
        }
        return new int[]{-1,-1};
    }

}
