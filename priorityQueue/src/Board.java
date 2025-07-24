/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     23 Jul, 2025
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Board {

    /* Create a board from an n-by-n array of sliding tiles, where tiles[row][col] = tile at (row, col)
    Assume that the constructor receives an n-by-n array containing the n2 integers between 0 and n2 − 1,
    where 0 represents the blank square.
    Also assume that 2 ≤ n < 128.
    */
    private final int[][] tiles;

    public Board(int[][] tiles) {
        this.tiles = tiles;
    }

    /*
    String representation of this board
    * The toString() method returns a string composed of n + 1 lines.
    * The first line contains the board size n;
    * The remaining n lines contains the n-by-n grid of tiles in row-major order, using 0 to designate the blank square.
    */
    public String toString() {
        int size = dimension();
        String output = Integer.toString(size);
        output = output + "\n";
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                output += Integer.toString(this.tiles[i][j]);
                output += "\t";
            }
            output += "\n";
        }
        return output;
    }

    // board dimension n
    public int dimension() {
        return this.tiles.length;
    }

    private int[][] getGoal() {
        int size = dimension();
        // Goal tile starts with 1
        int item = 1;
        int[][] goal = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                goal[i][j] = item++;
            }
        }
        // Last tile (bottom-right) is always empty in goal
        goal[size - 1][size - 1] = 0;
        return goal;
    }

    // number of tiles out of place
    public int hamming() {
        int[][] goal = getGoal();
        int size = dimension();
        int wrongPositionCounter = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // we ignore position for empty tile
                if (tiles[i][j] == 0) continue;
                if (this.tiles[i][j] != goal[i][j]) wrongPositionCounter++;
            }
        }
        return wrongPositionCounter;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int size = dimension();
        int manhattenSum = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // we ignore position for empty tile
                if (tiles[i][j] == 0) continue;
                // -1 in both rows and columns is to account for 0-indexing
                // i.e. (1,1) tile is stored at (0,0) array
                // 24 in 5x5 tile is stored at: (23 = 5*4+3) = (4,3) for 0th index
                int goal_row = (int) Math.floor((double) (this.tiles[i][j] - 1) / size);
                int goal_col = (int) ((double) (this.tiles[i][j] - 1) % size);
                int manhattenDist = Math.abs(goal_col - j) + Math.abs(goal_row - i);
                StdOut.printf("Manhatten distance for %d is: %d\n", tiles[i][j], manhattenDist);
                manhattenSum += manhattenDist;
            }
        }
        return manhattenSum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return null;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return null;
    }

    // unit testing
    public static void main(String[] args) {
        int dimension = StdIn.readInt();
        if (dimension < 2 || dimension >= 128) {
            StdOut.printf(
                    "The dimension of the tile: %d doesn't meet the expected constraint 2 ≤ n < 128.\n",
                    dimension);
            return;
        }
        // StdOut.printf("The size of tiles is: %d x %d\n", dimension, dimension);
        int[][] tiles = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                tiles[i][j] = StdIn.readInt();
            }
        }
        Board board = new Board(tiles);
        StdOut.print(board.toString());
        StdOut.printf("\nThe number of wrongly placed tiles is: %d\n", board.hamming());
        StdOut.printf("\nThe Manhatten sum is: %d\n", board.manhattan());

    }
}
