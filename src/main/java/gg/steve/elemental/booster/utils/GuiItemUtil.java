package gg.steve.elemental.booster.utils;

import gg.steve.elemental.booster.core.Booster;
import gg.steve.elemental.booster.core.BoosterType;
import gg.steve.elemental.booster.core.PlayerBoosterManager;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class GuiItemUtil {

    public static ItemStack createItem(ConfigurationSection section, String entry, UUID playerId) {
        boolean boosterItem = section.getBoolean(entry + ".booster-item");
        ItemBuilderUtil builder;
        if (boosterItem && PlayerBoosterManager.isBoosterActive(playerId, BoosterType.valueOf(section.getString(entry + ".booster-type").toUpperCase()))) {
            Booster booster = PlayerBoosterManager.getBooster(playerId, BoosterType.valueOf(section.getString(entry + ".booster-type").toUpperCase()));
            builder = new ItemBuilderUtil(Booster.getBoosterItem(booster.getBoosterId(), booster.getDuration()));
            builder.addGuiNBT();
            return builder.getItem();
        } else if (boosterItem && !PlayerBoosterManager.noBoosterActive(playerId)) {
            return new ItemStack(Material.AIR);
        } else if (boosterItem) {
            return new ItemStack(Material.BARRIER);
        }
        if (section.getString(entry + ".material").startsWith("hdb")) {
            String[] parts = section.getString(entry + ".material").split("-");
            builder = new ItemBuilderUtil(new HeadDatabaseAPI().getItemHead(parts[1]));
        } else {
            builder = new ItemBuilderUtil(section.getString(entry + ".material"), section.getString(entry + ".data"));
        }
        builder.addName(section.getString(entry + ".name"), new TimeUtil(1));
        builder.addLore(section.getStringList(entry + ".lore"));
        builder.addEnchantments(section.getStringList(entry + ".enchantments"));
        builder.addItemFlags(section.getStringList(entry + ".item-flags"));
        builder.addGuiNBT();
        return builder.getItem();
    }
}
