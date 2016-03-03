package de.k4lly.enchant;

import de.k4lly.enchant.controller.ConfigController;
import de.k4lly.enchant.controller.PluginController;
import de.k4lly.enchant.listener.Command;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private PluginController controller = new PluginController();

    @Override
    public void onEnable() {
        controller.setMain(this);
        PluginManager pluginManager = getServer().getPluginManager();
        registerEvent(pluginManager);
        controller.setConfig(new ConfigController(controller));
    }

    private void registerEvent(PluginManager pluginManager) {
        Command command = new Command(controller);
        this.getCommand("k4enchant").setExecutor(command);
        this.getCommand("ke").setExecutor(command);
    }

    @Override
    public void onDisable() {
    }
}
