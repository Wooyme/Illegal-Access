package me.wooy.game.nax.world.device;

import me.wooy.game.nax.world.command.Command;
import me.wooy.game.nax.world.organization.Organization;

import java.util.List;

public class DatabaseServer implements Device{
    public DatabaseServer(int size){

    }
    @Override
    public String getAddress() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public int getSecurityLevel() {
        return 0;
    }

    @Override
    public Arch getArch() {
        return null;
    }

    @Override
    public int getCPU() {
        return 0;
    }

    @Override
    public int getStorage() {
        return 0;
    }

    @Override
    public List<Organization> getOwners() {
        return null;
    }

    @Override
    public List<Command> getCommands() {
        return null;
    }
}
