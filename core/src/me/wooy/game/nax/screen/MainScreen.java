package me.wooy.game.nax.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.czyzby.noise4j.map.generator.room.AbstractRoomGenerator;
import com.github.czyzby.noise4j.map.generator.room.dungeon.DungeonGenerator;
import me.wooy.game.nax.NoAccess;

public class MainScreen extends ScreenAdapter implements Screen {
    private final Stage stage;
    private final Stage uiStage;
    private final Texture worldTexture = new Texture(NoAccess.worldGenerator.update());
    public MainScreen(NoAccess noAccess){
        Viewport viewport = new FitViewport(NoAccess.WIDTH, NoAccess.HEIGHT, NoAccess.mainCamera);
        Viewport uiViewport = new FitViewport(NoAccess.WIDTH,NoAccess.HEIGHT,NoAccess.uiCamera);
        stage = new Stage(viewport,noAccess.batch);
        uiStage = new Stage(uiViewport,noAccess.batch);
        Image worldTable = new Image();
        worldTable.setDrawable(new TextureRegionDrawable(worldTexture));
        worldTable.setSize(NoAccess.WORLD_WIDTH,NoAccess.WORLD_HEIGHT);
        DungeonGenerator.Point center = NoAccess.worldGenerator.getStart();
        System.out.println("Home: "+center.x+" "+center.y);
        noAccess.moveCamera(center.x,NoAccess.WORLD_HEIGHT-center.y);
        stage.setDebugAll(true);
        stage.addActor(worldTable);
        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int actualX = (int)x;
                int actualY = NoAccess.WORLD_HEIGHT - (int)y;
                AbstractRoomGenerator.Room room = NoAccess.worldGenerator.getRoom(actualX,actualY);
                if(room != null){
                    System.out.println("Click on Room: "+room.getX()+","+room.getY());
                }
                return true;
            }
        });
        uiStage.setDebugAll(true);
        //initUI();
        NoAccess.inputMultiplexer.addProcessor(stage);
        NoAccess.inputMultiplexer.addProcessor(uiStage);
    }

    private void initUI(){
        Table table = new Table();
        table.setSize(NoAccess.WIDTH,NoAccess.HEIGHT);
        Table buttonTable = new Table();
        Button button = new Button(NoAccess.skin);
        buttonTable.add(button).width(20).height(15);
        table.row().colspan(3).expand().fill();
        table.add(buttonTable).maxWidth(80).maxHeight(60).left().bottom().colspan(1).expandX();
        table.add().expandX();
        table.add().expandX();
        uiStage.addActor(table);
    }


    @Override
    public void render(float delta) {
        stage.draw();
        stage.act();
        uiStage.draw();
        uiStage.act();
        if(NoAccess.shouldUpdate){
            NoAccess.shouldUpdate = false;
            worldTexture.load(new PixmapTextureData(NoAccess.worldGenerator.update(),null,false,false));
        }
    }

    @Override
    public void dispose() {
        NoAccess.inputMultiplexer.removeProcessor(stage);
        NoAccess.inputMultiplexer.removeProcessor(uiStage);
        stage.dispose();
        uiStage.dispose();
        worldTexture.getTextureData().disposePixmap();
        worldTexture.dispose();
    }

}
