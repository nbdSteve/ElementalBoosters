package gg.steve.elemental.booster.api;

import gg.steve.elemental.booster.ElementalBoosters;
import gg.steve.elemental.booster.core.BoosterType;
import gg.steve.elemental.booster.core.PlayerBoosterManager;
import org.bukkit.entity.Player;

public class BoostersApi {

    public static ElementalBoosters getInstance() {
        return ElementalBoosters.get();
    }

    public static boolean noBoostersActive(Player player) {
        return PlayerBoosterManager.noBoosterActive(player.getUniqueId());
    }

    public static boolean isBoosterActive(Player player, BoosterType type) {
        return PlayerBoosterManager.isBoosterActive(player.getUniqueId(), type);
    }

    public static double getBoostAmount(Player player, BoosterType type) {
        if (!PlayerBoosterManager.isBoosterActive(player.getUniqueId(), type)) return 0;
        return PlayerBoosterManager.getBooster(player.getUniqueId(), type).getBoostAmount();
    }
}
