package me.wooy.game.nax.i18n;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

public class Local {
    private static final I18NBundle local;
    static{
        FileHandle internal = Gdx.files.internal("i18n/local");
        local = I18NBundle.createBundle(internal, Locale.getDefault());
    }
    public static String get(String code){
        return local.get(code);
    }
    public static String get(String code, Object... args){
        return local.format(code, args);
    }
}
