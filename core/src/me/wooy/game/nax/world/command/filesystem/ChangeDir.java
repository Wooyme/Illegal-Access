package me.wooy.game.nax.world.command.filesystem;

import me.wooy.game.nax.i18n.Local;
import me.wooy.game.nax.world.command.Command;
import me.wooy.game.nax.world.device.Device;
import me.wooy.game.nax.world.device.misc.FileSystem;

import java.util.List;

public class ChangeDir implements Command {
    public static final ChangeDir instance = new ChangeDir();
    @Override
    public String getCmd() {
        return "cd";
    }

    @Override
    public String getDescription() {
        return Local.get("C_CD");
    }

    @Override
    public String execute(Device device, String argv) {
        if(device instanceof FileSystem){
            FileSystem fileSystem = (FileSystem) device;
            if(argv.startsWith("/")){
                List<String> folders = fileSystem.getFolders("/");
                if(folders.contains(argv.substring(1))){
                    fileSystem.setCurrentFolder(argv.substring(1));
                    return "";
                }
            }else{
                List<String> folders = fileSystem.getFolders(fileSystem.getCurrentFolder());
                if(folders.contains(argv)){
                    fileSystem.setCurrentFolder(argv);
                    return "";
                }
            }
            return Local.get("C_CD_ERROR_1");
        }
        return Local.get("C_CD_ERROR");
    }
}
