package me.wooy.game.nax.world.command.filesystem;

import me.wooy.game.nax.i18n.Local;
import me.wooy.game.nax.world.command.Command;
import me.wooy.game.nax.world.device.Device;
import me.wooy.game.nax.world.device.misc.FileSystem;

public class Cat implements Command {
    public static final Cat instance = new Cat();
    @Override
    public String getCmd() {
        return "cat";
    }

    @Override
    public String getDescription() {
        return Local.get("C_CAT");
    }

    @Override
    public String execute(Device device, String argv) {
        if(device instanceof FileSystem){
            FileSystem fileSystem = (FileSystem) device;
            return fileSystem.readFile(argv);
        }
        return Local.get("C_CAT_ERROR");
    }
}
