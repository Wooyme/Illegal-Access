package me.wooy.game.nax.world.organization;

import com.badlogic.gdx.graphics.Color;
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

    @Override
    public int getColor() {
        return Color.rgba8888(Color.ROYAL);
    }
}
