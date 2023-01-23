package pl.botprzemek.bpBungeeUtils.BungeeUtils.Utils;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import pl.botprzemek.bpBungeeUtils.BpBungeeUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Config {

    protected BpBungeeUtils instance;

    protected String name;

    protected Configuration configuration;

    private final Serializer serializer;

    private final HashMap<String, String> streamerList;

    public Config(BpBungeeUtils instance, String name) {

        this.instance = instance;

        this.name = name;

        serializer = new Serializer(this);

        streamerList = new HashMap<>();

    }

    public void loadConfig() {

        try {

            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(instance.getDataFolder(), name));

            setStreamerList();

        }

        catch (IOException error) {

            error.printStackTrace();

        }

    }

    public String getCommandMessage(String path) {

        Configuration section = configuration.getSection("commands");

        if (section == null) return null;

        return section.getString(path);

    }

    public String getPrefix() {

        return configuration.getString("prefix");

    }

    public List<String> getServerList() {

        return configuration.getStringList("servers");

    }

    public void setStreamerList() {

        if (streamerList.size() != 0) streamerList.clear();

        Configuration streamersSection = configuration.getSection("streamers");

        for (String streamerSection : streamersSection.getKeys()) {

            Configuration streamerConfig = streamersSection.getSection(streamerSection);

            String streamerName = streamerConfig.getString("name");

            String streamerLink = streamerConfig.getString("link");

            streamerList.put(streamerName, streamerLink);

        }

    }

    public HashMap<String, String> getStreamerList() {

        return streamerList;

    }

    public String getStreamerLink(String streamerName) {

        return streamerList.get(streamerName);

    }

    public Serializer getSerializer() {

        return serializer;

    }

}
