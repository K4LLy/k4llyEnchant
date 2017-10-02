package de.k4lly.enchant.listener;

import de.k4lly.enchant.controller.PluginController;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandSafeMining implements CommandExecutor{

    private PluginController controller;

    public CommandSafeMining(PluginController controller) { this.controller = controller; }

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args) {
        return true;
    }
}
