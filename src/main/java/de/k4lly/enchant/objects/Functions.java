package de.k4lly.enchant.objects;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Functions {

    public ArrayList<Material> enchantableMaterial = new ArrayList<Material>();

    public Functions() {
        for (int i = 0; i <= 450; i++) {
            if (Material.getMaterial(i) != null && isEnchantable(Material.getMaterial(i))) {
                enchantableMaterial.add(i, Material.getMaterial(i));
            } else {
                enchantableMaterial.add(i, null);
            }
        }
    }

    public boolean isBook(Material material) {
        switch (material) {
            case ENCHANTED_BOOK:
            case BOOK:
                return true;
            default:
                return false;
        }
    }

    public boolean isEnchantedBook(Material material) {
        switch (material) {
            case ENCHANTED_BOOK:
                return true;
            default:
                return false;
        }
    }

    public boolean isEnchantable(Material material) {
        switch(material) {
            case CHAINMAIL_BOOTS:
            case CHAINMAIL_CHESTPLATE:
            case CHAINMAIL_HELMET:
            case CHAINMAIL_LEGGINGS:
            case LEATHER_BOOTS:
            case LEATHER_CHESTPLATE:
            case LEATHER_HELMET:
            case LEATHER_LEGGINGS:
            case IRON_BOOTS:
            case IRON_CHESTPLATE:
            case IRON_HELMET:
            case IRON_LEGGINGS:
            case GOLD_BOOTS:
            case GOLD_CHESTPLATE:
            case GOLD_HELMET:
            case GOLD_LEGGINGS:
            case DIAMOND_BOOTS:
            case DIAMOND_CHESTPLATE:
            case DIAMOND_HELMET:
            case DIAMOND_LEGGINGS:
            case WOOD_AXE:
            case WOOD_HOE:
            case WOOD_PICKAXE:
            case WOOD_SPADE:
            case STONE_AXE:
            case STONE_HOE:
            case STONE_PICKAXE:
            case STONE_SPADE:
            case IRON_AXE:
            case IRON_HOE:
            case IRON_PICKAXE:
            case IRON_SPADE:
            case GOLD_AXE:
            case GOLD_HOE:
            case GOLD_PICKAXE:
            case GOLD_SPADE:
            case DIAMOND_AXE:
            case DIAMOND_HOE:
            case DIAMOND_PICKAXE:
            case DIAMOND_SPADE:
            case WOOD_SWORD:
            case STONE_SWORD:
            case IRON_SWORD:
            case GOLD_SWORD:
            case DIAMOND_SWORD:
            case BOW:
            case FISHING_ROD:
            case FLINT_AND_STEEL:
            case SHIELD:
            case ELYTRA:
            case SHEARS:
                return true;
            default:
                return false;
        }
    }

    public boolean isArmor(Material material) {
        switch(material) {
            case CHAINMAIL_BOOTS:
            case CHAINMAIL_CHESTPLATE:
            case CHAINMAIL_HELMET:
            case CHAINMAIL_LEGGINGS:
            case LEATHER_BOOTS:
            case LEATHER_CHESTPLATE:
            case LEATHER_HELMET:
            case LEATHER_LEGGINGS:
            case IRON_BOOTS:
            case IRON_CHESTPLATE:
            case IRON_HELMET:
            case IRON_LEGGINGS:
            case GOLD_BOOTS:
            case GOLD_CHESTPLATE:
            case GOLD_HELMET:
            case GOLD_LEGGINGS:
            case DIAMOND_BOOTS:
            case DIAMOND_CHESTPLATE:
            case DIAMOND_HELMET:
            case DIAMOND_LEGGINGS:
                return true;
            default:
                return false;
        }
    }

    public boolean isTool(Material material) {
        switch (material) {
            case WOOD_AXE:
            case WOOD_HOE:
            case WOOD_PICKAXE:
            case WOOD_SPADE:
            case STONE_AXE:
            case STONE_HOE:
            case STONE_PICKAXE:
            case STONE_SPADE:
            case IRON_AXE:
            case IRON_HOE:
            case IRON_PICKAXE:
            case IRON_SPADE:
            case GOLD_AXE:
            case GOLD_HOE:
            case GOLD_PICKAXE:
            case GOLD_SPADE:
            case DIAMOND_AXE:
            case DIAMOND_HOE:
            case DIAMOND_PICKAXE:
            case DIAMOND_SPADE:
                return true;
            default:
                return false;
        }
    }

    public boolean isWeapon(Material material) {
        switch (material) {
            case WOOD_SWORD:
            case STONE_SWORD:
            case IRON_SWORD:
            case GOLD_SWORD:
            case DIAMOND_SWORD:
                return true;
            default:
                return false;
        }
    }

    public boolean isRepairMaterial(Material material) {
        switch (material) {
            case LEATHER:
            case WOOD:
            case COBBLESTONE:
            case IRON_INGOT:
            case GOLD_INGOT:
            case DIAMOND:
                return true;
            default:
                return false;
        }
    }

    public ItemStack getEnchantableItem(int index) {
        if (enchantableMaterial.get(index) != null) {
            return new ItemStack(enchantableMaterial.get(index));
        } else {
            return null;
        }
    }


}
