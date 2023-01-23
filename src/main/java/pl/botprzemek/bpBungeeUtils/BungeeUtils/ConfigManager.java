package pl.botprzemek.bpBungeeUtils.BungeeUtils;

import net.md_5.bungee.api.ProxyServer;
import pl.botprzemek.bpBungeeUtils.BungeeUtils.Utils.Config;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private final List<Config> configs = new ArrayList<>();

    private final Config config;

    public ConfigManager(UtilsManager utilsManager) {

        configs.add(this.config = new Config(utilsManager.getInstance(), "config.yml"));

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
