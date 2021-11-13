package me.wooy.game.nax.world.organization;

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
}
