package pl.botprzemek.bpBungeeUtils.Events;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import pl.botprzemek.bpBungeeUtils.BpBungeeUtils;
import pl.botprzemek.bpBungeeUtils.Config.Config;
import pl.botprzemek.bpBungeeUtils.Utils.DiscordWebhook;
import pl.botprzemek.bpBungeeUtils.UtilsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JoinQuitEvent implements Listener {

    private final BpBungeeUtils instance;

    private final Config config;

    private final DiscordWebhook discordWebhook;

    private final List<UUID> connectedPlayers;

    public JoinQuitEvent(UtilsManager utilsManager) {

        this.config = utilsManager.getConfig();

        this.connectedPlayers = new ArrayList<>();

        this.discordWebhook = utilsManager.getDiscordWebhook();

        this.instance = utilsManager.getInstance();

    }

    @EventHandler
    public void onPlayerJoinEvent(ServerSwitchEvent event) {

        if (event.getPlayer().getServer().getInfo().getName().startsWith("lobby")) {

            connectedPlayers.remove(event.getPlayer().getUniqueId());

            return;

        }

        ProxiedPlayer player = event.getPlayer();

        connectedPlayers.add(player.getUniqueId());

        discordWebhook.addEmbed(discordWebhook.createEmbedMessage("quit", -1, player, config, instance));

        discordWebhook.sendEmbeds(instance);

    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerDisconnectEvent event) {

        ProxiedPlayer player = event.getPlayer();

        if (!connectedPlayers.contains(player.getUniqueId())) return;

        discordWebhook.addEmbed(discordWebhook.createEmbedMessage("quit", -1, player, config, instance));

        discordWebhook.sendEmbeds(instance);

        connectedPlayers.remove(event.getPlayer().getUniqueId());

    }

}
