package de.k4lly.enchant.objects;

import de.k4lly.enchant.controller.PluginController;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AnvilItems {

    private PluginController controller;
    private ItemStack itemLeft;
    private ItemStack itemRight;
    private ArrayList<Enchantment> itemResultEnchantment = new ArrayList<>();
    private ArrayList<String> uselessCustomEnchantment = new ArrayList<>();
    private ArrayList<Integer> itemResultELevel = new ArrayList<>();
    private ArrayList<String> itemResultCustomEnchantment = new ArrayList<>();
    private ArrayList<Integer> itemResultCELevel = new ArrayList<>();
    private Functions func = new Functions();

    public AnvilItems(PluginController controller, ItemStack itemLeft, ItemStack itemRight) throws Exception {
        this.controller = controller;
        this.itemLeft = itemLeft;
        this.itemRight = itemRight;
        uselessCustomEnchantment.add(de.k4lly.enchant.listener.Enchantment.FIRE_TOUCH);
        uselessCustomEnchantment.add(de.k4lly.enchant.listener.Enchantment.NIGHT_VISION);
        uselessCustomEnchantment.add(de.k4lly.enchant.listener.Enchantment.WITHER);

        if (func.isEnchantedBook(itemLeft.getType())) {
            doCombineBooks(itemLeft, itemRight);
        } else if (func.isEnchantable(itemLeft.getType()) && func.isEnchantedBook(itemRight.getType())) {
            doCombine(itemLeft, itemRight);
        } else if ((func.isEnchantable(itemLeft.getType()) && func.isEnchantable(itemRight.getType()))) {
            doCombine2(itemLeft, itemRight);
        }
    }

    private void doCombine(ItemStack itemLeft, ItemStack itemRight) throws Exception {
        ItemMeta itemMeta = itemLeft.getItemMeta();
        EnchantmentStorageMeta itemMetaRight = (EnchantmentStorageMeta) itemRight.getItemMeta();
        int i = 0;

        for (Enchantment enchantment : Enchantment.values()) {
            if (itemMeta.hasEnchant(enchantment) && itemMetaRight.hasStoredEnchant(enchantment)) {
                itemResultEnchantment.add(enchantment);
                if (itemMeta.getEnchantLevel(enchantment) == itemMetaRight.getStoredEnchantLevel(enchantment) && itemMeta.getEnchantLevel(enchantment) < controller.getMain().getConfig().getInt(enchantment.getName())) {
                    itemResultELevel.add(itemMeta.getEnchantLevel(enchantment) + 1);
                } else if (itemMeta.getEnchantLevel(enchantment) >= itemMetaRight.getStoredEnchantLevel(enchantment)) {
                    itemResultELevel.add(itemMeta.getEnchantLevel(enchantment));
                } else if (itemMeta.getEnchantLevel(enchantment) < itemMetaRight.getStoredEnchantLevel(enchantment)) {
                    itemResultELevel.add(itemMetaRight.getStoredEnchantLevel(enchantment));
                }
                i++;
            } else if (itemMeta.hasEnchant(enchantment) && !itemMetaRight.hasStoredEnchant(enchantment)) {
                itemResultEnchantment.add(enchantment);
                itemResultELevel.add(itemMeta.getEnchantLevel(enchantment));
                i++;
            } else if (!itemMeta.hasEnchant(enchantment) && itemMetaRight.hasStoredEnchant(enchantment)) {
                itemResultEnchantment.add(enchantment);
                itemResultELevel.add(itemMetaRight.getStoredEnchantLevel(enchantment));
                i++;
            }
        }
        //addCustomEnchantment(itemLeft, itemRight);
        //checkCEConfliction(itemLeft, itemRight);
        checkConfliction(itemMeta, null, null, itemMetaRight);
        checkPosibility();
    }

    private void doCombine2(ItemStack itemLeft, ItemStack itemRight) throws Exception {
        ItemMeta itemMeta = itemLeft.getItemMeta();
        ItemMeta itemMeta2 = itemRight.getItemMeta();

        for (Enchantment enchantment : Enchantment.values()) {
            if (itemMeta.hasEnchant(enchantment) && itemMeta2.hasEnchant(enchantment)) {
                itemResultEnchantment.add(enchantment);
                if (itemMeta.getEnchantLevel(enchantment) == itemMeta2.getEnchantLevel(enchantment) && itemMeta.getEnchantLevel(enchantment) < controller.getMain().getConfig().getInt(enchantment.getName())) {
                    itemResultELevel.add(itemMeta.getEnchantLevel(enchantment) + 1);
                } else if (itemMeta.getEnchantLevel(enchantment) >= itemMeta2.getEnchantLevel(enchantment)) {
                    itemResultELevel.add(itemMeta.getEnchantLevel(enchantment));
                } else if (itemMeta.getEnchantLevel(enchantment) < itemMeta2.getEnchantLevel(enchantment)) {
                    itemResultELevel.add(itemMeta2.getEnchantLevel(enchantment));
                }
            } else if (itemMeta.hasEnchant(enchantment) && !itemMeta2.hasEnchant(enchantment)) {
                itemResultEnchantment.add(enchantment);
                itemResultELevel.add(itemMeta.getEnchantLevel(enchantment));
            } else if (!itemMeta.hasEnchant(enchantment) && itemMeta2.hasEnchant(enchantment)) {
                itemResultEnchantment.add(enchantment);
                itemResultELevel.add(itemMeta2.getEnchantLevel(enchantment));
            }
        }
        addCustomEnchantment(itemLeft, itemRight);
        checkCEConfliction(itemLeft, itemRight);
        checkConfliction(itemMeta, null, itemMeta2, null);
        checkPosibility();
    }

    private void doCombineBooks(ItemStack itemLeft, ItemStack itemRight) throws Exception {
        EnchantmentStorageMeta itemMetaLeft = (EnchantmentStorageMeta) itemLeft.getItemMeta();
        EnchantmentStorageMeta itemMetaRight = (EnchantmentStorageMeta) itemRight.getItemMeta();

        for (Enchantment enchantment : Enchantment.values()) {
            if (itemMetaLeft.hasStoredEnchant(enchantment) && itemMetaRight.hasStoredEnchant(enchantment)) {
                itemResultEnchantment.add(enchantment);
                if (itemMetaLeft.getStoredEnchantLevel(enchantment) == itemMetaRight.getStoredEnchantLevel(enchantment) && itemMetaLeft.getStoredEnchantLevel(enchantment) < controller.getMain().getConfig().getInt(enchantment.getName())) {
                    itemResultELevel.add(itemMetaLeft.getStoredEnchantLevel(enchantment) + 1);
                } else if (itemMetaLeft.getStoredEnchantLevel(enchantment) >= itemMetaRight.getStoredEnchantLevel(enchantment)) {
                    itemResultELevel.add(itemMetaLeft.getStoredEnchantLevel(enchantment));
                } else if (itemMetaLeft.getStoredEnchantLevel(enchantment) < itemMetaRight.getStoredEnchantLevel(enchantment)) {
                    itemResultELevel.add(itemMetaRight.getStoredEnchantLevel(enchantment));
                }
            } else if (itemMetaLeft.hasStoredEnchant(enchantment) && !itemMetaRight.hasStoredEnchant(enchantment)) {
                itemResultEnchantment.add(enchantment);
                itemResultELevel.add(itemMetaLeft.getStoredEnchantLevel(enchantment));
            } else if (!itemMetaLeft.hasStoredEnchant(enchantment) && itemMetaRight.hasStoredEnchant(enchantment)) {
                itemResultEnchantment.add(enchantment);
                itemResultELevel.add(itemMetaRight.getStoredEnchantLevel(enchantment));
            }
        }
        addCustomEnchantment(itemLeft, itemRight);
        checkCEConfliction(itemLeft, itemRight);
        checkConfliction(null, itemMetaLeft, null, itemMetaRight);
    }

    private void checkPosibility() {
        if (func.isArmor(itemLeft.getType())) {
            for (int i = itemResultEnchantment.size()-1; i >= 0; i--) {
                Enchantment enchant = itemResultEnchantment.get(i);
                if (!enchant.equals(Enchantment.PROTECTION_ENVIRONMENTAL) && !enchant.equals(Enchantment.PROTECTION_EXPLOSIONS) && !enchant.equals(Enchantment.PROTECTION_FIRE) &&
                    !enchant.equals(Enchantment.PROTECTION_PROJECTILE) && !enchant.equals(Enchantment.THORNS) && !enchant.equals(Enchantment.BINDING_CURSE) && !enchant.equals(Enchantment.DURABILITY) &&
                    !enchant.equals(Enchantment.MENDING) && !enchant.equals(Enchantment.VANISHING_CURSE)) {
                    itemResultEnchantment.remove(i);
                    itemResultELevel.remove(i);
                }
            }
            if (func.isHelmet(itemLeft.getType())) {
                for (int i = itemResultEnchantment.size()-1; i >= 0; i--) {
                    Enchantment enchant = itemResultEnchantment.get(i);
                    if (!enchant.equals(Enchantment.OXYGEN) || !enchant.equals(Enchantment.WATER_WORKER)) {
                        itemResultEnchantment.remove(i);
                        itemResultELevel.remove(i);
                    }
                }
            }
            if (func.isBoots(itemLeft.getType())) {
                for (int i = itemResultEnchantment.size()-1; i >= 0; i--) {
                    Enchantment enchant = itemResultEnchantment.get(i);
                    if (!enchant.equals(Enchantment.DEPTH_STRIDER) || !enchant.equals(Enchantment.FROST_WALKER)) {
                        itemResultEnchantment.remove(i);
                        itemResultELevel.remove(i);
                    }
                }
            }
        } else if (func.isTool(itemLeft.getType())) {
            for (int i = itemResultEnchantment.size()-1; i >= 0; i--) {
                Enchantment enchant = itemResultEnchantment.get(i);
                if (!enchant.equals(Enchantment.DURABILITY) && !enchant.equals(Enchantment.MENDING) && enchant.equals(Enchantment.VANISHING_CURSE)) {
                    itemResultEnchantment.remove(i);
                    itemResultELevel.remove(i);
                }
            }
            if (!func.isShears(itemLeft.getType()) && !func.isFishingRod(itemLeft.getType()) && !func.isFlintSteel(itemLeft.getType()) && !func.isCarrotStick(itemLeft.getType())) {
                for (int i = itemResultEnchantment.size()-1; i >= 0; i--) {
                    Enchantment enchant = itemResultEnchantment.get(i);
                    if (!enchant.equals(Enchantment.SILK_TOUCH) && !enchant.equals(Enchantment.LOOT_BONUS_BLOCKS)) {
                        itemResultEnchantment.remove(i);
                        itemResultELevel.remove(i);
                    }
                }
            }
            if (!func.isFishingRod(itemLeft.getType()) && !func.isFlintSteel(itemLeft.getType()) && !func.isCarrotStick(itemLeft.getType())) {
                for (int i = itemResultEnchantment.size()-1; i >= 0; i--) {
                    Enchantment enchant = itemResultEnchantment.get(i);
                    if (!enchant.equals(Enchantment.DIG_SPEED)) {
                        itemResultEnchantment.remove(i);
                        itemResultELevel.remove(i);
                    }
                }
            }
            if (func.isFishingRod(itemLeft.getType())) {
                for (int i = itemResultEnchantment.size()-1; i >= 0; i--) {
                    Enchantment enchant = itemResultEnchantment.get(i);
                    if (!enchant.equals(Enchantment.LUCK) && !enchant.equals(Enchantment.LURE)) {
                        itemResultEnchantment.remove(i);
                        itemResultELevel.remove(i);
                    }
                }
            }
            if (func.isAxe(itemLeft.getType())) {
               for (int i = itemResultEnchantment.size()-1; i >= 0; i--) {
                    Enchantment enchant = itemResultEnchantment.get(i);
                    if (!enchant.equals(Enchantment.DAMAGE_ALL) && !enchant.equals(Enchantment.DAMAGE_ARTHROPODS) && !enchant.equals(Enchantment.DAMAGE_UNDEAD)) {
                        itemResultEnchantment.remove(i);
                        itemResultELevel.remove(i);
                    }
                }
            }
        } else if (func.isWeapon(itemLeft.getType())) {
            for (int i = itemResultEnchantment.size()-1; i >= 0; i--) {
                Enchantment enchant = itemResultEnchantment.get(i);
                if (!enchant.equals(Enchantment.MENDING) && !enchant.equals(Enchantment.VANISHING_CURSE) && !enchant.equals(Enchantment.DURABILITY)) {
                    itemResultEnchantment.remove(i);
                    itemResultELevel.remove(i);
                }
            }
            if (!func.isBow(itemLeft.getType()) && !func.isShield(itemLeft.getType())) {
                for (int i = itemResultEnchantment.size()-1; i >= 0; i--) {
                    Enchantment enchant = itemResultEnchantment.get(i);
                    if (!enchant.equals(Enchantment.DAMAGE_ALL) && !enchant.equals(Enchantment.DAMAGE_ARTHROPODS) && !enchant.equals(Enchantment.DAMAGE_UNDEAD) &&
                        !enchant.equals(Enchantment.KNOCKBACK) && !enchant.equals(Enchantment.FIRE_ASPECT) && !enchant.equals(Enchantment.LOOT_BONUS_MOBS) && !enchant.equals(Enchantment.SWEEPING_EDGE)) {
                        itemResultEnchantment.remove(i);
                        itemResultELevel.remove(i);
                    }
                }
            }
            if (func.isBow(itemLeft.getType())) {
                for (int i = itemResultEnchantment.size()-1; i >= 0; i--) {
                    Enchantment enchant = itemResultEnchantment.get(i);
                    if (!enchant.equals(Enchantment.ARROW_DAMAGE) && !enchant.equals(Enchantment.ARROW_FIRE) && !enchant.equals(Enchantment.ARROW_INFINITE) && !enchant.equals(Enchantment.ARROW_KNOCKBACK)) {
                        itemResultEnchantment.remove(i);
                        itemResultELevel.remove(i);
                    }
                }
            }
        }
    }

    private void checkConfliction(ItemMeta metaLeft, EnchantmentStorageMeta eMetaLeft, ItemMeta metaRight, EnchantmentStorageMeta eMetaRight) {
        if (func.isArmor(itemLeft.getType())) {
            checkArmor(metaLeft, eMetaLeft, metaRight, eMetaRight);
        } else if (func.isWeapon(itemLeft.getType())) {
            checkWeapon(metaLeft, eMetaLeft, metaRight, eMetaRight);
        } else if (func.isTool(itemLeft.getType())) {
            checkTool(metaLeft, eMetaLeft, metaRight, eMetaRight);
        } else if (func.isEnchantedBook(itemLeft.getType())) {
            checkArmor(metaLeft, eMetaLeft, metaRight, eMetaRight);
            checkTool(metaLeft, eMetaLeft, metaRight, eMetaRight);
            checkWeapon(metaLeft, eMetaLeft, metaRight, eMetaRight);
        }
    }

    private void checkArmor(ItemMeta metaLeft, EnchantmentStorageMeta eMetaLeft, ItemMeta metaRight, EnchantmentStorageMeta eMetaRight) {
        if (metaLeft != null && metaRight != null) {
            if (itemResultEnchantment.contains(Enchantment.FROST_WALKER) && itemResultEnchantment.contains(Enchantment.DEPTH_STRIDER)) {
                if (metaLeft.hasEnchant(Enchantment.FROST_WALKER)) {
                    itemResultEnchantment.remove(Enchantment.DEPTH_STRIDER);
                    itemResultELevel.remove(metaRight.getEnchantLevel(Enchantment.DEPTH_STRIDER));
                } else {
                    itemResultEnchantment.remove(Enchantment.FROST_WALKER);
                    itemResultELevel.remove(metaRight.getEnchantLevel(Enchantment.FROST_WALKER));
                }
            }

            if ((itemResultEnchantment.contains(Enchantment.PROTECTION_ENVIRONMENTAL) && itemResultEnchantment.contains(Enchantment.PROTECTION_EXPLOSIONS)) ||
                    (itemResultEnchantment.contains(Enchantment.PROTECTION_ENVIRONMENTAL) && itemResultEnchantment.contains(Enchantment.PROTECTION_FIRE)) ||
                    (itemResultEnchantment.contains(Enchantment.PROTECTION_ENVIRONMENTAL) && itemResultEnchantment.contains(Enchantment.PROTECTION_PROJECTILE)) ||
                    (itemResultEnchantment.contains(Enchantment.PROTECTION_FIRE) && itemResultEnchantment.contains(Enchantment.PROTECTION_EXPLOSIONS)) ||
                    (itemResultEnchantment.contains(Enchantment.PROTECTION_FIRE) && itemResultEnchantment.contains(Enchantment.PROTECTION_PROJECTILE)) ||
                    (itemResultEnchantment.contains(Enchantment.PROTECTION_EXPLOSIONS) && itemResultEnchantment.contains(Enchantment.PROTECTION_PROJECTILE))) {
                if (metaLeft.hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL) && metaRight.hasEnchant(Enchantment.PROTECTION_EXPLOSIONS)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_EXPLOSIONS);
                    itemResultELevel.remove(metaRight.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS));
                } else if (metaLeft.hasEnchant(Enchantment.PROTECTION_EXPLOSIONS) && metaRight.hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_ENVIRONMENTAL);
                    itemResultELevel.remove(metaRight.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL));
                }

                if (metaLeft.hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL) && metaRight.hasEnchant(Enchantment.PROTECTION_FIRE)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_FIRE);
                    itemResultELevel.remove(metaRight.getEnchantLevel(Enchantment.PROTECTION_FIRE));
                } else if (metaLeft.hasEnchant(Enchantment.PROTECTION_FIRE) && metaRight.hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_ENVIRONMENTAL);
                    itemResultELevel.remove(metaRight.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL));
                }

                if (metaLeft.hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL) && metaRight.hasEnchant(Enchantment.PROTECTION_PROJECTILE)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_PROJECTILE);
                    itemResultELevel.remove(metaRight.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE));
                } else if (metaLeft.hasEnchant(Enchantment.PROTECTION_PROJECTILE) && metaRight.hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_ENVIRONMENTAL);
                    itemResultELevel.remove(metaRight.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL));
                }

                if (metaLeft.hasEnchant(Enchantment.PROTECTION_FIRE) && metaRight.hasEnchant(Enchantment.PROTECTION_EXPLOSIONS)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_EXPLOSIONS);
                    itemResultELevel.remove(metaRight.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS));
                } else if (metaLeft.hasEnchant(Enchantment.PROTECTION_EXPLOSIONS) && metaRight.hasEnchant(Enchantment.PROTECTION_FIRE)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_FIRE);
                    itemResultELevel.remove(metaRight.getEnchantLevel(Enchantment.PROTECTION_FIRE));
                }

                if (metaLeft.hasEnchant(Enchantment.PROTECTION_FIRE) && metaRight.hasEnchant(Enchantment.PROTECTION_PROJECTILE)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_PROJECTILE);
                    itemResultELevel.remove(metaRight.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE));
                } else if (metaLeft.hasEnchant(Enchantment.PROTECTION_PROJECTILE) && metaRight.hasEnchant(Enchantment.PROTECTION_FIRE)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_FIRE);
                    itemResultELevel.remove(metaRight.getEnchantLevel(Enchantment.PROTECTION_FIRE));
                }

                if (metaLeft.hasEnchant(Enchantment.PROTECTION_EXPLOSIONS) && metaRight.hasEnchant(Enchantment.PROTECTION_PROJECTILE)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_PROJECTILE);
                    itemResultELevel.remove(metaRight.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE));
                } else if (metaLeft.hasEnchant(Enchantment.PROTECTION_PROJECTILE) && metaRight.hasEnchant(Enchantment.PROTECTION_EXPLOSIONS)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_EXPLOSIONS);
                    itemResultELevel.remove(metaRight.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS));
                }
            }
        } else if (metaLeft != null && eMetaRight != null) {
            if (itemResultEnchantment.contains(Enchantment.FROST_WALKER) && itemResultEnchantment.contains(Enchantment.DEPTH_STRIDER)) {
                if (metaLeft.hasEnchant(Enchantment.FROST_WALKER)) {
                    itemResultEnchantment.remove(Enchantment.DEPTH_STRIDER);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.DEPTH_STRIDER));
                } else {
                    itemResultEnchantment.remove(Enchantment.FROST_WALKER);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.FROST_WALKER));
                }
            }

            if ((itemResultEnchantment.contains(Enchantment.PROTECTION_ENVIRONMENTAL) && itemResultEnchantment.contains(Enchantment.PROTECTION_EXPLOSIONS)) ||
                    (itemResultEnchantment.contains(Enchantment.PROTECTION_ENVIRONMENTAL) && itemResultEnchantment.contains(Enchantment.PROTECTION_FIRE)) ||
                    (itemResultEnchantment.contains(Enchantment.PROTECTION_ENVIRONMENTAL) && itemResultEnchantment.contains(Enchantment.PROTECTION_PROJECTILE)) ||
                    (itemResultEnchantment.contains(Enchantment.PROTECTION_FIRE) && itemResultEnchantment.contains(Enchantment.PROTECTION_EXPLOSIONS)) ||
                    (itemResultEnchantment.contains(Enchantment.PROTECTION_FIRE) && itemResultEnchantment.contains(Enchantment.PROTECTION_PROJECTILE)) ||
                    (itemResultEnchantment.contains(Enchantment.PROTECTION_EXPLOSIONS) && itemResultEnchantment.contains(Enchantment.PROTECTION_PROJECTILE))) {
                if (metaLeft.hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL) && eMetaRight.hasStoredEnchant(Enchantment.PROTECTION_EXPLOSIONS)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_EXPLOSIONS);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS));
                } else if (metaLeft.hasEnchant(Enchantment.PROTECTION_EXPLOSIONS) && eMetaRight.hasStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_ENVIRONMENTAL);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL));
                }

                if (metaLeft.hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL) && eMetaRight.hasStoredEnchant(Enchantment.PROTECTION_FIRE)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_FIRE);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.PROTECTION_FIRE));
                } else if (metaLeft.hasEnchant(Enchantment.PROTECTION_FIRE) && eMetaRight.hasStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_ENVIRONMENTAL);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL));
                }

                if (metaLeft.hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL) && eMetaRight.hasStoredEnchant(Enchantment.PROTECTION_PROJECTILE)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_PROJECTILE);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE));
                } else if (metaLeft.hasEnchant(Enchantment.PROTECTION_PROJECTILE) && eMetaRight.hasStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_ENVIRONMENTAL);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL));
                }

                if (metaLeft.hasEnchant(Enchantment.PROTECTION_FIRE) && eMetaRight.hasStoredEnchant(Enchantment.PROTECTION_EXPLOSIONS)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_EXPLOSIONS);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS));
                } else if (metaLeft.hasEnchant(Enchantment.PROTECTION_EXPLOSIONS) && eMetaRight.hasStoredEnchant(Enchantment.PROTECTION_FIRE)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_FIRE);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.PROTECTION_FIRE));
                }

                if (metaLeft.hasEnchant(Enchantment.PROTECTION_FIRE) && eMetaRight.hasStoredEnchant(Enchantment.PROTECTION_PROJECTILE)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_PROJECTILE);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE));
                } else if (metaLeft.hasEnchant(Enchantment.PROTECTION_PROJECTILE) && eMetaRight.hasStoredEnchant(Enchantment.PROTECTION_FIRE)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_FIRE);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.PROTECTION_FIRE));
                }

                if (metaLeft.hasEnchant(Enchantment.PROTECTION_EXPLOSIONS) && eMetaRight.hasStoredEnchant(Enchantment.PROTECTION_PROJECTILE)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_PROJECTILE);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE));
                } else if (metaLeft.hasEnchant(Enchantment.PROTECTION_PROJECTILE) && eMetaRight.hasStoredEnchant(Enchantment.PROTECTION_EXPLOSIONS)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_EXPLOSIONS);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS));
                }
            }
        } else if (eMetaLeft != null && eMetaRight != null) {
            if (itemResultEnchantment.contains(Enchantment.FROST_WALKER) && itemResultEnchantment.contains(Enchantment.DEPTH_STRIDER)) {
                if (eMetaLeft.hasStoredEnchant(Enchantment.FROST_WALKER)) {
                    itemResultEnchantment.remove(Enchantment.DEPTH_STRIDER);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.DEPTH_STRIDER));
                } else {
                    itemResultEnchantment.remove(Enchantment.FROST_WALKER);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.FROST_WALKER));
                }
            }

            if ((itemResultEnchantment.contains(Enchantment.PROTECTION_ENVIRONMENTAL) && itemResultEnchantment.contains(Enchantment.PROTECTION_EXPLOSIONS)) ||
                    (itemResultEnchantment.contains(Enchantment.PROTECTION_ENVIRONMENTAL) && itemResultEnchantment.contains(Enchantment.PROTECTION_FIRE)) ||
                    (itemResultEnchantment.contains(Enchantment.PROTECTION_ENVIRONMENTAL) && itemResultEnchantment.contains(Enchantment.PROTECTION_PROJECTILE)) ||
                    (itemResultEnchantment.contains(Enchantment.PROTECTION_FIRE) && itemResultEnchantment.contains(Enchantment.PROTECTION_EXPLOSIONS)) ||
                    (itemResultEnchantment.contains(Enchantment.PROTECTION_FIRE) && itemResultEnchantment.contains(Enchantment.PROTECTION_PROJECTILE)) ||
                    (itemResultEnchantment.contains(Enchantment.PROTECTION_EXPLOSIONS) && itemResultEnchantment.contains(Enchantment.PROTECTION_PROJECTILE))) {
                if (eMetaLeft.hasStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL) && eMetaRight.hasStoredEnchant(Enchantment.PROTECTION_EXPLOSIONS)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_EXPLOSIONS);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS));
                } else if (eMetaLeft.hasStoredEnchant(Enchantment.PROTECTION_EXPLOSIONS) && eMetaRight.hasStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_ENVIRONMENTAL);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL));
                }

                if (eMetaLeft.hasStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL) && eMetaRight.hasStoredEnchant(Enchantment.PROTECTION_FIRE)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_FIRE);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.PROTECTION_FIRE));
                } else if (eMetaLeft.hasStoredEnchant(Enchantment.PROTECTION_FIRE) && eMetaRight.hasStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_ENVIRONMENTAL);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL));
                }

                if (eMetaLeft.hasStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL) && eMetaRight.hasStoredEnchant(Enchantment.PROTECTION_PROJECTILE)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_PROJECTILE);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE));
                } else if (eMetaLeft.hasStoredEnchant(Enchantment.PROTECTION_PROJECTILE) && eMetaRight.hasStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_ENVIRONMENTAL);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL));
                }

                if (eMetaLeft.hasStoredEnchant(Enchantment.PROTECTION_FIRE) && eMetaRight.hasStoredEnchant(Enchantment.PROTECTION_EXPLOSIONS)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_EXPLOSIONS);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS));
                } else if (eMetaLeft.hasStoredEnchant(Enchantment.PROTECTION_EXPLOSIONS) && eMetaRight.hasStoredEnchant(Enchantment.PROTECTION_FIRE)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_FIRE);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.PROTECTION_FIRE));
                }

                if (eMetaLeft.hasStoredEnchant(Enchantment.PROTECTION_FIRE) && eMetaRight.hasStoredEnchant(Enchantment.PROTECTION_PROJECTILE)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_PROJECTILE);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE));
                } else if (eMetaLeft.hasStoredEnchant(Enchantment.PROTECTION_PROJECTILE) && eMetaRight.hasStoredEnchant(Enchantment.PROTECTION_FIRE)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_FIRE);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.PROTECTION_FIRE));
                }

                if (eMetaLeft.hasStoredEnchant(Enchantment.PROTECTION_EXPLOSIONS) && eMetaRight.hasStoredEnchant(Enchantment.PROTECTION_PROJECTILE)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_PROJECTILE);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE));
                } else if (eMetaLeft.hasStoredEnchant(Enchantment.PROTECTION_PROJECTILE) && eMetaRight.hasStoredEnchant(Enchantment.PROTECTION_EXPLOSIONS)) {
                    itemResultEnchantment.remove(Enchantment.PROTECTION_EXPLOSIONS);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS));
                }
            }
        }
    }

    private void checkWeapon(ItemMeta metaLeft, EnchantmentStorageMeta eMetaLeft, ItemMeta metaRight, EnchantmentStorageMeta eMetaRight) {
        if (metaLeft != null && metaRight != null) {
            if ((itemResultEnchantment.contains(Enchantment.DAMAGE_ALL) && itemResultEnchantment.contains(Enchantment.DAMAGE_ARTHROPODS)) ||
                    (itemResultEnchantment.contains(Enchantment.DAMAGE_ALL) && itemResultEnchantment.contains(Enchantment.DAMAGE_UNDEAD)) ||
                    (itemResultEnchantment.contains(Enchantment.DAMAGE_ARTHROPODS) && itemResultEnchantment.contains(Enchantment.DAMAGE_UNDEAD))) {

                if (metaLeft.hasEnchant(Enchantment.DAMAGE_ALL) && metaRight.hasEnchant(Enchantment.DAMAGE_ARTHROPODS)) {
                    itemResultEnchantment.remove(Enchantment.DAMAGE_ARTHROPODS);
                    itemResultELevel.remove(metaRight.getEnchantLevel(Enchantment.DAMAGE_ARTHROPODS));
                } else if (metaLeft.hasEnchant(Enchantment.DAMAGE_ARTHROPODS) && metaRight.hasEnchant(Enchantment.DAMAGE_ALL)) {
                    itemResultEnchantment.remove(Enchantment.DAMAGE_ALL);
                    itemResultELevel.remove(metaRight.getEnchantLevel(Enchantment.DAMAGE_ALL));
                }

                if (metaLeft.hasEnchant(Enchantment.DAMAGE_ALL) && metaRight.hasEnchant(Enchantment.DAMAGE_UNDEAD)) {
                    itemResultEnchantment.remove(Enchantment.DAMAGE_UNDEAD);
                    itemResultELevel.remove(metaRight.getEnchantLevel(Enchantment.DAMAGE_UNDEAD));
                } else if (metaLeft.hasEnchant(Enchantment.DAMAGE_UNDEAD) && metaRight.hasEnchant(Enchantment.DAMAGE_ALL)) {
                    itemResultEnchantment.remove(Enchantment.DAMAGE_ALL);
                    itemResultELevel.remove(metaRight.getEnchantLevel(Enchantment.DAMAGE_ALL));
                }

                if (metaLeft.hasEnchant(Enchantment.DAMAGE_ARTHROPODS) && metaRight.hasEnchant(Enchantment.DAMAGE_UNDEAD)) {
                    itemResultEnchantment.remove(Enchantment.DAMAGE_UNDEAD);
                    itemResultELevel.remove(metaRight.getEnchantLevel(Enchantment.DAMAGE_UNDEAD));
                } else if (metaLeft.hasEnchant(Enchantment.DAMAGE_UNDEAD) && metaRight.hasEnchant(Enchantment.DAMAGE_ARTHROPODS)) {
                    itemResultEnchantment.remove(Enchantment.DAMAGE_ARTHROPODS);
                    itemResultELevel.remove(metaRight.getEnchantLevel(Enchantment.DAMAGE_ARTHROPODS));
                }
            }

            if (metaLeft.hasEnchant(Enchantment.ARROW_INFINITE) && metaRight.hasEnchant(Enchantment.MENDING)) {
                itemResultEnchantment.remove(Enchantment.MENDING);
                itemResultELevel.remove(metaRight.getEnchantLevel(Enchantment.MENDING));
            } else if (metaLeft.hasEnchant(Enchantment.MENDING) && metaRight.hasEnchant(Enchantment.ARROW_INFINITE)) {
                itemResultEnchantment.remove(Enchantment.ARROW_INFINITE);
                itemResultELevel.remove(metaRight.getEnchantLevel(Enchantment.ARROW_INFINITE));
            }
        } else if (metaLeft != null && eMetaRight != null) {
            if ((itemResultEnchantment.contains(Enchantment.DAMAGE_ALL) && itemResultEnchantment.contains(Enchantment.DAMAGE_ARTHROPODS)) ||
                    (itemResultEnchantment.contains(Enchantment.DAMAGE_ALL) && itemResultEnchantment.contains(Enchantment.DAMAGE_UNDEAD)) ||
                    (itemResultEnchantment.contains(Enchantment.DAMAGE_ARTHROPODS) && itemResultEnchantment.contains(Enchantment.DAMAGE_UNDEAD))) {

                if (metaLeft.hasEnchant(Enchantment.DAMAGE_ALL) && eMetaRight.hasStoredEnchant(Enchantment.DAMAGE_ARTHROPODS)) {
                    itemResultEnchantment.remove(Enchantment.DAMAGE_ARTHROPODS);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.DAMAGE_ARTHROPODS));
                } else if (metaLeft.hasEnchant(Enchantment.DAMAGE_ARTHROPODS) && eMetaRight.hasStoredEnchant(Enchantment.DAMAGE_ALL)) {
                    itemResultEnchantment.remove(Enchantment.DAMAGE_ALL);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.DAMAGE_ALL));
                }

                if (metaLeft.hasEnchant(Enchantment.DAMAGE_ALL) && eMetaRight.hasStoredEnchant(Enchantment.DAMAGE_UNDEAD)) {
                    itemResultEnchantment.remove(Enchantment.DAMAGE_UNDEAD);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.DAMAGE_UNDEAD));
                } else if (metaLeft.hasEnchant(Enchantment.DAMAGE_UNDEAD) && eMetaRight.hasStoredEnchant(Enchantment.DAMAGE_ALL)) {
                    itemResultEnchantment.remove(Enchantment.DAMAGE_ALL);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.DAMAGE_ALL));
                }

                if (metaLeft.hasEnchant(Enchantment.DAMAGE_ARTHROPODS) && eMetaRight.hasStoredEnchant(Enchantment.DAMAGE_UNDEAD)) {
                    itemResultEnchantment.remove(Enchantment.DAMAGE_UNDEAD);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.DAMAGE_UNDEAD));
                } else if (metaLeft.hasEnchant(Enchantment.DAMAGE_UNDEAD) && eMetaRight.hasStoredEnchant(Enchantment.DAMAGE_ARTHROPODS)) {
                    itemResultEnchantment.remove(Enchantment.DAMAGE_ARTHROPODS);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.DAMAGE_ARTHROPODS));
                }
            }

            if (metaLeft.hasEnchant(Enchantment.ARROW_INFINITE) && eMetaRight.hasEnchant(Enchantment.MENDING)) {
                itemResultEnchantment.remove(Enchantment.MENDING);
                itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.MENDING));
            } else if (metaLeft.hasEnchant(Enchantment.MENDING) && eMetaRight.hasEnchant(Enchantment.ARROW_INFINITE)) {
                itemResultEnchantment.remove(Enchantment.ARROW_INFINITE);
                itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.ARROW_INFINITE));
            }
        } else if (eMetaLeft != null && eMetaRight != null) {
            if ((itemResultEnchantment.contains(Enchantment.DAMAGE_ALL) && itemResultEnchantment.contains(Enchantment.DAMAGE_ARTHROPODS)) ||
                    (itemResultEnchantment.contains(Enchantment.DAMAGE_ALL) && itemResultEnchantment.contains(Enchantment.DAMAGE_UNDEAD)) ||
                    (itemResultEnchantment.contains(Enchantment.DAMAGE_ARTHROPODS) && itemResultEnchantment.contains(Enchantment.DAMAGE_UNDEAD))) {

                if (eMetaLeft.hasStoredEnchant(Enchantment.DAMAGE_ALL) && eMetaRight.hasStoredEnchant(Enchantment.DAMAGE_ARTHROPODS)) {
                    itemResultEnchantment.remove(Enchantment.DAMAGE_ARTHROPODS);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.DAMAGE_ARTHROPODS));
                } else if (eMetaLeft.hasStoredEnchant(Enchantment.DAMAGE_ARTHROPODS) && eMetaRight.hasStoredEnchant(Enchantment.DAMAGE_ALL)) {
                    itemResultEnchantment.remove(Enchantment.DAMAGE_ALL);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.DAMAGE_ALL));
                }

                if (eMetaLeft.hasStoredEnchant(Enchantment.DAMAGE_ALL) && eMetaRight.hasStoredEnchant(Enchantment.DAMAGE_UNDEAD)) {
                    itemResultEnchantment.remove(Enchantment.DAMAGE_UNDEAD);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.DAMAGE_UNDEAD));
                } else if (eMetaLeft.hasStoredEnchant(Enchantment.DAMAGE_UNDEAD) && eMetaRight.hasStoredEnchant(Enchantment.DAMAGE_ALL)) {
                    itemResultEnchantment.remove(Enchantment.DAMAGE_ALL);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.DAMAGE_ALL));
                }

                if (eMetaLeft.hasStoredEnchant(Enchantment.DAMAGE_ARTHROPODS) && eMetaRight.hasStoredEnchant(Enchantment.DAMAGE_UNDEAD)) {
                    itemResultEnchantment.remove(Enchantment.DAMAGE_UNDEAD);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.DAMAGE_UNDEAD));
                } else if (eMetaLeft.hasStoredEnchant(Enchantment.DAMAGE_UNDEAD) && eMetaRight.hasStoredEnchant(Enchantment.DAMAGE_ARTHROPODS)) {
                    itemResultEnchantment.remove(Enchantment.DAMAGE_ARTHROPODS);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.DAMAGE_ARTHROPODS));
                }
            }

            if (eMetaLeft.hasEnchant(Enchantment.ARROW_INFINITE) && eMetaRight.hasEnchant(Enchantment.MENDING)) {
                itemResultEnchantment.remove(Enchantment.MENDING);
                itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.MENDING));
            } else if (eMetaLeft.hasEnchant(Enchantment.MENDING) && eMetaRight.hasEnchant(Enchantment.ARROW_INFINITE)) {
                itemResultEnchantment.remove(Enchantment.ARROW_INFINITE);
                itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.ARROW_INFINITE));
            }
        }
    }

    private void checkTool(ItemMeta metaLeft, EnchantmentStorageMeta eMetaLeft, ItemMeta metaRight, EnchantmentStorageMeta eMetaRight) {
        if (metaLeft != null && metaRight != null) {
            if (itemResultEnchantment.contains(Enchantment.SILK_TOUCH) && itemResultEnchantment.contains(Enchantment.LOOT_BONUS_BLOCKS)) {
                if (metaLeft.hasEnchant(Enchantment.SILK_TOUCH)) {
                    itemResultEnchantment.remove(Enchantment.LOOT_BONUS_BLOCKS);
                    itemResultELevel.remove(metaRight.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS));
                } else {
                    itemResultEnchantment.remove(Enchantment.SILK_TOUCH);
                    itemResultELevel.remove(metaRight.getEnchantLevel(Enchantment.SILK_TOUCH));
                }
            }
        } else if (metaLeft != null && eMetaRight != null) {
            if (itemResultEnchantment.contains(Enchantment.SILK_TOUCH) && itemResultEnchantment.contains(Enchantment.LOOT_BONUS_BLOCKS)) {
                if (metaLeft.hasEnchant(Enchantment.SILK_TOUCH)) {
                    itemResultEnchantment.remove(Enchantment.LOOT_BONUS_BLOCKS);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS));
                } else {
                    itemResultEnchantment.remove(Enchantment.SILK_TOUCH);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.SILK_TOUCH));
                }
            }
        } else if (eMetaLeft != null && eMetaRight != null) {
            if (itemResultEnchantment.contains(Enchantment.SILK_TOUCH) && itemResultEnchantment.contains(Enchantment.LOOT_BONUS_BLOCKS)) {
                if (eMetaLeft.hasStoredEnchant(Enchantment.SILK_TOUCH)) {
                    itemResultEnchantment.remove(Enchantment.LOOT_BONUS_BLOCKS);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS));
                } else {
                    itemResultEnchantment.remove(Enchantment.SILK_TOUCH);
                    itemResultELevel.remove(eMetaRight.getEnchantLevel(Enchantment.SILK_TOUCH));
                }
            }
        }
    }

    private void checkCEConfliction(ItemStack itemLeft, ItemStack itemRight) {
        if ((itemLeft.getItemMeta().getLore() == null || itemLeft.getItemMeta().getLore().isEmpty()) && (itemRight.getItemMeta().getLore() == null || itemRight.getItemMeta().getLore().isEmpty())) return;
        if (!func.hasCustomEnchant(itemLeft.getItemMeta().getLore()) && !func.hasCustomEnchant(itemRight.getItemMeta().getLore())) return;
        if (func.isArmor(itemLeft.getType())) {
            checkCEArmor(itemLeft, itemRight);
        } else if (func.isWeapon(itemLeft.getType())) {
            checkCEWeapon(itemLeft, itemRight);
        } else if (func.isTool(itemLeft.getType())) {
            checkCETool(itemLeft, itemRight);
        } else if (func.isEnchantedBook(itemLeft.getType())) {
            checkCEArmor(itemLeft, itemRight);
            checkCEWeapon(itemLeft, itemRight);
            checkCETool(itemLeft, itemRight);
        }
    }

    private void checkCEArmor(ItemStack itemLeft, ItemStack itemRight) {

    }

    private void checkCEWeapon(ItemStack itemLeft, ItemStack itemRight) {

    }

    private void checkCETool(ItemStack itemLeft, ItemStack itemRight) {
        if (func.isTool(itemLeft.getType())) {
            if (itemResultEnchantment.contains(Enchantment.SILK_TOUCH) && itemResultCustomEnchantment.contains(ChatColor.GRAY + "Fire Touch")) {
                if (itemLeft.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
                    for (String str : itemRight.getItemMeta().getLore()) {
                        if (!func.isCustomEnchant(str)) continue;
                        if (str.startsWith(ChatColor.GRAY + "Fire Touch ")) {
                            itemResultCustomEnchantment.remove(str);
                            itemResultCELevel.remove(str);
                        }
                    }
                } else {
                    itemResultEnchantment.remove(Enchantment.SILK_TOUCH);
                    itemResultELevel.remove(itemRight.getItemMeta().getEnchantLevel(Enchantment.SILK_TOUCH));
                }
            }

            if (itemResultEnchantment.contains(Enchantment.LOOT_BONUS_BLOCKS) && itemResultCustomEnchantment.contains(ChatColor.GRAY + "Fire Touch")) {
                if (itemLeft.getItemMeta().hasEnchant(Enchantment.LOOT_BONUS_BLOCKS)) {
                    for (String str : itemRight.getItemMeta().getLore()) {
                        if (!func.isCustomEnchant(str)) continue;
                        if (str.startsWith(ChatColor.GRAY + "Fire Touch ")) {
                            itemResultCustomEnchantment.remove(str);
                            itemResultCELevel.remove(str);
                        }
                    }
                } else {
                    itemResultEnchantment.remove(Enchantment.LOOT_BONUS_BLOCKS);
                    itemResultELevel.remove(itemRight.getItemMeta().getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS));
                }
            }
        } else if (func.isEnchantedBook(itemLeft.getType())) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemLeft.getItemMeta();
            if (itemResultEnchantment.contains(Enchantment.SILK_TOUCH) && itemResultCustomEnchantment.contains(ChatColor.GRAY + "Fire Touch")) {
                if (meta.hasStoredEnchant(Enchantment.SILK_TOUCH)) {
                    for (String str : itemRight.getItemMeta().getLore()) {
                        if (!func.isCustomEnchant(str)) continue;
                        if (str.startsWith(ChatColor.GRAY + "Fire Touch ")) {
                            itemResultCustomEnchantment.remove(str);
                            itemResultCELevel.remove(str);
                        }
                    }
                } else {
                    itemResultEnchantment.remove(Enchantment.SILK_TOUCH);
                    itemResultELevel.remove(itemRight.getItemMeta().getEnchantLevel(Enchantment.SILK_TOUCH));
                }
            }

            if (itemResultEnchantment.contains(Enchantment.LOOT_BONUS_BLOCKS) && itemResultCustomEnchantment.contains(ChatColor.GRAY + "Fire Touch")) {
                if (meta.hasStoredEnchant(Enchantment.LOOT_BONUS_BLOCKS)) {
                    for (String str : itemRight.getItemMeta().getLore()) {
                        if (!func.isCustomEnchant(str)) continue;
                        if (str.startsWith(ChatColor.GRAY + "Fire Touch ")) {
                            itemResultCustomEnchantment.remove(str);
                            itemResultCELevel.remove(str);
                        }
                    }
                } else {
                    itemResultEnchantment.remove(Enchantment.LOOT_BONUS_BLOCKS);
                    itemResultELevel.remove(itemRight.getItemMeta().getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS));
                }
            }
        }
    }

    public void clearAllArray() {
        itemResultEnchantment.clear();
        itemResultELevel.clear();
        itemResultCustomEnchantment.clear();
        itemResultCELevel.clear();
    }

    private void addCustomEnchantment(ItemStack itemLeft, ItemStack itemRight) throws Exception {
        if (itemLeft.getItemMeta().hasLore() && itemRight.getItemMeta().hasLore()) {
            if (func.hasCustomEnchant(itemLeft.getItemMeta().getLore()) && func.hasCustomEnchant(itemRight.getItemMeta().getLore())) {
                for (String str : itemLeft.getItemMeta().getLore()) {
                    for (String str2 : itemRight.getItemMeta().getLore()) {
                        if (func.isCustomEnchant(str) && func.isCustomEnchant(str2)) {
                            if (func.getCEName(str).equalsIgnoreCase(func.getCEName(str2)) && !uselessCustomEnchantment.contains(func.getCEName(str))) {
                                itemResultCustomEnchantment.add(func.getCEName(str));
                                int levelLeft = func.getCELevel(str);
                                int levelRight = func.getCELevel(str2);
                                if (levelLeft == levelRight && levelLeft <= controller.getMain().getConfig().getInt(func.getInternName(str))) {
                                    itemResultCELevel.add(levelLeft + 1);
                                } else if (levelLeft > levelRight && levelLeft <= controller.getMain().getConfig().getInt(func.getInternName(str))) {
                                    itemResultCELevel.add(levelLeft);
                                } else if (levelLeft < levelRight && levelRight <= controller.getMain().getConfig().getInt(func.getInternName(str))) {
                                    itemResultCELevel.add(levelRight);
                                }
                            }
                        }
                    }
                }
            } else if (func.hasCustomEnchant(itemLeft.getItemMeta().getLore()) && !func.hasCustomEnchant(itemRight.getItemMeta().getLore())) {
                for (String str : itemLeft.getItemMeta().getLore()) {
                    itemResultCustomEnchantment.add(func.getCEName(str));
                    itemResultCELevel.add(func.getCELevel(str));
                }
            } else if (!func.hasCustomEnchant(itemLeft.getItemMeta().getLore()) && func.hasCustomEnchant(itemRight.getItemMeta().getLore())) {
                for (String str : itemRight.getItemMeta().getLore()) {
                    itemResultCustomEnchantment.add(func.getCEName(str));
                    itemResultCELevel.add(func.getCELevel(str));
                }
            }
        } else if (!itemLeft.getItemMeta().hasLore() && itemRight.getItemMeta().hasLore()) {
            if (func.hasCustomEnchant(itemRight.getItemMeta().getLore())) {
                for (String str : itemRight.getItemMeta().getLore()) {
                    itemResultCustomEnchantment.add(func.getCEName(str));
                    itemResultCELevel.add(func.getCELevel(str));
                }
            }
        }
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

    public int getItemResultCustomEnchantmentSize() {
        return itemResultCustomEnchantment.size();
    }

    public String getItemResultCustomEnchantment(int index) {
        return itemResultCustomEnchantment.get(index);
    }

    public int getItemResultCELevel(int index) {
        return itemResultCELevel.get(index);
    }

}
