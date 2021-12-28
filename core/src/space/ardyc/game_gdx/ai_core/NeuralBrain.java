package space.ardyc.game_gdx.ai_core;

import java.util.Arrays;

/**
 * @author Ardyc
 * @version 1.0
 */

public class NeuralBrain {

    public static final int INPUT_LAYER_NEURON_COUNT = 2;
    public static final int OUTPUT_LAYER_NEURON_COUNT = 2;
    public static final int HIDDEN_LAYER_NEURON_COUNT = 3;

    private final int RANDOM_WEIGHT_OFFSET = 4;

    public double[] layer0 = new double[INPUT_LAYER_NEURON_COUNT];
    public double[] layer1 = new double[HIDDEN_LAYER_NEURON_COUNT];
    public double[] layer2 = new double[OUTPUT_LAYER_NEURON_COUNT];

    public double[] layer1Weights = new double[INPUT_LAYER_NEURON_COUNT * HIDDEN_LAYER_NEURON_COUNT];
    public double[] layer2Weights = new double[OUTPUT_LAYER_NEURON_COUNT * HIDDEN_LAYER_NEURON_COUNT];

    public boolean isJump(float distanceToUpperPipe, float distanceToLowerPipe) {
        fillWeightsArraysWithZero();
        layer0[0] = distanceToUpperPipe;
        layer0[1] = distanceToLowerPipe;

        calculateHiddenLayerNeurons();
        calculateOutputLayerNeurons();

        return layer2[0] > layer2[1];
    }

    private void calculateHiddenLayerNeurons() {
        int weightIterator = 0;
        for (int i = 0; i < layer1.length; i++) {
            for (double layer0NeuronValue : layer0) {
                layer1[i] += layer0NeuronValue * layer1Weights[weightIterator];
                weightIterator += 1;
            }
        }
    }

    private void calculateOutputLayerNeurons() {
        int weightIterator = 0;
        for (int i = 0; i < layer2.length; i++) {
            for (double layer1NeuronValue : layer1) {
                layer2[i] += layer1NeuronValue * layer2Weights[weightIterator];
                weightIterator += 1;
            }
        }
    }

    public void randomizeWeights() {
        for (int i = 0; i < layer1Weights.length; i++) {
            layer1Weights[i] = Math.random() * (RANDOM_WEIGHT_OFFSET * 2) - RANDOM_WEIGHT_OFFSET;
        }
        for (int i = 0; i < layer2Weights.length; i++) {
            layer2Weights[i] = Math.random() * (RANDOM_WEIGHT_OFFSET * 2) - RANDOM_WEIGHT_OFFSET;
        }

    }

    public double getWeight(int weightID) {
        if (weightID < INPUT_LAYER_NEURON_COUNT * HIDDEN_LAYER_NEURON_COUNT)
            return layer1Weights[weightID];
        else if (weightID < INPUT_LAYER_NEURON_COUNT * HIDDEN_LAYER_NEURON_COUNT + HIDDEN_LAYER_NEURON_COUNT * OUTPUT_LAYER_NEURON_COUNT)
            return layer2Weights[weightID - INPUT_LAYER_NEURON_COUNT * HIDDEN_LAYER_NEURON_COUNT];
        else throw new ArrayIndexOutOfBoundsException("Goes beyond the arrays of weights");
    }

    public void setWeight(int weightID, double weightValue) {
        if (weightID < INPUT_LAYER_NEURON_COUNT * HIDDEN_LAYER_NEURON_COUNT)
            layer1Weights[weightID] = weightValue;
        else if (weightID < INPUT_LAYER_NEURON_COUNT * HIDDEN_LAYER_NEURON_COUNT + HIDDEN_LAYER_NEURON_COUNT * OUTPUT_LAYER_NEURON_COUNT)
            layer2Weights[weightID - INPUT_LAYER_NEURON_COUNT * HIDDEN_LAYER_NEURON_COUNT] = weightValue;
        else throw new ArrayIndexOutOfBoundsException("Goes beyond the arrays of weights");
    }

    private void fillWeightsArraysWithZero() {
        Arrays.fill(layer1, 0);
        Arrays.fill(layer2, 0);
    }
}
