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
        if (args.length == 1 && args[0].equalsIgnoreCase("default")) {
            function2();
            commandSender.sendMessage(ChatColor.GREEN + "Successfully changed to Default value");
            return true;
        } else if (args.length != 3) {
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

    private void function2() {
        controller.getMain().getConfig().set("Max-Level", 30);

        controller.getMain().getConfig().set(Enchantment.PROTECTION_ENVIRONMENTAL.getName(), 20);
        controller.getMain().getConfig().set(Enchantment.PROTECTION_FIRE.getName(), 20);
        controller.getMain().getConfig().set(Enchantment.PROTECTION_FALL.getName(), 20);
        controller.getMain().getConfig().set(Enchantment.PROTECTION_EXPLOSIONS.getName(), 20);
        controller.getMain().getConfig().set(Enchantment.PROTECTION_PROJECTILE.getName(), 20);
        controller.getMain().getConfig().set(Enchantment.OXYGEN.getName(), 20);
        controller.getMain().getConfig().set(Enchantment.WATER_WORKER.getName(), 20);
        controller.getMain().getConfig().set(Enchantment.THORNS.getName(), 20);
        controller.getMain().getConfig().set(Enchantment.DEPTH_STRIDER.getName(), 20);

        controller.getMain().getConfig().set(Enchantment.DAMAGE_ALL.getName(), 20);
        controller.getMain().getConfig().set(Enchantment.DAMAGE_UNDEAD.getName(), 20);
        controller.getMain().getConfig().set(Enchantment.DAMAGE_ARTHROPODS.getName(), 20);
        controller.getMain().getConfig().set(Enchantment.KNOCKBACK.getName(), 20);
        controller.getMain().getConfig().set(Enchantment.FIRE_ASPECT.getName(), 20);
        controller.getMain().getConfig().set(Enchantment.LOOT_BONUS_MOBS.getName(), 20);

        controller.getMain().getConfig().set(Enchantment.DIG_SPEED.getName(), 20);
        //controller.getMain().getConfig().set(Enchantment.SILK_TOUCH.getName(), 20);
        controller.getMain().getConfig().set(Enchantment.DURABILITY.getName(), 20);
        controller.getMain().getConfig().set(Enchantment.LOOT_BONUS_BLOCKS.getName(), 20);

        controller.getMain().getConfig().set(Enchantment.ARROW_DAMAGE.getName(), 20);
        controller.getMain().getConfig().set(Enchantment.ARROW_KNOCKBACK.getName(), 20);
        //controller.getMain().getConfig().set(Enchantment.ARROW_FIRE.getName(), 20);
        //controller.getMain().getConfig().set(Enchantment.ARROW_INFINITE.getName(), 20);

        controller.getMain().getConfig().set(Enchantment.LUCK.getName(), 20);
        controller.getMain().getConfig().set(Enchantment.LURE.getName(), 20);
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
