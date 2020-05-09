package gg.steve.elemental.booster;

import gg.steve.elemental.booster.core.PlayerBoosterManager;
import gg.steve.elemental.booster.managers.FileManager;
import gg.steve.elemental.booster.managers.SetupManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;

public final class ElementalBoosters extends JavaPlugin {
    private static ElementalBoosters instance;
    private static DecimalFormat numberFormat = new DecimalFormat("#,###.##");

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        SetupManager.setupFiles(new FileManager(instance));
        SetupManager.registerCommands(instance);
        SetupManager.registerEvents(instance);
        PlayerBoosterManager.initialise();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        PlayerBoosterManager.saveBoosterData();
    }

    public static ElementalBoosters get() {
        return instance;
    }

    public static DecimalFormat getNumberFormat() {
        return numberFormat;
    }
}
