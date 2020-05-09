package gg.steve.elemental.booster.gui;

import gg.steve.elemental.booster.core.PlayerBoosterManager;
import gg.steve.elemental.booster.utils.GuiItemUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BoosterGui extends AbstractGui {
    private ConfigurationSection section;
    private Player player;

    /**
     * Constructor the create a new Gui
     *
     * @param section
     */
    public BoosterGui(ConfigurationSection section, Player player) {
        super(section, section.getString("type"), section.getInt("size"), player);
        this.section = section;
        this.player = player;
        refresh();
    }

    public void refresh() {
        if (PlayerBoosterManager.noBoosterActive(player.getUniqueId())) {
            for (int i = 12; i < 17; i++) {
                setItemInSlot(i, new ItemStack(Material.BARRIER), player1 -> {
                });
            }
        } else {
            for (int i = 12; i < 17; i++) {
                setItemInSlot(i, new ItemStack(Material.AIR), player1 -> {
                });
            }
        }
        for (String entry : section.getKeys(false)) {
            try {
                Integer.parseInt(entry);
            } catch (Exception e) {
                continue;
            }
            setItemInSlot(section.getInt(entry + ".slot"), GuiItemUtil.createItem(section, entry, player.getUniqueId()), player1 -> {
                switch (section.getString(entry + ".action")) {
                    case "close":
                        player1.closeInventory();
                        break;
                }
            });
        }
    }

    public void refreshBoosterItems() {
        if (PlayerBoosterManager.noBoosterActive(player.getUniqueId())){
            for (int i = 12; i < 17; i++) {
                setItemInSlot(i, new ItemStack(Material.BARRIER), player1 -> {
                });
            }
            return;
        } else {
            for (int i = 12; i < 14; i++) {
                setItemInSlot(i, GuiItemUtil.createItem(section, String.valueOf(i), player.getUniqueId()), player1 -> {
                });
            }
        }
    }
}
