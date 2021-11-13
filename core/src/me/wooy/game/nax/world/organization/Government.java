package me.wooy.game.nax.world.organization;

import me.wooy.game.nax.i18n.Local;

public class Government implements Organization{
    @Override
    public String getName() {
        return Local.get("O_GOVERNMENT_NAME");
    }

    @Override
    public String getDescription() {
        return Local.get("O_GOVERNMENT_DESCRIPTION");
    }
}
