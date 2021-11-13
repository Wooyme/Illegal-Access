package me.wooy.game.nax.world.command.virus;

import me.wooy.game.nax.i18n.Local;
import me.wooy.game.nax.world.command.Command;
import me.wooy.game.nax.world.device.Device;

public class Boom implements Command {
    @Override
    public String getCmd() {
        return "boom";
    }

    @Override
    public String getDescription() {
        return Local.get("C_BOOM");
    }

    @Override
    public String execute(Device device, String argv) {
        return null;
    }
}
