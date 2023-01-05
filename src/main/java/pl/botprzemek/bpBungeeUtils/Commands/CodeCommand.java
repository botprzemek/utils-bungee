package pl.botprzemek.bpBungeeUtils.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.botprzemek.bpBungeeUtils.Codes.CodesManager;
import pl.botprzemek.bpBungeeUtils.Config.Config;
import pl.botprzemek.bpBungeeUtils.UtilsManager;

import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;

public class CodeCommand extends Command {

    private final CodesManager codesManager;

    private final HashMap<UUID, Long> coolDown;

    private final Config config;

    public CodeCommand(UtilsManager utilsManager) {

        super("code");

        this.coolDown = new HashMap<>();

        this.codesManager = utilsManager.getCodesManager();

        this.config = utilsManager.getConfig();

    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!(sender instanceof ProxiedPlayer player)) return;

        if (args.length == 0) {

            player.sendMessage(config.getStringSerializer().serializeStringFromPath("codes.usage"));

            return;

        }

        if (coolDown.get(player.getUniqueId()) != null) {

            if (coolDown.get(player.getUniqueId()) >= Instant.now().getEpochSecond() - config.getConfiguration().getInt("codes.cooldown.time")) {

                int left = (int) (Instant.now().getEpochSecond() - coolDown.get(player.getUniqueId())) * -1;

                player.sendMessage(config.getStringSerializer().serializeStringFromPath("codes.cooldown.message", String.valueOf(config.getConfiguration().getInt("codes.cooldown.time") + left)));

                return;

            }

            coolDown.remove(player.getUniqueId());

        }

        if (!codesManager.validateCode(args[0])) {

            player.sendMessage(config.getStringSerializer().serializeStringFromPath("codes.invalid"));

            return;

        }

        player.sendMessage(config.getStringSerializer().serializeStringFromPath("codes.success"));

        coolDown.put(player.getUniqueId(), (Instant.now().getEpochSecond()));

        codesManager.updateCode(args[0], player.getUniqueId());

    }
}
