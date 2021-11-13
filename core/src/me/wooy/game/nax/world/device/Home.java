package me.wooy.game.nax.world.device;

import com.badlogic.gdx.graphics.Color;
import me.wooy.game.nax.i18n.Local;
import me.wooy.game.nax.world.command.Command;
import me.wooy.game.nax.world.organization.Organization;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Home implements Device {
    private final String address;

    public Home() {
        this.address = UUID.randomUUID().toString();
    }
    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String getDescription() {
        return Local.get("D_HOME");
    }

    @Override
    public int getSecurityLevel() {
        return 20;
    }

    @Override
    public Arch getArch() {
        return Arch.ARM;
    }

    @Override
    public int getCPU() {
        return 4;
    }

    @Override
    public int getStorage() {
        return 40;
    }

    @Override
    public List<Organization> getOwners() {
        return Collections.emptyList();
    }

    @Override
    public List<Command> getCommands() {
        return Collections.emptyList();
    }
}
