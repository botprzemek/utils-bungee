package pl.botprzemek.bpBungeeUtils.Utils;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class ServerStartup {

    public ServerStartup(UtilsManager utilsManager) {

        CommandSender sender = utilsManager.getInstance().getProxy().getConsole();

        String[] startUpMessage = {
            "Starting bpBungeeUtils-0.1 by botprzemek", "    __          __                                       __  ",
            "   / /_  ____  / /_____  _________  ___  ____ ___  ___  / /__", "  / __ \\/ __ \\/ __/ __ \\/ ___/_  / / _ \\/ __ `__ \\/ _ \\/ //_/",
            " / /_/ / /_/ / /_/ /_/ / /    / /_/  __/ / / / / /  __/ ,<   ", "/_.___/\\____/\\__/ .___/_/    /___/\\___/_/ /_/ /_/\\___/_/|_|  ",
            "               /_/                                           ", "                                                             "};

        for (String string : startUpMessage) sender.sendMessage(new ComponentBuilder(string).create());

    }

}
