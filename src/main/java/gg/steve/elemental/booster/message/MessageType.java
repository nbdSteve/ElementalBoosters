package gg.steve.elemental.booster.message;

import gg.steve.elemental.booster.managers.Files;
import gg.steve.elemental.booster.utils.ColorUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public enum MessageType {
    RELOAD("reload"),
    HELP("help"),
    GIVE_GIVER("give-giver", "{player}", "{booster-type}"),
    GIVE_RECEIVER("give-receiver", "{booster-type}"),
    BOOSTER_ACTIVE("booster-active", "{booster-type}"),
    BOOSTER_EXPIRE("booster-expire", "{booster-type}"),
    SELL_BOOSTER_ACTIVATION("sell-booster-activation", "{boost}", "{days}", "{hours}", "{minutes}", "{seconds}"),
    TOKEN_BOOSTER_ACTIVATION("token-booster-activation", "{boost}", "{days}", "{hours}", "{minutes}", "{seconds}");

    private String path;
    private List<String> placeholders;

    MessageType(String path, String... placeholders) {
        this.path = path;
        this.placeholders = Arrays.asList(placeholders);
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void message(Player receiver, String... replacements) {
        List<String> data = Arrays.asList(replacements);
        for (String line : Files.MESSAGES.get().getStringList(this.path)) {
            for (int i = 0; i < this.placeholders.size(); i++) {
                line = line.replace(this.placeholders.get(i), data.get(i));
            }
            receiver.sendMessage(ColorUtil.colorize(line));
        }
    }

    public void message(CommandSender receiver, String... replacements) {
        List<String> data = Arrays.asList(replacements);
        for (String line : Files.MESSAGES.get().getStringList(this.path)) {
            for (int i = 0; i < this.placeholders.size(); i++) {
                line = line.replace(this.placeholders.get(i), data.get(i));
            }
            receiver.sendMessage(ColorUtil.colorize(line));
        }
    }

    public static void doMessage(Player receiver, List<String> lines) {
        for (String line : lines) {
            receiver.sendMessage(ColorUtil.colorize(line));
        }
    }
}