package biocomputation.classifications;

import static biocomputation.helper.Helper.*;
import biocomputation.helper.Row64;
import biocomputation.helper.Individual;

import java.io.*;
import java.util.*;

public class Classification64 {

    static final int POP_SIZE = 300;
    static final int NUM_GEN = 500;
    static final int NUM_RULES = 30;
    static final int NUM_GENES = NUM_RULES * 7;
    
    static Individual[] population = new Individual[POP_SIZE];
    static Individual[] offspring = new Individual[POP_SIZE];
    static Individual fittest = new Individual(NUM_GENES);

    public static void main(String[] args) throws UnsupportedEncodingException, 
            FileNotFoundException, IOException {
        
        Row64[] input64 = getData64();

        int i, j;
        
        int highestFitness = 0;

        for (int genNum = 0; genNum < NUM_GEN; genNum++) {

            int initialFitness = 0;
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
                    Individual rando = population[i];
                    population[i].setFitness(calcFitness(rando, input64));
                    initialFitness += population[i].getFitness();
                }

                System.out.println("Generation Number: \t\t\t" + genNum
                        + "\nPopulation Size: \t\t\t" + POP_SIZE
                        + "\nNumber of Genes: \t\t\t" + NUM_GENES
                        + "\nInitial Fitness: \t\t\t" + initialFitness);
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
                offspring[i].setFitness(calcFitness(offspring[i], input64));
                currentFitness += offspring[i].getFitness();
            }

            System.out.println("Post Tournament Fitness: \t\t" + currentFitness);

            //Crossover
            offspring = executeCrossover(offspring, NUM_GENES);
       
            //Mutation
            for (int x = 0; x < POP_SIZE; x++) {
                int[] genes = offspring[x].getGenes();
                genes = mutation(genes);
                
                for (int y = 0; y < genes.length; y++) {
                    offspring[x].setSingleGenes(y, genes[y]);
                }
            }

            //Fittness Re-assesment
            for (i = 0; i < POP_SIZE; i++) {
                offspring[i].setFitness(calcFitness(offspring[i], input64));
                if (highestFitness < offspring[i].getFitness()) {
                    highestFitness = offspring[i].getFitness();
                    
                    for (int k = 0; k < NUM_GENES; k++) { 
                        fittest.setSingleGenes(k, offspring[i].getSingleGenes(k));
                        
                        if (k == NUM_GENES - 1) {
                            fittest.setFitness(offspring[i].getFitness());
                        }
                    }

                }
                offspringFitness += offspring[i].getFitness();
            }

            System.out.println("Post crossover/mutation fitness: \t" + offspringFitness);
            System.out.println("Fitest Individual: \t\t\t" + highestFitness + "\n");

            writeLine("2", genNum, highestFitness, NUM_GEN, POP_SIZE, NUM_GENES);
        }
        
        System.out.println("Fitest Overall Individual: \t\t" + fittest.getFitness());
        System.out.println("Created file: \t\t\t\t" + "data64-" + NUM_GEN + "-" + POP_SIZE + "-" + NUM_GENES + ".csv");
        System.out.println("\n10 Rules from the fittest individual: ");
        
        
        Row64[] rules = getRules64(fittest, NUM_RULES);
        
        for (i = 0; i < 10; i++) {
            System.out.println(Arrays.toString(rules[i].getInputVariable()) 
                    + " " + rules[i].getPredicted());
        }
    }

    
    private static int calcFitness(Individual fittest, Row64[] ruleSet) {
        int fitness = 0;
        Row64[] fittestRules = getRules64(fittest, NUM_RULES);

        for (Row64 rule : ruleSet) {
            for (int j = 0; j < NUM_RULES; j++) {
                if (compareRules64(fittestRules[j].getInputVariable(), rule.getInputVariable())) {
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
