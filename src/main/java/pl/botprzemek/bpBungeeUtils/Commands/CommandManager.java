package pl.botprzemek.bpBungeeUtils.Commands;

import net.md_5.bungee.api.ProxyServer;
import pl.botprzemek.bpBungeeUtils.BpBungeeUtils;
import pl.botprzemek.bpBungeeUtils.UtilsManager;

public class CommandManager {

    public CommandManager(UtilsManager utilsManager) {

        BpBungeeUtils instance = utilsManager.getInstance();

        ProxyServer.getInstance().getPluginManager().registerCommand(instance, new UtilsCommand(utilsManager));

        ProxyServer.getInstance().getPluginManager().registerCommand(instance, new LobbyCommand(utilsManager));

        ProxyServer.getInstance().getPluginManager().registerCommand(instance, new DiscordCommand(utilsManager));

        ProxyServer.getInstance().getPluginManager().registerCommand(instance, new CodesCommand(utilsManager));

        ProxyServer.getInstance().getPluginManager().registerCommand(instance, new CodeCommand(utilsManager));

    }

}
