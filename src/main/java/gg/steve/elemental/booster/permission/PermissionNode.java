package gg.steve.elemental.booster.permission;

import gg.steve.elemental.booster.managers.Files;
import org.bukkit.command.CommandSender;

public enum PermissionNode {
    GUI("command.gui"),
    RELOAD("command.reload"),
    GIVE("command.give"),
    HELP("command.help");

    private String path;

    PermissionNode(String path) {
        this.path = path;
    }

    public String get() {
        return Files.PERMISSIONS.get().getString(this.path);
    }

    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(get());
    }
}
