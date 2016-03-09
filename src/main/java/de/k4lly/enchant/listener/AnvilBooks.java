package de.k4lly.enchant.listener;

import de.k4lly.enchant.controller.PluginController;
import de.k4lly.enchant.objects.AnvilItems;
import de.k4lly.enchant.objects.Functions;
import org.bukkit.Material;
import org.bukkit.Sound;
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
    private ArrayList<Player> players = new ArrayList<>();
    private int SLOT_0 = 0;
    private int SLOT_1 = 1;
    private int SLOT_2 = 2;

    public AnvilBooks(PluginController controller) {
        this.controller = controller;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent clickEvent) {
        if (clickEvent.getInventory().getType() != null && clickEvent.getInventory().getType() == InventoryType.ANVIL) {
            int slot = clickEvent.getSlot();
            ItemStack item0 = clickEvent.getInventory().getItem(SLOT_0);
            ItemStack item1 = clickEvent.getInventory().getItem(SLOT_1);
            Functions mat = new Functions();

            if ((item0 != null && mat.isBook(item0.getType())) && (item1 != null && mat.isBook(item0.getType()))) {
                clickEvent.setResult(Event.Result.DENY);
                if (!players.contains(clickEvent.getWhoClicked())) {
                    clickEvent.getClickedInventory().clear(SLOT_2);
                    clickEvent.getInventory().setItem(SLOT_2, item2(item0, item1));
                    players.add((Player) clickEvent.getWhoClicked());
                }
                if (slot == SLOT_2 && mat.isEnchantedBook(clickEvent.getInventory().getItem(clickEvent.getSlot()).getType())) {
                    clickEvent.getClickedInventory().remove(item0);
                    clickEvent.getClickedInventory().remove(item1);
                    clickEvent.getWhoClicked().setItemOnCursor(item2(item0, item1));
                    Player player = (Player) clickEvent.getWhoClicked();
                    player.playSound(clickEvent.getWhoClicked().getLocation(), Sound.ANVIL_USE, 3.0F, 0.533F);
                    players.remove(clickEvent.getWhoClicked());
                } else if (clickEvent.getInventory().getItem(clickEvent.getSlot()).getType() != null) {
                    ItemStack item = clickEvent.getCurrentItem();
                    clickEvent.getWhoClicked().setItemOnCursor(item);
                    clickEvent.getClickedInventory().clear(clickEvent.getSlot());
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
