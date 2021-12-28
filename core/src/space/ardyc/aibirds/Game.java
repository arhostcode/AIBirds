package space.ardyc.aibirds;

import com.badlogic.gdx.ApplicationAdapter;
import space.ardyc.aibirds.world.World;
import space.ardyc.aibirds.world.WorldRenderer;

/**
 * @author Ardyc
 * @version 1.1
 */

public class Game extends ApplicationAdapter {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 600;

    public static final int POPULATION = 20;

    private World world;
    private WorldRenderer worldRenderer;

    @Override
    public void create() {
        world = new World();
        world.create();
        worldRenderer = new WorldRenderer(world);
    }

    @Override
    public void render() {
        world.worldTick();
        worldRenderer.render();
    }

    @Override
    public void dispose() {
        worldRenderer.dispose();
    }

}
