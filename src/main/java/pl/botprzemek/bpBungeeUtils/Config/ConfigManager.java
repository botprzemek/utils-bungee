package pl.botprzemek.bpBungeeUtils.Config;

import net.md_5.bungee.api.ProxyServer;
import pl.botprzemek.bpBungeeUtils.BpBungeeUtils;
import pl.botprzemek.bpBungeeUtils.UtilsManager;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private final BpBungeeUtils instance;

    private final List<Config> configs = new ArrayList<>();

    private final Config config;

    public ConfigManager(UtilsManager utilsManager) {

        this.instance = utilsManager.getInstance();

        configs.add(this.config = new Config(instance, "config.yml"));

    }

    public void loadConfigs() {

        ProxyServer.getInstance().getLogger().info("Loading configs...");

        for (Config config : configs) {

            config.loadConfig();

        }

    }

    public Config getConfig() {

        return config;

    }

}
