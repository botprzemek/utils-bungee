package pl.botprzemek.bpBungeeUtils.Config;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import pl.botprzemek.bpBungeeUtils.BpBungeeUtils;
import pl.botprzemek.bpBungeeUtils.Utils.StringSerializer;

import java.io.File;
import java.io.IOException;

public class Config {

    protected BpBungeeUtils instance;

    protected String name;

    protected Configuration configuration;

    private final StringSerializer stringSerializer;

    public Config(BpBungeeUtils instance, String name) {

        this.instance = instance;

        this.name = name;

        this.stringSerializer = new StringSerializer(this);

    }

    public void loadConfig() {

        try {

            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(instance.getDataFolder(), name));

        }

        catch (IOException error) {

            error.printStackTrace();

        }

    }

    public Configuration getConfiguration() {

        return configuration;

    }

    public String getString(String path) {

        return configuration.getString(path);

    }

    public int getInt(String path) {

        return configuration.getInt(path);

    }

    public Configuration getSection(String path) {

        return configuration.getSection(path);

    }

    public StringSerializer getStringSerializer() {

        return stringSerializer;

    }

}
