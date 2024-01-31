package com.bloodypk.utilplugin.commands;

import com.bloodypk.utilplugin.data.dataManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class open implements CommandExecutor {

    dataManager data;

    public open(dataManager data) {
        this.data = data;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        data.getConfig().set("Server.closed", false);
        data.saveConfig();
        sender.sendMessage(Component.text("Server is open!").color(NamedTextColor.GOLD));
        return true;
    }
}
