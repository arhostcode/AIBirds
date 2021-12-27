package space.ardyc.game_gdx;

import com.badlogic.gdx.math.Rectangle;
import space.ardyc.game_gdx.ai_core.GeneticCore;
import space.ardyc.game_gdx.ai_core.NeuralBrain;
import space.ardyc.game_gdx.utils.BirdCharacteristic;

/**
 * @author Ardyc
 * @version 1.0
 */

public class Bird {

    private final int MOVING_DOWN_AMOUNT = 2;
    private final int MOVING_UP_AMOUNT = 20;
    private final int MOVING_UP_ACTION_TIME_AMOUNT = 3;

    private final Rectangle boundingBox;
    private boolean alive = true;
    private int fit = 0;
    private NeuralBrain brain;
    private int actionTimeOut = 0;

    public Bird(BirdCharacteristic characteristic) {
        boundingBox = new Rectangle();
        boundingBox.height = characteristic.getHeight();
        boundingBox.width = characteristic.getWidth();
        boundingBox.y = characteristic.getStartPosition().getY();
        boundingBox.x = characteristic.getStartPosition().getX();
    }

    public Bird(BirdCharacteristic characteristic, NeuralBrain brain) {
        this(characteristic);
        this.brain = brain;
    }

    public Bird(NeuralBrain brain) {
        this(BirdCharacteristic.createDefaultCharacteristic());
        this.brain = brain;
    }

    public Bird() {
        this(BirdCharacteristic.createDefaultCharacteristic());
        brain = new NeuralBrain();
        brain.randomizeWeights();
    }

    public void action(float upperPipePositionY, float lowerPipePositionY) {
        if (actionTimeOut <= 0) {
            addFit(GeneticCore.JUMP_FIT);
            if (brain.isJump(getDistanceUpperPipe(upperPipePositionY), getDistanceLowerPipe(lowerPipePositionY)))
                jump();
        } else {
            actionTimeOut -= 1;
        }
    }

    public boolean isCollided(Rectangle lowerPipe, Rectangle upperPipe){
        return getBox().overlaps(lowerPipe) | getBox().overlaps(upperPipe) | getBox().y < 0 | getBox().y + getBox().height > Game.HEIGHT;
    }

    public void destroy() {
        alive = false;
    }

    public void fall() {
        boundingBox.y -= MOVING_DOWN_AMOUNT;
    }

    public void jump() {
        actionTimeOut = MOVING_UP_ACTION_TIME_AMOUNT;
        boundingBox.y += MOVING_UP_AMOUNT;
    }

    public boolean isAlive() {
        return alive;
    }

    public Rectangle getBox() {
        return boundingBox;
    }

    public int getFit() {
        return fit;
    }

    public NeuralBrain getBrain() {
        return brain;
    }

    private float getDistanceUpperPipe(float pipeY) {
        return pipeY - getBox().y + getBox().height;
    }

    private float getDistanceLowerPipe(float pipeY) {
        return pipeY - getBox().y;
    }

    public void addFit() {
        fit++;
    }

    public void addFit(int amount) {
        fit += amount;
    }
}
