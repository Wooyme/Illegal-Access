package me.wooy.game.nax.world.device;

import com.badlogic.gdx.graphics.Color;
import me.wooy.game.nax.i18n.Local;
import me.wooy.game.nax.world.command.*;
import me.wooy.game.nax.world.command.filesystem.Cat;
import me.wooy.game.nax.world.command.filesystem.ChangeDir;
import me.wooy.game.nax.world.command.filesystem.Echo;
import me.wooy.game.nax.world.command.filesystem.ListFile;
import me.wooy.game.nax.world.command.network.Ping;
import me.wooy.game.nax.world.device.misc.FileSystem;
import me.wooy.game.nax.world.device.misc.FileSystemHelper;
import me.wooy.game.nax.world.organization.Organization;
import me.wooy.game.nax.world.utils.Domains;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WebServer implements Device, FileSystem {
    private static final List<Command> commandList = new ArrayList<>();
    private static final int MAX_SECURITY_LEVEL = 90;
    private static final int MIN_SECURITY_LEVEL = 20;
    private static final int MAX_CPU = 32;
    private static final int MIN_CPU = 2;
    private static final int MAX_STORAGE = 512;
    private static final int MIN_STORAGE = 40;
    private final String address;
    private final String domain;
    private final int securityLevel;
    private final int cpu;
    private final int storage;
    private final FileSystemHelper fileSystemHelper = new FileSystemHelper();
    private final List<Organization> owners = new ArrayList<>();
    static {
        commandList.add(Ping.instance);
        commandList.add(ChangeDir.instance);
        commandList.add(ListFile.instance);
        commandList.add(Cat.instance);
        commandList.add(Echo.instance);
    }

    public WebServer(int size) {
        this.address = UUID.randomUUID().toString();
        List<String> domains = Domains.getList();
        int index = (int) (Math.random() * domains.size());
        domain = domains.get(index);
        securityLevel = MIN_SECURITY_LEVEL + (int) (((float) (MAX_SECURITY_LEVEL - MIN_SECURITY_LEVEL)) / ((float) (maxSize() - minSize())) * size);
        cpu = MIN_CPU + (int) (((float) (MAX_CPU - MIN_CPU)) / ((float) (maxSize() - minSize())) * size);
        storage = MIN_STORAGE + (int) (((float) (MIN_STORAGE - MAX_STORAGE)) / ((float) (maxSize() - minSize())) * size);
        initDisk();

    }

    private void initDisk() {
        fileSystemHelper.createFolder("/web");
        fileSystemHelper.setCurrentFolder("/web");
        fileSystemHelper.createFile("index.html");
        fileSystemHelper.setCurrentFolder("/");
    }
    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String getDescription() {
        return Local.get("D_WEBSERVER_DESCRIPTION", domain);
    }

    @Override
    public int getSecurityLevel() {
        return securityLevel;
    }

    @Override
    public Arch getArch() {
        return Arch.X86;
    }

    @Override
    public int getCPU() {
        return cpu;
    }

    @Override
    public int getStorage() {
        return storage;
    }

    @Override
    public List<Organization> getOwners() {
        return owners;
    }

    @Override
    public List<Command> getCommands() {
        return commandList;
    }

    @Override
    public String getCurrentFolder() {
        return fileSystemHelper.getCurrentFolder();
    }

    @Override
    public void setCurrentFolder(String folder) {
        fileSystemHelper.setCurrentFolder(folder);
    }

    @Override
    public List<String> getFolders(String folder) {
        return fileSystemHelper.getFolders(folder);
    }

    @Override
    public List<String> getFiles(String folder) {
        return fileSystemHelper.getFiles(folder);
    }

    @Override
    public String readFile(String file) {
        return fileSystemHelper.readFile(file);
    }

    @Override
    public void writeFile(String file, String value) {
        fileSystemHelper.writeFile(file, value);
    }

    @Override
    public void remove(String file) {
        fileSystemHelper.remove(file);
    }

    @Override
    public void createFolder(String folder) {
        fileSystemHelper.createFolder(folder);
    }

    @Override
    public void createFile(String file) {
        fileSystemHelper.createFile(file);
    }
}
