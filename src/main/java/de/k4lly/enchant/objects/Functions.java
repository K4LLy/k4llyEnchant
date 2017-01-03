package de.k4lly.enchant.objects;

import de.k4lly.enchant.listener.Enchantment;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;

public class Functions {

    private ArrayList<Material> enchantableMaterial = new ArrayList<>();

    public Functions() {
        for (Material m : Material.values()) {
            if (m != null && isEnchantable(m)) {
                enchantableMaterial.add(m);
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
        switch (material) {
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
        switch (material) {
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
            case ELYTRA:
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
            case SHEARS:
            case FISHING_ROD:
            case FLINT_AND_STEEL:
            case CARROT_STICK:
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
            case BOW:
            case SHIELD:
                return true;
            default:
                return false;
        }
    }

    public boolean isAxe (Material material) {
        switch (material) {
            case WOOD_AXE:
            case STONE_AXE:
            case IRON_AXE:
            case GOLD_AXE:
            case DIAMOND_AXE:
                return true;
            default:
                return false;
        }
    }

    public boolean isElytra (Material material) {
        switch (material) {
            case ELYTRA:
                return true;
            default:
                return false;
        }
    }

    public boolean isShield (Material material) {
        switch (material) {
            case SHIELD:
                return true;
            default:
                return false;
        }
    }

    public boolean isBow (Material material) {
        switch (material) {
            case BOW:
                return true;
            default:
                return false;
        }
    }

    public boolean isHelmet (Material material) {
        switch (material) {
            case LEATHER_HELMET:
            case CHAINMAIL_HELMET:
            case IRON_HELMET:
            case GOLD_HELMET:
            case DIAMOND_HELMET:
                return true;
            default:
                return false;
        }
    }

    public boolean isBoots (Material material) {
        switch (material) {
            case LEATHER_BOOTS:
            case CHAINMAIL_BOOTS:
            case IRON_BOOTS:
            case GOLD_BOOTS:
            case DIAMOND_BOOTS:
                return true;
            default:
                return false;
        }
    }

    public boolean isShears (Material material) {
        switch (material) {
            case SHEARS:
                return true;
            default:
                return false;
        }
    }

    public boolean isFishingRod (Material material) {
        switch (material) {
            case FISHING_ROD:
                return true;
            default:
                return false;
        }
    }

    public boolean isFlintSteel (Material material) {
        switch (material) {
            case FLINT_AND_STEEL:
                return true;
            default:
                return false;
        }
    }

    public boolean isCarrotStick (Material material) {
        switch (material) {
            case CARROT_STICK:
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

    public ArrayList<Material> getEnchantableMaterial() {
        return enchantableMaterial;
    }

    public void setEnchantableMaterial(ArrayList<Material> enchantableMaterial) {
        this.enchantableMaterial = enchantableMaterial;
    }

    public String getRomanNumber(int level) {
        if (level < 1 || level > 3999)
            return "Invalid Roman Number Value";
        String s = "";
        while (level >= 1000) {
            s += "M";
            level -= 1000;        }
        while (level >= 900) {
            s += "CM";
            level -= 900;
        }
        while (level >= 500) {
            s += "D";
            level -= 500;
        }
        while (level >= 400) {
            s += "CD";
            level -= 400;
        }
        while (level >= 100) {
            s += "C";
            level -= 100;
        }
        while (level >= 90) {
            s += "XC";
            level -= 90;
        }
        while (level >= 50) {
            s += "L";
            level -= 50;
        }
        while (level >= 40) {
            s += "XL";
            level -= 40;
        }
        while (level >= 10) {
            s += "X";
            level -= 10;
        }
        while (level >= 9) {
            s += "IX";
            level -= 9;
        }
        while (level >= 5) {
            s += "V";
            level -= 5;
        }
        while (level >= 4) {
            s += "IV";
            level -= 4;
        }
        while (level >= 1) {
            s += "I";
            level -= 1;
        }
        return s;
    }

    public int parseRomanNumber(String str) throws Exception {
        String[] letras = {"M","CM","D","CD","C","XC","L","XL","X", "IX","V","IV","I"};
        int[] valores = {1000,900,500,400,100,90,50,40,10,9,5,4,1};

        // here we can do even more business validations like avoiding sequences like XXXXM
        if (str==null || str.isEmpty()) {
            return 0;
        }
        for(int i=0; i<letras.length; i++) {
            if (str.startsWith(letras[i])) {
                return valores[i] + parseRomanNumber(str.substring(letras[i].length()));
            }
        }
        throw new Exception("Something bad happened.");
    }

    public boolean hasCustomEnchant(List<String> lore) {
        for (String str : lore) {
            if (isCustomEnchant(str)) return true;
        }
        return false;
    }

    public boolean isCustomEnchant(String str) {
        return str.startsWith(ChatColor.GRAY + "Wither ") || str.startsWith(ChatColor.GRAY + "Fire Touch ") || str.startsWith(ChatColor.GRAY + "Poison Touch ") || str.startsWith(ChatColor.GRAY + "XP-Boost ") || str.startsWith(ChatColor.GRAY + "Night Vision ") || str.startsWith(ChatColor.GRAY + "Infinity ");
    }

    public boolean containsEnchant(String name, ItemStack item) {
        for (String str : item.getItemMeta().getLore()) {
            if (str.startsWith(ChatColor.GRAY + name)) return true;
        }
        return false;
    }

    public void enchantItem(String name, int level, ItemStack item) {
        if (level == 0) return;
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore == null || isEmpty(lore)) {
            List<String> newLore = new ArrayList<>();
            if (name.startsWith(ChatColor.GRAY + "")) {
                newLore.add(name + " " + getRomanNumber(level));
                itemMeta.setLore(newLore);
            } else {
                newLore.add(ChatColor.GRAY + name + " " + getRomanNumber(level));
                itemMeta.setLore(newLore);
            }
        } else {
            if (name.startsWith(ChatColor.GRAY + "")) {
                if (!containsEnchant(name, item)) lore.add(name + " " + getRomanNumber(level));
                itemMeta.setLore(lore);
            } else {
                if (!containsEnchant(name, item)) lore.add(ChatColor.GRAY + name + " " + getRomanNumber(level));
                itemMeta.setLore(lore);
            }
        }
        item.setItemMeta(itemMeta);
    }

    private boolean isEmpty(List<String> lore) {
        for (String str : lore) {
            if (!str.equals("") && str != null) return false;
        }
        return true;
    }

    public int getCELevel(String str) throws Exception {
        String[] words = str.trim().split("\\s+");
        return words.length == 3 ? parseRomanNumber(words[2]) : parseRomanNumber(words[1]);
    }

    public String getCEName(String str) {
        String[] words = str.trim().split("\\s+");
        return words.length == 3 ? words[0] + " " + words[1] : words [0];
    }

    public String getInternName(String str) {
        if (str.startsWith(ChatColor.GRAY + "XP-Boost")) {
            return Enchantment.XP_BOOST;
        } else if (str.startsWith(ChatColor.GRAY + "Fire Touch")) {
            return Enchantment.FIRE_TOUCH;
        } else if (str.startsWith(ChatColor.GRAY + "Wither ")) {
            return Enchantment.WITHER;
        } else if (str.startsWith(ChatColor.GRAY + "Poison Touch")) {
            return Enchantment.POISON_TOUCH;
        } else if (str.startsWith(ChatColor.GRAY + "Night Vision")) {
            return Enchantment.NIGHT_VISION;
        }/* else if (str.startsWith(ChatColor.GRAY + "Infinity")) {
            return Enchantment.INFINITY;
        } */else {
            return null;
        }
    }
}
