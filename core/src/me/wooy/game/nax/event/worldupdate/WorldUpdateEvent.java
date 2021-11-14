package me.wooy.game.nax.event.worldupdate;

import me.wooy.game.nax.world.device.Device;

public interface WorldUpdateEvent {
    int order();
    String getName();
    boolean willExecute(Device device);
    void execute(Device device);
}
