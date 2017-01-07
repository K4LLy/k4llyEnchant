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
    public static String RAPID_FIRE = "Rapid Fire";

    //XP Boost (1-5) #finished
    //Fire Touch #finished
    //Wither #finished
    //Poison Touch (1-2) #finished
    //Nightvision #finished

    public Enchanting(PluginController controller) {
        this.controller = controller;
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent enchantEvent) {
        if (!controller.getMain().getConfig().getBoolean("enableCustomEnchantment")) return;
        if ((func.isWeapon(enchantEvent.getItem().getType()) && !func.isShield(enchantEvent.getItem().getType())) || func.isBook(enchantEvent.getItem().getType())) {
            int randWither = (int) (Math.random() * 1000);
            int randPoison = (int) (Math.random() * 1000);
            int randXPBoost = (int) (Math.random() * 1000);
            if (controller.getMain().getConfig().getBoolean("enableWither")) {
                if (randWither <= 80) { //8%
                    func.enchantItem(this.WITHER, 1, enchantEvent.getItem());
                }
            }
            if (controller.getMain().getConfig().getBoolean("enablePoisonTouch")) {
                if (randPoison <= 60) { //6%
                    func.enchantItem(this.POISON_TOUCH, 2, enchantEvent.getItem());
                } else if (randPoison > 60 && randPoison <= 170) { //11%
                    func.enchantItem(this.POISON_TOUCH, 1, enchantEvent.getItem());
                }
            }
            if (controller.getMain().getConfig().getBoolean("enableXPBoost")) {
                if (randXPBoost <= 50) { //5%
                    func.enchantItem(this.XP_BOOST, 5, enchantEvent.getItem());
                } else if (randXPBoost > 50 && randXPBoost <= 150) { //10%
                    func.enchantItem(this.XP_BOOST, 4, enchantEvent.getItem());
                } else if (randXPBoost > 150 && randXPBoost <= 300) { //15%
                    func.enchantItem(this.XP_BOOST, 3, enchantEvent.getItem());
                } else if (randXPBoost > 300 && randXPBoost <= 600) { //20%
                    func.enchantItem(this.XP_BOOST, 2, enchantEvent.getItem());
                } else if (randXPBoost > 600 && randXPBoost <= 850) { //25%
                    func.enchantItem(this.XP_BOOST, 1, enchantEvent.getItem());
                }
            }
        }
        if ((func.isTool(enchantEvent.getItem().getType()) && !func.isShears(enchantEvent.getItem().getType()) && !func.isFishingRod(enchantEvent.getItem().getType())
            && !func.isFlintSteel(enchantEvent.getItem().getType()) && !func.isElytra(enchantEvent.getItem().getType())  && !func.isCarrotStick(enchantEvent.getItem().getType())) || func.isBook(enchantEvent.getItem().getType())) {
            int randFireT = (int) (Math.random() * 1000);
            if (controller.getMain().getConfig().getBoolean("enableFireTouch")) {
                if (randFireT <= 280) { //28%
                    if (!enchantEvent.getEnchantsToAdd().containsKey(org.bukkit.enchantments.Enchantment.LOOT_BONUS_BLOCKS) && !enchantEvent.getEnchantsToAdd().containsKey(org.bukkit.enchantments.Enchantment.SILK_TOUCH)) {
                        func.enchantItem(this.FIRE_TOUCH, 1, enchantEvent.getItem());
                    }
                }
            }
        }
        if (func.isHelmet(enchantEvent.getItem().getType()) || func.isBook(enchantEvent.getItem().getType())) {
            int randNVision = (int) (Math.random() * 1000);
            if (controller.getMain().getConfig().getBoolean("enableNightVision")) {
                if (randNVision <= 130) { //13%
                    func.enchantItem(this.NIGHT_VISION, 1, enchantEvent.getItem());
                }
            }
        }
        if (func.isBow(enchantEvent.getItem().getType()) || func.isBook(enchantEvent.getItem().getType())) {
            int randRFire = (int) (Math.random() * 1000);
            if (controller.getMain().getConfig().getBoolean("enableRapidFire")) {
                if (randRFire <= 5) { //0,5%
                    func.enchantItem(this.RAPID_FIRE, 3, enchantEvent.getItem());
                } else if (randRFire > 5 && randRFire <= 15) { //1%
                    func.enchantItem(this.RAPID_FIRE, 2, enchantEvent.getItem());
                } else if (randRFire > 15 && randRFire <= 35) { //2%
                    func.enchantItem(this.RAPID_FIRE, 1, enchantEvent.getItem());
                }
            }
        }
    }
}
