package space.ardyc.game_gdx.ai_core;

import java.util.Arrays;

/**
 * @author Ardyc
 * @version 1.0
 */

public class NeuralBrain {

    public double[] l0 = new double[2];
    public double[] l1 = new double[3];
    public double[] l2 = new double[2];

    public double[] l0weight = new double[6];
    public double[] l1weight = new double[6];

    /**
     * Getting action
     * @param d1 distance to upper tube
     * @param d2 distance to downer tube
     * @return boolean jump or not
     */

    public boolean isJump(float d1, float d2) {
        zeros();
        l0[0] = d1;
        l0[1] = d2;

        int k = 0;

        for (int i = 0; i < l1.length; i++) {
            for (double v : l0) {
                l1[i] += v * l0weight[k];
                k += 1;
            }
        }

        k = 0;
        for (int i = 0; i < l2.length; i++) {
            for (double v : l1) {
                l2[i] += v * l1weight[k];
                k += 1;
            }
        }

        return l2[0] > l2[1];
    }

    /**
     * Randomizing weights
     */
    public void randomize() {
        for (int i = 0; i < l0weight.length; i++) {
            l0weight[i] = Math.random() * 8 - 4;
        }
        for (int i = 0; i < l1weight.length; i++) {
            l1weight[i] = Math.random() * 8 - 4;
        }

    }

    /**
     * Get weights
     * @param i weight id
     * @return weight value
     */
    public double getWeight(int i) {
        if (i < 6)
            return l0weight[i];
        else
            return l1weight[i - 6];
    }

    /**
     * Setting weight
     * @param i weight id
     * @param w weight value
     */
    public void setWeight(int i, double w) {
        if (i < 6)
            l0weight[i] = w;
        else
            l1weight[i - 6] = w;
    }

    /**
     * Fill neurons values zeros
     */
    private void zeros() {
        Arrays.fill(l1, 0);
        Arrays.fill(l2, 0);
    }
}
