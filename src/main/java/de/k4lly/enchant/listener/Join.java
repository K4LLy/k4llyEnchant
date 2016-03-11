package de.k4lly.enchant.listener;

import de.k4lly.enchant.controller.PluginController;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {

    private PluginController controller;

    public Join(PluginController controller) {
        this.controller=controller;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent playerJoinEvent) {
        //System.out.print("[k4llyEnchant] Update checker will bei implemented soon.");
    }
}
