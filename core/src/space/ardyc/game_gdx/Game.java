package space.ardyc.game_gdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import space.ardyc.game_gdx.ai_core.GeneticCore;
import space.ardyc.game_gdx.ai_core.NeuralBrain;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Ardyc
 * @version 1.0
 */

public class Game extends ApplicationAdapter {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 600;

    public static final int POPULATION = 20;

    private World world;

    @Override
    public void create() {
        world = new World();
        world.create();
    }

    @Override
    public void render() {
        world.render();
    }

    @Override
    public void dispose() {
        world.dispose();
    }

}
