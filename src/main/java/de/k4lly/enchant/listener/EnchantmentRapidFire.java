package de.k4lly.enchant.listener;

import de.k4lly.enchant.controller.PluginController;
import de.k4lly.enchant.objects.Functions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class EnchantmentRapidFire implements Listener {
    private PluginController controller;
    private Functions func = new Functions();

    public EnchantmentRapidFire (PluginController controller) {
        this.controller = controller;
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent entityShootBowEvent) {
        if (!(entityShootBowEvent.getEntity() instanceof Player)) return;
        if (!controller.getMain().getConfig().getBoolean("enableRapidFire")) return;
        Player player = (Player) entityShootBowEvent.getEntity();
        player.sendMessage("The Enchantment 'Rapid Fire' will be added soon.");
        //TODO: Add Arrows here
    }
}
