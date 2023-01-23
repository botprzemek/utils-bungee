package pl.botprzemek.bpBungeeUtils.BungeeUtils;

import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import pl.botprzemek.bpBungeeUtils.BpBungeeUtils;
import pl.botprzemek.bpBungeeUtils.Commands.CommandManager;

public class UtilsManager {

    private final BpBungeeUtils instance;

    private final ConfigManager configManager;

    public UtilsManager(BpBungeeUtils instance) {

        this.instance = instance;

        configManager = new ConfigManager(this);

        configManager.loadConfigs();

        BungeeAudiences.create(instance);

        new CommandManager(this);

    }

    public void cleanUp() {
        // do something
    }

    public ConfigManager getConfigManager() {

        return configManager;

    }

    public BpBungeeUtils getInstance() {

        return instance;

    }

}
