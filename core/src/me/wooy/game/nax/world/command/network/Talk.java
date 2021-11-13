package me.wooy.game.nax.world.command.network;

import me.wooy.game.nax.i18n.Local;
import me.wooy.game.nax.world.command.Command;
import me.wooy.game.nax.world.device.Device;

public class Talk implements Command {
    public static final Talk instance = new Talk();
    @Override
    public String getCmd() {
        return "bc";
    }

    @Override
    public String getDescription() {
        return Local.get("C_BROADCAST");
    }

    @Override
    public String execute(Device device, String argv) {
        return null;
    }
}
