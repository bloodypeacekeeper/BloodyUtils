package com.bloodypk.utilplugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class help implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        sender.sendMessage(Component.text("Welcome to the BB_35 SMP. If you have any questions feel free to ask on the Discord server.").color(NamedTextColor.GREEN));
        return true;
    }
}
