package de.k4lly.enchant.listener;

import de.k4lly.enchant.controller.PluginController;
import de.k4lly.enchant.objects.AnvilItems;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.ArrayList;

public class AnvilBooks implements Listener {

    private PluginController controller;
    private ArrayList<Player> players;
    //private boolean slot2 = false;
    private ItemStack item;

    public AnvilBooks(PluginController controller) {
        this.controller = controller;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent clickEvent) {
        if (clickEvent.getInventory().getType() != null && clickEvent.getInventory().getType() == InventoryType.ANVIL) {
            int slot = clickEvent.getSlot();
            ItemStack item0 = clickEvent.getInventory().getItem(0);
            ItemStack item1 = clickEvent.getInventory().getItem(1);

            if ((item0 != null && item0.getTypeId() == Material.ENCHANTED_BOOK.getId()) && (item1 != null && item1.getTypeId() == Material.ENCHANTED_BOOK.getId())) {
                clickEvent.setResult(Event.Result.DENY);
                if (!players.contains(clickEvent.getWhoClicked())) {
                    item = item2(item0, item1);
                    clickEvent.getInventory().setItem(2, item);
                    players.add((Player) clickEvent.getWhoClicked());
                }
                if (slot == 2 && clickEvent.getInventory().getItem(clickEvent.getSlot()).getTypeId() == Material.ENCHANTED_BOOK.getId()) {
                    clickEvent.getClickedInventory().remove(item0);
                    clickEvent.getClickedInventory().remove(item1);
                    clickEvent.getWhoClicked().setItemOnCursor(item);
                    players.remove(clickEvent.getWhoClicked());
                }
            }
        }
    }

    public ItemStack item2(ItemStack item0, ItemStack item1) {
        AnvilItems anvilItems = new AnvilItems(controller, item0, item1);
        ItemStack item2 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta2 = (EnchantmentStorageMeta) item2.getItemMeta();
        for (int i = 0; i < anvilItems.getItemResultEnchantmentSize(); i++) {
            meta2.addStoredEnchant(anvilItems.getItemResultEnchantment(i), anvilItems.getItemResultELevel(i), true);
            item2.setItemMeta(meta2);
        }
        anvilItems.clearAllArray();
        return item2;
    }
}
