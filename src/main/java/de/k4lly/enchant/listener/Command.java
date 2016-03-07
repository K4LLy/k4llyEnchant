package de.k4lly.enchant.listener;

import de.k4lly.enchant.controller.PluginController;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;

public class Command implements CommandExecutor {

    private PluginController controller;
    private final String sucCom = "[k4llyEnchant] Successfully changed settings.";
    private final String faiCom = "[k4llyEnchant] Failed to change settings.";

    public Command(PluginController controller) {
        this.controller = controller;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args) {
        if (args.length != 3) {
            System.out.print(faiCom);
            return false;
        }
        if (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("s")) {
            HashMap<String, String> enchants = Enchants();
            if (enchants.containsKey(args[1])) {
               return function(commandSender, enchants.get(args[1]), Integer.valueOf(args[2]));
            } else if (enchants.containsValue(args[1].toUpperCase())) {
                return  function(commandSender, args[1].toUpperCase(), Integer.valueOf(args[2]));
            } else if (args[1].equalsIgnoreCase("max-level")) {
                return function(commandSender, "Max-Level", Integer.valueOf(args[2]));
            }
            commandSender.sendMessage(ChatColor.RED + "Please enter a valid Enchantment.");
            System.out.print(faiCom);
            return false;
        }
        System.out.print(faiCom);
        return false;
    }

    private boolean function(CommandSender commandSender, String enchantment, int maxValue) {
        if (enchantment.equals(Enchantment.ARROW_FIRE.getName()) || enchantment.equals(Enchantment.ARROW_INFINITE.getName()) || enchantment.equals(Enchantment.SILK_TOUCH.getName())) {
            commandSender.sendMessage(ChatColor.RED + "This isn´t useful to do.");
            System.out.print(faiCom);
            return true;
        } else {
            if (maxValue <= controller.getMain().getConfig().getInt("Max-Level")) {
                controller.getMain().getConfig().set(enchantment, maxValue);
                controller.getMain().saveConfig();
                commandSender.sendMessage(ChatColor.GREEN + "Successfully changed " + enchantment + " to " + maxValue);
                System.out.print(sucCom);
                return true;
            } else {
                commandSender.sendMessage(ChatColor.RED + "Please enter a valid number between 4 and " + controller.getMain().getConfig().getInt("Max-Level") + ".");
                System.out.print(faiCom);
                return true;
            }
        }
    }

    private HashMap<String, String> Enchants() {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i <= 80; i++) {
            if (Enchantment.getById(i) != null) {
                map.put(Enchantment.getById(i).getId() + "", Enchantment.getById(i).getName());
            }
        }
        return map;
    }
}
