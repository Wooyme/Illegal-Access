package me.wooy.game.nax.world.command.network;

import me.wooy.game.nax.i18n.Local;
import me.wooy.game.nax.world.command.Command;
import me.wooy.game.nax.world.device.Device;

public class Log implements Command {
    public static final Log instance = new Log();
    @Override
    public String getCmd() {
        return "log";
    }

    @Override
    public String getDescription() {
        return Local.get("C_DESCRIPTION");
    }

    @Override
    public String execute(Device device, String argv) {
        return null;
    }
}
