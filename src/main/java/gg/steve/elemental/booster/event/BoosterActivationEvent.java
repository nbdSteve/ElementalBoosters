package gg.steve.elemental.booster.event;

import gg.steve.elemental.booster.core.Booster;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BoosterActivationEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private Booster booster;
    private boolean cancel;

    public BoosterActivationEvent(Player player, Booster booster) {
        this.player = player;
        this.booster = booster;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public Player getPlayer() {
        return player;
    }

    public Booster getBooster() {
        return booster;
    }
}
