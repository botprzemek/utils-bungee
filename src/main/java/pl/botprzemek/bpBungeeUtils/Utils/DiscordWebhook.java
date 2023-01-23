package pl.botprzemek.bpBungeeUtils.Utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import pl.botprzemek.bpBungeeUtils.BpBungeeUtils;
import pl.botprzemek.bpBungeeUtils.Config.Config;

import java.awt.*;

public class DiscordWebhook {

    private JDA discordWebhook;

    private TextChannel discordChannel;

    private final BpBungeeUtils instance;

    public DiscordWebhook(BpBungeeUtils instance, Config config) {

        this.instance = instance;

        connect(config);

    }

    private void connect(Config config) {

        Configuration discordConfig = config.getSection("discord");

        String token = discordConfig.getString("token");

        String channelID = discordConfig.getString("channel-id");

        String status = discordConfig.getString("status");

        if (channelID == null || token == null) return;

        try {

            discordWebhook = JDABuilder.createDefault(token).build().awaitReady();

            discordChannel = discordWebhook.getTextChannelById(channelID);

            setStatus(status);

        }

        catch (InterruptedException error) {

            error.printStackTrace();

        }

    }

    public void disconnect() {

        if (this.discordWebhook != null) this.discordWebhook.shutdownNow();

    }

    public MessageEmbed createEmbed(Config config, ProxiedPlayer player, String path, int playerAmount) {

        if (this.discordWebhook == null || this.discordChannel == null) return null;

        String playerName = player.getDisplayName();

        Configuration discordConfig = config.getSection("discord");

        EmbedBuilder builder = new EmbedBuilder();

        builder.setColor(new Color((Integer.parseInt(discordConfig.getString("color").replace("#", ""), 16))));

        builder.setAuthor(discordConfig.getString(path + ".title")
            .replace("%player_name%", playerName),
        null,
        "https://mc-heads.net/avatar/" + playerName + "/100");

        if (playerAmount != -1) builder.setDescription(discordConfig.getString(path + ".description")
            .replace("%online%", String.valueOf(playerAmount)));

        return builder.build();

    }

    public void sendEmbeds(MessageEmbed embed) {

        if (embed == null || this.discordWebhook == null || this.discordChannel == null) return;

        ProxyServer.getInstance().getScheduler().runAsync(this.instance, ()-> this.discordChannel.sendMessageEmbeds(embed).queue());

    }

    public void setStatus(String name) {

        discordWebhook.getPresence().setActivity(Activity.playing(name));

    }


}