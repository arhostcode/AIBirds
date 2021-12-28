package space.ardyc.aibirds.ai_core;

import space.ardyc.aibirds.bird.Bird;

import java.util.ArrayList;

/**
 * @author Ardyc
 * @version 1.0
 */

public class GeneticCore {

    public static final int JUMP_FIT = 1;
    public static final int MOVING_OUT_OF_PIPE_FIT = 1000;

    public static ArrayList<Bird> getNewPopulation(ArrayList<Bird> birds) {
        sort(birds);
        return createNewPopulation(birds);
    }

    private static Bird getRandomWithWeights(ArrayList<Bird> birds, int allFit) {
        int fit = (int) (Math.random() * allFit);
        for (Bird b : birds) {
            fit -= b.getFit();
            if (fit <= 0)
                return b;
        }
        return null;
    }

    private static ArrayList<Bird> createNewPopulation(ArrayList<Bird> birds){
        ArrayList<Bird> newGen = new ArrayList<>();
        int fitSum = calculateFitSum(birds);
        NeuralBrain n;
        for (int i = 0; i < birds.size(); i++) {
            n = new NeuralBrain();
            for (int j = 0; j < NeuralBrain.INPUT_LAYER_NEURON_COUNT * NeuralBrain.HIDDEN_LAYER_NEURON_COUNT + NeuralBrain.HIDDEN_LAYER_NEURON_COUNT * NeuralBrain.OUTPUT_LAYER_NEURON_COUNT; j++) {
                n.setWeight(j, getRandomWithWeights(birds, fitSum).getBrain().getWeight(j));
                if ((int) (Math.random() * 20) == 5) {
                    n.setWeight(j, Math.random() * 8 - 4);
                }
            }
            newGen.add(new Bird(n));
        }
        return newGen;
    }

    private static int calculateFitSum(ArrayList<Bird> birds){
        int fitSum = 0;
        for (Bird b : birds) {
            fitSum += b.getFit();
        }
        return fitSum;
    }

    private static ArrayList<Bird> sort(ArrayList<Bird> birds){
        Bird s;
        for (int out = birds.size() - 1; out >= 1; out--){
            for (int in = 0; in < out; in++){
                if (birds.get(in).getFit() < birds.get(in + 1).getFit()) {
                    s = birds.get(in);
                    birds.set(in, birds.get(in + 1));
                    birds.set(in + 1, s);
                }
            }
        }
        return birds;
    }

}
