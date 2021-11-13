package me.wooy.game.nax.event.command.impl;

import me.wooy.game.nax.event.command.CommandEvent;
import me.wooy.game.nax.player.Player;
import me.wooy.game.nax.system.DeepNetSystem;
import me.wooy.game.nax.world.command.Command;
import me.wooy.game.nax.world.command.filesystem.Cat;
import me.wooy.game.nax.world.device.Device;
import me.wooy.game.nax.world.device.misc.FileSystem;
import me.wooy.game.nax.world.organization.deepnet.DeepNet;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeepNetFoundEvent implements CommandEvent {
    private static final Pattern p = Pattern.compile("https://([a-z0-9A-Z]+\\.union)/");
    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public boolean willExecute(Device device, Command command,String argv) {
        if(device instanceof FileSystem && command instanceof Cat  && DeepNetSystem.ownedBy(device)!=null){
            return true;
        }
        return false;
    }

    @Override
    public String execute(Device device, Command command,String argv) {
        if(command instanceof Cat){
            return handleWhenCat(device,(Cat) command,argv);
        }
        return command.execute(device,argv);
    }

    private String handleWhenCat(Device device,Cat cat,String argv){
        String result = cat.execute(device, argv);
        List<String> domains = new ArrayList<>();
        Matcher m = p.matcher(result);
        while (m.find()) {
            domains.add(m.group());
        }
        for (DeepNet deepNet : DeepNetSystem.getDeepNets()) {
            if(domains.contains(deepNet.getDomain())){
                Player.getInstance().findDeepNet(deepNet);
            }
        }
        return result;
    }
}
