package me.wooy.game.nax.world.command.filesystem;

import me.wooy.game.nax.i18n.Local;
import me.wooy.game.nax.world.command.Command;
import me.wooy.game.nax.world.device.Device;
import me.wooy.game.nax.world.device.misc.FileSystem;

public class Echo implements Command {
    public static final Echo instance = new Echo();
    @Override
    public String getCmd() {
        return "echo";
    }

    @Override
    public String getDescription() {
        return Local.get("C_ECHO");
    }

    @Override
    public String execute(Device device, String argv) {
        if(device instanceof FileSystem){
            FileSystem fileSystem = (FileSystem) device;
            String[] args = argv.split(" ");
            try {
                fileSystem.writeFile(args[0], args[1]);
            }catch (Exception e){
                return e.getMessage();
            }
        }
        return Local.get("C_ECHO_ERROR");
    }
}
