package de.k4lly.enchant.listener;

import de.k4lly.enchant.controller.PluginController;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;

public class Command implements CommandExecutor{

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
            for (int i=0; i <= EnchantName().length; i++) {
                if (args[1].equalsIgnoreCase(EnchantName()[i]) || args[1].equals(EnchantID()[i])) {
                    if (Integer.valueOf(args[2]) < 3 || Integer.valueOf(args[2]) > controller.getMain().getConfig().getInt("Max-Level")) {
                        commandSender.sendMessage(ChatColor.RED + "Please choose a valid number between 3 and " + controller.getMain().getConfig().getInt("Max-Level"));
                    } else {
                        controller.getMain().getConfig().set(EnchantName()[i], args[2]);
                        controller.getMain().saveConfig();
                        commandSender.sendMessage(ChatColor.GREEN + "Sucessfully changed " + EnchantName()[i] + " to " + args[2]);
                    }
                }
            }
            commandSender.sendMessage(ChatColor.RED + "Please enter a valid Enchantment.");
            return false;
        }

        return false;
    }

    private String[] EnchantName() {
        String[] str = null;
        int x = 0;
        for (int i=0; i<=80; i++) {
            if (Enchantment.getById(i) != null) {
                str[x] = Enchantment.getById(i).getName();
                x++;
            }
        }
        return str;
    }

    private int[] EnchantID() {
        int x = 0;
        for (int i=0; i<=80; i++) {
            if (Enchantment.getById(i) != null) {
                EnchantID()[x] = Enchantment.getById(i).getId();
                x++;
            }
        }
        return EnchantID();
    }
}
