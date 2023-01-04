package pl.botprzemek.bpBungeeUtils.Config;

import pl.botprzemek.bpBungeeUtils.BpBungeeUtils;
import pl.botprzemek.bpBungeeUtils.Utils.UtilsManager;

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

        instance.getLogger().info("Loading configs...");

        for (Config config : configs) {

            config.loadConfig();

        }

    }

    public Config getConfig() {

        return config;

    }

}
