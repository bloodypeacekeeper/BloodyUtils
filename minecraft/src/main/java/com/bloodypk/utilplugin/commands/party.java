package com.bloodypk.utilplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class party implements CommandExecutor, TabCompleter {

    Map<String, List<String>> invites = new HashMap<String, List<String>>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args[0].equalsIgnoreCase("invite")) {
            Player player = Bukkit.getPlayerExact(args[1]);
            if(player == null)
            {
                sender.sendMessage(ChatColor.DARK_RED + "The player could not be found");
                return true;
            }

            invites.get(((Player) sender).getUniqueId().toString()).add(player.getUniqueId().toString());
            sender.sendMessage(ChatColor.BLUE + player.getName() + ChatColor.GREEN + "was invited. They have 5 minutes to accept the invitation.");
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
