package gg.steve.elemental.booster.listener;

import gg.steve.elemental.booster.core.Booster;
import gg.steve.elemental.booster.core.PlayerBoosterManager;
import gg.steve.elemental.booster.event.BoosterActivationEvent;
import gg.steve.elemental.booster.message.MessageType;
import gg.steve.elemental.booster.nbt.NBTItem;
import gg.steve.elemental.booster.utils.LogUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void playerClick(PlayerInteractEvent event) {
        if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)))
            return;
        if (event.getItem() == null || event.getItem().getType().equals(Material.AIR)) return;
        NBTItem item = new NBTItem(event.getItem());
        if (!item.getBoolean("booster.item")) return;
        event.setCancelled(true);
        UUID playerId = event.getPlayer().getUniqueId();
        Booster booster = new Booster(item.getInteger("booster.id"), item.getInteger("booster.duration"), playerId);
        if (PlayerBoosterManager.isBoosterActive(playerId, booster.getType())) {
            MessageType.BOOSTER_ACTIVE.message(event.getPlayer(), booster.getType().name());
            return;
        }
        Bukkit.getPluginManager().callEvent(new BoosterActivationEvent(event.getPlayer(), booster));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void boosterActivate(BoosterActivationEvent event) {
        if (event.isCancelled()) return;
        event.getPlayer().setItemInHand(new ItemStack(Material.AIR));
        event.getBooster().onActivation(event.getPlayer());
        PlayerBoosterManager.addBoosterToPlayer(event.getPlayer().getUniqueId(), event.getBooster());
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        PlayerBoosterManager.removeBoosterGui(event.getPlayer().getUniqueId());
    }
}
