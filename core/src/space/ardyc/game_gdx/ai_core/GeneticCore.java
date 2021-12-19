package space.ardyc.game_gdx.ai_core;

import space.ardyc.game_gdx.Bird;

import java.util.ArrayList;

/**
 * @author Ardyc
 * @version 1.0
 */

public class GeneticCore {

    public int playersCount = 10;
    public int bestCount = 5;

    public GeneticCore(int playersCount, int bestCount) {
        this.playersCount = playersCount;
        this.bestCount = bestCount;
    }

    /**
     * Get new generation
     *
     * @param players old generation
     * @return new generation
     */
    public ArrayList<Bird> getNewGen(ArrayList<Bird> players) {

        ArrayList<Bird> newGen = new ArrayList<>();

        boolean sort = true;
        Bird s;
        while (sort) {
            sort = false;
            for (int i = 0; i < players.size() - 1; i++) {
                if (players.get(i).getFit() < players.get(i + 1).getFit()) {
                    s = players.get(i);
                    players.set(i, players.get(i + 1));
                    players.set(i + 1, s);
                    sort = true;
                }
            }
        }

        int sum = 0;
        for (Bird b : players) {
            sum += b.getFit();
        }

        NeuralBrain n;
        for (int i = 0; i < playersCount; i++) {
            n = new NeuralBrain();
            for (int j = 0; j < 12; j++) {
//                n.setWeight(j, players.get((int)(Math.random() * bestCount)).getBrain().getWeight(j));
                n.setWeight(j, getRandomWithWeights(players, sum).getBrain().getWeight(j));

                if ((int) (Math.random() * 20) == 5) {
                    n.setWeight(j, Math.random() * 8 - 4);
                }
            }
            newGen.add(new Bird(n));
        }

        return newGen;

    }

    /**
     * Get generation from best birds
     *
     * @param players old generation
     * @return best generation
     */

    public ArrayList<Bird> getBestGen(ArrayList<Bird> players) {

        ArrayList<Bird> newGen = new ArrayList<>();

        boolean sort = true;
        Bird s;
        while (sort) {
            sort = false;
            for (int i = 0; i < players.size() - 1; i++) {
                if (players.get(i).getFit() < players.get(i + 1).getFit()) {
                    s = players.get(i);
                    players.set(i, players.get(i + 1));
                    players.set(i + 1, s);
                    sort = true;
                }
            }
        }

        NeuralBrain n;
        for (int i = 0; i < playersCount; i++) {
            n = new NeuralBrain();
            for (int j = 0; j < 12; j++) {
                n.setWeight(j, players.get(0).getBrain().getWeight(j));
            }
            newGen.add(new Bird(n, 40));
        }

        return newGen;

    }

    private Bird getRandomWithWeights(ArrayList<Bird> birds, int allFit) {
        int fit = (int) (Math.random() * allFit);
        for (Bird b : birds) {
            fit -= b.getFit();
            if (fit <= 0)
                return b;
        }
        return null;
    }

}
