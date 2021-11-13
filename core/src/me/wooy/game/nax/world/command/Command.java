package me.wooy.game.nax.world.command;

import me.wooy.game.nax.world.device.Device;

public interface Command {
    String getCmd();
    String getDescription();
    String execute(Device device, String argv);
}
