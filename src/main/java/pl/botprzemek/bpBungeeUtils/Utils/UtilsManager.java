package pl.botprzemek.bpBungeeUtils.Utils;

import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import pl.botprzemek.bpBungeeUtils.BpBungeeUtils;
import pl.botprzemek.bpBungeeUtils.Commands.CommandManager;
import pl.botprzemek.bpBungeeUtils.Config.Config;
import pl.botprzemek.bpBungeeUtils.Config.ConfigManager;

public class UtilsManager {

    private final BpBungeeUtils instance;

    private final ConfigManager configManager;

    public UtilsManager(BpBungeeUtils instance) {

        this.instance = instance;

        this.configManager = new ConfigManager(this);

        configManager.loadConfigs();

        BungeeAudiences.create(instance);

        new ServerStartup(this);

        new CommandManager(this);

    }

    public void cleanUp() {

        // todo

    }

    public Config getConfig() {

        return configManager.getConfig();

    }

    public BpBungeeUtils getInstance() {

        return instance;

    }

}
