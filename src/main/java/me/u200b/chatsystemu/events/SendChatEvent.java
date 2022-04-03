package me.u200b.chatsystemu.events;

import me.u200b.chatsystemu.ChatSystemU;
import me.u200b.chatsystemu.commands.ChatCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class SendChatEvent implements Listener {
    ChatSystemU plugin;
    public SendChatEvent(ChatSystemU instance) {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = (Player) event.getPlayer();
        if (player.hasPermission("csu.mute.bypass") || player.hasPermission("csu.bypass")) return;
        if (ChatCommand.muteChat) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.messageConfig.getString("chat.mute.cant-talk")));
            return;
        }
    }
}