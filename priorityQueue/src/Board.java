/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     23 Jul, 2025
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    /* Create a board from an n-by-n array of sliding tiles, where tiles[row][col] = tile at (row, col)
    Assume that the constructor receives an n-by-n array containing the n2 integers between 0 and n2 − 1,
    where 0 represents the blank square.
    Also assume that 2 ≤ n < 128.
    */
    private final int[][] tiles;
    private final int blankX;
    private final int blankY;


    public Board(int[][] tiles) {
        // creating a deep, defensive copy of the tiles to avoid exposing the internal representation of the Board
        int n = tiles.length;
        int xBlank = -1;
        int yBlank = -1;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(tiles[i], 0, this.tiles[i], 0, n);
            // to find out blank tiles
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] == 0) {
                    xBlank = i;
                    yBlank = j;
                }
            }
        }
        this.blankX = xBlank;
        this.blankY = yBlank;
    }

    /*
    String representation of this board
    * The toString() method returns a string composed of n + 1 lines.
    * The first line contains the board size n;
    * The remaining n lines contains the n-by-n grid of tiles in row-major order, using 0 to designate the blank square.
    */
    public String toString() {
        int size = dimension();
        // String output = Integer.toString(size);
        // using + operator to add string can take quadratic time in the length of resulting string, so using StringBuilder
        StringBuilder output = new StringBuilder(Integer.toString(size));
        output.append("\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                output.append(Integer.toString(this.tiles[i][j]));
                output.append("\t");
            }
            output.append("\n");
        }
        return output.toString();
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
                /* -1 in both rows and columns is to account for 0-indexing
                i.e. (1,1) tile is stored at (0,0) array
                Since 24 is stored at 23 in flat, 0th indexing;
                so...24 in 5x5 tile is stored at: (23 = 5*4+3) = (4,3) = (quotient, remainder)
                or, 4 = 23 // 5 and 3 = 23 % 5
                */
                int goalRow = (int) Math.floor((double) (this.tiles[i][j] - 1) / size);
                int goalCol = (int) ((double) (this.tiles[i][j] - 1) % size);
                int manhattenDist = Math.abs(goalCol - j) + Math.abs(goalRow - i);
                // StdOut.printf("Manhattan distance for %d is: %d\n", tiles[i][j], manhattanDist);
                manhattenSum += manhattenDist;
            }
        }
        return manhattenSum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        // Checking for reference equality
        if (this == y) return true;

        // Checking for null or class mismatch
        if (y == null || this.getClass() != y.getClass()) return false;

        int n = this.dimension();

        // Casting y to Board to check content if neither of the above holds true
        Board that = (Board) y;

        if (n == that.dimension()) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (this.tiles[i][j] != that.tiles[i][j])
                        return false;
                }
            }
            return true;
        }
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        // Depending on location of blank space, number of neighbors can be 2,3 or 4
        List<Board> neighbors = new ArrayList<>();

        // possible directions for blanks: top, left, right, bottom
        int[][] neighborsDirections = new int[][] {
                { -1, 0 }, { 0, -1 }, { 0, 1 }, { 1, 0 }
        };
        int size = dimension();

        for (int[] directions : neighborsDirections) {
            int newRow = blankX + directions[0];
            int newCol = blankY + directions[1];
            int[][] copyTiles = copyTiles();

            if (newRow >= 0 && newCol >= 0 && newRow < size && newCol < size) {
                neighbors.add(
                        new Board(swapTiles(copyTiles, blankX, blankY, newRow, newCol)));
            }
        }

        return neighbors;

    }

    // a board that is obtained by exchanging any pair of tiles (excluding blanks)
    public Board twin() {
        // Use StdRandom to generate random indices
        int n = dimension();
        int[][] copy = copyTiles();
        // making the twin() method deterministic and simpler by avoiding random indices
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (copy[i][j] != 0 && copy[i][j + 1] != 0) {
                    return new Board(swapTiles(copy, i, j, i, j + 1));
                }
            }
        }
        throw new IllegalStateException("No valid twin found");
    }

    private int[][] copyTiles() {
        // copies original tile for other operations like swapping
        int size = dimension();
        int[][] copy = new int[size][size];
        for (int i = 0; i < size; i++) {
            copy[i] = Arrays.copyOf(this.tiles[i], dimension());
        }
        return copy;
    }

    private int[][] swapTiles(int[][] arr, int blankRow, int blankCol, int newRow, int newCol) {
        int temp = arr[newRow][newCol];
        arr[newRow][newCol] = arr[blankRow][blankCol];
        arr[blankRow][blankCol] = temp;
        return arr;
    }

    // unit testing
    public static void main(String[] args) {
        In in = new In(args[0]);
        int dimension = in.readInt();
        if (dimension < 2 || dimension >= 128) {
            throw new IllegalArgumentException(
                    String.format(
                            "Dimension out of bounds: %d. Doesn't meet the expected constraint: 2 ≤ n < 128.\n",
                            dimension));
        }
        int[][] tiles = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board board = new Board(tiles);
        StdOut.printf("\nThe original board is: %s \n", board);

        StdOut.printf("\nThe number of wrongly placed tiles is: %d\n", board.hamming());
        StdOut.printf("\nThe Manhattan sum is: %d\n", board.manhattan());
        StdOut.printf("Is Goal?: %b\n", board.isGoal());

        StdOut.print("\nThe neighbors are: \n");
        for (Board neighbor : board.neighbors()) {
            System.out.println(neighbor);
        }

        StdOut.print("\n A twin board is:\n");
        StdOut.print(board.twin());

    }
}
