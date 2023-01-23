package pl.botprzemek.bpBungeeUtils;

import net.md_5.bungee.api.plugin.Plugin;
import pl.botprzemek.bpBungeeUtils.BungeeUtils.UtilsManager;

public class BpBungeeUtils extends Plugin {

    private UtilsManager utilsManager;

    @Override
    public void onEnable() {

        this.utilsManager = new UtilsManager(this);

    }

    @Override
    public void onDisable() {

        if (utilsManager != null) utilsManager.cleanUp();

    }
}