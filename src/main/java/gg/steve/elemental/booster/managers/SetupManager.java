package gg.steve.elemental.booster.managers;

import gg.steve.elemental.booster.ElementalBoosters;
import gg.steve.elemental.booster.cmd.BoosterCmd;
import gg.steve.elemental.booster.gui.GuiClickListener;
import gg.steve.elemental.booster.listener.PlayerInteractListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
 * Class that handles setting up the plugin on start
 */
public class SetupManager {

    private SetupManager() throws IllegalAccessException {
        throw new IllegalAccessException("Manager class cannot be instantiated.");
    }

    /**
     * Loads the files into the file manager
     *
     * @param fileManager FileManager, the plugins file manager
     */
    public static void setupFiles(FileManager fileManager) {
        // general files
        for (Files file : Files.values()) {
            file.load(fileManager);
        }
    }

    public static void registerCommands(ElementalBoosters instance) {
        instance.getCommand("booster").setExecutor(new BoosterCmd());
    }

    /**
     * Register all of the events for the plugin
     *
     * @param instance Plugin, the main plugin instance
     */
    public static void registerEvents(Plugin instance) {
        PluginManager pm = instance.getServer().getPluginManager();
        pm.registerEvents(new PlayerInteractListener(), instance);
        pm.registerEvents(new GuiClickListener(), instance);
    }
}
