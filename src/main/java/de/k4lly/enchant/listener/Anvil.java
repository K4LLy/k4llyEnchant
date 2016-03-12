package de.k4lly.enchant.listener;

import de.k4lly.enchant.controller.PluginController;
import de.k4lly.enchant.objects.AnvilItems;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class Anvil implements Listener {

    private PluginController controller;
    private static int SLOT_0 = 0;
    private static int SLOT_1 = 1;

    public Anvil(PluginController controller) {
        this.controller = controller;
    }

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent prepareAnvilEvent) {
        ItemStack item0 = prepareAnvilEvent.getInventory().getItem(SLOT_0);
        ItemStack item1 = prepareAnvilEvent.getInventory().getItem(SLOT_1);
        if (item0 != null && item1 != null) {
            prepareAnvilEvent.setResult(item2(item0, item1));
        }
    }

    public ItemStack item2(ItemStack item0, ItemStack item1) {
        AnvilItems anvilItems = new AnvilItems(controller, item0, item1);
        ItemStack item2 = item0.clone();
        if (item0.getType().equals(Material.ENCHANTED_BOOK)) {
            EnchantmentStorageMeta meta2 = (EnchantmentStorageMeta) item2.getItemMeta();
            for (int i = 0; i < anvilItems.getItemResultEnchantmentSize(); i++) {
                meta2.addStoredEnchant(anvilItems.getItemResultEnchantment(i), anvilItems.getItemResultELevel(i), true);
                item2.setItemMeta(meta2);
            }
        } else {
            ItemMeta meta2 = item2.getItemMeta();
            for (int i = 0; i < anvilItems.getItemResultEnchantmentSize(); i++) {
                meta2.addEnchant(anvilItems.getItemResultEnchantment(i), anvilItems.getItemResultELevel(i), true);
                item2.setItemMeta(meta2);
            }
        }

        anvilItems.clearAllArray();
        return item2;
    }
}
