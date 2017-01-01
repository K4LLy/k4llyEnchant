package de.k4lly.enchant.listener;

import de.k4lly.enchant.controller.PluginController;
import de.k4lly.enchant.objects.Functions;
import org.bukkit.Achievement;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

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
    private ItemStack oldHelmet;
    private ItemStack oldChestplate;
    private ItemStack oldLeggins;
    private ItemStack oldBoots;

    HashMap<Player, ItemStack> storedItem = new HashMap<Player, ItemStack>();

    public static String XP_BOOST = "XP-Boost";
    public static String FIRE_TOUCH = "Fire Touch";
    public static String WITHER = "Wither";
    public static String POISON_TOUCH = "Poison Touch";
    //public static String INFINITY = "Infinity";
    public static String NIGHT_VISION = "Night Vision";

    //XP Boost (1-5) #finished
    //Fire Touch #finished
    //Wither #finished
    //Poison Touch (1-2) #finished
    //Absorbing (1-3) #finished
    //Nightvision #WIP TODO: Check if Item breaks or is rightclicked put on

    public Enchantment(PluginController controller) {
        this.controller = controller;
    }

    /*@EventHandler
    public void onEnchant(EnchantItemEvent enchantEvent) {
        if (func.isWeapon(enchantEvent.getItem().getType()) || func.isBook(enchantEvent.getItem().getType())) {
            int randWither = (int) (Math.random() * 1000);
            int randPoison = (int) (Math.random() * 1000);
            int randXPBoost = (int) (Math.random() * 1000);
            if (randWither <= 79) { //8%
                func.enchantItem("Wither", 1, enchantEvent.getItem());
            }
            if (randPoison <= 59) { //6%
                func.enchantItem("Poison Touch", 2, enchantEvent.getItem());
            } else if (randPoison >= 60 && randPoison <= 169) { //11%
                func.enchantItem("Poison Touch", 1, enchantEvent.getItem());
            }
            if (randXPBoost <= 49) { //5%
                func.enchantItem("XP-Boost", 5, enchantEvent.getItem());
            } else if (randXPBoost >= 50 && randXPBoost <= 149) { //10%
                func.enchantItem("XP-Boost", 4, enchantEvent.getItem());
            } else if (randXPBoost >= 150 && randXPBoost <= 299) { //15%
                func.enchantItem("XP-Boost", 3, enchantEvent.getItem());
            } else if (randXPBoost >= 300 && randXPBoost <= 599) { //20%
                func.enchantItem("XP-Boost", 2, enchantEvent.getItem());
            } else if (randXPBoost >= 600 && randXPBoost <= 849) { //25%
                func.enchantItem("XP-Boost", 1, enchantEvent.getItem());
            }
        }
        if (func.isTool(enchantEvent.getItem().getType()) || func.isBook(enchantEvent.getItem().getType())) {
            int randFireT = (int) (Math.random() * 1000);
            if (randFireT <= 279) {
                int randFortune = (int) (Math.random() * 1000);
                if (enchantEvent.getEnchantsToAdd().containsKey(org.bukkit.enchantments.Enchantment.LOOT_BONUS_BLOCKS)) {
                    if (randFortune <= 499) {
                        enchantEvent.getEnchantsToAdd().remove(org.bukkit.enchantments.Enchantment.LOOT_BONUS_BLOCKS);
                        func.enchantItem("Fire Touch", 1, enchantEvent.getItem());
                    }
                } else if (enchantEvent.getEnchantsToAdd().containsKey(org.bukkit.enchantments.Enchantment.SILK_TOUCH)) {
                    if (randFortune <= 499) {
                        enchantEvent.getEnchantsToAdd().remove(org.bukkit.enchantments.Enchantment.SILK_TOUCH);
                        func.enchantItem("Fire Touch", 1, enchantEvent.getItem());
                    }
                } else {
                    func.enchantItem("Fire Touch", 1, enchantEvent.getItem());
                }
            }
        }
        if (enchantEvent.getItem().getType().name().endsWith("HELMET") || func.isBook(enchantEvent.getItem().getType())) {
            int randNVision = (int) (Math.random() * 1000);
            if (randNVision <= 129) { //13%
                func.enchantItem("Night Vision", 1, enchantEvent.getItem());
            }
        }
        /*if (func.isArmor(enchantEvent.getItem().getType()) || func.isBook(enchantEvent.getItem().getType())) {
            int randAbsobtion = (int) (Math.random() * 1000);
            if (randAbsobtion <= 29) { //3%
                func.enchantItem("Absorbing", 3, enchantEvent.getItem());
            } else if (randAbsobtion >= 30 && randAbsobtion <= 79) { //5%
                func.enchantItem("Absorbing", 2, enchantEvent.getItem());
            } else if (randAbsobtion >= 80 && randAbsobtion <= 159) { //8%
                func.enchantItem("Absorbing", 1, enchantEvent.getItem());
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
        if (!func.hasCustomEnchant(damager.getEquipment().getItemInMainHand().getItemMeta().getLore())) return;
        for (String str : damager.getEquipment().getItemInMainHand().getItemMeta().getLore()) {
            if (!func.isCustomEnchant(str)) continue;
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
        if (!func.hasCustomEnchant(player.getEquipment().getItemInMainHand().getItemMeta().getLore())) return;
        boolean b = false;
        for (String str : player.getEquipment().getItemInMainHand().getItemMeta().getLore()) {
            if (!func.isCustomEnchant(str)) continue;
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
        if (!func.hasCustomEnchant(killer.getEquipment().getItemInMainHand().getItemMeta().getLore())) return;
        for (String str : killer.getEquipment().getItemInMainHand().getItemMeta().getLore()) {
            if (!func.isCustomEnchant(str)) continue;
            String[] words = str.trim().split("\\s+");
            int level = words.length == 3 ? func.parseRomanNumber(words[2]) : func.parseRomanNumber(words[1]);
            if (str.startsWith(ChatColor.GRAY + "XP-Boost ")) deathEvent.setDroppedExp(deathEvent.getDroppedExp()*level);
        }
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
    }*/

    @EventHandler
    public void onEntityShootBow (EntityShootBowEvent entityShootBowEvent) {
        if (!(entityShootBowEvent.getEntity() instanceof Player)) return;
        returnItem((Player) entityShootBowEvent.getEntity());
    }

    @EventHandler
    public void onPlayerItemHeld (PlayerItemHeldEvent playerItemHeldEvent) {
        Player p = playerItemHeldEvent.getPlayer();
        ItemStack prevItem = p.getInventory().getItem(playerItemHeldEvent.getPreviousSlot());
        if(!storedItem.containsKey(p) || prevItem.isSimilar(null)) return;
        if (prevItem.getItemMeta().hasEnchant(org.bukkit.enchantments.Enchantment.ARROW_INFINITE)) {
            returnItem(p);
        }
    }

    @EventHandler
    public void onInventoryOpen (InventoryOpenEvent event) {
        Player p = (Player) event.getPlayer();
        if (!storedItem.containsKey(p)) return;
        returnItem(p);
    }

    @EventHandler
    public void onInventoryClick (InventoryClickEvent event) {
        if (!(event.getClickedInventory() instanceof PlayerInventory)) return;
        Player p = (Player) event.getWhoClicked();
        ItemStack item9 = event.getClickedInventory().getItem(9);
        if (item9 == null || !item9.isSimilar(new ItemStack(Material.ARROW))) return;
        if (!storedItem.containsKey(p)) return;
        returnItem(p);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent playerInteractEvent) {
        Player p = playerInteractEvent.getPlayer();
        if (!(playerInteractEvent.getAction().equals(Action.RIGHT_CLICK_AIR) || playerInteractEvent.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
        if(storedItem.containsKey(p)) return;
        if(!(p.getItemInHand().getType().equals(Material.BOW)) && p.getItemInHand().containsEnchantment(org.bukkit.enchantments.Enchantment.ARROW_INFINITE)) return;
        ItemStack item = p.getInventory().getItem(9);
        storedItem.put(p, item);
        p.getInventory().setItem(9, new ItemStack(Material.ARROW, 64));
    }

    private void returnItem(Player player) {
        if (storedItem.containsKey(player.getPlayer())) {
            player.getInventory().setItem(9, storedItem.get(player));
            player.updateInventory();
            storedItem.remove(player);
        }
    }
}
