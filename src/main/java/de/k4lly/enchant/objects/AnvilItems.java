package de.k4lly.enchant.objects;

import de.k4lly.enchant.controller.PluginController;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import java.util.ArrayList;

public class AnvilItems {

    private PluginController controller;
    private ItemStack itemLeft;
    private ItemStack itemRight;
    private EnchantmentStorageMeta itemMetaLeft;
    private EnchantmentStorageMeta itemMetaRight;
    private ArrayList<Enchantment> itemLeftEnchantment = new ArrayList<>();
    private ArrayList<Enchantment> itemRightEnchantment = new ArrayList<>();
    private ArrayList<Enchantment> itemResultEnchantment = new ArrayList<>();
    private ArrayList<Integer> itemLeftELevel = new ArrayList<>();
    private ArrayList<Integer> itemRightELevel = new ArrayList<>();
    private ArrayList<Integer> itemResultELevel = new ArrayList<>();

    public AnvilItems(PluginController controller, ItemStack itemLeft, ItemStack itemRight) {
        this.controller = controller;
        this.itemLeft = itemLeft;
        this.itemRight = itemRight;
        if (itemLeft.getType() == Material.ENCHANTED_BOOK) {
            doCombineBooks(itemLeft, itemRight);
        }
    }

    private void doCombineBooks(ItemStack itemLeft, ItemStack itemRight) {
        this.itemMetaLeft = (EnchantmentStorageMeta) itemLeft.getItemMeta();
        this.itemMetaRight = (EnchantmentStorageMeta) itemRight.getItemMeta();

        for (int i = 0; i <= 80; i++) {
            if (itemMetaLeft.hasStoredEnchant(Enchantment.getById(i))) {
                itemLeftEnchantment.add(i, Enchantment.getById(i));
                itemLeftELevel.add(i, itemMetaLeft.getStoredEnchantLevel(Enchantment.getById(i)));
            } else {
                itemLeftEnchantment.add(i, null);
                itemLeftELevel.add(i, null);
            }
            if (itemMetaRight.hasStoredEnchant(Enchantment.getById(i))) {
                itemRightEnchantment.add(i, Enchantment.getById(i));
                itemRightELevel.add(i, itemMetaRight.getStoredEnchantLevel(Enchantment.getById(i)));
            } else {
                itemRightEnchantment.add(i, null);
                itemRightELevel.add(i, null);
            }
        }
        for (int i = 0; i <= 80; i++) {
            if (itemLeftEnchantment.get(i) != null && itemLeftEnchantment.get(i).equals(itemRightEnchantment.get(i))) {
                itemResultEnchantment.add(itemLeftEnchantment.get(i));
                if (itemLeftELevel.get(i).equals(itemRightELevel.get(i)) && itemLeftELevel.get(i) < controller.getMain().getConfig().getInt(itemLeftEnchantment.get(i).getName())) {
                    itemResultELevel.add(itemLeftELevel.get(i) + 1);
                } else if (itemLeftELevel.get(i) > itemRightELevel.get(i) && itemLeftELevel.get(i) <= controller.getMain().getConfig().getInt(itemLeftEnchantment.get(i).getName())) {
                    itemResultELevel.add(itemLeftELevel.get(i));
                } else if (itemLeftELevel.get(i) < itemRightELevel.get(i) && itemRightELevel.get(i) <= controller.getMain().getConfig().getInt(itemLeftEnchantment.get(i).getName())) {
                    itemResultELevel.add(itemRightELevel.get(i));
                }
            } else if (itemLeftEnchantment.get(i) != null && itemRightEnchantment.get(i) == null) {
                itemResultEnchantment.add(itemLeftEnchantment.get(i));
                itemResultELevel.add(itemLeftELevel.get(i));
            } else if (itemLeftEnchantment.get(i) == null && itemRightEnchantment.get(i) != null) {
                itemResultEnchantment.add(itemRightEnchantment.get(i));
                itemResultELevel.add(itemRightELevel.get(i));
            }
        }
    }

    public void clearAllArray() {
        itemLeftEnchantment.clear();
        itemLeftELevel.clear();
        itemRightEnchantment.clear();
        itemRightELevel.clear();
        itemResultEnchantment.clear();
        itemResultELevel.clear();
    }

    public int getItemLeftEnchantmentSize() {
        return itemLeftEnchantment.size();
    }

    public Enchantment getItemLeftEnchantment(int index) {
        return itemLeftEnchantment.get(index);
    }

    public int getItemLeftELevel(int index) {
        return itemLeftELevel.get(index);
    }

    public int getItemRightEnchantmentSize() {
        return itemRightEnchantment.size();
    }

    public Enchantment getItemRightEnchantment(int index) {
        return itemRightEnchantment.get(index);
    }

    public int getItemRightELevel(int index) {
        return itemRightELevel.get(index);
    }

    public int getItemResultEnchantmentSize() {
        return itemResultEnchantment.size();
    }

    public Enchantment getItemResultEnchantment(int index) {
        return itemResultEnchantment.get(index);
    }

    public int getItemResultELevel(int index) {
        return itemResultELevel.get(index);
    }

}
