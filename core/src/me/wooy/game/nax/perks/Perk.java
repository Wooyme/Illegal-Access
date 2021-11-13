package me.wooy.game.nax.perks;

import me.wooy.game.nax.perks.constants.ENPerkType;

import java.util.List;

public interface Perk {
    String getName();
    String getDescription();

    /**
     * 影响每轮可以感染的设备数
     * @return
     */
    double getSpread();

    /**
     * 影响每轮可以感染的设备等级登记安全安全登记
     * @return
     */
    double getInfect();
    double getDestroy();
    double getValue();
    List<Perk> getDependencies();   
}
