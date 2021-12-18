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

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Ardyc
 * @version 1.0
 */

public class Game extends ApplicationAdapter {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 600;
    public static final int TUBE_OFFSET = 100;
    public static final int POPULATION = 1000;
    public static final int BEST_POPULATION = 30;

    /**
     * Typical bird
     */
    public static Bird type = new Bird(50, 255, 75, 45);

    private GeneticCore core;
    private SpriteBatch batch;
    private ShapeRenderer renderer;
    private BitmapFont font;
    private Texture birdT;
    private Texture tubeTD;
    private Texture tubeTU;
    private Rectangle tubeTDR;
    private Rectangle tubeTUR;
    private ArrayList<Bird> birds = new ArrayList<>();

    private int nowPopulation = POPULATION;
    private int nowResult = 0;
    private int nowGeneration = 0;


    @Override
    public void create() {
        core = new GeneticCore(POPULATION, BEST_POPULATION);
        for (int i = 0; i < POPULATION; i++) {
            birds.add(new Bird());
        }

        renderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();

        font.setColor(0,0,0,1);

        tubeTDR = new Rectangle(500, -300, 80, 486);
        tubeTUR = new Rectangle(500, 400, 80, 486);

        birdT = new Texture("flappy.png");
        tubeTD = new Texture("tube_down.png");
        tubeTU = new Texture("tube_up.png");
    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();
        Iterator<Bird> birdIterator = birds.iterator();
        nowPopulation = 0;
        while (birdIterator.hasNext()) {
            Bird bird = birdIterator.next();
            if (bird.isAlive()) {
                nowPopulation++;
                batch.draw(birdT, bird.getBox().x, bird.getBox().y, bird.getBox().width, bird.getBox().height);
                if (bird.getBox().overlaps(tubeTDR) | bird.getBox().overlaps(tubeTUR) | bird.getBox().y < 0 | bird.getBox().y + bird.getBox().height > HEIGHT) {
                    bird.destroy();
                } else {
                    bird.action(tubeTUR.y, tubeTDR.y + tubeTDR.height);
                    bird.translateDown();
                }
            }
        }
        if (nowPopulation == 0 | Gdx.input.isKeyPressed(Input.Keys.SPACE) ) {
            nowGeneration++;
            nowResult = 0;
            birds = core.getNewGen(birds);
            tubeTDR.x = -100;
            tubeTUR.x = -100;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S) ) {
            nowGeneration++;
            nowResult = 0;
            birds = core.getBestGen(birds);
            tubeTDR.x = -100;
            tubeTUR.x = -100;
        }


        batch.draw(tubeTD, tubeTDR.x, tubeTDR.y, tubeTDR.width, tubeTDR.height);
        batch.draw(tubeTU, tubeTUR.x, tubeTUR.y, tubeTUR.width, tubeTUR.height);

        font.draw(batch, "Population: " + nowPopulation, 20, 20);
        font.draw(batch, "Generation: " + nowGeneration, 150, 20);
        font.draw(batch, "Now result: " + nowResult, 260, 20);

        batch.end();


        moveTubes();
    }

    @Override
    public void dispose() {
        batch.dispose();
        birdT.dispose();
        tubeTD.dispose();
        tubeTU.dispose();
    }

    /**
     * Move tubes by x
     */
    private void moveTubes() {
        tubeTDR.x -= 3;
        tubeTUR.x -= 3;

        if (tubeTDR.x < 0 - tubeTDR.width) {
            tubeTDR.x = WIDTH;
            tubeTUR.x = WIDTH;
            tubeTUR.y = TUBE_OFFSET + ((int) (Math.random() * (HEIGHT - TUBE_OFFSET - TUBE_OFFSET)));
            tubeTDR.y = tubeTUR.y - TUBE_OFFSET - tubeTDR.height;
            nowResult++;
        }
    }

}
