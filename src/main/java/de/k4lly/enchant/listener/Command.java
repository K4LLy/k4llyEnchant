package de.k4lly.enchant.listener;

import de.k4lly.enchant.controller.PluginController;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.HashMap;

public class Command implements CommandExecutor {

    private PluginController controller;

    public Command(PluginController controller) {
        this.controller = controller;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args) {
        if (args.length != 3) {
            return false;
        }

        if (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("s")) {
           /* ArrayList<String> enchantName = EnchantName();
            ArrayList<Integer> enchantID = EnchantID();*/
            HashMap<String, String> enchants = Enchants();
            if (enchants.containsKey(args[1])) {
                controller.getMain().getConfig().set(enchants.get(args[1]), args[2]);
                controller.getMain().saveConfig();
                commandSender.sendMessage(ChatColor.GREEN + "Sucessfully changed " + enchants.get(args[1]) + " to " + args[2]);
                return true;
            } else if (enchants.containsValue(args[1])) {
                controller.getMain().getConfig().set(args[1], args[2]);
                controller.getMain().saveConfig();
                commandSender.sendMessage(ChatColor.GREEN + "Sucessfully changed " + args[1] + " to " + args[2]);
                return true;
            }
            /*for (int i = 0; i < enchantName.size(); i++) {
                if (args[1].equalsIgnoreCase(enchantName.get(i)) || args[1].equals(Enchantments().get(enchantName.get(i)))) {
                    if (Integer.valueOf(args[2]) < 3 || Integer.valueOf(args[2]) > controller.getMain().getConfig().getInt("Max-Level")) {
                        commandSender.sendMessage(ChatColor.RED + "Please choose a valid number between 3 and " + controller.getMain().getConfig().getInt("Max-Level"));
                    } else {
                        controller.getMain().getConfig().set(enchantName.get(i), args[2]);
                        controller.getMain().saveConfig();
                        commandSender.sendMessage(ChatColor.GREEN + "Sucessfully changed " + enchantName.get(i) + " to " + args[2]);
                        return true;
                    }
                }
            }*/
            commandSender.sendMessage(ChatColor.RED + "Please enter a valid Enchantment.");
            return false;
        }

        return false;
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

    private ArrayList<String> EnchantName() {
        ArrayList<String> str = new ArrayList();

        for (int i = 0; i <= 80; i++) {
            if (Enchantment.getById(i) != null) {
                str.add(Enchantment.getById(i).getName());
            }
        }
        return str;
    }

    private ArrayList<Integer> EnchantID() {
        ArrayList<Integer> integer = new ArrayList();
        for (int i = 0; i <= 80; i++) {
            if (Enchantment.getById(i) != null) {
                integer.add(Enchantment.getById(i).getId());
            }
        }
        return integer;
    }
}
