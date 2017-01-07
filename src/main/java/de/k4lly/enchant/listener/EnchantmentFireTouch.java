package de.k4lly.enchant.listener;

import de.k4lly.enchant.controller.PluginController;
import de.k4lly.enchant.objects.Functions;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

public class EnchantmentFireTouch implements Listener {
    private PluginController controller;
    private Functions func = new Functions();

    public EnchantmentFireTouch(PluginController controller) {
        this.controller = controller;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent blockBreakEvent) {
        if (blockBreakEvent.isCancelled()) return;
        if (smeltedItem(blockBreakEvent.getBlock().getType()) == null) return;
        Player player = blockBreakEvent.getPlayer();
        if (player.getEquipment().getItemInMainHand() == null || !func.isTool(player.getEquipment().getItemInMainHand().getType()) || !player.getEquipment().getItemInMainHand().hasItemMeta())
            return;
        if (!player.getEquipment().getItemInMainHand().getItemMeta().hasLore() || player.getEquipment().getItemInMainHand().containsEnchantment(org.bukkit.enchantments.Enchantment.SILK_TOUCH) || player.getEquipment().getItemInMainHand().containsEnchantment(org.bukkit.enchantments.Enchantment.LOOT_BONUS_BLOCKS)) return;
        if (!func.hasCustomEnchant(player.getEquipment().getItemInMainHand().getItemMeta().getLore())) return;
        boolean b = false;
        for (String str : player.getEquipment().getItemInMainHand().getItemMeta().getLore()) {
            if (!func.isCustomEnchant(str)) continue;
            if (str.startsWith(ChatColor.GRAY + "Fire Touch ")) b = true;
        }
        if (!b) return;
        Material mat = blockBreakEvent.getBlock().getType();
        blockBreakEvent.getBlock().setType(Material.AIR);
        blockBreakEvent.getBlock().getWorld().dropItemNaturally(blockBreakEvent.getBlock().getLocation().add(0, 0, 0), smeltedItem(mat));
        blockBreakEvent.setExpToDrop(xpToDrop(mat));
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
            case GRASS:
                return new ItemStack(Material.DIRT, 1);
            case REDSTONE_WIRE:
                return new ItemStack(Material.REDSTONE, 1);
            case SPONGE:
                return new ItemStack(Material.SPONGE, 1, (short) 1);
            default:
                return new ItemStack(material);
        }
    }

    private int xpToDrop(Material material) {
        switch (material) {
            case SAND:
            case COBBLESTONE:
            case NETHERRACK:
            case CLAY:
            case LOG:
            case LOG_2:
            case CACTUS:
            case SPONGE:
            case IRON_ORE:
            case GOLD_ORE:
                return 1;
            default:
                return 0;
        }
    }
}
