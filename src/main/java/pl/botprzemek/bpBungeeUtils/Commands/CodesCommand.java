package pl.botprzemek.bpBungeeUtils.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.botprzemek.bpBungeeUtils.Codes.CodesManager;
import pl.botprzemek.bpBungeeUtils.UtilsManager;

import java.util.Random;

public class CodesCommand extends Command {

    private final CodesManager codesManager;

    public CodesCommand(UtilsManager utilsManager) {

        super("codes");

        this.codesManager = utilsManager.getCodesManager();

    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!(sender instanceof ProxiedPlayer player)) return;

        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        String prefix = "test_";

        for (int i = 0; i < 5; i++) {

            Random rand = new Random();

            StringBuilder sb = new StringBuilder();

            for (int j = 0; j < 6; j++) {

                sb.append(characters.charAt(rand.nextInt(characters.length())));

            }

            String randomString = sb.toString();

            String code = prefix + randomString;

            codesManager.addCode(code);

            player.sendMessage(code);

        }

    }
}
