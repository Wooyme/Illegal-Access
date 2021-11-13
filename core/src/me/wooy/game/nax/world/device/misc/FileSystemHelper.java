package me.wooy.game.nax.world.device.misc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class FileSystemHelper implements FileSystem{
    Map<String,Map<String,String>> fileSystem = new HashMap<>();
    @Override
    public String getCurrentFolder() {
        return null;
    }

    @Override
    public void setCurrentFolder(String folder) {

    }

    @Override
    public List<String> getFolders(String folder) {
        return null;
    }

    @Override
    public List<String> getFiles(String folder) {
        return null;
    }

    @Override
    public String readFile(String file) {
        return null;
    }

    @Override
    public void writeFile(String file, String value) {

    }

    @Override
    public void remove(String file) {

    }

    @Override
    public void createFolder(String folder) {
        
    }

    @Override
    public void createFile(String file) {

    }
}
