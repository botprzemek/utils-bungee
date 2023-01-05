package pl.botprzemek.bpBungeeUtils.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import pl.botprzemek.bpBungeeUtils.Codes.CodesManager;
import pl.botprzemek.bpBungeeUtils.UtilsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CodesCommand extends Command {

    private final CodesManager codesManager;

    public CodesCommand(UtilsManager utilsManager) {

        super("codes");

        this.codesManager = utilsManager.getCodesManager();

    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        String prefix = (args.length > 0) ? args[0] : "code_";

        int amount = (args.length > 1) ? Integer.parseInt(args[1]) : 1;

        List<String> codes = new ArrayList<>();

        for (int i = 0; i < amount; i++) {

            Random rand = new Random();

            StringBuilder sb = new StringBuilder();

            for (int j = 0; j < 8; j++) sb.append(characters.charAt(rand.nextInt(characters.length())));

            String randomString = sb.toString();

            String code = prefix + randomString;

            ProxyServer.getInstance().getLogger().info("[CODES] " + code);

            codes.add(code);

        }

        codesManager.addCodes(codes);

    }
}
