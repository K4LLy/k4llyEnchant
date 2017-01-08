package de.k4lly.enchant.listener;

import de.k4lly.enchant.controller.PluginController;
import de.k4lly.enchant.objects.Functions;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EnchantmentXPBoost implements Listener {
    private PluginController controller;
    private Functions func = new Functions();

    public EnchantmentXPBoost(PluginController controller) {
        this.controller = controller;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent deathEvent) throws Exception {
        if (!(deathEvent.getEntity().getKiller() instanceof Player)) return;
        if (deathEvent.getEntity() instanceof Player) return;
        if (!controller.getMain().getConfig().getBoolean("enableXPBoost")) return;
        Player killer = deathEvent.getEntity().getKiller();
        if (killer.getEquipment().getItemInMainHand() == null || !func.isWeapon(killer.getEquipment().getItemInMainHand().getType()) || !killer.getEquipment().getItemInMainHand().hasItemMeta()) return;
        if (!killer.getEquipment().getItemInMainHand().getItemMeta().hasLore()) return;
        if (!func.hasCustomEnchant(killer.getEquipment().getItemInMainHand().getItemMeta().getLore())) return;
        for (String str : killer.getEquipment().getItemInMainHand().getItemMeta().getLore()) {
            if (!func.isCustomEnchant(str)) continue;
            String[] words = str.trim().split("\\s+");
            int level = words.length == 3 ? func.parseRomanNumber(words[2]) : func.parseRomanNumber(words[1]);
            if (str.startsWith(ChatColor.GRAY + "XP-Boost ")) deathEvent.setDroppedExp(deathEvent.getDroppedExp()*(level+1));
        }
    }
}
