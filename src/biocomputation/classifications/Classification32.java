package biocomputation.classifications;

import static biocomputation.helper.Helper.*;
import biocomputation.helper.Row32;
import biocomputation.helper.Individual;

import java.io.*;
import java.util.*;

public class Classification32 {

    static final int POP_SIZE = 100;
    static final int NUM_GEN = 500;
    static final int NUM_RULES = 15;
    static final int NUM_GENES = NUM_RULES * 6;

    static Individual[] population = new Individual[POP_SIZE];
    static Individual[] offspring = new Individual[POP_SIZE];
    static Individual fittest = new Individual(POP_SIZE);

    public static void main(String[] args) throws UnsupportedEncodingException,
            FileNotFoundException, IOException {

        Row32[] input32 = getData32();

        int i, j;

        int highestFitness = 0;


        for (int genNum = 0; genNum < NUM_GEN; genNum++) {
            int InitialFitness = 0;
            int currentFitness = 0;
            int offspringFitness = 0;
                    
            if (genNum == 0) {
                for (i = 0; i < POP_SIZE; i++) {
                    population[i] = new Individual(NUM_GENES);
                    for (j = 0; j < NUM_GENES; j++) {
                        population[i].setSingleGenes(j, random());
                    }
                    population[i].setFitness(0);
                }

                for (i = 0; i < POP_SIZE; i++) {
                    Individual indiv = population[i];
                    population[i].setFitness(calcFitness(indiv, input32));
                    InitialFitness += population[i].getFitness();
                }

                System.out.println("Generation Number: \t\t\t" + genNum
                        + "\nPopulation Size: \t\t\t" + POP_SIZE
                        + "\nNumber of Genes: \t\t\t" + NUM_GENES
                        + "\nInitial Fitness: \t\t\t" + InitialFitness);

            } else {
                 for (int k = 0; k < POP_SIZE; k++) {
                    for (int m = 0; m < NUM_GENES; m++) {     
                        population[k].setSingleGenes(m, offspring[k].getSingleGenes(m));
                    }
                    population[k].setFitness(offspring[k].getFitness());
                }

                System.out.println("Generation Number: \t\t\t" + genNum);
            }

            //Tournement Selection
            for (i = 0; i < POP_SIZE; i++) {
                int parent1 = randomRange(0, POP_SIZE - 1);
                int parent2 = randomRange(0, POP_SIZE - 1);

                int p1Fitness = population[parent1].getFitness();
                int p2Fitness = population[parent2].getFitness();

                if (p1Fitness >= p2Fitness) {
                    offspring[i] = population[parent1];
                } else {
                    offspring[i] = population[parent2];
                }
            }

            //Check the fitness after tournament selection
            for (i = 0; i < POP_SIZE; i++) {
                offspring[i].setFitness(calcFitness(offspring[i], input32));
                currentFitness += offspring[i].getFitness();
            }

            System.out.println("Post Tournament Fitness: \t\t" + currentFitness);

            //Crossover
            offspring = executeCrossover(offspring, NUM_GENES);

            //Mutation    
            for (int x = 0; x < POP_SIZE; x++) {
                int[] genes = offspring[x].getGenes();
                genes = mutation32(genes);

                for (int y = 0; y < genes.length; y++) {
                    offspring[x].setSingleGenes(y, genes[y]);
                }
            }

            //Fittness Re-assesment    
            for (i = 0; i < POP_SIZE; i++) {
                offspring[i].setFitness(calcFitness(offspring[i], input32));
                if (highestFitness < offspring[i].getFitness()) {
                    highestFitness = offspring[i].getFitness();
                    fittest = offspring[i];
                }
                offspringFitness += offspring[i].getFitness();
            }
            
            System.out.println("Post crossover/mutation fitness: \t" + offspringFitness);
            System.out.println("Fitest Individual: \t\t\t" + highestFitness + "\n");

            writeLine("1", genNum, highestFitness, NUM_GEN, POP_SIZE, NUM_GENES);         
        }
        System.out.println("Fitest Overall Individual: \t\t" + fittest.getFitness());
        System.out.println("Created file: \t\t\t\t" + "data32-" + NUM_GEN + "-" + POP_SIZE + "-" + NUM_GENES + ".csv");
        System.out.println("\n10 Rules from the fittest individual: ");
        
        Row32[] rules = getRules32(fittest, NUM_RULES);
  
        for (i = 0; i < 10; i++) {
            System.out.println(Arrays.toString(rules[i].getInputVariable())
                    + " " + rules[i].getPredicted());
        }

    }

    private static int calcFitness(Individual fittest, Row32[] ruleSet) {
        int fitness = 0;
        Row32[] fittestRules = getRules32(fittest, NUM_RULES);

        for (Row32 rule : ruleSet) {
            for (int j = 0; j < NUM_RULES; j++) {
                if (compareRules32(fittestRules[j].getInputVariable(), rule.getInputVariable())) {
                    if (rule.getPredicted() == fittestRules[j].getPredicted()) {
                        fitness++;
                    }
                    break;
                }
            }
        }
        return fitness;
    }

}
