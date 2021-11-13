package me.wooy.game.nax.system;

import me.wooy.game.nax.world.organization.Organization;
import me.wooy.game.nax.world.organization.deepnet.DeepNet;
import me.wooy.game.nax.world.device.Device;
import java.util.Collections;
import java.util.List;

public class DeepNetSystem {
    public static List<DeepNet> getDeepNets(){
        return Collections.emptyList();
    }

    public static DeepNet ownedBy(Device device){
        for (Organization owner : device.getOwners()) {
            if(owner instanceof DeepNet){
                return (DeepNet) owner;
            }
        }
        return null;
    }

}
