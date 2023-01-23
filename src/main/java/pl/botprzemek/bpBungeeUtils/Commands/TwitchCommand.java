package pl.botprzemek.bpBungeeUtils.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import pl.botprzemek.bpBungeeUtils.BungeeUtils.Utils.Config;
import pl.botprzemek.bpBungeeUtils.BungeeUtils.Utils.Serializer;
import pl.botprzemek.bpBungeeUtils.BungeeUtils.UtilsManager;

import java.util.HashMap;

public class TwitchCommand extends Command implements TabExecutor {

    private final Config config;

    private final Serializer serializer;

    public TwitchCommand(UtilsManager utilsManager) {

        super("twitch", "bputils.twitch", "ttv", "streamerzy");

        config = utilsManager.getConfigManager().getConfig();

        serializer = config.getSerializer();

    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!(sender instanceof ProxiedPlayer player)) return;

        if (args.length == 0) {

            HashMap<String, String> streamerList = config.getStreamerList();

            StringBuilder stringBuilder = new StringBuilder();

            for (String streamerName : streamerList.keySet()) stringBuilder.append("<gradient:#FFE04A:#FFA400><insert:/twitch ").append(streamerList.get(streamerName)).append(">").append(streamerName).append(",</bold></gradient> ");

            serializer.sendCommandMessage(player, "twitch.list", stringBuilder.toString());

            return;

        }

        String streamerName = args[0];

        String streamerLink = config.getStreamerLink(streamerName);

        if (streamerLink == null) {

            serializer.sendCommandMessage(player, "twitch.invalid");

            return;

        }

        serializer.sendCommandMessage(player, "twitch.success", streamerName, streamerLink);

    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {

        HashMap<String, String> streamerList = config.getStreamerList();

        return streamerList.keySet().stream().toList();

    }
}
