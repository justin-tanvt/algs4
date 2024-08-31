import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        double[] results = new double[trials];

        int totalSites = n * n;
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                // generate random row and column within range (1 to n)
                int randomRow = StdRandom.uniformInt(1, n + 1);
                int randomCol = StdRandom.uniformInt(1, n + 1);

                if (!p.isOpen(randomRow, randomCol)) {
                    p.open(randomRow, randomCol);
                }
            }

            results[i] = 1.0 * p.numberOfOpenSites() / totalSites;
        }

        this.mean = StdStats.mean(results);
        this.stddev = StdStats.stddev(results);
        final double CONFIDENCE_95 = 1.96 * stddev / Math.sqrt(n);
        this.confidenceLo = mean - CONFIDENCE_95;
        this.confidenceHi = mean + CONFIDENCE_95;
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.printf("mean = %s\n", ps.mean());
        System.out.printf("stddev = %s\n", ps.stddev());
        System.out.printf("95%% confidence interval = [%s, %s]\n", ps.confidenceLo(), ps.confidenceHi());
    }
}