package me.wooy.game.nax.event.command;

import me.wooy.game.nax.world.command.Command;
import me.wooy.game.nax.world.device.Device;

public interface CommandEvent {
    String getName();
    String getDescription();
    boolean willExecute(Device device, Command command,String argv);
    String execute(Device device,Command command,String argv);
}
