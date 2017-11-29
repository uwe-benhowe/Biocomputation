package biocomputation.classifications;

import static biocomputation.helper.Helper.*;
import biocomputation.helper.Row2000;
import biocomputation.helper.Individual2000;

import java.io.*;
import java.util.*;

public class Classification2000 {

    static final int POP_SIZE = 100;
    static final int NUM_GEN = 500;
    static final int NUM_RULES = 10;
    static final int NUM_GENES = NUM_RULES * 13;

    static Individual2000[] population = new Individual2000[POP_SIZE];
    static Individual2000[] offspring = new Individual2000[POP_SIZE];
    static Individual2000 fittest = new Individual2000(NUM_GENES);
    static Individual2000 worst = new Individual2000(NUM_GENES);

    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, IOException {
        
        Row2000[][] data = getData2000();       
        Row2000[] trainingData = data[0];
        Row2000[] testData = data[1];
       
        int highestFitness = 0;
        
        for (int genNum = 0; genNum < NUM_GEN; genNum++) {
            
            int initialFitness = 0;
            int currentFitness = 0;
            int offspringFitness = 0;
            int averageFitness = 0;
            int lowestFitness = 2000; //Start at the highest and then lower
            
            if (genNum == 0) { //First generation
                
                //Initalising population
                for (int i = 0; i < POP_SIZE; i++) {

                    population[i] = new Individual2000(NUM_GENES);
                    
                    for (int j = 1; j <= NUM_GENES; j++) {
                        double random = randomDouble(0, 1);
                        if (j % 13 == 0) {
                            int output = random();
                            random = (double) output;
                        }
                        population[i].setSingleGenes(j - 1, random);
                    }
                    population[i].setFitness(0);
                }

                //Calculating fitness of the population
                for (int i = 0; i < POP_SIZE; i++) {
                    Individual2000 rando = population[i];                
                    population[i].setFitness(calcFitness(rando, trainingData));
                    initialFitness += population[i].getFitness();
                }

                System.out.println("Generation Number: \t\t\t" + genNum
                        + "\nPopulation Size: \t\t\t" + POP_SIZE
                        + "\nNumber of Genes: \t\t\t" + NUM_GENES
                        + "\nInitial Fitness: \t\t\t" + initialFitness);

            } else { //All other generations

                for (int k = 0; k < POP_SIZE; k++) {
                    for (int m = 0; m < NUM_GENES; m++) {                
                        population[k].setSingleGenes(m, offspring[k].getSingleGenes(m));
                    }
                    population[k].setFitness(offspring[k].getFitness());
                }
                
                System.out.println("Generation Number: \t\t\t" + genNum);

            }
            
            //Tournement Selection
            for (int i = 0; i < POP_SIZE; i++) {
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

            //re-Calc fitness
            for (int i = 0; i < POP_SIZE; i++) {
                offspring[i].setFitness(calcFitness(offspring[i], trainingData));
                currentFitness += offspring[i].getFitness();

            }

            System.out.println("Post Tournament Fitness: \t\t" + currentFitness);

            //Crossover
            offspring = executeCrossover(offspring, NUM_GENES);

            //Mutatiom
            for (int x = 0; x < POP_SIZE; x++) {
                double[] genes = offspring[x].getGenes();
                
                genes = mutation(genes);
                for (int y = 0; y < genes.length; y++) {
                    offspring[x].setSingleGenes(y, genes[y]);
                }
            }

            //Re-Assess Fitness
            int worstCounter = 0;
            for (int i = 0; i < POP_SIZE; i++) {
                offspring[i].setFitness(calcFitness(offspring[i], trainingData));
                if (highestFitness < offspring[i].getFitness()) {
                    highestFitness = offspring[i].getFitness();
                    for (int k = 0; k < NUM_GENES; k++) {
                        fittest.setSingleGenes(k, offspring[i].getSingleGenes(k));
                        if (k == NUM_GENES - 1) {
                            fittest.setFitness(offspring[i].getFitness());
                        }
                    }

                }
                
                //Keeping count of lowest fitnesses
                if(lowestFitness > offspring[i].getFitness()){
                    lowestFitness = offspring[i].getFitness();
                    for (int j = 0; j < NUM_GENES; j++) {
                        worst.setSingleGenes(j, offspring[i].getSingleGenes(j));
                        
                    }
                    worst.setFitness(offspring[i].getFitness());
                    worstCounter = i;
                }
                offspringFitness += offspring[i].getFitness();
            }
            
            averageFitness = offspringFitness / POP_SIZE;
            
            System.out.println("Post crossover & mutation fitness: \t" + offspringFitness);
            System.out.println("Highest/Average/Lowest fitness: \t" + 
                    highestFitness + "/" + averageFitness + "/" + lowestFitness + "\n");
            
            //Writing current generation to file
            writeLine("3", genNum, highestFitness, NUM_GEN, POP_SIZE, NUM_GENES);
            
            offspring[worstCounter].setGenes(fittest.getGenes());
            offspring[worstCounter].setFitness(fittest.getFitness());
                        
        }
        
        System.out.println("Fitest Overall Individual: \t\t" + fittest.getFitness());
        System.out.println("Created file: \t\t\t\t" + "data64-" + NUM_GEN + "-" + POP_SIZE + "-" + NUM_GENES + ".csv");
        
//      System.out.println("\n10 Rules from the fittest individual: ");

//      Row2000[] rules = getRules2000(fittest, NUM_RULES);

//      for (int i = 0; i < 10; i++) {
//          System.out.println(Arrays.toString(rules[i].var) + " " + rules[i].predicted);
//      }
        
        //Running the test data agaisnt rules generated by training data
        double tFit = (double) calcFitness(fittest, testData);
        double percentage = (tFit / 500) * 100;
        System.out.println("Test Data Results: \t\t\t" + tFit + "/500 = " + 
                String.valueOf(percentage).substring(0,4) + "%");
 
    }

    private static int calcFitness(Individual2000 fittest, Row2000[] ruleSet) {
        int fitness = 0;
        Row2000[] fittestRules = getRules2000(fittest, NUM_RULES);

        for (Row2000 rule : ruleSet) {
            for (int j = 0; j < NUM_RULES; j++) {
                if (compareRules2000(fittestRules[j].getInputVariable(), rule.getInputVariable())) {
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
