package pl.botprzemek.bpBungeeUtils.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.botprzemek.bpBungeeUtils.Config.Config;
import pl.botprzemek.bpBungeeUtils.UtilsManager;

public class DiscordCommand extends Command {

    private final Config config;

    public DiscordCommand(UtilsManager utilsManager) {

        super("discord");

        this.config = utilsManager.getConfig();

    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!(sender instanceof ProxiedPlayer player)) return;

        player.sendMessage(config.getStringSerializer().serializeStringFromPath("discord.success"));

    }
}
