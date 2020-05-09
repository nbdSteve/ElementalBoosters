package gg.steve.elemental.booster.cmd;

import gg.steve.elemental.booster.cmd.sub.GiveCmd;
import gg.steve.elemental.booster.cmd.sub.GuiCmd;
import gg.steve.elemental.booster.cmd.sub.HelpCmd;
import gg.steve.elemental.booster.cmd.sub.ReloadCmd;
import gg.steve.elemental.booster.message.CommandDebug;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BoosterCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("booster")) {
            if (args.length == 0) {
                GuiCmd.gui(sender);
                return true;
            }
            switch (args[0].toLowerCase()) {
                case "h":
                case "help":
                    HelpCmd.help(sender);
                    break;
                case "r":
                case "reload":
                    ReloadCmd.reload(sender);
                    break;
                case "gui":
                case "m":
                case "menu":
                    GuiCmd.gui(sender);
                    break;
                case "give":
                case "g":
                    GiveCmd.give(sender, args);
                    break;
                default:
                    CommandDebug.INVALID_COMMAND.message(sender);
                    break;
            }
        }
        return true;
    }
}
