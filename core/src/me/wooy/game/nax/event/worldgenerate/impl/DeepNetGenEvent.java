package me.wooy.game.nax.event.worldgenerate.impl;

import me.wooy.game.nax.event.worldgenerate.WorldGenerateEvent;
import me.wooy.game.nax.i18n.Local;
import me.wooy.game.nax.system.DeepNetSystem;
import me.wooy.game.nax.world.WorldGenerator;
import me.wooy.game.nax.world.device.Device;
import me.wooy.game.nax.world.device.misc.FileSystem;
import me.wooy.game.nax.world.organization.deepnet.DeepNet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeepNetGenEvent implements WorldGenerateEvent {
    private static final DeepNetGenEvent instance = new DeepNetGenEvent();
    public static DeepNetGenEvent getInstance(){
        return instance;
    }
    static{
        WorldGenerator.EVENT_LIST.add(instance);
    }
    private static final float DEEP_NET_GEN_PROB = 0.01f;
    private final Map<DeepNet,Float> deepNetProbMap = new HashMap<>();
    private final float totalProb;
    public DeepNetGenEvent(){
        List<DeepNet> deepNetList = DeepNetSystem.getDeepNets();
        deepNetList.forEach(deepNet -> deepNetProbMap.put(deepNet, (float) Math.random()));
        totalProb = deepNetProbMap.values().stream().reduce(Float::sum).orElseThrow(()->new RuntimeException("Must not happen"));
    }

    @Override
    public int order() {
        return 0;
    }
    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public boolean willExecute(Device device) {
        return Math.random()>DEEP_NET_GEN_PROB;
    }

    @Override
    public void execute(Device device) {
        DeepNet deepNet = chooseOne();
        if(deepNet==null) {
            return;
        }
        if(device instanceof FileSystem){
            ownFileSystem(deepNet,(FileSystem) device);
        }
    }

    private DeepNet chooseOne(){
        float p = (float) Math.random()*totalProb;
        float cumulativeProbability = 0f;
        for (Map.Entry<DeepNet, Float> deepNetFloatEntry : deepNetProbMap.entrySet()) {
            cumulativeProbability+=deepNetFloatEntry.getValue();
            if(cumulativeProbability>p){
                return deepNetFloatEntry.getKey();
            }
        }
        return null;
    }

    private void ownFileSystem(DeepNet deepNet,FileSystem fileSystem){
        fileSystem.createFile(".union.history.log");
        fileSystem.writeFile(".union.history.log", Local.get("E_DEEP_NET_INFO",deepNet.getDomain()));
    }
}
