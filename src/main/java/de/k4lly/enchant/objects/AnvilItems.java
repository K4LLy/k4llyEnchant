package de.k4lly.enchant.objects;

import de.k4lly.enchant.controller.PluginController;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

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
    private Functions func = new Functions();

    public AnvilItems(PluginController controller, ItemStack itemLeft, ItemStack itemRight) {
        this.controller = controller;
        this.itemLeft = itemLeft;
        this.itemRight = itemRight;

        if (func.isEnchantedBook(itemLeft.getType())) {
            doCombineBooks(itemLeft, itemRight);
        } else if (func.isArmor(itemLeft.getType()) && func.isEnchantedBook(itemRight.getType())) {
            doCombineArmor(itemLeft, itemRight);
        } else if (func.isArmor(itemLeft.getType()) && func.isArmor(itemRight.getType())) {
            doCombineArmor2(itemLeft, itemRight);
        } else if (func.isTool(itemLeft.getType()) && func.isEnchantedBook(itemRight.getType())) {
            doCombineTools(itemLeft, itemRight);
        } else if (func.isTool(itemLeft.getType()) && func.isTool(itemRight.getType())) {
            doCombineTools2(itemLeft, itemRight);
        } else if (func.isWeapon(itemLeft.getType()) && func.isEnchantedBook(itemRight.getType())) {
            doCombineWeapons(itemLeft, itemRight);
        } else if (func.isWeapon(itemLeft.getType()) && func.isWeapon(itemRight.getType())) {
            doCombineWeapons2(itemLeft, itemRight);
        }
    }

    private void doCombineArmor(ItemStack itemLeft, ItemStack itemRight) {
        this.itemMetaRight = (EnchantmentStorageMeta) itemRight.getItemMeta();
        ItemMeta itemMeta = itemLeft.getItemMeta();

        for (int i = 0; i <= 80; i++) {
            if (itemMeta.hasEnchant(Enchantment.getById(i))) {
                itemLeftEnchantment.add(i, Enchantment.getById(i));
                itemLeftELevel.add(i, itemMeta.getEnchantLevel(Enchantment.getById(i)));
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
        checkConfliction();
    }

    private void doCombineArmor2(ItemStack itemLeft, ItemStack itemRight) {
        ItemMeta itemMeta2 = itemRight.getItemMeta();
        ItemMeta itemMeta = itemLeft.getItemMeta();

        for (int i = 0; i <= 80; i++) {
            if (itemMeta.hasEnchant(Enchantment.getById(i))) {
                itemLeftEnchantment.add(i, Enchantment.getById(i));
                itemLeftELevel.add(i, itemMeta.getEnchantLevel(Enchantment.getById(i)));
            } else {
                itemLeftEnchantment.add(i, null);
                itemLeftELevel.add(i, null);
            }
            if (itemMeta2.hasEnchant(Enchantment.getById(i))) {
                itemRightEnchantment.add(i, Enchantment.getById(i));
                itemRightELevel.add(i, itemMeta2.getEnchantLevel(Enchantment.getById(i)));
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
        checkConfliction();
    }

    private void doCombineTools(ItemStack itemLeft, ItemStack itemRight) {
        this.itemMetaRight = (EnchantmentStorageMeta) itemRight.getItemMeta();
        ItemMeta itemMeta = itemLeft.getItemMeta();

        for (int i = 0; i <= 80; i++) {
            if (itemMeta.hasEnchant(Enchantment.getById(i))) {
                itemLeftEnchantment.add(i, Enchantment.getById(i));
                itemLeftELevel.add(i, itemMeta.getEnchantLevel(Enchantment.getById(i)));
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
        checkConfliction();
    }

    private void doCombineTools2(ItemStack itemLeft, ItemStack itemRight) {
        ItemMeta itemMeta2 = itemRight.getItemMeta();
        ItemMeta itemMeta = itemLeft.getItemMeta();

        for (int i = 0; i <= 80; i++) {
            if (itemMeta.hasEnchant(Enchantment.getById(i))) {
                itemLeftEnchantment.add(i, Enchantment.getById(i));
                itemLeftELevel.add(i, itemMeta.getEnchantLevel(Enchantment.getById(i)));
            } else {
                itemLeftEnchantment.add(i, null);
                itemLeftELevel.add(i, null);
            }
            if (itemMeta2.hasEnchant(Enchantment.getById(i))) {
                itemRightEnchantment.add(i, Enchantment.getById(i));
                itemRightELevel.add(i, itemMeta2.getEnchantLevel(Enchantment.getById(i)));
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
        checkConfliction();
    }

    private void doCombineWeapons(ItemStack itemLeft, ItemStack itemRight) {
        this.itemMetaRight = (EnchantmentStorageMeta) itemRight.getItemMeta();
        ItemMeta itemMeta = itemLeft.getItemMeta();

        for (int i = 0; i <= 80; i++) {
            if (itemMeta.hasEnchant(Enchantment.getById(i))) {
                itemLeftEnchantment.add(i, Enchantment.getById(i));
                itemLeftELevel.add(i, itemMeta.getEnchantLevel(Enchantment.getById(i)));
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
        checkConfliction();
    }

    private void doCombineWeapons2(ItemStack itemLeft, ItemStack itemRight) {
        ItemMeta itemMeta2 = itemRight.getItemMeta();
        ItemMeta itemMeta = itemLeft.getItemMeta();

        for (int i = 0; i <= 80; i++) {
            if (itemMeta.hasEnchant(Enchantment.getById(i))) {
                itemLeftEnchantment.add(i, Enchantment.getById(i));
                itemLeftELevel.add(i, itemMeta.getEnchantLevel(Enchantment.getById(i)));
            } else {
                itemLeftEnchantment.add(i, null);
                itemLeftELevel.add(i, null);
            }
            if (itemMeta2.hasEnchant(Enchantment.getById(i))) {
                itemRightEnchantment.add(i, Enchantment.getById(i));
                itemRightELevel.add(i, itemMeta2.getEnchantLevel(Enchantment.getById(i)));
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
        checkConfliction();
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
                } else if (itemLeftELevel.get(i).equals(itemRightELevel.get(i)) && itemLeftELevel.get(i) == controller.getMain().getConfig().getInt(itemLeftEnchantment.get(i).getName())) {
                    itemResultELevel.add(itemLeftELevel.get(i));
                }
            } else if (itemLeftEnchantment.get(i) != null && itemRightEnchantment.get(i) == null) {
                itemResultEnchantment.add(itemLeftEnchantment.get(i));
                itemResultELevel.add(itemLeftELevel.get(i));
            } else if (itemLeftEnchantment.get(i) == null && itemRightEnchantment.get(i) != null) {
                itemResultEnchantment.add(itemRightEnchantment.get(i));
                itemResultELevel.add(itemRightELevel.get(i));
            }
        }
        checkConfliction();
    }

    private void checkConfliction() {
        if (func.isArmor(itemLeft.getType())) {
            checkArmor();
        } else if (func.isWeapon(itemLeft.getType())) {
            checkWeapon();
        } else if (func.isTool(itemLeft.getType())) {
            checkTool();
        } else if (func.isEnchantedBook(itemLeft.getType())) {
            checkArmor();
            checkTool();
            checkWeapon();
        }
    }

    private void checkArmor() {
        if ((itemResultEnchantment.contains(Enchantment.PROTECTION_ENVIRONMENTAL) && itemResultEnchantment.contains(Enchantment.PROTECTION_EXPLOSIONS)) ||
                (itemResultEnchantment.contains(Enchantment.PROTECTION_ENVIRONMENTAL) && itemResultEnchantment.contains(Enchantment.PROTECTION_FIRE)) ||
                (itemResultEnchantment.contains(Enchantment.PROTECTION_ENVIRONMENTAL) && itemResultEnchantment.contains(Enchantment.PROTECTION_PROJECTILE)) ||
                (itemResultEnchantment.contains(Enchantment.PROTECTION_FIRE) && itemResultEnchantment.contains(Enchantment.PROTECTION_EXPLOSIONS)) ||
                (itemResultEnchantment.contains(Enchantment.PROTECTION_FIRE) && itemResultEnchantment.contains(Enchantment.PROTECTION_PROJECTILE)) ||
                (itemResultEnchantment.contains(Enchantment.PROTECTION_EXPLOSIONS) && itemResultEnchantment.contains(Enchantment.PROTECTION_PROJECTILE))) {

            if (itemLeftEnchantment.contains(Enchantment.PROTECTION_ENVIRONMENTAL) && itemRightEnchantment.contains(Enchantment.PROTECTION_EXPLOSIONS)) {
                itemResultEnchantment.remove(Enchantment.PROTECTION_EXPLOSIONS);
                itemResultELevel.remove(itemRightELevel.get(Enchantment.PROTECTION_EXPLOSIONS.getId()));
            } else if (itemLeftEnchantment.contains(Enchantment.PROTECTION_EXPLOSIONS) && itemRightEnchantment.contains(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                itemResultEnchantment.remove(Enchantment.PROTECTION_ENVIRONMENTAL);
                itemResultELevel.remove(itemRightELevel.get(Enchantment.PROTECTION_ENVIRONMENTAL.getId()));
            }

            if (itemLeftEnchantment.contains(Enchantment.PROTECTION_ENVIRONMENTAL) && itemRightEnchantment.contains(Enchantment.PROTECTION_FIRE)) {
                itemResultEnchantment.remove(Enchantment.PROTECTION_FIRE);
                itemResultELevel.remove(itemRightELevel.get(Enchantment.PROTECTION_FIRE.getId()));
            } else if (itemLeftEnchantment.contains(Enchantment.PROTECTION_FIRE) && itemRightEnchantment.contains(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                itemResultEnchantment.remove(Enchantment.PROTECTION_ENVIRONMENTAL);
                itemResultELevel.remove(itemRightELevel.get(Enchantment.PROTECTION_ENVIRONMENTAL.getId()));
            }

            if (itemLeftEnchantment.contains(Enchantment.PROTECTION_ENVIRONMENTAL) && itemRightEnchantment.contains(Enchantment.PROTECTION_PROJECTILE)) {
                itemResultEnchantment.remove(Enchantment.PROTECTION_PROJECTILE);
                itemResultELevel.remove(itemRightELevel.get(Enchantment.PROTECTION_PROJECTILE.getId()));
            } else if (itemLeftEnchantment.contains(Enchantment.PROTECTION_PROJECTILE) && itemRightEnchantment.contains(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                itemResultEnchantment.remove(Enchantment.PROTECTION_ENVIRONMENTAL);
                itemResultELevel.remove(itemRightELevel.get(Enchantment.PROTECTION_ENVIRONMENTAL.getId()));
            }

            if (itemLeftEnchantment.contains(Enchantment.PROTECTION_FIRE) && itemRightEnchantment.contains(Enchantment.PROTECTION_EXPLOSIONS)) {
                itemResultEnchantment.remove(Enchantment.PROTECTION_EXPLOSIONS);
                itemResultELevel.remove(itemRightELevel.get(Enchantment.PROTECTION_EXPLOSIONS.getId()));
            } else if (itemLeftEnchantment.contains(Enchantment.PROTECTION_EXPLOSIONS) && itemRightEnchantment.contains(Enchantment.PROTECTION_FIRE)) {
                itemResultEnchantment.remove(Enchantment.PROTECTION_FIRE);
                itemResultELevel.remove(itemRightELevel.get(Enchantment.PROTECTION_FIRE.getId()));
            }

            if (itemLeftEnchantment.contains(Enchantment.PROTECTION_FIRE) && itemRightEnchantment.contains(Enchantment.PROTECTION_PROJECTILE)) {
                itemResultEnchantment.remove(Enchantment.PROTECTION_PROJECTILE);
                itemResultELevel.remove(itemRightELevel.get(Enchantment.PROTECTION_PROJECTILE.getId()));
            } else if (itemLeftEnchantment.contains(Enchantment.PROTECTION_PROJECTILE) && itemRightEnchantment.contains(Enchantment.PROTECTION_FIRE)) {
                itemResultEnchantment.remove(Enchantment.PROTECTION_FIRE);
                itemResultELevel.remove(itemRightELevel.get(Enchantment.PROTECTION_FIRE.getId()));
            }

            if (itemLeftEnchantment.contains(Enchantment.PROTECTION_EXPLOSIONS) && itemRightEnchantment.contains(Enchantment.PROTECTION_PROJECTILE)) {
                itemResultEnchantment.remove(Enchantment.PROTECTION_PROJECTILE);
                itemResultELevel.remove(itemRightELevel.get(Enchantment.PROTECTION_PROJECTILE.getId()));
            } else if (itemLeftEnchantment.contains(Enchantment.PROTECTION_PROJECTILE) && itemRightEnchantment.contains(Enchantment.PROTECTION_EXPLOSIONS)) {
                itemResultEnchantment.remove(Enchantment.PROTECTION_EXPLOSIONS);
                itemResultELevel.remove(itemRightELevel.get(Enchantment.PROTECTION_EXPLOSIONS.getId()));
            }
        }
    }

    private void checkWeapon() {
        if ((itemResultEnchantment.contains(Enchantment.DAMAGE_ALL) && itemResultEnchantment.contains(Enchantment.DAMAGE_ARTHROPODS)) ||
                (itemResultEnchantment.contains(Enchantment.DAMAGE_ALL) && itemResultEnchantment.contains(Enchantment.DAMAGE_UNDEAD)) ||
                (itemResultEnchantment.contains(Enchantment.DAMAGE_ARTHROPODS) && itemResultEnchantment.contains(Enchantment.DAMAGE_UNDEAD))) {

            if (itemLeftEnchantment.contains(Enchantment.DAMAGE_ALL) && itemRightEnchantment.contains(Enchantment.DAMAGE_ARTHROPODS)) {
                itemResultEnchantment.remove(Enchantment.DAMAGE_ARTHROPODS);
                itemResultELevel.remove(itemRightELevel.get(Enchantment.DAMAGE_ARTHROPODS.getId()));
            } else if (itemLeftEnchantment.contains(Enchantment.DAMAGE_ARTHROPODS) && itemRightEnchantment.contains(Enchantment.DAMAGE_ALL)) {
                itemResultEnchantment.remove(Enchantment.DAMAGE_ALL);
                itemResultELevel.remove(itemRightELevel.get(Enchantment.DAMAGE_ALL.getId()));
            }

            if (itemLeftEnchantment.contains(Enchantment.DAMAGE_ALL) && itemRightEnchantment.contains(Enchantment.DAMAGE_UNDEAD)) {
                itemResultEnchantment.remove(Enchantment.DAMAGE_UNDEAD);
                itemResultELevel.remove(itemRightELevel.get(Enchantment.DAMAGE_UNDEAD.getId()));
            } else if (itemLeftEnchantment.contains(Enchantment.DAMAGE_UNDEAD) && itemRightEnchantment.contains(Enchantment.DAMAGE_ALL)) {
                itemResultEnchantment.remove(Enchantment.DAMAGE_ALL);
                itemResultELevel.remove(itemRightELevel.get(Enchantment.DAMAGE_ALL.getId()));
            }

            if (itemLeftEnchantment.contains(Enchantment.DAMAGE_ARTHROPODS) && itemRightEnchantment.contains(Enchantment.DAMAGE_UNDEAD)) {
                itemResultEnchantment.remove(Enchantment.DAMAGE_UNDEAD);
                itemResultELevel.remove(itemRightELevel.get(Enchantment.DAMAGE_UNDEAD.getId()));
            } else if (itemLeftEnchantment.contains(Enchantment.DAMAGE_UNDEAD) && itemRightEnchantment.contains(Enchantment.DAMAGE_ARTHROPODS)) {
                itemResultEnchantment.remove(Enchantment.DAMAGE_ARTHROPODS);
                itemResultELevel.remove(itemRightELevel.get(Enchantment.DAMAGE_ARTHROPODS.getId()));
            }
        }
    }

    private void checkTool() {
        if (itemResultEnchantment.contains(Enchantment.SILK_TOUCH) && itemResultEnchantment.contains(Enchantment.LOOT_BONUS_BLOCKS)) {
            if (itemLeftEnchantment.contains(Enchantment.SILK_TOUCH)) {
                itemResultEnchantment.remove(Enchantment.LOOT_BONUS_BLOCKS);
                itemResultELevel.remove(itemRightELevel.get(Enchantment.LOOT_BONUS_BLOCKS.getId()));
            } else {
                itemResultEnchantment.remove(Enchantment.SILK_TOUCH);
                itemResultELevel.remove(itemRightELevel.get(Enchantment.SILK_TOUCH.getId()));
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
