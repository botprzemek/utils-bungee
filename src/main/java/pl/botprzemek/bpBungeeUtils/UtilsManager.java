package pl.botprzemek.bpBungeeUtils;

import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import pl.botprzemek.bpBungeeUtils.Codes.CodesManager;
import pl.botprzemek.bpBungeeUtils.Commands.CommandManager;
import pl.botprzemek.bpBungeeUtils.Config.Config;
import pl.botprzemek.bpBungeeUtils.Config.ConfigManager;
import pl.botprzemek.bpBungeeUtils.Events.EventManager;
import pl.botprzemek.bpBungeeUtils.Utils.DiscordWebhook;
import pl.botprzemek.bpBungeeUtils.Utils.MySQLDatabase;
import pl.botprzemek.bpBungeeUtils.Utils.ServerStartup;

public class UtilsManager {

    private final BpBungeeUtils instance;

    private final ConfigManager configManager;

    private final MySQLDatabase mySQLDatabaseConnection;

    private DiscordWebhook discordWebhook;

    private final CodesManager codesManager;

    public UtilsManager(BpBungeeUtils instance) {

        this.instance = instance;

        this.configManager = new ConfigManager(this);

        configManager.loadConfigs();

        this.discordWebhook = new DiscordWebhook(instance, configManager.getConfig());

        this.mySQLDatabaseConnection = null;
//        this.mySQLDatabaseConnection = new MySQLDatabase(configManager.getConfig().getConfiguration());
//
//        this.codesManager = new CodesManager(mySQLDatabaseConnection, instance);
        this.codesManager = null;

        BungeeAudiences.create(instance);

        new ServerStartup(this);

        new CommandManager(this);

        new EventManager(this);

    }

    public void cleanUp() {

        //mySQLDatabaseConnection.disconnectDatabase();

        discordWebhook.disconnect();

    }

    public Config getConfig() {

        return configManager.getConfig();

    }

    public BpBungeeUtils getInstance() {

        return instance;

    }

    public void updateDiscordWebhook(Config config) {

        if (discordWebhook != null) discordWebhook.disconnect();

        discordWebhook = new DiscordWebhook(instance, config);

    }

    public DiscordWebhook getDiscordWebhook() {

        return discordWebhook;

    }

    public CodesManager getCodesManager() {

        return codesManager;

    }
}
