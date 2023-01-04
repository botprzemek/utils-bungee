package pl.botprzemek.bpBungeeUtils.Commands;

import pl.botprzemek.bpBungeeUtils.BpBungeeUtils;
import pl.botprzemek.bpBungeeUtils.Utils.UtilsManager;

public class CommandManager {

    public CommandManager(UtilsManager utilsManager) {

        BpBungeeUtils instance = utilsManager.getInstance();

        instance.getProxy().getPluginManager().registerCommand(instance, new UtilsCommand(utilsManager));

        instance.getProxy().getPluginManager().registerCommand(instance, new LobbyCommand(utilsManager));

        instance.getProxy().getPluginManager().registerCommand(instance, new DiscordCommand(utilsManager));

    }

}
