package space.ardyc.aibirds.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import space.ardyc.aibirds.Game;
import space.ardyc.aibirds.ai_core.GeneticCore;
import space.ardyc.aibirds.bird.Bird;

import java.util.ArrayList;
import java.util.Iterator;

public class World {

    private final int START_PIPE_OFFSET = 100;
    private final int OVER_SCREEN_OFFSET = -100;
    private final int PIPE_MOVE_AMOUNT = 3;

    protected Rectangle lowerPipeBoundingBox;
    protected Rectangle upperPipeBoundingBox;
    private ArrayList<Bird> birdsList = new ArrayList<>();

    protected int nowPopulationCount = Game.POPULATION;
    protected int nowPopulationResult = 0;
    protected int nowGenerationNumber = 0;

    public void create() {
        initFirstPopulation();
        createPipes();
    }

    private void createPipes() {
        lowerPipeBoundingBox = new Rectangle(500, -300, 80, 486);
        upperPipeBoundingBox = new Rectangle(500, 400, 80, 486);
    }

    private void initFirstPopulation() {
        for (int i = 0; i < Game.POPULATION; i++) {
            birdsList.add(new Bird());
        }
    }

    public void worldTick() {
        ScreenUtils.clear(1, 1, 1, 1);
        Iterator<Bird> birdIterator = birdsList.iterator();
        nowPopulationCount = 0;
        while (birdIterator.hasNext()) {
            Bird bird = birdIterator.next();
            if (bird.isAlive()) {
                nowPopulationCount++;
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

        moveEnvironment();

    }

    private void checkKeyPressing() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            updatePopulation();
    }

    private void updatePopulation() {
        nowGenerationNumber++;
        nowPopulationResult = 0;
        birdsList = GeneticCore.getNewPopulation(birdsList);
        lowerPipeBoundingBox.x = OVER_SCREEN_OFFSET;
        upperPipeBoundingBox.x = OVER_SCREEN_OFFSET;
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

    public ArrayList<Bird> getBirdsList() {
        return birdsList;
    }
}
