package de.k4lly.enchant.listener;

import de.k4lly.enchant.controller.PluginController;
import de.k4lly.enchant.objects.Functions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.util.Vector;

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
        Entity arrow = entityShootBowEvent.getProjectile();
        Vector v = arrow.getVelocity();
        Location l = arrow.getLocation();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                spawnArrow(arrow, player, l, v);
            }
        };
        Bukkit.getScheduler().runTaskLater(controller.getMain(), r, 7L);
    }

    private void spawnArrow(Entity arrow, Player player, Location aL, Vector vector) {
        player.getWorld().spawnArrow(aL, vector, (float) 1, (float) 1);
        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1F, 1F);
        //Arrow arrow = event.getProjectile().getWorld().spawnArrow(/*event.getProjectile().getLocation().clone()*/event.getEntity().getLocation().clone().add(0, 2, 0), event.getProjectile().getVelocity(),(float) 2, (float) 2);
        //Arrow arrow2 = event.getProjectile().getWorld().spawnArrow(event.getProjectile().getLocation().clone(), event.getProjectile().getVelocity(),(float) 2, (float) 2);
    }
}
