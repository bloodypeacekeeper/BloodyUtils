package com.bloodypk.utilplugin.commands;

import com.bloodypk.utilplugin.data.dataManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class close implements CommandExecutor {

    dataManager data;

    public close(dataManager data) {
        this.data = data;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        data.getConfig().set("Server.closed", true);
        data.saveConfig();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if(!player.hasPermission("utilplugin.owner")) {
                player.kick(Component.text("Server is closed. Please wait for it to open.\nIf you believe this is an error, please report it on Discord.").color(NamedTextColor.DARK_RED));
            }
        }

        sender.sendMessage(Component.text("Server closed!").color(NamedTextColor.GREEN));
        return true;
    }
}
