package de.k4lly.enchant.objects;

import org.bukkit.Material;

public class Functions {
    public Functions() {}

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


}
