package de.k4lly.enchant.listener;


import de.k4lly.enchant.controller.PluginController;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class AnvilArmor implements Listener{
    private PluginController controller;

    public AnvilArmor(PluginController controller) {
        this.controller = controller;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent clickEvent) {
        if (clickEvent.getInventory().getType() != null && clickEvent.getInventory().getType() == InventoryType.ANVIL) {
            int slot = clickEvent.getSlot();
            ItemStack item0 = clickEvent.getInventory().getItem(0);
            ItemStack item1 = clickEvent.getInventory().getItem(1);

            if ((item0 != null && item0.getTypeId() == Material.ENCHANTED_BOOK.getId()) && (item1 != null && item1.getTypeId() == Material.ENCHANTED_BOOK.getId())) {

            }
        }
    }
}
