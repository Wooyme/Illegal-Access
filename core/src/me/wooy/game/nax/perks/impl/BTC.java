package me.wooy.game.nax.perks.impl;

import me.wooy.game.nax.i18n.Local;
import me.wooy.game.nax.perks.Perk;
import me.wooy.game.nax.player.Player;

import java.util.List;

public class BTC implements Perk {
    @Override
    public String getName() {
        return Local.get("P_BTC");
    }

    @Override
    public String getDescription() {
        return Local.get("P_BTC_DESCRIPTION");
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
        int cpu = Player.getInstance().getOwnedCpu();
        int storage = Player.getInstance().getOwnedStorage();
        return cpu*0.01+storage*0.001;
    }

    @Override
    public List<Perk> getDependencies() {
        return null;
    }
}
