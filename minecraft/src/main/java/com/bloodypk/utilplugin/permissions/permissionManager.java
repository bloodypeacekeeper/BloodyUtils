package com.bloodypk.utilplugin.permissions;

import com.bloodypk.utilplugin.UtilPlugin;
import com.bloodypk.utilplugin.data.dataManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class permissionManager {

    public HashMap<UUID, PermissionAttachment> playerPermissions = new HashMap<>();

    private UtilPlugin main;
    private dataManager data;

    public permissionManager(UtilPlugin main, dataManager data) {
        this.main = main;
        this.data = data;
    }

    public void setupPermissions(Player player) {
        PermissionAttachment attachment = player.addAttachment(main);
        this.playerPermissions.put(player.getUniqueId(), attachment);
        permissionsSetter(player.getUniqueId());
    }

    private void permissionsSetter(UUID uuid) {
        PermissionAttachment attachment = this.playerPermissions.get(uuid);
        for (String permissions : data.getConfig().getStringList("Permissions." + uuid.toString())) {
            attachment.setPermission(permissions, true);
        }
    }

    public void givePermission(UUID uuid, String permission) {
        PermissionAttachment attachment = this.playerPermissions.get(uuid);
        attachment.setPermission(permission, true);

        List<String> playerPerms = data.getConfig().getStringList("Permissions." + uuid.toString());
        playerPerms.add(permission);

        data.getConfig().set("Permissions." + uuid.toString(), playerPerms);
        data.saveConfig();
    }

    public void removePermission(UUID uuid, String permission) {
        PermissionAttachment attachment = this.playerPermissions.get(uuid);
        attachment.setPermission(permission, false);

        data.getConfig().set("Permissions." + uuid.toString(), data.getConfig().getStringList("Permissions." + uuid.toString()).remove(permission));
        data.saveConfig();
    }

    public void playerJoin(Player player) {
        if(player.hasPermission("utilplugin.owner")) {
            player.displayName(Component.text("[OWNER] ").color(NamedTextColor.RED).append(Component.text(player.getName()).color(NamedTextColor.RED)));
            player.playerListName(Component.text("[OWNER] ").color(NamedTextColor.RED).append(Component.text(player.getName()).color(NamedTextColor.RED)));
            player.customName(Component.text("[OWNER] ").color(NamedTextColor.RED).append(Component.text(player.getName()).color(NamedTextColor.RED)));
        } else if(player.hasPermission("utilplugin.donator")) {
            player.displayName(Component.text("[DONATOR] ").color(NamedTextColor.BLUE).append(Component.text(player.getName()).color(NamedTextColor.BLUE)));
            player.playerListName(Component.text("[DONATOR] ").color(NamedTextColor.BLUE).append(Component.text(player.getName()).color(NamedTextColor.BLUE)));
            player.customName(Component.text("[DONATOR] ").color(NamedTextColor.BLUE).append(Component.text(player.getName()).color(NamedTextColor.BLUE)));
        } else {
            player.displayName(Component.text(player.getName()).color(NamedTextColor.LIGHT_PURPLE));
            player.playerListName(Component.text(player.getName()).color(NamedTextColor.LIGHT_PURPLE));
            player.customName(Component.text(player.getName()).color(NamedTextColor.LIGHT_PURPLE));
        }
    }

    public void playerLeave(UUID uuid) {
        playerPermissions.remove(uuid);
    }
}
