package biocomputation.helper;

public class Row32 {

    int[] var = new int[5];
    int predicted;

    public Row32(int[] v, int p) {
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