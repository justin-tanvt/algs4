import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Sattolo {

    private Sattolo() {
    }

    public static void cycle(Object[] a) {
        // loop through n-1 elements, starting from highest index
        for (int i = a.length - 1; i >= 1; i--) {
            int j = StdRandom.uniformInt(i);     // select any unstruck index exclusive of current
            // swap places of current object and object randomly chosen
            Object swap = a[i];
            a[i] = a[j];
            a[j] = swap;
        }
    }

    public static void main(String[] args) {
        String[] input = StdIn.readAllStrings();
        cycle(input);
        for (int i = 0; i < input.length; i++) {
            System.out.println(input[i]);
        }
    }
}
