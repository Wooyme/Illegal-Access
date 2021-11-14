package me.wooy.game.nax.world.organization;

import com.badlogic.gdx.graphics.Color;

public class SecurityOrg implements Organization{
    public static final SecurityOrg instance = new SecurityOrg();
    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }
    
    public double getDifficulty(){
        return 1;
    }

    @Override
    public int getColor() {
        return Color.rgba8888(Color.WHITE);
    }
}
