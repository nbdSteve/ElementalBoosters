package gg.steve.elemental.booster.core;

import gg.steve.elemental.booster.ElementalBoosters;
import gg.steve.elemental.booster.gui.AbstractGui;
import gg.steve.elemental.booster.gui.BoosterGui;
import gg.steve.elemental.booster.managers.Files;
import gg.steve.elemental.booster.utils.LogUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerBoosterManager {
    private static Map<UUID, Map<BoosterType, Booster>> activeBoosters;
    private static Map<UUID, BoosterGui> playerGuis;

    public static void initialise() {
        activeBoosters = new HashMap<>();
        playerGuis = new HashMap<>();
        for (String playerId : Files.DATA.get().getKeys(false)) {
            for (String booster : Files.DATA.get().getConfigurationSection(playerId).getKeys(false)) {
                addBoosterToPlayer(UUID.fromString(playerId), new Booster(Files.DATA.get().getInt(playerId + "." + booster + ".booster-id"),
                        Files.DATA.get().getInt(playerId + "." + booster + ".duration"), UUID.fromString(playerId)));
            }
        }
        LogUtil.info("Successfully loaded all existing boosters from disk.");
        Bukkit.getScheduler().runTaskTimerAsynchronously(ElementalBoosters.get(), () -> {
            if (activeBoosters.isEmpty()) return;
            for (UUID playerId : activeBoosters.keySet()) {
                if (activeBoosters.get(playerId).isEmpty()) continue;
                List<BoosterType> completedBoosters = new ArrayList<>();
                for (Booster booster : activeBoosters.get(playerId).values()) {
                    booster.decrementDuration();
                    if (booster.isComplete()) {
                        booster.onComplete();
                        completedBoosters.add(booster.getType());
                    }
                }
                for (BoosterType type : completedBoosters) {
                    removeBoosterFromPlayer(playerId, type);
                }
            }
            if (AbstractGui.openInventories.isEmpty()) return;
            for (UUID playerId : AbstractGui.openInventories.keySet()) {
                playerGuis.get(playerId).refreshBoosterItems();
            }
        }, 0L, 20L);
    }

    public static boolean isBoosterActive(UUID playerId, BoosterType type) {
        if (activeBoosters.get(playerId) == null) return false;
        return activeBoosters.get(playerId).containsKey(type);
    }

    public static boolean noBoosterActive(UUID playerId) {
        if (activeBoosters.get(playerId) == null) return true;
        return activeBoosters.get(playerId).isEmpty();
    }

    public static Booster getBooster(UUID playerId, BoosterType type) {
        return activeBoosters.get(playerId).get(type);
    }

    public static void addBoosterToPlayer(UUID playerId, Booster booster) {
        if (!activeBoosters.containsKey(playerId)) activeBoosters.put(playerId, new HashMap<>());
        activeBoosters.get(playerId).put(booster.getType(), booster);
    }

    public static void removeBoosterFromPlayer(UUID playerId, BoosterType type) {
        activeBoosters.get(playerId).remove(type);
    }

    public static void openBoosterGui(Player player) {
        if (!playerGuis.containsKey(player.getUniqueId())) {
            playerGuis.put(player.getUniqueId(), new BoosterGui(Files.CONFIG.get().getConfigurationSection("gui"), player));
        } else {
            playerGuis.get(player.getUniqueId()).refresh();
        }
        playerGuis.get(player.getUniqueId()).open(player);
    }

    public static void removeBoosterGui(UUID playerId) {
        playerGuis.remove(playerId);
    }

    public static void saveBoosterData() {
        for (String entry : Files.DATA.get().getKeys(false)) {
            Files.DATA.get().set(entry, null);
        }
        Files.DATA.save();
        for (UUID playerId : activeBoosters.keySet()) {
            if (activeBoosters.get(playerId).isEmpty()) continue;
            Files.DATA.get().createSection(String.valueOf(playerId));
            for (Booster booster : activeBoosters.get(playerId).values()) {
                Files.DATA.get().set(playerId + "." + booster.getType().name() + ".booster-id", booster.getBoosterId());
                Files.DATA.get().set(playerId + "." + booster.getType().name() + ".duration", booster.getDuration());
            }
        }
        Files.DATA.save();
    }
}
