package pl.botprzemek.bpBungeeUtils.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.botprzemek.bpBungeeUtils.Config.Config;
import pl.botprzemek.bpBungeeUtils.UtilsManager;

import java.util.List;

public class LobbyCommand extends Command {

    private final Config config;

    public LobbyCommand(UtilsManager utilsManager) {

        super("lobby");

        this.config = utilsManager.getConfig();

    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!(sender instanceof ProxiedPlayer player)) return;

        List<String> servers = config.getConfiguration().getStringList("servers");

        if (player.getServer().getInfo().getName().startsWith("lobby")) {

            player.sendMessage(config.getStringSerializer().serializeStringFromPath("lobby.connected"));

            return;

        }

        if (((int) Math.abs(System.currentTimeMillis() % 10)) % 2 == 0) player.connect(ProxyServer.getInstance().getServerInfo(servers.get(0)));
        else player.connect(ProxyServer.getInstance().getServerInfo(servers.get(1)));

        player.sendMessage(config.getStringSerializer().serializeStringFromPath("lobby.success"));

    }
}
