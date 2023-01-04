package pl.botprzemek.bpBungeeUtils.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.botprzemek.bpBungeeUtils.Codes.CodesManager;
import pl.botprzemek.bpBungeeUtils.UtilsManager;

public class CodeCommand extends Command {

    private final CodesManager codesManager;

    public CodeCommand(UtilsManager utilsManager) {

        super("code");

        this.codesManager = utilsManager.getCodesManager();

    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!(sender instanceof ProxiedPlayer player)) return;

        if (args.length == 0) player.sendMessage("blad chuju");

        if (!codesManager.validateCode(args[0])) {

            player.sendMessage("blad chuju");

            return;

        }

        player.sendMessage("essa poprawny");

        codesManager.deleteCode(args[0]);

    }
}
