package pl.botprzemek.bpBungeeUtils.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.botprzemek.bpBungeeUtils.BungeeUtils.Utils.Serializer;
import pl.botprzemek.bpBungeeUtils.BungeeUtils.UtilsManager;

public class DiscordCommand extends Command {

    private final Serializer serializer;

    public DiscordCommand(UtilsManager utilsManager) {

        super("discord", "bputils.discord", "dc");

        serializer = utilsManager.getConfigManager().getConfig().getSerializer();

    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!(sender instanceof ProxiedPlayer player)) return;

        serializer.sendCommandMessage(player, "discord.success");

    }
}
