package space.ardyc.game_gdx;

import com.badlogic.gdx.math.Rectangle;
import space.ardyc.game_gdx.ai_core.NeuralBrain;

/**
 * @author Ardyc
 * @version 1.0
 */

public class Bird {

    private Rectangle bird_box;
    private boolean alive = true;
    private int fit = 0;
    private NeuralBrain brain;
    private int actionTimeOut = 0;

    public Bird(int x, int y, int width, int height) {
        bird_box = new Rectangle();
        bird_box.height = height;
        bird_box.width = width;
        bird_box.y = y;
        bird_box.x = x;
        brain = new NeuralBrain();
        brain.randomize();
    }

    public Bird(int x, int y, int width, int height, NeuralBrain brain) {
        bird_box = new Rectangle();
        bird_box.height = height;
        bird_box.width = width;
        bird_box.y = y;
        bird_box.x = x;
        this.brain = brain;
    }

    public Bird(NeuralBrain brain) {
        bird_box = new Rectangle();
        bird_box.height = Game.type.getBox().height;
        bird_box.width = Game.type.getBox().width;
        bird_box.y = Game.type.getBox().y;
        bird_box.x = Game.type.getBox().x;
        this.brain = brain;
    }

    public Bird(NeuralBrain brain, int randomOffset) {
        bird_box = new Rectangle();
        bird_box.height = Game.type.getBox().height;
        bird_box.width = Game.type.getBox().width;
        bird_box.y = Game.type.getBox().y + ((int) (Math.random() * (2 * randomOffset)) - randomOffset);
        bird_box.x = Game.type.getBox().x;
        this.brain = brain;
    }

    public Bird() {
        bird_box = new Rectangle();
        bird_box.height = Game.type.getBox().height;
        bird_box.width = Game.type.getBox().width;
        bird_box.y = Game.type.getBox().y;
        bird_box.x = Game.type.getBox().x;
        brain = new NeuralBrain();
        brain.randomize();
    }

    /**
     * Getting action
     * @param dU distance to upper tube
     * @param dD distance to downer tube
     */
    public void action(float dU, float dD) {
        if (actionTimeOut <= 0) {
            if (brain.isJump(getDistanceU(dU), getDistanceD(dD)))
                translateUp();
        } else {
            actionTimeOut -= 1;
        }
    }


    /**
     * Set alive - false
     */
    public void destroy() {
        alive = false;
    }

    /**
     * Translate down
     */
    public void translateDown() {
        bird_box.y -= 2;
    }

    /**
     * Translate up
     */
    public void translateUp() {
        actionTimeOut = 3;
        bird_box.y += 20;
    }


    /**
     * Getting alive value, add fit if alive
     * @return alive
     */
    public boolean isAlive() {
        if (alive)
            fit += 1;
        return alive;
    }

    public Rectangle getBox() {
        return bird_box;
    }

    public int getFit() {
        return fit;
    }

    public NeuralBrain getBrain() {
        return brain;
    }

    /**
     * Get distance to upper tube
     * @param pos upper tube pos
     * @return distance to upper tube
     */
    private float getDistanceU(float pos) {
        return pos - getBox().y + getBox().height;
    }

    /**
     * Get distance to downer tube
     * @param pos downer tube pos
     * @return distance to downer tube
     */
    private float getDistanceD(float pos) {
        return pos - getBox().y;
    }
}
