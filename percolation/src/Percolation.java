/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     25 Mar, 2025
 **************************************************************************** */

// Corner cases:
// By convention, the row and column indices are integers between 1 and n,
// where (1, 1) is the upper-left site.
// Throw an IllegalArgumentException if any argument to open(), isOpen(), or isFull() is outside its prescribed range.
// Throw an IllegalArgumentException in the constructor if n ≤ 0.


// Performance requirements:
// The constructor must take time proportional to n2;
// all instance methods must take constant time plus a constant number of calls to union() and find().

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;   // stores whether Open(true) or Blocked(false)
    private int size;
    private int openCount;
    private WeightedQuickUnionUF unionFindwithVT; // extra space for virtual top
    private WeightedQuickUnionUF unionFindwithVTB; // extra space for virtual top and bottom
    private int virtualTopSite;
    private int virtualBottomSite;

    /**
     * creates n-by-n grid, with all sites initially blocked
     *
     * @param size dimension of the grid
     */
    public Percolation(int size) {
        this.size = size;
        if (size <= 0) {
            throw new IllegalArgumentException("Input cannot be equal or less than 0");
        }
        this.grid = new boolean[size][size];
        this.openCount = 0;

        unionFindwithVT = new WeightedQuickUnionUF(size * size + 1);
        unionFindwithVTB = new WeightedQuickUnionUF(size * size + 2);

        virtualTopSite = size * size;
        // last element of grid is (n*n-1) so "last element of unionFindwithVT"/ "2nd last element of unionFindwithVTB" is n*n
        virtualBottomSite = virtualTopSite + 1;
        // last element of virtual bottom site for unionFindwithVTB is n*n+1

        // now connecting all top rows of both UF-data types to virtual top site
        for (int i = 0; i < size; i++) {
            unionFindwithVT.union(i, virtualTopSite);
            unionFindwithVTB.union(i, virtualTopSite);
        }

        // now connecting all bottom rows of top-bottom UF-data types to virtual top site
        for (int i = 0; i < size; i++) {
            unionFindwithVTB.union(size * (size - 1) + i, virtualBottomSite);
        }
    }

    private void checkValidity(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException(
                    String.format("Site (%d, %d) is outside its prescribed range.", row, col));
        }
    }

    // opens the site (row, col) if it is not open already
    // all instance methods must take constant time plus a constant number of calls to union() and find() so avoid for loops as much as possible
    public void open(int row, int col) {
        checkValidity(row, col);
        int actualRow = row - 1;   // 11 is mapped as 00, 12->01, 22->11,....
        int actualCol = col - 1;

        if (!grid[actualRow][actualCol]) {
            grid[actualRow][actualCol] = true;
            openCount++;
            // connects the opened site to adjacent open sites
            int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

            for (int[] dir : directions) {
                int adjacentX = actualRow + dir[0];
                int adjacentY = actualCol + dir[1];

                int currentMap = actualRow * size
                        + actualCol;  // map grid-site to union-data type arrays i.e. 00 -> 0
                int adjacentMap = adjacentX * size + adjacentY;

                if (adjacentX >= 0 && adjacentX < size &&
                        adjacentY >= 0 && adjacentY < size &&
                        (grid[adjacentX][adjacentY])) {
                    unionFindwithVTB.union(currentMap, adjacentMap);
                    unionFindwithVT.union(currentMap, adjacentMap);
                }
            }
            // StdOut.printf("Site (%d, %d) is opened!", row, col);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkValidity(row, col);
        return grid[row - 1][col - 1];
    }


    // is the site (row, col) full?
    // A full site is an open site that can be connected to an open site in the top row
    // via a chain of neighboring (left, right, up, down) open sites.
    public boolean isFull(int row, int col) {
        return isOpen(row, col) &&
                (unionFindwithVT.find((row - 1) * size + (col - 1)) == unionFindwithVT.find(
                        virtualTopSite));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    // the system percolates if there is a full site in the bottom row
    public boolean percolates() {        // if size is 1, percolates if the only site is open
        // else check if canonical element of virtual-topsite and virtual-bottomsite is same
        return size != 1 ? (unionFindwithVTB.find(virtualTopSite) == unionFindwithVTB.find(
                virtualBottomSite)) : grid[0][0];
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = StdIn.readInt();

        Percolation percolation = new Percolation(n);
        StdOut.printf("The size of grid is: (%d, %d)\n", percolation.size, percolation.size);

        while (!StdIn.isEmpty()) {
            int randX = StdIn.readInt();
            int randY = StdIn.readInt();
            StdOut.printf("The random site selected is: (%d, %d) \n", randX, randY);
            percolation.open(randX, randY);      // i.e. percolation.open(2, 0)
        }

        StdOut.printf("\nThe final sitemap is: \n");
        // checking the value of the grid
        for (int i = 0; i < percolation.size; i++) {
            for (int j = 0; j < percolation.size; j++) {
                StdOut.printf(String.valueOf(percolation.grid[i][j]));
                StdOut.printf(" ");
            }
            StdOut.printf("\n");
        }

        double totalSites = Math.pow(percolation.size, 2);
        StdOut.printf("\nTotal number of Sites is: %d", (int) totalSites);
        StdOut.printf("\nTotal Open Sites is: %d\n", percolation.numberOfOpenSites());
        StdOut.printf("\nPercolation Threshold is: %f\n",
                      percolation.numberOfOpenSites() / totalSites);

    }
}