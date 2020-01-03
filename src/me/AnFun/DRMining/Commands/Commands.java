package me.AnFun.DRMining.Commands;

import me.AnFun.DRMining.Generator.Generator;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Commands {



    public static void onCommand(CommandSender sender, String commandString, String[] args) {
        if (sender instanceof Player) {

            Player player = (Player) sender;
            Command command = Command.valueOf(commandString.toUpperCase());

            switch (command) {
                case GENERATEPICKAXE:
                    player.sendMessage("# of args: "+args.length);
                    if (args.length !=1) {
                        player.sendMessage("Error: Incorrect arguments");
                        break;
                    }
                    else {
                        try {
                            ItemStack pick = Generator.generatePickaxe(Integer.parseInt(args[0]),0);
                            ItemMeta meta = pick.getItemMeta();
                            List<String> lore = meta.getLore();
                            lore.add(ChatColor.GRAY.toString() + "Durability: 3000 / 3000");
                            meta.setLore(lore);
                            pick.setItemMeta(meta);
                            player.getInventory().addItem(pick);
                        }
                        catch (NumberFormatException e) {
                            player.sendMessage("Error: Argument not an integer");
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }


     private enum Command {
        GENERATEPICKAXE
    }

}
