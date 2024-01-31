package com.bloodypk.utilplugin.commands;

import com.bloodypk.utilplugin.permissions.permissionManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class perm implements CommandExecutor, TabCompleter {

    private permissionManager permManager;

    public perm(permissionManager permManager) {
        this.permManager = permManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(args.length < 3) sender.sendMessage(Component.text("Usage: /perm <set, revoke> <player> <permission>").color(NamedTextColor.DARK_RED));

        if(args[0].equalsIgnoreCase("set")) {
            if (Bukkit.getPlayer(args[1]) != null) {
                permManager.givePermission(Bukkit.getPlayer(args[1]).getUniqueId(), args[2]);
            }
        } else if (args[0].equalsIgnoreCase("revoke")) {
            if (Bukkit.getPlayer(args[1]) != null) {
                permManager.removePermission(Bukkit.getPlayer(args[1]).getUniqueId(), args[2]);
            }
        } else {
            sender.sendMessage(Component.text("Usage: /perm <set, revoke> <player> <permission>").color(NamedTextColor.DARK_RED));
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1) {
            return Arrays.asList("set", "revoke");
        }
        return null;
    }
}
