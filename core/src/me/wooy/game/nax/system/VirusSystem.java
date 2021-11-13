package me.wooy.game.nax.system;

import me.wooy.game.nax.perks.Perk;
import me.wooy.game.nax.player.Player;
import me.wooy.game.nax.world.device.Device;
import me.wooy.game.nax.world.organization.PlayerOrg;
import me.wooy.game.nax.world.organization.SecurityOrg;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VirusSystem {
    public static void update(){
        double difficulty = SecurityOrg.instance.getDifficulty();
        double maxVirusInfect = calcVirusInfect();
        double maxVirusSpread = calcVirusSpread();
        int maxVirusValue = (int) calcVirusValue();
        Player.getInstance().money.addAndGet(maxVirusValue);
        Set<Device> devices = Player.getInstance().getOwnedDevices();
        Set<Device> nextDevices = new HashSet<>();
        devices.forEach(device -> {
            List<Device> neighbors = DeviceSystem.getNeighbors(device);
            neighbors.forEach(next->{
                double pSpread = Math.random()*difficulty;
                if(pSpread<maxVirusSpread && next.getSecurityLevel()<maxVirusInfect){
                    nextDevices.add(next);
                }
            });
        });
        nextDevices.forEach(device-> DeviceSystem.own(device, PlayerOrg.instance));
        
    }
    
    private static double calcVirusInfect(){
        List<Perk> perks =  Player.getInstance().getPerks();
        return perks.stream().map(Perk::getInfect).reduce(Double::sum).orElse(0d);
    }
    
    private static double calcVirusSpread(){
        List<Perk> perks =  Player.getInstance().getPerks();
        return perks.stream().map(Perk::getSpread).reduce(Double::sum).orElse(0d);
    }
    private static double calcVirusValue(){
        List<Perk> perks = Player.getInstance().getPerks();
        return perks.stream().map(Perk::getValue).reduce(Double::sum).orElse(0d);
    }
}
