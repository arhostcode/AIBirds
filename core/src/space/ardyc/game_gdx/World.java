package space.ardyc.game_gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import space.ardyc.game_gdx.ai_core.GeneticCore;
import space.ardyc.game_gdx.utils.AddonsRender;
import space.ardyc.game_gdx.utils.Point;

import java.util.ArrayList;
import java.util.Iterator;

public class World {

    private final int START_PIPE_OFFSET = 100;
    private final int OVER_SCREEN_OFFSET = -100;
    private final int PIPE_MOVE_AMOUNT = 3;

    private SpriteBatch spriteRenderer;
    private BitmapFont fontRenderer;

    private Texture birdTexture;
    private Texture lowerPipeTexture;
    private Texture upperPipeTexture;

    private GeneticCore geneticCore;

    private Rectangle lowerPipeBoundingBox;
    private Rectangle upperPipeBoundingBox;
    private ArrayList<Bird> birdsList = new ArrayList<>();

    private int nowPopulationCount = Game.POPULATION;
    private int nowPopulationResult = 0;
    private int nowGenerationNumber = 0;

    public void create() {
        geneticCore = new GeneticCore(Game.POPULATION);
        spriteRenderer = new SpriteBatch();
        initFirstPopulation();
        loadTextures();
        initFontRenderer();
        createPipes();
    }

    private void loadTextures() {
        birdTexture = new Texture("flappy.png");
        lowerPipeTexture = new Texture("tube_down.png");
        upperPipeTexture = new Texture("tube_up.png");
    }

    private void createPipes() {
        lowerPipeBoundingBox = new Rectangle(500, -300, 80, 486);
        upperPipeBoundingBox = new Rectangle(500, 400, 80, 486);
    }

    private void initFontRenderer() {
        fontRenderer = new BitmapFont();
        fontRenderer.setColor(0, 0, 0, 1);
    }

    private void initFirstPopulation() {
        for (int i = 0; i < Game.POPULATION; i++) {
            birdsList.add(new Bird());
        }
    }

    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);
        spriteRenderer.begin();
        Iterator<Bird> birdIterator = birdsList.iterator();
        nowPopulationCount = 0;
        while (birdIterator.hasNext()) {
            Bird bird = birdIterator.next();
            if (bird.isAlive()) {
                nowPopulationCount++;
                renderBird(bird);
                if (bird.isCollided(lowerPipeBoundingBox, upperPipeBoundingBox)) {
                    bird.destroy();
                } else {
                    bird.action(upperPipeBoundingBox.y, lowerPipeBoundingBox.y + lowerPipeBoundingBox.height);
                    bird.fall();
                }
            }
        }
        if (nowPopulationCount == 0) {
            updatePopulation();
        }

        checkKeyPressing();

        renderPipes();
        renderTextInfo();
        spriteRenderer.end();

        AddonsRender.render(new Point(340, 550), birdsList);

        moveEnvironment();
    }

    private void checkKeyPressing() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            updatePopulation();
    }

    private void updatePopulation() {
        nowGenerationNumber++;
        nowPopulationResult = 0;
        birdsList = geneticCore.getNewPopulation(birdsList);
        lowerPipeBoundingBox.x = OVER_SCREEN_OFFSET;
        upperPipeBoundingBox.x = OVER_SCREEN_OFFSET;
    }

    private void renderTextInfo() {
        fontRenderer.draw(spriteRenderer, "Population: " + nowPopulationCount, 20, 20);
        fontRenderer.draw(spriteRenderer, "Generation: " + nowGenerationNumber, 150, 20);
        fontRenderer.draw(spriteRenderer, "Now result: " + nowPopulationResult, 260, 20);
    }

    private void renderBird(Bird bird) {
        spriteRenderer.draw(birdTexture, bird.getBox().x, bird.getBox().y, bird.getBox().width, bird.getBox().height);
    }

    private void renderPipes() {
        spriteRenderer.draw(lowerPipeTexture, lowerPipeBoundingBox.x, lowerPipeBoundingBox.y, lowerPipeBoundingBox.width, lowerPipeBoundingBox.height);
        spriteRenderer.draw(upperPipeTexture, upperPipeBoundingBox.x, upperPipeBoundingBox.y, upperPipeBoundingBox.width, upperPipeBoundingBox.height);
    }

    private void moveEnvironment() {
        lowerPipeBoundingBox.x -= PIPE_MOVE_AMOUNT;
        upperPipeBoundingBox.x -= PIPE_MOVE_AMOUNT;

        if (lowerPipeBoundingBox.x < 0 - lowerPipeBoundingBox.width) {
            nowPopulationResult++;
            updatePipes();
            addMovingOutOfPipeFit();
        }
    }

    private void updatePipes() {
        lowerPipeBoundingBox.x = Game.WIDTH;
        upperPipeBoundingBox.x = Game.WIDTH;
        upperPipeBoundingBox.y = START_PIPE_OFFSET + ((int) (Math.random() * (Game.HEIGHT - START_PIPE_OFFSET - START_PIPE_OFFSET)));
        lowerPipeBoundingBox.y = upperPipeBoundingBox.y - START_PIPE_OFFSET - lowerPipeBoundingBox.height;

    }

    private void addMovingOutOfPipeFit() {
        for (Bird b : birdsList) {
            if (b.isAlive())
                b.addFit(GeneticCore.MOVING_OUT_OF_PIPE_FIT);
        }
    }

    public void dispose() {
        spriteRenderer.dispose();
        birdTexture.dispose();
        lowerPipeTexture.dispose();
        upperPipeTexture.dispose();
    }

}
