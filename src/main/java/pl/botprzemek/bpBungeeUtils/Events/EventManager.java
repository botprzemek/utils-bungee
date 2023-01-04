package pl.botprzemek.bpBungeeUtils.Events;

import pl.botprzemek.bpBungeeUtils.BpBungeeUtils;
import pl.botprzemek.bpBungeeUtils.UtilsManager;

public class EventManager {

    public EventManager(UtilsManager utilsManager) {

        BpBungeeUtils instance = utilsManager.getInstance();

        instance.getProxy().getPluginManager().registerListener(instance, new JoinQuitEvent(utilsManager));

    }
}
