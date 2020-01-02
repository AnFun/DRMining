package me.AnFun.DRMining.Commands;

import me.AnFun.DRMining.Generator.Generator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                            player.getInventory().addItem(Generator.generatePickaxe(Integer.parseInt(args[0]),0));
                        }
                        catch (NumberFormatException e) {
                            player.sendMessage("Error: Argument not and integer");
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
