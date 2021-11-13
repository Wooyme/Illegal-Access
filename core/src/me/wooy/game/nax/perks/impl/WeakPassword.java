package me.wooy.game.nax.perks.impl;

import com.badlogic.gdx.utils.I18NBundle;
import me.wooy.game.nax.i18n.Local;
import me.wooy.game.nax.perks.Perk;
import me.wooy.game.nax.perks.constants.ENPerkType;

import java.util.ArrayList;
import java.util.List;

public class WeakPassword implements Perk {
    private final static List<Perk> dependencies = new ArrayList<>();
    static{
        dependencies.add(Crypto.instance);
        dependencies.add(Social.instance);
    }
    @Override
    public String getName() {
        return Local.get("P_WEAK_PASSWORD");
    }

    @Override
    public String getDescription() {
        return Local.get("P_WEAK_PASSWORD_DESCRIPTION");
    }

    @Override
    public double getSpread() {
        return 0.1;
    }

    @Override
    public double getInfect() {
        return 0.5;
    }

    @Override
    public double getDestroy() {
        return 0;
    }

    @Override
    public double getValue() {
        return 0;
    }

    @Override
    public List<Perk> getDependencies() {
        return dependencies;
    }
}
