package pl.botprzemek.bpBungeeUtils.Events;

import net.md_5.bungee.api.ProxyServer;
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

    private final Config config;

    private final DiscordWebhook discordWebhook;

    private final List<UUID> connectedPlayers;

    public JoinQuitEvent(UtilsManager utilsManager) {

        this.discordWebhook = utilsManager.getDiscordWebhook();

        this.config = utilsManager.getConfig();

        this.connectedPlayers = new ArrayList<>();

    }

    @EventHandler
    public void onPlayerJoinEvent(ServerSwitchEvent event) {

        ProxiedPlayer player = event.getPlayer();

        if (player.getServer().getInfo().getName().startsWith("lobby")) return;

        connectedPlayers.add(player.getUniqueId());

        discordWebhook.sendEmbeds(discordWebhook.createEmbed(config, player, "join", ProxyServer.getInstance().getOnlineCount()));

        discordWebhook.setStatus(ProxyServer.getInstance().getOnlineCount() + " gracz/y");

    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerDisconnectEvent event) {

        ProxiedPlayer player = event.getPlayer();

        if (!connectedPlayers.contains(player.getUniqueId())) return;

        connectedPlayers.remove(player.getUniqueId());

        discordWebhook.sendEmbeds(discordWebhook.createEmbed(config, player, "quit", ProxyServer.getInstance().getOnlineCount() - 1));

        discordWebhook.setStatus(ProxyServer.getInstance().getOnlineCount() - 1 + " gracz/y");

    }
}
