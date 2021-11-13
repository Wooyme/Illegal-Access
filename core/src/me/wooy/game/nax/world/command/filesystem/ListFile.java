package me.wooy.game.nax.world.command.filesystem;

import me.wooy.game.nax.i18n.Local;
import me.wooy.game.nax.world.command.Command;
import me.wooy.game.nax.world.device.Device;
import me.wooy.game.nax.world.device.misc.FileSystem;

import java.util.List;
import java.util.stream.Collectors;

public class ListFile implements Command {
    public static final ListFile instance = new ListFile();
    @Override
    public String getCmd() {
        return "ls";
    }

    @Override
    public String getDescription() {
        return Local.get("C_LS");
    }

    @Override
    public String execute(Device device, String argv) {
        if(device instanceof FileSystem){
            FileSystem fileSystem = (FileSystem) device;
            List<String> folders = fileSystem.getFolders(fileSystem.getCurrentFolder());
            List<String> files = fileSystem.getFiles(fileSystem.getCurrentFolder());
            return folders.stream().map(it->"[BLUE]"+it+"[]").collect(Collectors.joining(" "))
                    +"\n"
                    +files.stream().collect(Collectors.joining(" "));
        }
        return Local.get("C_LS_ERROR");
    }
}
