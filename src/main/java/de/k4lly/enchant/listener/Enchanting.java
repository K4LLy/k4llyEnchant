package de.k4lly.enchant.listener;

import de.k4lly.enchant.controller.PluginController;
import de.k4lly.enchant.objects.Functions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class Enchanting implements Listener {

    private PluginController controller;
    private Functions func = new Functions();

    public static String XP_BOOST = "XP-Boost";
    public static String FIRE_TOUCH = "Fire Touch";
    public static String WITHER = "Wither";
    public static String POISON_TOUCH = "Poison Touch";
    public static String NIGHT_VISION = "Night Vision";

    //XP Boost (1-5) #finished
    //Fire Touch #WIP TODO: Add Exp drops for smelted items
    //Wither #finished
    //Poison Touch (1-2) #finished
    //Nightvision #WIP TODO: Check if Item breaks or is right clicked put on

    public Enchanting(PluginController controller) {
        this.controller = controller;
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent enchantEvent) {
        if (!controller.getMain().getConfig().getBoolean("enableCustomEnchantment")) return;
        if (func.isWeapon(enchantEvent.getItem().getType()) || func.isBook(enchantEvent.getItem().getType())) {
            int randWither = (int) (Math.random() * 1000);
            int randPoison = (int) (Math.random() * 1000);
            int randXPBoost = (int) (Math.random() * 1000);
            if (controller.getMain().getConfig().getBoolean("enableWither")) {
                if (randWither <= 79) { //8%
                    func.enchantItem("Wither", 1, enchantEvent.getItem());
                }
            }
            if (controller.getMain().getConfig().getBoolean("enablePoisonTouch")) {
                if (randPoison <= 59) { //6%
                    func.enchantItem("Poison Touch", 2, enchantEvent.getItem());
                } else if (randPoison >= 60 && randPoison <= 169) { //11%
                    func.enchantItem("Poison Touch", 1, enchantEvent.getItem());
                }
            }
            if (controller.getMain().getConfig().getBoolean("enableXPBoost")) {
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
        }
        if ((func.isTool(enchantEvent.getItem().getType()) && !func.isShears(enchantEvent.getItem().getType()) && !func.isFishingRod(enchantEvent.getItem().getType())
            && !func.isFlintSteel(enchantEvent.getItem().getType()) && !func.isElytra(enchantEvent.getItem().getType())  && !func.isCarrotStick(enchantEvent.getItem().getType())) || func.isBook(enchantEvent.getItem().getType())) {
            int randFireT = (int) (Math.random() * 1000);
            if (controller.getMain().getConfig().getBoolean("enableFireTouch")) {
                if (randFireT <= 279) { //28%
                    if (!enchantEvent.getEnchantsToAdd().containsKey(org.bukkit.enchantments.Enchantment.LOOT_BONUS_BLOCKS) && !enchantEvent.getEnchantsToAdd().containsKey(org.bukkit.enchantments.Enchantment.SILK_TOUCH)) {
                        func.enchantItem("Fire Touch", 1, enchantEvent.getItem());
                    }
                }
            }
        }
        if (func.isHelmet(enchantEvent.getItem().getType()) || func.isBook(enchantEvent.getItem().getType())) {
            int randNVision = (int) (Math.random() * 1000);
            if (randNVision <= 129) { //13%
                func.enchantItem("Night Vision", 1, enchantEvent.getItem());
            }
        }
    }
}
