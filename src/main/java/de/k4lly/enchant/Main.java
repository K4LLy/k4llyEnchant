package de.k4lly.enchant;

import de.k4lly.enchant.controller.ConfigController;
import de.k4lly.enchant.controller.PluginController;
import de.k4lly.enchant.listener.*;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

    private PluginController controller = new PluginController();
    private final String enable = "[k4llyEnchant] Loaded " + this.getDescription().getName() + " in Version " + this.getDescription().getVersion() + " succesfully!";
    private final String regEvent = "[k4llyEnchant] Successfully register Events!";

    @Override
    public void onEnable() {
        controller.setMain(this);
        PluginManager pluginManager = getServer().getPluginManager();
        registerEvent(pluginManager);
        controller.setConfig(new ConfigController(controller));
        System.out.print(enable);
    }

    private void registerEvent(PluginManager pluginManager) {
        Command command = new Command(controller);
        this.getCommand("k4llyEnchant").setExecutor(command);
        this.getCommand("kE").setExecutor(command);
        pluginManager.registerEvents(new Join(controller), this);
        pluginManager.registerEvents(new AnvilBooks(controller), this);
        pluginManager.registerEvents(new AnvilArmor(controller), this);
        pluginManager.registerEvents(new AnvilTools(controller), this);
        pluginManager.registerEvents(new AnvilWeapons(controller), this);
        System.out.print(regEvent);
    }

    @Override
    public void onDisable() {
    }
}
