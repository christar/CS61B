package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] threshold;
    private double mu;
    private double theta;
    private double cLow;
    private double cHigh;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("Parameter illegal!");
        }
        threshold = new double[T];
        // StdRandom.getSeed();
        for (int i = 0; i < T; i++) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int r = StdRandom.uniform(N);
                int c = StdRandom.uniform(N);
                p.open(r, c);
            }
            threshold[i] = 1.0 * p.numberOfOpenSites() / (N * N);
        }
        mu = StdStats.mean(threshold);
        theta = StdStats.stddev(threshold);
        cLow = (mu - 1.96 * theta / Math.sqrt(T));
        cHigh = (mu + 1.96 * theta / Math.sqrt(T));
    }

    public double mean() {
        return mu;
    }

    public double stddev() {
        return theta;
    }

    public double confidenceLow() {
        return cLow;
    }

    public double confidenceHigh() {
        return cHigh;
    }
}
