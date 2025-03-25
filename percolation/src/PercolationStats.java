/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     25 Mar, 2025
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private int trials;
    private double[] percolationThresholds;
    private final double confidenceConst;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.trials = trials;
        percolationThresholds = new double[trials];
        confidenceConst = 1.96;
        for (int i = 0; i < trials; i++) {
            Stopwatch stopwatch = new Stopwatch();

            Percolation percolation = new Percolation(n);
            int openCount;

            // StdOut.printf("The size of grid is: (%d, %d)\n", percolation.size, percolation.size);
            while (!percolation.percolates()) {
                int randX = StdRandom.uniformInt(1, n + 1);
                int randY = StdRandom.uniformInt(1, n + 1);
                // StdOut.printf("The random site selected is: (%d, %d) \n", randX, randY);
                // randomly opens a site
                percolation.open(randX, randY);      // i.e. percolation.open(2, 0)
            }
            openCount = percolation.numberOfOpenSites();

            double totalSites = Math.pow(n, 2);
            // StdOut.printf("\nTotal number of Sites is: %d", (int) totalSites);
            // StdOut.printf("\nTotal Open Sites is: %d", openCount);


            percolationThresholds[i] = (double) openCount / totalSites;
            // StdOut.printf("\nPercolation Threshold is: %f\n", percolationThresholds[i]);
            //
            // StdOut.printf("\nThe time elapsed for trial %d is: %f\n", i, stopwatch.elapsedTime());
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolationThresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolationThresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (confidenceConst * stddev()) / (Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (confidenceConst * stddev()) / (Math.sqrt(trials));
    }

    // test client

    public static void main(String[] args) {

        if (args.length < 2) {
            throw new IllegalArgumentException(
                    "Please provide two arguments: n and number of trials.");
        }

        int n = Integer.parseInt(args[0]);
        int runs = Integer.parseInt(args[1]);
        if (n <= 0 || runs <= 0) {
            throw new IllegalArgumentException("Both n and trials must be positive integers.");
        }

        PercolationStats percStats = new PercolationStats(n, runs);

        StdOut.printf("mean                     = %f\n", percStats.mean());
        StdOut.printf("stddev                   = %f\n", percStats.stddev());
        StdOut.printf("95%% confidence interval  = [%.3f, %.3f]\n", percStats.confidenceLo(),
                      percStats.confidenceHi());
    }

}

