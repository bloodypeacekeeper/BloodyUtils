package com.bloodypk.utilplugin.commands;

import com.bloodypk.utilplugin.permissions.permissionManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class nick implements CommandExecutor  {

    permissionManager perm;

    public nick(permissionManager perm) {
        this.perm = perm;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length >= 1) {
                if (args[0].length() <= 16) {
                    player.displayName(Component.text(args[0]).color(NamedTextColor.LIGHT_PURPLE));
                    player.playerListName(Component.text(args[0]).color(NamedTextColor.LIGHT_PURPLE));
                    player.customName(Component.text(args[0]).color(NamedTextColor.LIGHT_PURPLE));
                    sender.sendMessage(Component.text("You are now nicked as " + args[0]).color(NamedTextColor.GREEN));
                }
                else
                {
                    player.sendMessage(Component.text("Username is too long.").color(NamedTextColor.DARK_RED));
                }
            }
            else {
                perm.playerJoin(((Player) sender));
                player.sendMessage(Component.text("You are no longer nicked.").color(NamedTextColor.GREEN));
            }
        }
        else {
            sender.sendMessage(Component.text("You have to be a player to run this command.").color(NamedTextColor.DARK_RED));
        }

        return true;
    }
}
