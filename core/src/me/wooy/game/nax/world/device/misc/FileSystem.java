package me.wooy.game.nax.world.device.misc;

import java.util.List;

public interface FileSystem {
    String getCurrentFolder();
    void setCurrentFolder(String folder);
    List<String> getFolders(String folder);
    List<String> getFiles(String folder);
    String readFile(String file);
    void writeFile(String file,String value);
    void remove(String file);
    void createFolder(String folder);
    void createFile(String file);
}
