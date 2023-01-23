package pl.botprzemek.bpBungeeUtils.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.botprzemek.bpBungeeUtils.BungeeUtils.ConfigManager;
import pl.botprzemek.bpBungeeUtils.BungeeUtils.Utils.Serializer;
import pl.botprzemek.bpBungeeUtils.BungeeUtils.UtilsManager;

public class ReloadCommand extends Command {

    private final ConfigManager configManager;

    private final Serializer serializer;

    public ReloadCommand(UtilsManager utilsManager) {

        super("bputils", "bputils.reload");

        configManager = utilsManager.getConfigManager();

        serializer = configManager.getConfig().getSerializer();

    }


    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!(sender instanceof ProxiedPlayer player)) {

            configManager.loadConfigs();

            return;

        }

        try {

            configManager.loadConfigs();

            serializer.sendCommandMessage(player, "reload.success");

        }

        catch (Exception error) {

            serializer.sendCommandMessage(player, "reload.failed");

        }

    }
}
