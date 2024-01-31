package com.bloodypk.utilplugin.events;

import com.bloodypk.utilplugin.UtilPlugin;
import com.bloodypk.utilplugin.data.dataManager;
import com.bloodypk.utilplugin.discord.discordBot;
import com.bloodypk.utilplugin.permissions.permissionManager;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class events implements Listener {

    discordBot dBot;
    permissionManager perm;
    UtilPlugin main;
    dataManager data;

    public events(UtilPlugin main, discordBot bot, permissionManager perm, dataManager data) {
        this.main = main;
        this.dBot = bot;
        this.perm = perm;
        this.data = data;
    }

    @EventHandler
    public void onPlayerConnect(PlayerLoginEvent e) {
        perm.setupPermissions(e.getPlayer());
        if(!data.getConfig().getBoolean("Server.closed") || e.getPlayer().hasPermission("utilplugin.owner")) {
            return;
        }
        e.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("Server is closed. Please wait for it to open.\nIf you believe this is an error, please report it on Discord.").color(NamedTextColor.DARK_RED));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        perm.playerJoin(e.getPlayer());
        dBot.playerJoin(PlainTextComponentSerializer.plainText().serialize(e.getPlayer().displayName()));
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        dBot.playerLeave(PlainTextComponentSerializer.plainText().serialize(e.getPlayer().displayName()));
        perm.playerLeave(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void playerChat(AsyncChatEvent e) {
        ChatRenderer renderer = (sender, displayName, message, viewer) -> {
            // do whatever you want here
            String msg = PlainTextComponentSerializer.plainText().serialize(message);

            if(sender.hasPermission("utilplugin.donator")) {
                msg = msg.replaceAll(":shrug:", "¯\\\\_(ツ)_/¯");
            } else {
                msg = msg.replaceAll("¯\\_(ツ)_/¯", "");
            }

            return displayName.append(Component.text(": ").append(Component.text(msg)).color(NamedTextColor.WHITE));
        };

        e.renderer(renderer);
        dBot.playerMessageSent(PlainTextComponentSerializer.plainText().serialize(e.originalMessage()), PlainTextComponentSerializer.plainText().serialize(e.getPlayer().displayName()));
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        dBot.playerDeath(PlainTextComponentSerializer.plainText().serialize(e.deathMessage()));
    }
}
