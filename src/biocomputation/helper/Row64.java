package biocomputation.helper;

public class Row64 {

    int[] var = new int[6];
    int predicted;

    public Row64(int[] v, int p) {
        var = v;
        predicted = p;
    }

    public int[] getInputVariable() {
        return var;
    }

    public int getPredicted() {
        return predicted;
    }
}