package biocomputation.helper;

public class Individual2000 {

    double[] genes;
    int fitness;

    public Individual2000(int geneSize) {
        genes = new double[geneSize];
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int f) {
        fitness = f;
    }

    public double[] getGenes() {
        return genes;
    }

    public void setGenes(double g[]) {
        genes = g;
    }

    public double getSingleGenes(int index) {
        return genes[index];
    }

    public void setSingleGenes(int index, double value) {
        genes[index] = value;
    }

    public int getGenesLength() {
        return genes.length;
    }
}
