package me.wooy.game.nax.player;

import me.wooy.game.nax.perks.Perk;
import me.wooy.game.nax.perks.impl.Crypto;
import me.wooy.game.nax.perks.impl.Network;
import me.wooy.game.nax.perks.impl.OperateSystem;
import me.wooy.game.nax.perks.impl.Social;
import me.wooy.game.nax.world.device.Device;
import me.wooy.game.nax.world.organization.deepnet.DeepNet;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import me.wooy.game.nax.misc.AtomicDouble;
public class Player {
    private static final Player instance = new Player();

    public static Player getInstance(){
        return instance;
    }
    public static void load(){
        Player.getInstance().addPerk(Crypto.instance);
        Player.getInstance().addPerk(Network.instance);
        Player.getInstance().addPerk(OperateSystem.instance);
        Player.getInstance().addPerk(Social.instance);
    }
    public final AtomicDouble money = new AtomicDouble(0);
    private final List<Perk> perks = new LinkedList<>();
    private final Set<Device> owned = new HashSet<>();
    private final List<DeepNet> deepNetList = new LinkedList<>();

    public List<Perk> getPerks(){
        return perks;
    }

    public boolean hasPerk(Perk perk){
        return perks.contains(perk);
    }

    public void addPerk(Perk perk){
        if(!perks.contains(perk)){
            perks.add(perk);
        }
    }

    public int getPerkNum(){
        return perks.size();
    }

    public Set<Device> getOwnedDevices(){
        return owned;
    }

    public void addOwnedDevices(Set<Device> devices){
        this.owned.addAll(devices);
    }

    public int getOwnedDeviceNum(){
        return owned.size();
    }
    
    public int getOwnedCpu(){
        return owned.stream().map(Device::getCPU).reduce(Integer::sum).orElse(0);
    }
    
    public int getOwnedStorage(){
        return owned.stream().map(Device::getStorage).reduce(Integer::sum).orElse(0);
    }

    public List<DeepNet> getFoundDeepNets(){
        return deepNetList;
    }

    public void findDeepNet(DeepNet deepNet){
        if(!deepNetList.contains(deepNet)){
            deepNetList.add(deepNet);
        }
    }

    public int getFoundDeepNetNum(){
        return deepNetList.size();
    }

}
