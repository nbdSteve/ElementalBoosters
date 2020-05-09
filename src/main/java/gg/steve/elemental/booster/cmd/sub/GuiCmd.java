package gg.steve.elemental.booster.cmd.sub;

import gg.steve.elemental.booster.core.PlayerBoosterManager;
import gg.steve.elemental.booster.message.CommandDebug;
import gg.steve.elemental.booster.permission.PermissionNode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuiCmd {

    public static void gui(CommandSender sender) {
        if (!(sender instanceof Player)) {
            CommandDebug.ONLY_PLAYERS_ACCESSIBLE.message(sender);
            return;
        }
        if (!PermissionNode.GUI.hasPermission(sender)) {
            CommandDebug.INSUFFICIENT_PERMISSION.message(sender, PermissionNode.GUI.get());
            return;
        }
        Player player = (Player) sender;
        PlayerBoosterManager.openBoosterGui((Player) sender);
    }
}
