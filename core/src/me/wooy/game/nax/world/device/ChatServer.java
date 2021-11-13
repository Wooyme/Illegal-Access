package me.wooy.game.nax.world.device;

import com.badlogic.gdx.graphics.Color;
import me.wooy.game.nax.i18n.Local;
import me.wooy.game.nax.world.command.network.Log;
import me.wooy.game.nax.world.command.network.Talk;
import me.wooy.game.nax.world.command.Command;
import me.wooy.game.nax.world.command.network.Ping;
import me.wooy.game.nax.world.organization.Organization;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatServer implements Device{
    private static final List<Command> commandList = new ArrayList<>();
    private static final int MAX_SECURITY_LEVEL = 90;
    private static final int MIN_SECURITY_LEVEL = 20;
    private static final int MAX_CPU = 32;
    private static final int MIN_CPU = 2;
    private static final int MAX_STORAGE = 512;
    private static final int MIN_STORAGE = 40;
    private final String address;
    private final int securityLevel;
    private final int cpu;
    private final int storage;
    static {
        commandList.add(Ping.instance);
        commandList.add(Talk.instance);
        commandList.add(Log.instance);
    }
    public ChatServer(int size){
        this.address = UUID.randomUUID().toString();
        securityLevel = MIN_SECURITY_LEVEL + (int) (((float) (MAX_SECURITY_LEVEL - MIN_SECURITY_LEVEL)) / ((float) (maxSize() - minSize())) * size);
        cpu = MIN_CPU + (int) (((float) (MAX_CPU - MIN_CPU)) / ((float) (maxSize() - minSize())) * size);
        storage = MIN_STORAGE + (int) (((float) (MIN_STORAGE - MAX_STORAGE)) / ((float) (maxSize() - minSize())) * size);
    }
    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String getDescription() {
        return Local.get("D_CHAT");
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
        return null;
    }

    @Override
    public List<Command> getCommands() {
        return commandList;
    }
}
