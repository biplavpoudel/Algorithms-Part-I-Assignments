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

    // number of tiles out of place
    public int hamming() {
        return 0;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return 0;
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


    }
}
