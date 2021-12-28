package space.ardyc.game_gdx.utils;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import space.ardyc.game_gdx.Bird;
import space.ardyc.game_gdx.ai_core.NeuralBrain;

import java.util.ArrayList;

public class AddonsRender {

    private final int DISTANCE_BETWEEN_NEURONS_X = 40;
    private final int DISTANCE_BETWEEN_NEURONS_Y = 20;

    private ShapeRenderer renderer;
    private Point startPosition;

    public AddonsRender(Point startPosition) {
        this.startPosition = startPosition;
        initRenderer();
    }

    private void initRenderer() {
        renderer = new ShapeRenderer();
        renderer.setColor(0, 0, 0, 1);
    }

    public void dispose(){
        renderer.dispose();
    }

    public void render(ArrayList<Bird> birds) {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderNeuralNetwork();
        renderConnections(getAlive(birds).getBrain());
        renderer.end();
    }

    private void renderNeuralNetwork() {
        for (int i = 0; i < NeuralBrain.INPUT_LAYER_NEURON_COUNT; i++) {
            renderer.circle(startPosition.getX(), startPosition.getY() - i * DISTANCE_BETWEEN_NEURONS_Y, 5);
        }
        for (int i = 0; i < NeuralBrain.HIDDEN_LAYER_NEURON_COUNT; i++) {
            renderer.circle(DISTANCE_BETWEEN_NEURONS_X + startPosition.getX(), startPosition.getY() - i * DISTANCE_BETWEEN_NEURONS_Y + 10, 5);
        }
        for (int i = 0; i < NeuralBrain.OUTPUT_LAYER_NEURON_COUNT; i++) {
            renderer.circle(DISTANCE_BETWEEN_NEURONS_X * 2 + startPosition.getX(), startPosition.getY() - i * DISTANCE_BETWEEN_NEURONS_Y, 5);
        }
    }

    private void renderConnections(NeuralBrain brain) {
        if (brain.layer1Weights[0] > 0) {
            renderer.setColor(1, 0, 0, 1);
        } else {
            renderer.setColor(0, 0, 1, 1);
        }
        renderer.rectLine(340, 550, 380, 560, 2);

        if (brain.layer1Weights[1] > 0) {
            renderer.setColor(1, 0, 0, 1);
        } else {
            renderer.setColor(0, 0, 1, 1);
        }
        renderer.rectLine(340, 550, 380, 540, 2);

        if (brain.layer1Weights[2] > 0) {
            renderer.setColor(1, 0, 0, 1);
        } else {
            renderer.setColor(0, 0, 1, 1);
        }
        renderer.rectLine(340, 550, 380, 520, 2);

        if (brain.layer1Weights[3] > 0) {
            renderer.setColor(1, 0, 0, 1);
        } else {
            renderer.setColor(0, 0, 1, 1);
        }
        renderer.rectLine(340, 530, 380, 560, 2);

        if (brain.layer1Weights[4] > 0) {
            renderer.setColor(1, 0, 0, 1);
        } else {
            renderer.setColor(0, 0, 1, 1);
        }
        renderer.rectLine(340, 530, 380, 540, 2);

        if (brain.layer1Weights[5] > 0) {
            renderer.setColor(1, 0, 0, 1);
        } else {
            renderer.setColor(0, 0, 1, 1);
        }
        renderer.rectLine(340, 530, 380, 520, 2);

        if (brain.layer2Weights[0] > 0) {
            renderer.setColor(1, 0, 0, 1);
        } else {
            renderer.setColor(0, 0, 1, 1);
        }
        renderer.rectLine(380, 560, 420, 550, 2);

        if (brain.layer2Weights[1] > 0) {
            renderer.setColor(1, 0, 0, 1);
        } else {
            renderer.setColor(0, 0, 1, 1);
        }
        renderer.rectLine(380, 560, 420, 530, 2);

        if (brain.layer2Weights[2] > 0) {
            renderer.setColor(1, 0, 0, 1);
        } else {
            renderer.setColor(0, 0, 1, 1);
        }
        renderer.rectLine(380, 540, 420, 550, 2);

        if (brain.layer2Weights[3] > 0) {
            renderer.setColor(1, 0, 0, 1);
        } else {
            renderer.setColor(0, 0, 1, 1);
        }
        renderer.rectLine(380, 540, 420, 530, 2);

        if (brain.layer2Weights[4] > 0) {
            renderer.setColor(1, 0, 0, 1);
        } else {
            renderer.setColor(0, 0, 1, 1);
        }
        renderer.rectLine(380, 520, 420, 550, 2);

        if (brain.layer2Weights[5] > 0) {
            renderer.setColor(1, 0, 0, 1);
        } else {
            renderer.setColor(0, 0, 1, 1);
        }
        renderer.rectLine(380, 520, 420, 530, 2);
    }

    private Bird getAlive(ArrayList<Bird> birds) {
        for (Bird bird : birds) {
            if (bird.isAlive()) {
                return bird;
            }
        }
        return birds.get(0);
    }

}
