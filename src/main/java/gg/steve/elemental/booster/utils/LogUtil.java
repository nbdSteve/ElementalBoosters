package gg.steve.elemental.booster.utils;

import gg.steve.elemental.booster.ElementalBoosters;

public class LogUtil {

    public static void info(String message) {
        ElementalBoosters.get().getLogger().info(message);
    }

    public static void warning(String message) {
        ElementalBoosters.get().getLogger().warning(message);
    }
}
