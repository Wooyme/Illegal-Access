package me.wooy.game.nax.world.device;

import me.wooy.game.nax.world.command.Command;
import me.wooy.game.nax.world.organization.Organization;
import java.util.List;
import com.badlogic.gdx.graphics.Color;

public interface Device {
    public enum Arch{
        X86,ARM,MIPS
    }

    /**
     * 地址(IP的抽象)
     * @return
     */
    String getAddress();

    /**
     * 详细描述，
     * @return
     */
    String getDescription();
    int getSecurityLevel();
    Arch getArch();
    int getCPU();
    int getStorage();
    List<Organization> getOwners();
    List<Command> getCommands();

    default int maxSize(){
        return 49;
    }

    default int minSize(){
        return 9;
    }
}
