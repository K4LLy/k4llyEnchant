/*package de.k4lly.enchant.listener;

import de.k4lly.enchant.controller.PluginController;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;

public class AnvilZwei implements Listener {

    private PluginController controller;

    public AnvilZwei(PluginController controller) {
        this.controller = controller;
    }

    @EventHandler
    public void onPrepareAnvilEvent(PrepareAnvilEvent prepareAnvilEvent) {

        if (prepareAnvilEvent.getInventory().getItem(0) != null && prepareAnvilEvent.getInventory().getItem(1) != null) {
            prepareAnvilEvent.setResult(new ItemStack(Material.BEDROCK));
        }

    }
}*/
