package com.bloodypk.utilplugin;

import com.bloodypk.utilplugin.commands.*;
import com.bloodypk.utilplugin.data.dataManager;
import com.bloodypk.utilplugin.discord.discordBot;
import com.bloodypk.utilplugin.events.events;
import com.bloodypk.utilplugin.permissions.permissionManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class UtilPlugin extends JavaPlugin {

    discordBot dBot;
    dataManager data;
    permissionManager permManager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        data = new dataManager(this);
        dBot = new discordBot(this, data);
        permManager = new permissionManager(this, data);

        getServer().getPluginManager().registerEvents(new events(this, dBot, permManager, data), this);

        getCommand("help").setExecutor(new help());

        getCommand("discord").setExecutor(new discord());

        getCommand("open").setExecutor(new open(data));
        getCommand("close").setExecutor(new close(data));

        getCommand("nick").setExecutor(new nick(permManager));
        getCommand("unnick").setExecutor(new unnick(permManager));

        getCommand("perm").setExecutor(new perm(permManager));
        getCommand("perm").setTabCompleter(new perm(permManager));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
