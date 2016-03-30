package de.k4lly.enchant.listener;

import de.k4lly.enchant.controller.PluginController;
import de.k4lly.enchant.objects.AnvilItems;
import de.k4lly.enchant.objects.Functions;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class Anvil implements Listener {

    private PluginController controller;
    private Functions func = new Functions();
    private static int SLOT_0 = 0;
    private static int SLOT_1 = 1;

    public Anvil(PluginController controller) {
        this.controller = controller;
    }

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent prepareAnvilEvent) {
        ItemStack item0 = prepareAnvilEvent.getInventory().getItem(SLOT_0);
        ItemStack item1 = prepareAnvilEvent.getInventory().getItem(SLOT_1);
        if (item0 != null && item1 != null && !func.isRepairMaterial(item1.getType())) {
            if ((func.isEnchantable(item0.getType()) && func.isEnchantedBook(item1.getType())) || item0.getType().equals(item1.getType()) || (func.isEnchantable(item0.getType()) && func.isBook(item1.getType()))) {
                prepareAnvilEvent.setResult(item2(item0, item1));
            }
        }
    }

    public ItemStack item2(ItemStack item0, ItemStack item1) {
        AnvilItems anvilItems = new AnvilItems(controller, item0, item1);
        ItemStack item2;
        if (item0.getType().equals(Material.ENCHANTED_BOOK)) {
            item2 = new ItemStack(item0.getType());
            EnchantmentStorageMeta meta2 = (EnchantmentStorageMeta) item2.getItemMeta();
            meta2.setDisplayName(item0.getItemMeta().getDisplayName());
            for (int i = 0; i < anvilItems.getItemResultEnchantmentSize(); i++) {
                meta2.addStoredEnchant(anvilItems.getItemResultEnchantment(i), anvilItems.getItemResultELevel(i), true);
                item2.setItemMeta(meta2);
            }
        } else if (item1.getType().equals(Material.BOOK)) {
            item2 = new ItemStack(Material.ENCHANTED_BOOK);
            EnchantmentStorageMeta meta2 = (EnchantmentStorageMeta) item2.getItemMeta();
            meta2.setDisplayName("Enchanted Book");
            for (int i = 0; i < anvilItems.getItemResultEnchantmentSize(); i++) {
                meta2.addStoredEnchant(anvilItems.getItemResultEnchantment(i), anvilItems.getItemResultELevel(i), true);
                item2.setItemMeta(meta2);
            }
        } else {
            item2 = new ItemStack(item0.getType());
            ItemMeta meta2 = item2.getItemMeta();
            meta2.setDisplayName(item0.getItemMeta().getDisplayName());
            for (int i = 0; i < anvilItems.getItemResultEnchantmentSize(); i++) {
                meta2.addEnchant(anvilItems.getItemResultEnchantment(i), anvilItems.getItemResultELevel(i), true);
                item2.setItemMeta(meta2);
            }
        }
        anvilItems.clearAllArray();
        return item2;
    }
}
