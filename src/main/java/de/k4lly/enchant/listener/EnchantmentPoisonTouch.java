package de.k4lly.enchant.listener;

import de.k4lly.enchant.controller.PluginController;
import de.k4lly.enchant.objects.Functions;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EnchantmentPoisonTouch implements Listener {
    private PluginController controller;
    private Functions func = new Functions();

    public EnchantmentPoisonTouch(PluginController controller) {
        this.controller = controller;
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
            if (str.startsWith(ChatColor.GRAY + "Poison Touch ")) damaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80 + (level * 80), level>3?3:level, false, true));
        }
    }
}
