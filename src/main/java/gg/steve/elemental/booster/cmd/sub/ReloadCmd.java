package gg.steve.elemental.booster.cmd.sub;

import gg.steve.elemental.booster.ElementalBoosters;
import gg.steve.elemental.booster.managers.Files;
import gg.steve.elemental.booster.message.CommandDebug;
import gg.steve.elemental.booster.message.MessageType;
import gg.steve.elemental.booster.permission.PermissionNode;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ReloadCmd {

    public static void reload(CommandSender sender) {
        if (!PermissionNode.RELOAD.hasPermission(sender)) {
            CommandDebug.INSUFFICIENT_PERMISSION.message(sender, PermissionNode.RELOAD.get());
            return;
        }
        Files.reload();
        Bukkit.getPluginManager().disablePlugin(ElementalBoosters.get());
        Bukkit.getPluginManager().enablePlugin(ElementalBoosters.get());
        MessageType.RELOAD.message(sender);
    }
}
