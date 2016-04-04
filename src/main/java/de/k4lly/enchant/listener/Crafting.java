package de.k4lly.enchant.listener;

import de.k4lly.enchant.controller.PluginController;
import de.k4lly.enchant.objects.Functions;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class Crafting implements Listener {

    private PluginController controller;
    private Functions func = new Functions();
    boolean enchItem;
    boolean book;
    boolean xpBottle;
    private ItemStack item;

    public Crafting(PluginController controller) { this.controller = controller; }

    @EventHandler
    public void onItemCraft(CraftItemEvent itemCraftEvent) {
        System.out.print("onItemCraft!");
        if (controller.getMain().getConfig().getBoolean("enableTakeEnchantment")) {
            System.out.print("Config is true!");
            enchItem = false;
            book = false;
            xpBottle = false;

            for (int i=1;i<=9;i++) {
                if (func.isEnchantable(itemCraftEvent.getInventory().getItem(i).getType()) && !enchItem) {
                    System.out.print("enchItem = true!");
                    enchItem = true;
                    item = itemCraftEvent.getInventory().getItem(i);
                } else if (itemCraftEvent.getInventory().getItem(i).getType().equals(Material.BOOK) && !book) {
                    System.out.print("book = true!");
                    book = true;
                } else if (itemCraftEvent.getInventory().getItem(i).getType().equals(Material.EXP_BOTTLE) && !xpBottle) {
                    System.out.print("xpBottle = true!");
                    xpBottle = true;
                }
            }

            if (enchItem && book && xpBottle) {
                System.out.print("All = true!");
                ItemStack result = new ItemStack(Material.ENCHANTED_BOOK);
                EnchantmentStorageMeta resultMeta = (EnchantmentStorageMeta) result.getItemMeta();

                for (int i=0; i<=80; i++) {
                    if (item.getItemMeta().hasEnchant(Enchantment.getById(i))) {
                        resultMeta.addStoredEnchant(Enchantment.getById(i), item.getEnchantmentLevel(Enchantment.getById(i)), true);
                    }
                }

                result.setItemMeta(resultMeta);
                itemCraftEvent.getInventory().setResult(result);
                System.out.print("Finished!");
            }
        }
    }
}