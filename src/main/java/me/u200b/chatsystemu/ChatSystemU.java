package me.u200b.chatsystemu;

import me.u200b.chatsystemu.commands.ChatCommand;
import me.u200b.chatsystemu.events.SendChatEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class ChatSystemU extends JavaPlugin {
    public File messageFile = new File(getDataFolder(), "message.yml");
    public FileConfiguration messageConfig = YamlConfiguration.loadConfiguration(messageFile);

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents( new SendChatEvent(this), this);
        getCommand("chat").setExecutor(new ChatCommand(this));

        if (!messageFile.exists()) saveResource("message.yml", true);
        try {
            messageConfig.load(messageFile);
            return;
        } catch (Exception e) {
            getLogger().warning("Saving files failed: " + e.getMessage());
        }

        new Metrics(this, 14835);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}