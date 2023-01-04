package pl.botprzemek.bpBungeeUtils.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import pl.botprzemek.bpBungeeUtils.Config.Config;
import pl.botprzemek.bpBungeeUtils.Utils.UtilsManager;

public class UtilsCommand extends Command {

    private final Config config;

    public UtilsCommand(UtilsManager utilsManager) {

        super("bputils");

        this.config = utilsManager.getConfig();

    }


    @Override
    public void execute(CommandSender sender, String[] args) {

        config.loadConfig();

        sender.sendMessage(config.getStringSerializer().serializeStringFromPath("reload.success"));

    }
}
