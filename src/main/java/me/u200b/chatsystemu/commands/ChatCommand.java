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

    public static boolean muteChat = false;

    public void setBoolean(boolean bool) {
        this.muteChat = bool;
    }

    public boolean getBoolean() {
        return muteChat;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (player.hasPermission("csu.clear") || player.hasPermission("csu.mute") || player.hasPermission("csu.reload")) {
            if (args.length < 1) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7                              &8- &cChatSystemU &8-"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8> &7/chat clear: &fClear chat of the players."));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8> &7/chat mute: &fMute and unmute the chart for players."));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8> &7/chat reload: &fReload all the config files."));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8-----------------------------------------------------"));
            }
        }

        if (player.hasPermission("csu.clear") && args.length >= 1 && args[0].equalsIgnoreCase("clear")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission("csu.clear.bypass") || p.hasPermission("csu.bypass")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.messageConfig.getString("chat.clear.bypass").replace("{player}", player.getName())));
                    return true;
                }
                p.sendMessage(StringUtils.repeat(" \n", 100));
            }
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.messageConfig.getString("chat.clear.message").replace("{player}", player.getName())));
            return true;
        }

        if (player.hasPermission("csu.mute") && args.length >= 1 && args[0].equalsIgnoreCase("mute")) {
            if (muteChat) {
                muteChat = false;
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.messageConfig.getString("chat.mute.bc-unmute").replace("{player}", player.getName())));
                }
                return false;
            }
            muteChat = true;
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission("csu.mute.bypass") || p.hasPermission("csu.bypass")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.messageConfig.getString("chat.mute.bypass").replace("{player}", player.getName())));
                    return true;
                }
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.messageConfig.getString("chat.mute.bc-mute").replace("{player}", player.getName())));
            }
            return true;
        }

        if (player.hasPermission("ccu.reload") && args.length >= 1 && args[0].equalsIgnoreCase("reload")) {
            try {
                plugin.messageConfig.load(plugin.messageFile);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&ccsu&8] &aConfig files has been reloaded"));
                return true;
            } catch (Exception e) {
                plugin.getLogger().warning("Reload failed: " + e.getMessage());
                return true;
            }
        }
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.messageConfig.getString("chat.no-perms")));
        return false;
    }
}