package de.k4lly.enchant;

import de.k4lly.enchant.controller.ConfigController;
import de.k4lly.enchant.controller.PluginController;
import de.k4lly.enchant.listener.*;
import de.k4lly.enchant.objects.Functions;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private PluginController controller = new PluginController();
    private Functions func = new Functions();
    private final String enable = "[k4llyEnchant] Loaded " + this.getDescription().getName() + " in Version " + this.getDescription().getVersion() + " succesfully!";
    private final String regCrafting = "[k4llyEnchant] Successfuly registrered Crafting Recipes!";
    private final String regEvent = "[k4llyEnchant] Successfully registered Events!";

    @Override
    public void onEnable() {
        controller.setMain(this);
        PluginManager pluginManager = getServer().getPluginManager();
        registerEvent(pluginManager);
        controller.setConfig(new ConfigController(controller));
        registerRecipe();
        System.out.print(enable);
    }

    private void registerEvent(PluginManager pluginManager) {
        Command command = new Command(controller);
        CommandVersion commandVersion = new CommandVersion(controller);
        this.getCommand("k4llyEnchant").setExecutor(command);
        this.getCommand("kE").setExecutor(command);
        this.getCommand("kEVersion").setExecutor(commandVersion);
        pluginManager.registerEvents(new Anvil(controller), this);
        pluginManager.registerEvents(new Crafting(controller), this);
        pluginManager.registerEvents(new Enchantment(controller), this);
        pluginManager.registerEvents(new InfiniteArrow(controller), this);
        System.out.print(regEvent);
    }

    private void registerRecipe() {
        if (this.getConfig().getBoolean("enableTakeEnchantment")) {
            for (Material enchItem : func.getEnchantableMaterial()) {
                ShapelessRecipe sr = new ShapelessRecipe(new ItemStack(Material.ENCHANTED_BOOK, 1));
                sr.addIngredient(Material.BOOK);
                sr.addIngredient(Material.EXP_BOTTLE);
                sr.addIngredient(enchItem);
                this.getServer().addRecipe(sr);
            }
        }
    }

    @Override
    public void onDisable() {
    }
}
