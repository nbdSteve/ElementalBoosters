package gg.steve.elemental.booster.cmd.sub;

import gg.steve.elemental.booster.core.Booster;
import gg.steve.elemental.booster.core.BoosterType;
import gg.steve.elemental.booster.managers.Files;
import gg.steve.elemental.booster.message.CommandDebug;
import gg.steve.elemental.booster.message.MessageType;
import gg.steve.elemental.booster.permission.PermissionNode;
import gg.steve.elemental.booster.utils.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCmd {

    public static void give(CommandSender sender, String[] args) {
        // booster give nbdsteve 1 60
        if (args.length < 4) {
            CommandDebug.INVALID_NUMBER_OF_ARGUMENTS.message(sender);
            return;
        }
        if (!PermissionNode.GIVE.hasPermission(sender)) {
            CommandDebug.INSUFFICIENT_PERMISSION.message(sender, PermissionNode.GIVE.get());
            return;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            CommandDebug.TARGET_NOT_ONLINE.message(sender);
            return;
        }
        try {
            Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            CommandDebug.INVALID_BOOSTER.message(sender);
            return;
        }
        if (Files.BOOSTERS.get().getConfigurationSection(args[2]) == null) {
            CommandDebug.INVALID_BOOSTER.message(sender);
            return;
        }
        try {
            Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            CommandDebug.INVALID_TIME.message(sender);
            return;
        }
        Player player = null;
        if (sender instanceof Player) {
            if (!target.getUniqueId().equals(((Player) sender).getUniqueId())) {
                player = (Player) sender;
            }
        }
        BoosterType type = BoosterType.valueOf(Files.BOOSTERS.get().getString(args[2] + ".type").toUpperCase());
        target.getInventory().addItem(Booster.getBoosterItem(Integer.parseInt(args[2]), Integer.parseInt(args[3])));
        MessageType.GIVE_RECEIVER.message(target, type.name());
        if (!(sender instanceof Player) || player != null) {
            MessageType.GIVE_GIVER.message(sender, target.getName(), type.name());
        }
    }
}
