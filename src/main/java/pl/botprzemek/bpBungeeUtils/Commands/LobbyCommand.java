package pl.botprzemek.bpBungeeUtils.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.botprzemek.bpBungeeUtils.BungeeUtils.Utils.Config;
import pl.botprzemek.bpBungeeUtils.BungeeUtils.Utils.Serializer;
import pl.botprzemek.bpBungeeUtils.BungeeUtils.UtilsManager;

import java.util.List;

public class LobbyCommand extends Command {

    private final Config config;

    private final Serializer serializer;

    public LobbyCommand(UtilsManager utilsManager) {

        super("lobby", "bputils.lobby", "l", "hub");

        config = utilsManager.getConfigManager().getConfig();

        serializer = config.getSerializer();

    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!(sender instanceof ProxiedPlayer player)) return;

        List<String> servers = config.getServerList();

        if (player.getServer().getInfo().getName().startsWith("lobby")) {

            serializer.sendCommandMessage(player, "lobby.connected");

            return;

        }

        serializer.sendCommandMessage(player, "lobby.success");

        if (((int) Math.abs(System.currentTimeMillis() % 10)) % 2 == 0) player.connect(ProxyServer.getInstance().getServerInfo(servers.get(0)));

        else player.connect(ProxyServer.getInstance().getServerInfo(servers.get(1)));

    }

}
