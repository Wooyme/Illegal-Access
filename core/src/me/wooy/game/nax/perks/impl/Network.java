package me.wooy.game.nax.perks.impl;

import com.badlogic.gdx.Net;
import me.wooy.game.nax.i18n.Local;
import me.wooy.game.nax.perks.Perk;

import java.util.Collections;
import java.util.List;

public class Network implements Perk {
    public static final Network instance = new Network();
    @Override
    public String getName() {
        return Local.get("P_NETWORK");
    }

    @Override
    public String getDescription() {
        return Local.get("P_NETWORK_DESCRIPTION");
    }

    @Override
    public double getSpread() {
        return 0;
    }

    @Override
    public double getInfect() {
        return 0;
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
        return Collections.emptyList();
    }
}
