package me.wooy.game.nax.world.command.network;

import me.wooy.game.nax.i18n.Local;
import me.wooy.game.nax.world.command.Command;
import me.wooy.game.nax.world.device.Device;

public class Ping implements Command {
    public static final Ping instance = new Ping();
    @Override
    public String getCmd() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return Local.get("C_PING");
    }

    @Override
    public String execute(Device device, String argv) {
        return null;
    }
}
