package de.k4lly.enchant.controller;

import org.bukkit.enchantments.Enchantment;

public class ConfigController {
    private PluginController controller;

    public ConfigController(PluginController controller) {
        this.controller = controller;
        setDefaultConfig();
    }

    public void setDefaultConfig() {
        controller.getMain().getConfig().addDefault("Max-Level", 30);

        controller.getMain().getConfig().addDefault(Enchantment.PROTECTION_ENVIRONMENTAL.getName(), 20);
        controller.getMain().getConfig().addDefault(Enchantment.PROTECTION_FIRE.getName(), 20);
        controller.getMain().getConfig().addDefault(Enchantment.PROTECTION_FALL.getName(), 20);
        controller.getMain().getConfig().addDefault(Enchantment.PROTECTION_EXPLOSIONS.getName(), 20);
        controller.getMain().getConfig().addDefault(Enchantment.PROTECTION_PROJECTILE.getName(), 20);
        controller.getMain().getConfig().addDefault(Enchantment.OXYGEN.getName(), 20);
        controller.getMain().getConfig().addDefault(Enchantment.WATER_WORKER.getName(), 20);
        controller.getMain().getConfig().addDefault(Enchantment.THORNS.getName(), 20);
        controller.getMain().getConfig().addDefault(Enchantment.DEPTH_STRIDER.getName(), 20);
        controller.getMain().getConfig().addDefault(Enchantment.FROST_WALKER.getName(), 20);

        controller.getMain().getConfig().addDefault(Enchantment.DAMAGE_ALL.getName(), 20);
        controller.getMain().getConfig().addDefault(Enchantment.DAMAGE_UNDEAD.getName(), 20);
        controller.getMain().getConfig().addDefault(Enchantment.DAMAGE_ARTHROPODS.getName(), 20);
        controller.getMain().getConfig().addDefault(Enchantment.KNOCKBACK.getName(), 20);
        controller.getMain().getConfig().addDefault(Enchantment.FIRE_ASPECT.getName(), 20);
        controller.getMain().getConfig().addDefault(Enchantment.LOOT_BONUS_MOBS.getName(), 20);

        controller.getMain().getConfig().addDefault(Enchantment.DIG_SPEED.getName(), 20);
        //controller.getMain().getConfig().addDefault(Enchantment.SILK_TOUCH.getName(), 20);
        controller.getMain().getConfig().addDefault(Enchantment.DURABILITY.getName(), 20);
        controller.getMain().getConfig().addDefault(Enchantment.LOOT_BONUS_BLOCKS.getName(), 20);
        //controller.getMain().getConfig().addDefault(Enchantment.MENDING.getName(), 20);

        controller.getMain().getConfig().addDefault(Enchantment.ARROW_DAMAGE.getName(), 20);
        controller.getMain().getConfig().addDefault(Enchantment.ARROW_KNOCKBACK.getName(), 20);
        //controller.getMain().getConfig().addDefault(Enchantment.ARROW_FIRE.getName(), 20);
        //controller.getMain().getConfig().addDefault(Enchantment.ARROW_INFINITE.getName(), 20);

        controller.getMain().getConfig().addDefault(Enchantment.LUCK.getName(), 20);
        controller.getMain().getConfig().addDefault(Enchantment.LURE.getName(), 20);

        controller.getMain().getConfig().addDefault("enableTakeEnchantment", true);

        controller.getMain().getConfig().options().copyDefaults(true);
        controller.getMain().saveDefaultConfig();
        controller.getMain().reloadConfig();

        System.out.print("[k4llyEnchant] Saved Cofiguration.");
    }
}
