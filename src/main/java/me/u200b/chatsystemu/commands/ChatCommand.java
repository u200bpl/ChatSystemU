package me.u200b.chatsystemu.commands;

import me.u200b.chatsystemu.ChatSystemU;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCommand implements CommandExecutor {
    ChatSystemU plugin;
    public ChatCommand(ChatSystemU instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (player.hasPermission("csu.clear") && args.length >= 1 && args[0].equalsIgnoreCase("clear")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission("csu.clear.bypass") || p.hasPermission("csu.bypass")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.messageConfig.getString("chat.clear.bypass").replace("{player}", player.getName())));
                    return true;
                }
                p.sendMessage(StringUtils.repeat(" \n", 100));
            }
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.messageConfig.getString("chat.clear.message").replace("{player}", player.getName())));
        }

        if (player.hasPermission("ccu.reload") && args.length >= 1 && args[0].equalsIgnoreCase("reload")) {
            try {
                plugin.messageConfig.load(plugin.messageFile);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&ccsu&8] &aConfig files has been reloaded"));
                return true;
            } catch (Exception e) {
                plugin.getLogger().warning("Reload failed: " + e.getMessage());
                return false;
            }
        }
        return false;
    }
}
