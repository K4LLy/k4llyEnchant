package de.k4lly.enchant.listener;

import de.k4lly.enchant.controller.PluginController;
import de.k4lly.enchant.objects.AnvilItems;
import de.k4lly.enchant.objects.Functions;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.ArrayList;

public class AnvilBooks implements Listener {

    private PluginController controller;
    private ArrayList<Player> players = new ArrayList<>();
    private static int SLOT_0 = 0;
    private static int SLOT_1 = 1;
    private static int SLOT_2 = 2;

    public AnvilBooks(PluginController controller) {
        this.controller = controller;
    }

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent prepareAnvilEvent) {
        ItemStack item0 = prepareAnvilEvent.getInventory().getItem(SLOT_0);
        ItemStack item1 = prepareAnvilEvent.getInventory().getItem(SLOT_1);
        //if
        if (item0 != null && item1 != null) {
            prepareAnvilEvent.setResult(item2(item0, item1));
            Player player = (Player) prepareAnvilEvent.getView().getPlayer();
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 3.0F, 1.0F);
        }
    }

    public ItemStack item2(ItemStack item0, ItemStack item1) {
        AnvilItems anvilItems = new AnvilItems(controller, item0, item1);
        ItemStack item2 = item0.clone();
        EnchantmentStorageMeta meta2 = (EnchantmentStorageMeta) item2.getItemMeta();
        for (int i = 0; i < anvilItems.getItemResultEnchantmentSize(); i++) {
            meta2.addStoredEnchant(anvilItems.getItemResultEnchantment(i), anvilItems.getItemResultELevel(i), true);
            item2.setItemMeta(meta2);
        }
        anvilItems.clearAllArray();
        return item2;
    }
}
