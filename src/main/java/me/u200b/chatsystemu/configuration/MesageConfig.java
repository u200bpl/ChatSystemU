package me.u200b.chatsystemu.configuration;

import me.u200b.chatsystemu.ChatSystemU;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class MesageConfig {
    ChatSystemU plugin;
    public MesageConfig(ChatSystemU instance) {
        plugin = instance;
    }

    public File messageFile = new File(plugin.getDataFolder(), "message.yml");
    public FileConfiguration messageConfig = YamlConfiguration.loadConfiguration(messageFile);

    public void configMessage(Player player) {
        if (!messageFile.exists()) plugin.saveResource("message.yml", true);
    }
}
