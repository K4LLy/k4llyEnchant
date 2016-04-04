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

import java.util.ArrayList;

public class Main extends JavaPlugin {

    private PluginController controller = new PluginController();
    private Functions func = new Functions();
    private final String enable = "[k4llyEnchant] Loaded " + this.getDescription().getName() + " in Version " + this.getDescription().getVersion() + " succesfully!";
    private final String regEvent = "[k4llyEnchant] Successfully register Events!";
    private ArrayList<ShapelessRecipe> shapelessRecipes = new ArrayList<ShapelessRecipe>();

    @Override
    public void onEnable() {
        controller.setMain(this);
        PluginManager pluginManager = getServer().getPluginManager();
        registerEvent(pluginManager);
        controller.setConfig(new ConfigController(controller));
        System.out.print(enable);

        registerRecipe();
    }

    private void registerEvent(PluginManager pluginManager) {
        Command command = new Command(controller);
        CommandVersion commandVersion = new CommandVersion(controller);
        this.getCommand("k4llyEnchant").setExecutor(command);
        this.getCommand("kE").setExecutor(command);
        this.getCommand("kEVersion").setExecutor(commandVersion);
        pluginManager.registerEvents(new Anvil(controller), this);
        pluginManager.registerEvents(new Crafting(controller), this);
        System.out.print(regEvent);
    }

    private void registerRecipe() {
        for (int i = 0; i <= 450; i++) {
            int x = 0;
            if (func.getEnchantableItem(i) != null) {
                shapelessRecipes.add(x, new ShapelessRecipe(new ItemStack(Material.ENCHANTED_BOOK ,1)));
                shapelessRecipes.get(x).addIngredient(Material.BOOK);
                shapelessRecipes.get(x).addIngredient(Material.EXP_BOTTLE);
                shapelessRecipes.get(x).addIngredient(func.getEnchantableItem(i).getType());
                x++;
            }
        }
        for (int i = 0; i < shapelessRecipes.size(); i++) {
            this.getServer().addRecipe(shapelessRecipes.get(i));
        }
    }

    @Override
    public void onDisable() {
    }
}
