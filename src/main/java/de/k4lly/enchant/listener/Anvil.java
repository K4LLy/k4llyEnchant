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
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.material.MaterialData;

public class Anvil implements Listener {

    private PluginController controller;
    private Functions func = new Functions();
    private static int SLOT_0 = 0;
    private static int SLOT_1 = 1;

    public Anvil(PluginController controller) {
        this.controller = controller;
    }

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent prepareAnvilEvent) throws Exception {
        ItemStack item0 = prepareAnvilEvent.getInventory().getItem(SLOT_0);
        ItemStack item1 = prepareAnvilEvent.getInventory().getItem(SLOT_1);
        if (item0 != null && item1 != null && !func.isRepairMaterial(item1.getType())) {
            if ((func.isEnchantable(item0.getType()) && func.isEnchantedBook(item1.getType())) || item0.getType().equals(item1.getType())) {
                prepareAnvilEvent.setResult(item2(item0, item1));
            }
        }
    }

    public ItemStack item2(ItemStack item0, ItemStack item1) throws Exception {
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
            for (int i = 0; i < anvilItems.getItemResultCustomEnchantmentSize(); i++) {
                func.enchantItem(anvilItems.getItemResultCustomEnchantment(i), anvilItems.getItemResultCELevel(i), item2);
            }
        } else {
            item2 = new ItemStack(item0.getType());
            if (item1.getType().equals(Material.ENCHANTED_BOOK)) {
                item2.setDurability(item0.getDurability());
            } else {
                item2.setDurability((short)(item0.getDurability() + item1.getDurability() + (item2.getDurability() / 20)));
            }
            ItemMeta meta2 = item2.getItemMeta();
            meta2.setDisplayName(item0.getItemMeta().getDisplayName());
            for (int i = 0; i < anvilItems.getItemResultEnchantmentSize(); i++) {
                meta2.addEnchant(anvilItems.getItemResultEnchantment(i), anvilItems.getItemResultELevel(i), true);
                item2.setItemMeta(meta2);
            }
            for (int i = 0; i < anvilItems.getItemResultCustomEnchantmentSize(); i++) {
                func.enchantItem(anvilItems.getItemResultCustomEnchantment(i), anvilItems.getItemResultCELevel(i), item2);
            }
        }
        anvilItems.clearAllArray();
        return item2;
    }
}
