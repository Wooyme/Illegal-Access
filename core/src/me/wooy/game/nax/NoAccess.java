package me.wooy.game.nax;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import me.wooy.game.nax.screen.MainScreen;
import me.wooy.game.nax.system.DeviceSystem;
import me.wooy.game.nax.world.WorldGenerator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class NoAccess extends Game implements InputProcessor {
    private int WINDOW_WIDTH = WIDTH;
    private int WINDOW_HEIGHT = HEIGHT;
    public static final int WORLD_WIDTH = 2000;
    public static final int WORLD_HEIGHT = 2000;
    public static int WIDTH = 800;
    public static int HEIGHT = 450;
    private static final Vector2 R_16_9 = new Vector2(400, 225);
    private static final Vector2 R_5_3 = new Vector2(400, 240);
    private static final Vector2 R_4_3 = new Vector2(400, 300);
    private static final Vector2 R_3_2 = new Vector2(450, 300);
    private static final Map<BigDecimal, Vector2> resolutions = new HashMap<>();
    public SpriteBatch batch;
    public static final InputMultiplexer inputMultiplexer = new InputMultiplexer();
    public static final WorldGenerator worldGenerator = new WorldGenerator(WORLD_WIDTH, WORLD_HEIGHT);
    public static Pixmap map;
    public static OrthographicCamera mainCamera;
    public static OrthographicCamera uiCamera;
    public static Skin skin;
    static {
        resolutions.put(new BigDecimal("1.7"), R_16_9);
        resolutions.put(new BigDecimal("1.6"), R_5_3);
        resolutions.put(new BigDecimal("1.3"), R_4_3);
        resolutions.put(new BigDecimal("1.5"), R_3_2);
    }

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("skin/skin.json"));
        mainCamera = new OrthographicCamera();
        uiCamera = new OrthographicCamera();
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);
        batch = new SpriteBatch();
        DeviceSystem.load();
        worldGenerator.generate(texture -> map = texture);
    }


    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        BigDecimal ratio = new BigDecimal(width).divide(new BigDecimal(height), 1, RoundingMode.DOWN);
        Vector2 res = resolutions.get(ratio);
        if (res != null) {
            WIDTH = (int) res.x;
            HEIGHT = (int) res.y;
        }
        if (getScreen() != null) {
            getScreen().dispose();
        }
        setScreen(new MainScreen(this));
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.F) {
            if (Gdx.graphics.isFullscreen()) {
                Gdx.graphics.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT);
            } else {
                WINDOW_WIDTH = Gdx.graphics.getWidth();
                WINDOW_HEIGHT = Gdx.graphics.getHeight();
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        int x = Gdx.input.getDeltaX();//*(int)((float)WIDTH/(float)Gdx.graphics.getWidth());
        int y = Gdx.input.getDeltaY();//*(int)((float)HEIGHT/(float)Gdx.graphics.getHeight());
        mainCamera.position.add((mainCamera.position.x-x>=(NoAccess.WIDTH/2f) && mainCamera.position.x-x<=(WORLD_WIDTH-NoAccess.WIDTH/2f))?-x:0,
                (mainCamera.position.y+y>=(NoAccess.HEIGHT/2f) && mainCamera.position.y+y<=(WORLD_HEIGHT-NoAccess.HEIGHT/2f))?y:0, 0);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public void moveCamera(int centerX,int centerY){
        int actualX = centerX;
        int actualY = centerY;
        if(actualX<(NoAccess.WIDTH/2f)){
            actualX = NoAccess.WIDTH/2;
        }
        if(actualX>(WORLD_WIDTH-NoAccess.WIDTH/2f)){
            actualX = WORLD_WIDTH-NoAccess.WIDTH/2;
        }
        if(actualY<(NoAccess.HEIGHT/2f)){
            actualY = NoAccess.HEIGHT/2;
        }
        if(actualY>(WORLD_HEIGHT-NoAccess.HEIGHT/2f)){
            actualY = WORLD_HEIGHT-NoAccess.HEIGHT/2;
        }
        mainCamera.position.set(actualX,actualY,0f);
    }
}
