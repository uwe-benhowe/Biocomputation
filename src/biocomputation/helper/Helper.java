package biocomputation.helper;

import java.io.*;
import java.util.*;

public class Helper {

    public static String out = "src/biocomputation/output/";

    public static String data1Path = "src/biocomputation/data/data1.txt";
    public static String data2Path = "src/biocomputation/data/data2.txt";
    public static String data3Path = "src/biocomputation/data/data3.txt";

    /**Mutation method specific to data1.
     * Mutates a set of genes from data1.
     * @param genes Genes to be mutated.
     * @return Mutated genes.
     */
    public static int[] mutation32(int[] genes) {
        int length = genes.length;
        int[] newGenes = new int[length];

        for (int y = 1; y < length; y++) {
            int luck = randomRange(0, 500);
            if (luck == 7) {
                switch (genes[y]) {
                    case 0:
                        int luck2 = random();
                        if (luck2 == 1) {
                            newGenes[y - 1] = 1;
                        } else {
                            if (!(y % 6 == 0)) {
                                newGenes[y - 1] = 2;
                            } else {
                                newGenes[y - 1] = 1;
                            }
                        }
                        break;
                    case 1:
                        int luck3 = random();
                        if (luck3 == 1) {
                            newGenes[y - 1] = 0;
                        } else {
                            if (!(y % 6 == 0)) {
                                newGenes[y - 1] = 2;
                            } else {
                                newGenes[y - 1] = 0;
                            }

                        }
                        break;
                    default:
                        int luck4 = random();
                        if (luck4 == 1) {
                            newGenes[y - 1] = 0;
                        } else {
                            newGenes[y - 1] = 1;
                        }
                        break;
                }
            } else {
                newGenes[y - 1] = genes[y - 1];
            }
        }
        return newGenes;
    }

    /**
     * Boolean Mutation Mutation Mutation Takes in an array of genes and mutates
     * them.
     *
     * @param genes Array of genes
     * @return returns an array of mutated genes
     */
    public static int[] mutation(int[] genes) {
        int length = genes.length;

        int[] mGenes = new int[length];

        for (int y = 1; y < length; y++) {
            int luck = randomRange(0, 300);
            if (luck == 10) {
                switch (genes[y]) {
                    case 0:
                        int luck2 = random();
                        if (luck2 == 1) {
                            mGenes[y - 1] = 1;
                        } else if (!(y % 7 == 0)) {
                            mGenes[y - 1] = 2;
                        } else {
                            mGenes[y - 1] = 1;
                        }
                        break;
                    case 1:
                        int luck3 = random();
                        if (luck3 == 1) {
                            mGenes[y - 1] = 0;
                        } else if (!(y % 7 == 0)) {
                            mGenes[y - 1] = 2;
                        } else {
                            mGenes[y - 1] = 0;
                        }
                        break;
                    default:
                        int luck4 = random();
                        if (luck4 == 1) {
                            mGenes[y - 1] = 0;
                        } else {
                            mGenes[y - 1] = 1;
                        }
                        break;
                }
            } else {
                mGenes[y - 1] = genes[y - 1];
            }
        }
        return mGenes;
    }

    /**
     * Double Mutation Mutation Mutation takes in an array of genes and mutates
     * them.
     *
     * @param genes Array of genes
     * @return Returns an array of mutated genes.
     */
    public static double[] mutation(double[] genes) {
        int length = genes.length;

        for (int y = 1; y < length; y++) {
            int luck = randomRange(0, 100);
            if (luck == 1) {
                int luck3 = random();
                if (luck3 == 1) {
                    genes[y] += 0.1;
                } else {
                    genes[y] += -0.1;
                }
            }
        }
        return genes;

    }

    /**
     * Integer Crossover Crossover Takes in two individuals genes and crosses
     * them over.
     *
     * @param parent1 First array of genes
     * @param parent2 Second array of genes
     * @return returns the crossed over array of genes
     */
    private static int[][] crossover(int[] parent1, int[] parent2) {
        int numGenes = parent1.length;

        int[][] children = new int[2][numGenes];
        int crossPoint = randomRange(1, numGenes - 1);

        int[] temp = parent1.clone();
        for (int i = crossPoint; i < numGenes; i++) {
            parent1[i] = parent2[i];
            parent2[i] = temp[i];
        }

        children[0] = parent1;
        children[1] = parent2;

        return children;
    }

    /**
     * Double Crossover Double Crossover Takes in two individuals genes and
     * crosses them over
     *
     * @param parent1 First array of genes
     * @param parent2 Second array of genes
     * @return returns the crossed over array of genes
     */
    private static double[][] crossover(double[] parent1, double[] parent2) {
        int numGenes = parent1.length;
        double[][] children = new double[2][numGenes];
        int crossPoint = randomRange(1, numGenes - 1);

        double[] temp = parent1.clone();
        for (int i = crossPoint; i < numGenes; i++) {
            parent1[i] = parent2[i];
            parent2[i] = temp[i];
        }

        children[0] = parent1;
        children[1] = parent2;

        return children;
    }

    /**
     * Execute Crossover Execute Crossover Takes in an array of individuals and
     * crosses over the genes of individual i and i + 1.
     *
     * @param offspring input population
     * @param numGenes Number of genes specified
     * @return Returns an array of newly crossed over individuals.
     */
    public static Individual[] executeCrossover(Individual[] offspring, int numGenes) {
        int popSize = offspring.length;

        Individual[] output = new Individual[popSize];
        for (int i = 0; i < popSize; i++) {
            output[i] = new Individual(numGenes);
        }

        for (int i = 0; i < popSize; i += 2) {

            int[] p0 = offspring[i].getGenes();
            int[] p1 = offspring[i + 1].getGenes();

            int[][] kids = crossover(p0, p1);

            output[i].setGenes(kids[0]);
            output[i + 1].setGenes(kids[1]);

        }
        return output;
    }

    /**
     * Double Execute Crossover Takes in an array of individuals and crosses
     * over the genes of individual i and i + 1.
     *
     * @param offspring input population
     * @param numGenes number of genes in an individual
     * @return Returns an array of newly crossed over individuals.
     */
    public static Individual2000[] executeCrossover(Individual2000[] offspring, int numGenes) {
        int popSize = offspring.length;

        Individual2000[] output = new Individual2000[popSize];
        for (int i = 0; i < popSize; i++) {
            output[i] = new Individual2000(numGenes);
        }

        for (int i = 0; i < popSize; i += 2) {

            //Retrieve arrays of genes so as to pass them into the crossover method
            double[] p0 = offspring[i].genes;
            double[] p1 = offspring[i + 1].genes;

            double[][] kids = crossover(p0, p1);

            //The tails have been swapped, new children formed
            output[i].genes = kids[0];
            output[i + 1].genes = kids[1];

        }
        return output;
    }

    /**
     * Get Data 32 Gets the data from the data1.txt file.
     *
     * @return Returns an array of Row32 objects.
     * @throws FileNotFoundException
     */
    public static Row32[] getData32() throws FileNotFoundException {
        Row32[] input32 = new Row32[32];
        File dataFile = new File(data1Path);
        Scanner sc = new Scanner(dataFile);

        for (int i = 0; i < 32; i++) {
            String line = sc.nextLine();
            String[] parts = line.split(" ");
            int[] parsed = new int[5];

            for (int j = 0; j < 5; j++) {
                parsed[j] = Integer.parseInt(String.valueOf(parts[0].charAt(j)));
                input32[i] = new Row32(parsed, Integer.parseInt(parts[1]));
            }
        }
        return input32;
    }

    /**
     * Get Data 64 Gets the data from the data2.txt file.
     *
     * @return Returns an array of Row64 objects.
     * @throws FileNotFoundException
     */
    public static Row64[] getData64() throws FileNotFoundException {
        Row64[] input64 = new Row64[64];
        File dataFile = new File(data2Path);
        Scanner sc = new Scanner(dataFile);

        for (int i = 0; i < 64; i++) {
            String line = sc.nextLine();
            String[] parts = line.split(" ");
            int[] parsed = new int[6];

            for (int j = 0; j < 6; j++) {
                parsed[j] = Integer.parseInt(String.valueOf(parts[0].charAt(j)));
                input64[i] = new Row64(parsed, Integer.parseInt(parts[1]));
            }
        }
        return input64;
    }

    /**
     * Get Data 2000 Gets the data from the data3.txt file
     *
     * @return returns an array of Row2000 objects.
     * @throws FileNotFoundException
     */
    public static Row2000[][] getData2000() throws FileNotFoundException {
        Row2000[][] data = new Row2000[2][];
        data[0] = new Row2000[1500];
        data[1] = new Row2000[500];

        File dataFile = new File(data3Path);
        Scanner sc = new Scanner(dataFile);

        int i = 0;
        int x = 0;
        int count = 0;
        String line;
        while (sc.hasNextLine()) {

            line = sc.nextLine();
            String[] parts = line.split(" ");

            String[] var2000 = Arrays.copyOf(parts, parts.length - 1);
            double[] v = new double[6];

            for (int j = 0; j < 6; j++) {
                double temp = Double.parseDouble(var2000[j]);
                v[j] = temp;
            }

            if (count % 4 == 0) {
                data[1][x++] = new Row2000(v, Integer.parseInt(parts[6]));
            } else {
                data[0][i++] = new Row2000(v, Integer.parseInt(parts[6]));
            }

            count++;
        }

        return data;
    }

    /**
     * Gets all the rules
     *
     * @param fittestIndividual
     * @param numRules
     * @return Array of row 64 objects of the rule set
     */
    public static Row32[] getRules32(Individual fittestIndividual, int numRules) {
        Row32[] rules = new Row32[numRules];
        int g = 0;

        //Get the rules out of the fitest individual
        for (int i = 0; i < numRules; i++) {
            int[] genes = new int[5];
            int predicted = 0;
            for (int j = 0; j < 6; j++) {

                if (j == 5) {
                    predicted = fittestIndividual.getSingleGenes(g++);

                } else {
                    genes[j] = fittestIndividual.getSingleGenes(g++);
                }
            }
            rules[i] = new Row32(genes, predicted);
        }

        return rules;

    }

    /**
     * Gets all the rules
     *
     * @param fittestIndividual
     * @param numRules
     * @return Array of row 64 objects of the rule set
     */
    public static Row64[] getRules64(Individual fittestIndividual, int numRules) {
        Row64[] rules = new Row64[numRules];
        int g = 0;

        //Get the rules out of the fitest individual
        for (int i = 0; i < numRules; i++) {
            int[] genes = new int[6];
            int predicted = 0;
            for (int j = 0; j < 7; j++) {

                if (j == 6) {
                    predicted = fittestIndividual.getSingleGenes(g++);

                } else {
                    genes[j] = fittestIndividual.getSingleGenes(g++);
                }
            }
            rules[i] = new Row64(genes, predicted);
        }

        return rules;

    }

    /**
     * Gets all the rules Get all the rules for 2000
     *
     * @param fittestIndividual Fittest Individual.
     * @param numRules number of rules to get.
     * @return
     */
    public static Row2000[] getRules2000(Individual2000 fittestIndividual, int numRules) {
        Row2000[] rules = new Row2000[numRules];
        int g = 0;

        //feed in the genes to make some rules
        for (int i = 0; i < numRules; i++) {
            double[] tempArray = new double[12];
            int pv = 0;
            for (int j = 0; j < 13; j++) {

                if (j == 12) {
                    pv = (int) fittestIndividual.genes[g++];

                } else {
                    tempArray[j] = fittestIndividual.genes[g++];

                }
            }
            rules[i] = new Row2000(tempArray, pv);
        }

        return rules;

    }

    /**
     * Compare Rules 32 Checks to see if a rule generate by the algorithm
     * matches with a rule stored in the data files.
     *
     * @param a Rule generated by the GA
     * @param b Rule stored in the data file
     * @return returns true if all 5 items in the arrays match with each other
     */
    public static boolean compareRules32(int[] a, int[] b) {
        int length = a.length;
        int count = 0;

        for (int i = 0; i < length; i++) {
            if (a[i] == b[i] || a[i] == 2) {
                count++;
            }
        }
        return count == 5;
    }

    /**
     * Compare Rules 64 Checks to see if a rule generate by the algorithm
     * matches with a rule stored in the data files
     *
     * @param a Rule generated by the GA.
     * @param b Rule stored in the data file.
     * @return Returns true if all 6 items in the arrays match with each other.
     */
    public static boolean compareRules64(int[] a, int[] b) {
        int length = a.length;
        int count = 0;

        for (int i = 0; i < length; i++) {
            if (a[i] == b[i] || a[i] == 2) {
                count++;
            }
        }
        return count == 6;
    }

    /**
     * Compare Rules 2000 Checks to see if a rule generate by the algorithm
     * matches with a rule stored in the data files
     *
     * @param a Rule generated by the GA.
     * @param b Rule stored in the data file.
     * @return Returns true if all 6 items in the arrays match with each other.
     */
    public static boolean compareRules2000(double[] a, double[] b) {
        int length = a.length;
        int count = 0;
        int inputCounter = 0;
        for (int i = 0; i < 12; i += 2) {
            double c1 = a[i];
            double c2 = a[i + 1];
            double find = b[inputCounter];

            if (c1 > c2) {
                double temp = c1;
                c1 = c2;
                c2 = temp;
            }

            if (find > c1 && find < c2) {
                count++;
            }
            inputCounter++;
        }
        return count == 6;
    }

    /**
     * Random Range Generates a random number within the given range.
     *
     * @param min Lower range.
     * @param max Upper range.
     * @return Returns random number between min and max.
     */
    public static int randomRange(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min) + min;
    }

    /**
     * Random Double Generates a random floating point number between min and
     * max.
     *
     * @param min Lower range
     * @param max Upper range
     * @return random double number between the min and max values
     */
    public static double randomDouble(int min, int max) {
        Random r = new Random();
        double random = min + r.nextDouble() * (max - min);
        return random;
    }

    /**
     * Random Generates a 0 or a 1 randomly
     *
     * @return random 0 or 1
     */
    public static int random() {
        if (Math.random() < 0.5) {
            return 1;
        }
        return 0;
    }

    /**
     * Write Line Creates a csv file of the fitness and number of generations.
     * With the file name dataXX-NUM_GEN-POP_SIZE-NUM_GENES.csv
     *
     * @param file 1,2 or 3 depending on what classification.
     * @param genNum Generation Number for current line.
     * @param fittestIndividual Fittest individual for current line.
     * @param numGen Total number of generations for the file name.
     * @param popSize Population size for the file name.
     * @param numGenes Number of genes for the file name.
     * @throws IOException
     */
    public static void writeLine(String file, int genNum, int fittestIndividual, int numGen, int popSize, int numGenes) throws IOException {
        String suffix = numGen + "-" + popSize + "-" + numGenes + ".csv";
        switch (file) {
            case "1":
                file = out + "data32-" + suffix;
                break;
            case "2":
                file = out + "data64-" + suffix;
                break;
            case "3":
                file = out + "data2000-" + suffix;
                break;
            default:
                file = null;
                break;
        }

        String str = String.valueOf(genNum) + "," + String.valueOf(fittestIndividual);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.append(str + "\n");
        }
    }
}
