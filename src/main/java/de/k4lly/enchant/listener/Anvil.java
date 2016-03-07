package de.k4lly.enchant.listener;

import de.k4lly.enchant.controller.PluginController;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Anvil implements Listener {

    private PluginController controller;
    private boolean slot0 = false;
    private boolean slot1 = false;
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
            if (slot0) {
                checkSlot1(item1);
            } else if (slot1) {
                checkSlot0(item0);
            } else {
                checkSlot1(item1);
                checkSlot0(item0);
            }

            if (slot0 && slot1) {
                System.out.print("[k4llyEnchant] Change Enchantments...");
                if (slot == 2 && clickEvent.getInventory().getItem(clickEvent.getSlot()).getTypeId() == Material.ENCHANTED_BOOK.getId()) {
                    clickEvent.getClickedInventory().setItem(2, item2(item0, item1));
                    clickEvent.getClickedInventory().remove(item0);
                    clickEvent.getClickedInventory().remove(item1);
                    clickEvent.getWhoClicked().getInventory().addItem(item2(item0, item1));
                    System.out.print("[k4llyEnchant] Setting new Item.");
                    slot0 = false;
                    slot1 = false;
                }
                System.out.print("[k4llyEnchant] Finish changing.");
            }
            System.out.print("[k4llyEnchant] ClickEvent Finished.");
        }
    }

    public void checkSlot0(ItemStack item0) {
        if (item0 != null && item0.getTypeId() == Material.ENCHANTED_BOOK.getId()) {
            System.out.print("[k4llyEnchant] slot0 true.");
            slot0 = true;
        } else {
            System.out.print("[k4llyEnchant] slot0 false.");
            slot0 = false;
        }
    }

    public void checkSlot1(ItemStack item1) {
        if (item1 != null && item1.getTypeId() == Material.ENCHANTED_BOOK.getId()) {
            System.out.print("[k4llyEnchant] slot1 true.");
            slot1 = true;
        } else {
            System.out.print("[k4llyEnchant] slot1 false.");
            slot1 = false;
        }
    }

    public ItemStack item2(ItemStack item0, ItemStack item1) {
        System.out.print("[k4llyEnchant] Start creating item2...");
        for (int i = 0; i <= 80; i++) {
            if (item0.getEnchantments().containsValue(Enchantment.getById(i))) {
                item0Enchantment.add(i, Enchantment.getById(i));
                item0ELevel.add(i, item0.getEnchantmentLevel(Enchantment.getById(i)));
            } else {
                item0Enchantment.add(i, null);
                item0ELevel.add(i, null);
            }
            if (item1.getEnchantments().containsValue(Enchantment.getById(i))) {
                item1Enchantment.add(i, Enchantment.getById(i));
                item1ELevel.add(i, item0.getEnchantmentLevel(Enchantment.getById(i)));
            } else {
                item1Enchantment.add(i, null);
                item1ELevel.add(i, null);
            }
        }
        for (int i = 0; i <= 80 ; i++) {
            if (item0Enchantment.get(i) != null && item0Enchantment.get(i).equals(item1Enchantment.get(i)) && item0ELevel.get(i).equals(item1ELevel.get(i)) && item0ELevel.get(i) < controller.getMain().getConfig().getInt(item0Enchantment.get(i).getName())) {
                item2Enchantment.add(item0Enchantment.get(i));
                item2ELevel.add(item1ELevel.get(i) + 1);
            }
        }
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        for (int i = 0; i < item2Enchantment.size(); i++) {
            item.addUnsafeEnchantment(item2Enchantment.get(i), item2ELevel.get(i));
            //item.setItemMeta(new ItemMeta().addEnchant(item2Enchantment.get(i), item2ELevel.get(i), false);
        }
        System.out.print("[k4llyEnchant] Finish creating item2.");
        return item;
    }
}
