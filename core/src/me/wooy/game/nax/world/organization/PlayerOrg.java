package me.wooy.game.nax.world.organization;

import com.badlogic.gdx.graphics.Color;

public class PlayerOrg implements Organization{
    public static final PlayerOrg instance = new PlayerOrg();
    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public int getColor() {
        return Color.rgba8888(Color.YELLOW);
    }
}
