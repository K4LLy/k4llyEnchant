package de.k4lly.enchant.listener;

import de.k4lly.enchant.controller.PluginController;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.ArrayList;

public class Anvil implements Listener {

    private PluginController controller;
    private boolean slot2 = false;
    private ItemStack item;
    private ArrayList<Enchantment> item0Enchantment = new ArrayList<>();
    private ArrayList<Enchantment> item1Enchantment = new ArrayList<>();
    private ArrayList<Enchantment> item2Enchantment = new ArrayList<>();
    private ArrayList<Integer> item0ELevel = new ArrayList<>();
    private ArrayList<Integer> item1ELevel = new ArrayList<>();
    private ArrayList<Integer> item2ELevel = new ArrayList<>();

    public Anvil (PluginController controller) { this.controller = controller; }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent clickEvent) {
        if (clickEvent.getInventory().getType() != null && clickEvent.getInventory().getType() == InventoryType.ANVIL) {
            System.out.print("[k4llyEnchant] ClickEvent started...");
            int slot = clickEvent.getSlot();
            ItemStack item0 = clickEvent.getInventory().getItem(0);
            ItemStack item1 = clickEvent.getInventory().getItem(1);

            if ((item0 != null && item0.getTypeId() == Material.ENCHANTED_BOOK.getId()) && (item1 != null && item1.getTypeId() == Material.ENCHANTED_BOOK.getId())) {
                System.out.print("[k4llyEnchant] Change Enchantments...");
                clickEvent.setResult(Event.Result.DENY);
                if (!slot2) {
                    item = item2(item0, item1);
                    clickEvent.getInventory().setItem(2, item);
                    slot2 = true;
                }
                if (slot == 2 && clickEvent.getInventory().getItem(clickEvent.getSlot()).getTypeId() == Material.ENCHANTED_BOOK.getId()) {
                    clickEvent.getClickedInventory().remove(item0);
                    clickEvent.getClickedInventory().remove(item1);
                    clickEvent.getWhoClicked().setItemOnCursor(item);
                    slot2 = false;
                    System.out.print("[k4llyEnchant] Setting new Item.");
                }
                System.out.print("[k4llyEnchant] Finish changing.");
            }
            System.out.print("[k4llyEnchant] ClickEvent Finished.");
        }
    }

    public ItemStack item2(ItemStack item0, ItemStack item1) {
        System.out.print("[k4llyEnchant] Start creating item2...");
        EnchantmentStorageMeta meta0 = (EnchantmentStorageMeta) item0.getItemMeta();
        EnchantmentStorageMeta meta1 = (EnchantmentStorageMeta) item1.getItemMeta();
        for (int i = 0; i <= 80; i++) {
            if (meta0.hasStoredEnchant(Enchantment.getById(i))) {
                item0Enchantment.add(i, Enchantment.getById(i));
                item0ELevel.add(i, meta0.getStoredEnchantLevel(Enchantment.getById(i)));
            } else {
                item0Enchantment.add(i, null);
                item0ELevel.add(i, null);
            }
            if (meta1.hasStoredEnchant(Enchantment.getById(i))) {
                item1Enchantment.add(i, Enchantment.getById(i));
                item1ELevel.add(i, meta1.getStoredEnchantLevel(Enchantment.getById(i)));
            } else {
                item1Enchantment.add(i, null);
                item1ELevel.add(i, null);
            }
        }
        for (int i = 0; i <= 80 ; i++) {
            if (item0Enchantment.get(i) != null && item0Enchantment.get(i).equals(item1Enchantment.get(i))) {
                item2Enchantment.add(item0Enchantment.get(i));
                if (item0ELevel.get(i).equals(item1ELevel.get(i)) && item0ELevel.get(i) < controller.getMain().getConfig().getInt(item0Enchantment.get(i).getName())) {
                    item2ELevel.add(item0ELevel.get(i) + 1);
                } else if (item0ELevel.get(i) > item1ELevel.get(i) && item0ELevel.get(i) <= controller.getMain().getConfig().getInt(item0Enchantment.get(i).getName())) {
                    item2ELevel.add(item0ELevel.get(i));
                } else if (item0ELevel.get(i) < item1ELevel.get(i) && item1ELevel.get(i) <= controller.getMain().getConfig().getInt(item0Enchantment.get(i).getName())) {
                    item2ELevel.add(item1ELevel.get(i));
                }
            } else if (item0Enchantment.get(i) != null && item1Enchantment.get(i) == null) {
                item2Enchantment.add(item0Enchantment.get(i));
                item2ELevel.add(item0ELevel.get(i));
            } else if (item0Enchantment.get(i) == null && item1Enchantment.get(i) != null) {
                item2Enchantment.add(item1Enchantment.get(i));
                item2ELevel.add(item1ELevel.get(i));
            }
        }
        ItemStack item2 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta2 = (EnchantmentStorageMeta) item2.getItemMeta();
        for (int i = 0; i < item2Enchantment.size(); i++) {
            meta2.addStoredEnchant(item2Enchantment.get(i), item2ELevel.get(i), true);
            item2.setItemMeta(meta2);
        }
        item0Enchantment.clear();
        item0ELevel.clear();
        item1Enchantment.clear();
        item1ELevel.clear();
        item2Enchantment.clear();
        item2ELevel.clear();
        System.out.print("[k4llyEnchant] Finish creating item2.");
        return item2;
    }
}
