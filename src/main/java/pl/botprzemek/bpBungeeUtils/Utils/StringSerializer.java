package pl.botprzemek.bpBungeeUtils.Utils;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import pl.botprzemek.bpBungeeUtils.Config.Config;

public class StringSerializer {

    private final MiniMessage mm;

    private final Config config;

    public StringSerializer(Config config) {

        this.mm = MiniMessage.miniMessage();

        this.config = config;

    }

    public BaseComponent serializeStringFromPath(String path) {

        return BungeeComponentSerializer.get()
                .serialize(mm.deserialize(config.getConfiguration().getString(path)
                        .replace("%prefix%", config.getConfiguration().getString("prefix"))))[0];

    }

    public BaseComponent serializeStringFromPath(String path, String placeholder) {

        return BungeeComponentSerializer.get()
                .serialize(mm.deserialize(config.getConfiguration().getString(path)
                        .replace("%value%", placeholder)
                        .replace("%prefix%", config.getConfiguration().getString("prefix"))))[0];

    }

}
