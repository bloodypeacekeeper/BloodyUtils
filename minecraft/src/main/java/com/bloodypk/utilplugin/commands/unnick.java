package com.bloodypk.utilplugin.commands;

import com.bloodypk.utilplugin.permissions.permissionManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class unnick implements CommandExecutor {

    permissionManager perm;

    public unnick(permissionManager perm) {
        this.perm = perm;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
                perm.playerJoin(((Player) sender));
        }
        else {
            sender.sendMessage(Component.text("You have to be a player to run this command.").color(NamedTextColor.DARK_RED));
        }

        return true;
    }
}
