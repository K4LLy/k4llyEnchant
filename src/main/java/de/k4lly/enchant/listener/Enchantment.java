package de.k4lly.enchant.listener;

import de.k4lly.enchant.controller.PluginController;
import de.k4lly.enchant.objects.Functions;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Enchantment implements Listener {

    private PluginController controller;
    private Functions func = new Functions();
    private boolean hasHelmet = false;
    private boolean hasChestplate = false;
    private boolean hasLeggins = false;
    private boolean hasBoots = false;
    private ItemStack helmet;
    private ItemStack chestplate;
    private ItemStack leggins;
    private ItemStack boots;

    //XP Boost (1-5) #WIP TODO: advance xp given
    //Fire Touch #WIP TODO: disable fortune
    //Wither #finished
    //Poison Touch (1-2) #finished
    //Absorbing (1-3) #WIP
    //Nightvision #WIP TODO: Check if Item breaks or is rightclicked put on

    public Enchantment(PluginController controller) {
        this.controller = controller;
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent enchantEvent) {
        if (func.isWeapon(enchantEvent.getItem().getType()) || enchantEvent.getItem().getType().name().endsWith("BOW") || func.isBook(enchantEvent.getItem().getType())) {
            int randWither = (int) (Math.random() * 1000);
            int randPoison = (int) (Math.random() * 1000);
            int randXPBoost = (int) (Math.random() * 1000);
            if (randWither <= 79) { //8%
                enchantItem("Wither", 1, enchantEvent.getItem());
            }
            if (randPoison <= 59) { //6%
                enchantItem("Poison Touch", 2, enchantEvent.getItem());
            } else if (randPoison >= 60 && randPoison <= 169) { //11%
                enchantItem("Poison Touch", 1, enchantEvent.getItem());
            }
            if (randXPBoost <= 99) { //10%
                enchantItem("XP-Boost", 5, enchantEvent.getItem());
            } else if (randXPBoost >= 100 && randXPBoost <= 249) { //15%
                enchantItem("XP-Boost", 4, enchantEvent.getItem());
            } else if (randXPBoost >= 250 && randXPBoost <= 449) { //20%
                enchantItem("XP-Boost", 3, enchantEvent.getItem());
            } else if (randXPBoost >= 450 && randXPBoost <= 699) { //25%
                enchantItem("XP-Boost", 2, enchantEvent.getItem());
            } else if (randXPBoost >= 700 && randXPBoost <= 999) { //30%
                enchantItem("XP-Boost", 1, enchantEvent.getItem());
            }
        }
        if (func.isTool(enchantEvent.getItem().getType()) || func.isBook(enchantEvent.getItem().getType())) {
            int randFireT = (int) (Math.random() * 1000);
            if (randFireT <= 49) { //5%
                enchantItem("Fire Touch", 1, enchantEvent.getItem());
            }
        }
        if (enchantEvent.getItem().getType().name().endsWith("HELMET") || func.isBook(enchantEvent.getItem().getType())) {
            int randNVision = (int) (Math.random() * 1000);
            if (randNVision <= 129) { //13%
                enchantItem("Night Vision", 1, enchantEvent.getItem());
            }
        }
        if (func.isArmor(enchantEvent.getItem().getType()) || func.isBook(enchantEvent.getItem().getType())) {
            int randAbsobtion = (int) (Math.random() * 1000);
            if (randAbsobtion <= 29) { //3%
                enchantItem("Absorbing", 3, enchantEvent.getItem());
            } else if (randAbsobtion >= 30 && randAbsobtion <= 79) { //5%
                enchantItem("Absorbing", 2, enchantEvent.getItem());
            } else if (randAbsobtion >= 80 && randAbsobtion <= 159) { //8%
                enchantItem("Absorbing", 1, enchantEvent.getItem());
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent damageEvent) throws Exception {
        if (!(damageEvent.getDamager() instanceof LivingEntity)) return;
        if (!(damageEvent.getEntity() instanceof LivingEntity)) return;
        LivingEntity damager = (LivingEntity) damageEvent.getDamager();
        LivingEntity damaged = (LivingEntity) damageEvent.getEntity();
        if (damager.getEquipment().getItemInMainHand() == null || !func.isWeapon(damager.getEquipment().getItemInMainHand().getType()) || !damager.getEquipment().getItemInMainHand().hasItemMeta()) return;
        if (!damager.getEquipment().getItemInMainHand().getItemMeta().hasLore()) return;
        if (!hasCustomEnchant(damager.getEquipment().getItemInMainHand().getItemMeta().getLore())) return;
        for (String str : damager.getEquipment().getItemInMainHand().getItemMeta().getLore()) {
            if (!isCustomEnchant(str)) continue;
            String[] words = str.trim().split("\\s+");
            int level = words.length == 3 ? func.parseRomanNumber(words[2]) : func.parseRomanNumber(words[1]);
            if (str.startsWith(ChatColor.GRAY + "Wither ")) damaged.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 80 + (level * 80), level, false, true));
            if (str.startsWith(ChatColor.GRAY + "Poison Touch ")) damaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80 + (level * 80), level, false, true));
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent blockBreakEvent) {
        if (blockBreakEvent.isCancelled()) return;
        if (smeltedItem(blockBreakEvent.getBlock().getType()) == null) return;
        Player player = blockBreakEvent.getPlayer();
        if (player.getEquipment().getItemInMainHand() == null || !func.isTool(player.getEquipment().getItemInMainHand().getType()) || !player.getEquipment().getItemInMainHand().hasItemMeta())
            return;
        if (!player.getEquipment().getItemInMainHand().getItemMeta().hasLore()) return;
        if (!hasCustomEnchant(player.getEquipment().getItemInMainHand().getItemMeta().getLore())) return;
        boolean b = false;
        for (String str : player.getEquipment().getItemInMainHand().getItemMeta().getLore()) {
            if (!isCustomEnchant(str)) continue;
            if (str.startsWith(ChatColor.GRAY + "Fire Touch ")) b = true;
        }
        if (!b) return;
        Material mat = blockBreakEvent.getBlock().getType();
        blockBreakEvent.getBlock().setType(Material.AIR);
        blockBreakEvent.getBlock().getWorld().dropItemNaturally(blockBreakEvent.getBlock().getLocation().add(0, 1, 0), smeltedItem(mat));
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent deathEvent) throws Exception {
        if (!(deathEvent.getEntity().getKiller() instanceof Player)) return;
        if (deathEvent.getEntity() instanceof Player) return;
        Player killer = deathEvent.getEntity().getKiller();
        if (killer.getEquipment().getItemInMainHand() == null || !func.isWeapon(killer.getEquipment().getItemInMainHand().getType()) || !killer.getEquipment().getItemInMainHand().hasItemMeta()) return;
        if (!killer.getEquipment().getItemInMainHand().getItemMeta().hasLore()) return;
        if (!hasCustomEnchant(killer.getEquipment().getItemInMainHand().getItemMeta().getLore())) return;
        for (String str : killer.getEquipment().getItemInMainHand().getItemMeta().getLore()) {
            if (!isCustomEnchant(str)) continue;
            String[] words = str.trim().split("\\s+");
            int level = words.length == 3 ? func.parseRomanNumber(words[2]) : func.parseRomanNumber(words[1]);
            if (str.startsWith(ChatColor.GRAY + "XP-Boost ")) deathEvent.setDroppedExp(deathEvent.getDroppedExp()*level);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent closeEvent) throws Exception {
        if (!(closeEvent.getPlayer() instanceof Player)) return;
        Player player = (Player) closeEvent.getPlayer();
        for (ItemStack armor : player.getEquipment().getArmorContents()) {
            if (armor.getType().name().endsWith("HELMET")) {
                if (armor == null || !armor.hasItemMeta()) {
                    player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    if (!hasHelmet) return;
                    for (String str : helmet.getItemMeta().getLore()) {
                        if (!isCustomEnchant(str)) continue;
                        String[] words = str.trim().split("\\s+");
                        int level = words.length == 3 ? func.parseRomanNumber(words[2]) : func.parseRomanNumber(words[1]);
                        double health = player.getMaxHealth()-(0.833299994468689*level);
                        if (str.startsWith(ChatColor.GRAY + "Absorbing ")) player.setMaxHealth(health);
                        hasHelmet = false;
                    }
                } else {
                    helmet = armor;
                    if (!helmet.getItemMeta().hasLore()) return;
                    if (!hasCustomEnchant(helmet.getItemMeta().getLore())) return;
                    for (String str : helmet.getItemMeta().getLore()) {
                        if (!isCustomEnchant(str)) continue;
                        String[] words = str.trim().split("\\s+");
                        int level = words.length == 3 ? func.parseRomanNumber(words[2]) : func.parseRomanNumber(words[1]);
                        if (str.startsWith(ChatColor.GRAY + "Night Vision ")) player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999999, 1, false, true));
                        if (hasHelmet) return;
                        double health = player.getMaxHealth()+(0.833299994468689*level);
                        if (str.startsWith(ChatColor.GRAY + "Absorbing ")) player.setMaxHealth(health);
                        hasHelmet = true;
                    }
                }
            } else if (armor.getType().name().endsWith("CHESTPLATE")) {
                if (armor == null || !armor.hasItemMeta()) {
                    if (!hasChestplate) return;
                    for (String str : chestplate.getItemMeta().getLore()) {
                        if (!isCustomEnchant(str)) continue;
                        String[] words = str.trim().split("\\s+");
                        int level = words.length == 3 ? func.parseRomanNumber(words[2]) : func.parseRomanNumber(words[1]);
                        double health = player.getMaxHealth()-(0.833299994468689*level);
                        if (str.startsWith(ChatColor.GRAY + "Absorbing ")) player.setMaxHealth(health);
                        hasChestplate = false;
                    }
                } else {
                    chestplate = armor;
                    if (!chestplate.getItemMeta().hasLore()) return;
                    if (!hasCustomEnchant(chestplate.getItemMeta().getLore())) return;
                    for (String str : chestplate.getItemMeta().getLore()) {
                        if (!isCustomEnchant(str)) continue;
                        String[] words = str.trim().split("\\s+");
                        int level = words.length == 3 ? func.parseRomanNumber(words[2]) : func.parseRomanNumber(words[1]);
                        if (hasChestplate) return;
                        double health = player.getMaxHealth()+(0.833299994468689*level);
                        if (str.startsWith(ChatColor.GRAY + "Absorbing ")) player.setMaxHealth(health);
                        hasChestplate = true;
                    }
                }
            } else if (armor.getType().name().endsWith("LEGGINS")) {
                if (armor == null || !armor.hasItemMeta()) {
                    if (!hasLeggins) return;
                    for (String str : leggins.getItemMeta().getLore()) {
                        if (!isCustomEnchant(str)) continue;
                        String[] words = str.trim().split("\\s+");
                        int level = words.length == 3 ? func.parseRomanNumber(words[2]) : func.parseRomanNumber(words[1]);
                        double health = player.getMaxHealth()-(0.833299994468689*level);
                        if (str.startsWith(ChatColor.GRAY + "Absorbing ")) player.setMaxHealth(health);
                        hasLeggins = false;
                    }
                } else {
                    leggins = armor;
                    if (!leggins.getItemMeta().hasLore()) return;
                    if (!hasCustomEnchant(leggins.getItemMeta().getLore())) return;
                    for (String str : leggins.getItemMeta().getLore()) {
                        if (!isCustomEnchant(str)) continue;
                        String[] words = str.trim().split("\\s+");
                        int level = words.length == 3 ? func.parseRomanNumber(words[2]) : func.parseRomanNumber(words[1]);
                        if (hasLeggins) return;
                        double health = player.getMaxHealth()+(0.833299994468689*level);
                        if (str.startsWith(ChatColor.GRAY + "Absorbing ")) player.setMaxHealth(health);
                        hasLeggins = true;
                    }
                }
            } else if (armor.getType().name().endsWith("BOOTS")) {
                if (armor == null || !armor.hasItemMeta()) {
                    if (!hasBoots) return;
                    for (String str : boots.getItemMeta().getLore()) {
                        if (!isCustomEnchant(str)) continue;
                        String[] words = str.trim().split("\\s+");
                        int level = words.length == 3 ? func.parseRomanNumber(words[2]) : func.parseRomanNumber(words[1]);
                        double health = player.getMaxHealth()-(0.833299994468689*level);
                        if (str.startsWith(ChatColor.GRAY + "Absorbing ")) player.setMaxHealth(health);
                        hasBoots = false;
                    }
                } else {
                    boots = armor;
                    if (!boots.getItemMeta().hasLore()) return;
                    if (!hasCustomEnchant(boots.getItemMeta().getLore())) return;
                    for (String str : boots.getItemMeta().getLore()) {
                        if (!isCustomEnchant(str)) continue;
                        String[] words = str.trim().split("\\s+");
                        int level = words.length == 3 ? func.parseRomanNumber(words[2]) : func.parseRomanNumber(words[1]);
                        if (hasBoots) return;
                        double health = player.getMaxHealth()+(0.833299994468689*level);
                        if (str.startsWith(ChatColor.GRAY + "Absorbing ")) player.setMaxHealth(health);
                        hasBoots = true;
                    }
                }
            }
        }
    }

    private void enchantItem(String name, int level, ItemStack item) {
        if (level == 0) return;
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore == null || isEmpty(lore)) {
            List<String> newLore = new ArrayList<>();
            newLore.add(ChatColor.GRAY + name + " " + func.getRomanNumber(level));
            itemMeta.setLore(newLore);
        } else {
            if (!containsEnchant(name, item)) lore.add(ChatColor.GRAY + name + " " + func.getRomanNumber(level));
            itemMeta.setLore(lore);
        }
        item.setItemMeta(itemMeta);
    }

    private ItemStack smeltedItem(Material material) {
        switch (material) {
            case IRON_ORE:
                return new ItemStack(Material.IRON_INGOT, 1);
            case GOLD_ORE:
                return new ItemStack(Material.GOLD_INGOT, 1);
            case COBBLESTONE:
                return new ItemStack(Material.STONE, 1);
            case SAND:
                return new ItemStack(Material.GLASS, 1);
            case LOG:
                return new ItemStack(Material.COAL, 1, (short) 1);
            case LOG_2:
                return new ItemStack(Material.COAL, 1, (short) 1);
            case CLAY:
                return new ItemStack(Material.HARD_CLAY, 1);
            case NETHERRACK:
                return new ItemStack(Material.NETHER_BRICK_ITEM, 1);
            case CACTUS:
                return new ItemStack(Material.INK_SACK, 1, (short) 2);
            default:
                return new ItemStack(material);
        }
    }

    private boolean hasCustomEnchant(List<String> lore) {
        for (String str : lore) {
            if (isCustomEnchant(str)) return true;
        }
        return false;
    }

    private boolean isCustomEnchant(String str) {
        return str.startsWith(ChatColor.GRAY + "Wither ") || str.startsWith(ChatColor.GRAY + "Fire Touch ") || str.startsWith(ChatColor.GRAY + "Poison Touch ") || str.startsWith(ChatColor.GRAY + "XP-Boost ") || str.startsWith(ChatColor.GRAY + "Night Vision ") || str.startsWith(ChatColor.GRAY + "Absorbing ");
    }

    private boolean containsEnchant(String name, ItemStack item) {
        for (String str : item.getItemMeta().getLore()) {
            if (str.startsWith(ChatColor.GRAY + name)) return true;
        }
        return false;
    }

    private boolean isEmpty(List<String> lore) {
        for (String str : lore) {
            if (!str.equals("") && str != null) return false;
        }
        return true;
    }
}
