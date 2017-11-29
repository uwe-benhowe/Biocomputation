package biocomputation.helper;

public class Row2000 {

    double[] var = new double[2000];
    int predicted;

    public Row2000(double[] v, int p) {
        var = v;
        predicted = p;
    }

    public double[] getInputVariable() {
        return var;
    }

    public int getPredicted() {
        return predicted;
    }
}