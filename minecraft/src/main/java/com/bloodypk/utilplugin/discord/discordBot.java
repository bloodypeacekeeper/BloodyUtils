package com.bloodypk.utilplugin.discord;

import com.bloodypk.utilplugin.UtilPlugin;
import com.bloodypk.utilplugin.data.dataManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.logging.Level;

public class discordBot extends ListenerAdapter {

    JDA jda;
    dataManager data;
    UtilPlugin main;

    String playerChat = "1196540459389046865";

    public discordBot(UtilPlugin main, dataManager data) {
        this.data = data;
        this.main = main;
        
        try {
            discordBotInit();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void playerMessageSent(String msg, String playerName) {
        EmbedBuilder _msg = new EmbedBuilder();
        _msg.setTitle(ChatColor.stripColor(playerName));
        _msg.setColor(Color.red);
        _msg.setDescription(ChatColor.stripColor(msg));
        jda.getTextChannelById(playerChat).sendMessageEmbeds(_msg.build()).queue();
    }

    public void playerDeath(String deathMsg) {
        EmbedBuilder _msg = new EmbedBuilder();
        _msg.setTitle(deathMsg);
        _msg.setColor(Color.yellow);
        jda.getTextChannelById(playerChat).sendMessageEmbeds(_msg.build()).queue();
    }

    public void playerJoin(String playerName) {
        EmbedBuilder _msg = new EmbedBuilder();
        _msg.setTitle(ChatColor.stripColor(playerName) + " has joined the server.");
        _msg.setColor(Color.green);
        jda.getTextChannelById(playerChat).sendMessageEmbeds(_msg.build()).queue();
    }

    public void playerLeave(String playerName) {
        EmbedBuilder _msg = new EmbedBuilder();
        _msg.setTitle(ChatColor.stripColor(playerName) + " has left the server.");
        _msg.setColor(Color.green);
        jda.getTextChannelById(playerChat).sendMessageEmbeds(_msg.build()).queue();
    }

    void discordBotInit() throws InterruptedException, LoginException {

        String token = data.getConfig().getString("Discord.token");

        if (token.isEmpty()) {
            main.getLogger().log(Level.SEVERE, "No token found.");
            return;
        }

        JDABuilder jdaBuilder = JDABuilder.createDefault(token);

        jdaBuilder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
        jdaBuilder.setStatus(OnlineStatus.ONLINE);
        jdaBuilder.setActivity(Activity.playing("with your mom"));

        jda = jdaBuilder.build();
        jda.awaitReady();

        jda.addEventListener(this);

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        // Sends and chat msgs in smp-chat to minecraft chat
        if (!e.getAuthor().isBot() && e.getChannel().getId().equalsIgnoreCase(playerChat)) {
            String message = e.getMessage().getContentRaw();
            User user = e.getAuthor();
            Bukkit.broadcast(Component.text(user.getName() + ": ", NamedTextColor.DARK_PURPLE).append(Component.text(message, NamedTextColor.WHITE)));
        }
    }
}
