package biocomputation.helper;

public class Individual {

    int[] genes;
    int fitness;

    public Individual(int geneSize) {
        genes = new int[geneSize];
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int f) {
        fitness = f;
    }

    public int[] getGenes() {
        return genes;
    }

    public void setGenes(int g[]) {
        genes = g;
    }

    public int getSingleGenes(int index) {
        return genes[index];
    }

    public void setSingleGenes(int index, int value) {
        genes[index] = value;
    }

    public int getGenesLength() {
        return genes.length;
    }
}