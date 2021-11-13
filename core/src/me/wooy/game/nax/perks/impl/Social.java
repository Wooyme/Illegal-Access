package me.wooy.game.nax.perks.impl;

import me.wooy.game.nax.i18n.Local;
import me.wooy.game.nax.perks.Perk;

import java.util.Collections;
import java.util.List;

public class Social implements Perk {
    public static final Social instance = new Social();
    @Override
    public String getName() {
        return Local.get("P_SOCIAL");
    }

    @Override
    public String getDescription() {
        return Local.get("P_SOCIAL_DESCRIPTION");
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
