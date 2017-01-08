package de.k4lly.enchant.listener;

import de.k4lly.enchant.controller.PluginController;
import de.k4lly.enchant.objects.Functions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class EnchantmentNightVision implements Listener {
    private PluginController controller;
    private Functions func = new Functions();

    private HashMap<Player, Boolean> hasNightVision;

    public EnchantmentNightVision(PluginController controller) {
        this.controller = controller;
        this.hasNightVision = new HashMap<>();
    }

    @EventHandler
    public void onInventoryClick (InventoryClickEvent clickEvent) {
        if (!controller.getMain().getConfig().getBoolean("enableNightVision")) return;
        if (clickEvent.isCancelled()) return;
        if (clickEvent.getClickedInventory() != null && !(clickEvent.getClickedInventory() instanceof PlayerInventory)) return;
        Player player = (Player) clickEvent.getWhoClicked();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                check(player);
            }
        };
        Bukkit.getScheduler().runTaskLater(controller.getMain(), r, 1L);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent playerInteractEvent) {
        if (!controller.getMain().getConfig().getBoolean("enableNightVision")) return;
        Player player = playerInteractEvent.getPlayer();
        if (!(playerInteractEvent.getAction().equals(Action.RIGHT_CLICK_AIR) || playerInteractEvent.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
        if (!func.isHelmet(player.getItemInHand().getType())) return;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                check(player);
            }
        };
        Bukkit.getScheduler().runTaskLater(controller.getMain(), r, 1L);
    }

    @EventHandler
    public void onPlayerItemBreak (PlayerItemBreakEvent breakEvent) {
        if (!controller.getMain().getConfig().getBoolean("enableNightVision")) return;
        Player player = breakEvent.getPlayer();
        if (!func.isHelmet(breakEvent.getBrokenItem().getType())) return;
        if (!this.hasNightVision.containsKey(player)) return;
        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        this.hasNightVision.remove(player);
    }

    private void check(Player player) {
        if (player.getEquipment().getHelmet() != null) {
            if (player.getEquipment().getHelmet().getItemMeta().hasLore()) {
                if (func.hasCustomEnchant(player.getEquipment().getHelmet().getItemMeta().getLore())) {
                    for (String str : player.getEquipment().getHelmet().getItemMeta().getLore()) {
                        if (!func.isCustomEnchant(str)) continue;
                        if (str.startsWith(ChatColor.GRAY + "Night Vision ")) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 1, false, false));
                            this.hasNightVision.put(player, true);
                            return;
                        }
                    }
                }
            }
        }
        if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION) && this.hasNightVision.containsKey(player) && this.hasNightVision.get(player)) {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            this.hasNightVision.remove(player);
        }
    }
}
