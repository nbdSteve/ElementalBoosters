package gg.steve.elemental.booster.cmd.sub;

import gg.steve.elemental.booster.message.CommandDebug;
import gg.steve.elemental.booster.message.MessageType;
import gg.steve.elemental.booster.permission.PermissionNode;
import org.bukkit.command.CommandSender;

public class HelpCmd {

    public static void help(CommandSender sender) {
        if (!PermissionNode.HELP.hasPermission(sender)) {
            CommandDebug.INSUFFICIENT_PERMISSION.message(sender, PermissionNode.HELP.get());
            return;
        }
        MessageType.HELP.message(sender);
    }
}
