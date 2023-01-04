package pl.botprzemek.bpBungeeUtils.Events;

import net.md_5.bungee.api.ProxyServer;
import pl.botprzemek.bpBungeeUtils.BpBungeeUtils;
import pl.botprzemek.bpBungeeUtils.UtilsManager;

public class EventManager {

    public EventManager(UtilsManager utilsManager) {

        BpBungeeUtils instance = utilsManager.getInstance();

        ProxyServer.getInstance().getPluginManager().registerListener(instance, new JoinQuitEvent(utilsManager));

    }
}
