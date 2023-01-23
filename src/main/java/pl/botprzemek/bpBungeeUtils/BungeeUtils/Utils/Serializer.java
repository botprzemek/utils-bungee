package pl.botprzemek.bpBungeeUtils.BungeeUtils.Utils;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Serializer {

    private final MiniMessage mm;

    private final Config config;

    public Serializer(Config config) {

        this.mm = MiniMessage.miniMessage();

        this.config = config;

    }

    public void sendCommandMessage(ProxiedPlayer player, String path) {

        BaseComponent message = BungeeComponentSerializer.get()
                .serialize(mm.deserialize(config.getCommandMessage(path)
                    .replace("%prefix%", config.getPrefix())
                ))[0];

        player.sendMessage(message);

    }

    public void sendCommandMessage(ProxiedPlayer player, String path, String value) {

        BaseComponent message = BungeeComponentSerializer.get()
                .serialize(mm.deserialize(config.getCommandMessage(path)
                    .replace("%prefix%", config.getPrefix())
                    .replace("%value%", value)
                ))[0];

        player.sendMessage(message);

    }

    public void sendCommandMessage(ProxiedPlayer player, String path, String value1, String value2) {

        BaseComponent message = BungeeComponentSerializer.get()
                .serialize(mm.deserialize(config.getCommandMessage(path)
                    .replace("%prefix%", config.getPrefix())
                    .replace("%value1%", value1)
                    .replace("%value2%", value2)
                ))[0];

        player.sendMessage(message);

    }

}
