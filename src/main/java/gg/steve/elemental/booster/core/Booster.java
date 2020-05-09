package gg.steve.elemental.booster.core;

import gg.steve.elemental.booster.ElementalBoosters;
import gg.steve.elemental.booster.managers.Files;
import gg.steve.elemental.booster.message.MessageType;
import gg.steve.elemental.booster.utils.ItemBuilderUtil;
import gg.steve.elemental.booster.utils.LogUtil;
import gg.steve.elemental.booster.utils.SoundUtil;
import gg.steve.elemental.booster.utils.TimeUtil;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Booster {
    private ConfigurationSection section;
    private int boosterId;
    private UUID owner;
    private BoosterType type;
    private double boostAmount;
    private int duration;

    public Booster(int boosterId, int duration, UUID owner) {
        this.section = Files.BOOSTERS.get().getConfigurationSection(String.valueOf(boosterId));
        this.boosterId = boosterId;
        this.owner = owner;
        this.duration = duration;
        this.type = BoosterType.valueOf(this.section.getString("type").toUpperCase());
        this.boostAmount = this.section.getDouble("boost-amount");
    }

    public void decrementDuration() {
        this.duration--;
    }

    public int getDuration() {
        return duration;
    }

    public double getBoostAmount() {
        return boostAmount;
    }

    public BoosterType getType() {
        return type;
    }

    public boolean isComplete() {
        return this.duration < 1;
    }

    public int getBoosterId() {
        return boosterId;
    }

    public void onActivation(Player player) {
        TimeUtil time = new TimeUtil(this.duration);
        switch (type) {
            case TOKEN:
                MessageType.TOKEN_BOOSTER_ACTIVATION.message(player, ElementalBoosters.getNumberFormat().format(this.boostAmount), time.getDays(), time.getHours(), time.getMinutes(), time.getSeconds());
                break;
            case SELL:
                MessageType.SELL_BOOSTER_ACTIVATION.message(player, ElementalBoosters.getNumberFormat().format(this.boostAmount * 100), time.getDays(), time.getHours(), time.getMinutes(), time.getSeconds());
                break;
        }
        SoundUtil.playSound(this.section.getConfigurationSection("activation"), player);
    }

    public void onComplete() {
        if (Bukkit.getPlayer(this.owner) == null) return;
        MessageType.BOOSTER_EXPIRE.message(Bukkit.getPlayer(this.owner), this.type.name());
        SoundUtil.playSound(this.section.getConfigurationSection("expiry"), Bukkit.getPlayer(this.owner));
    }

    public UUID getOwner() {
        return owner;
    }

    public static ItemStack getBoosterItem(int boosterId, int duration) {
        TimeUtil time = new TimeUtil(duration);
        ConfigurationSection section = Files.BOOSTERS.get().getConfigurationSection(String.valueOf(boosterId));
        ItemBuilderUtil builder;
        if (section.getString("material").startsWith("hdb")) {
            String[] parts = section.getString("material").split("-");
            builder = new ItemBuilderUtil(new HeadDatabaseAPI().getItemHead(parts[1]));
        } else {
            builder = new ItemBuilderUtil(section.getString("material"), section.getString("data"));
        }
        builder.addName(section.getString("name"), time);
        builder.setLorePlaceholders("{days}", "{hours}", "{minutes}", "{seconds}");
        builder.addLore(section.getStringList("lore"), time.getDays(), time.getHours(), time.getMinutes(), time.getSeconds());
        builder.addEnchantments(section.getStringList("enchantments"));
        builder.addItemFlags(section.getStringList("item-flags"));
        builder.addItemNBT(boosterId, duration);
        return builder.getItem();
    }
}
