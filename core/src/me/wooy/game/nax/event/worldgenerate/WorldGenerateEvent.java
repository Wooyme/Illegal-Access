package me.wooy.game.nax.event.worldgenerate;
import me.wooy.game.nax.world.device.Device;
public interface WorldGenerateEvent {
    int order();
    String getName();
    boolean willExecute(Device device);
    void execute(Device device);
}
