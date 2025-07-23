/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     23 Jul, 2025
 **************************************************************************** */

public class Board {

    /* Create a board from an n-by-n array of sliding tiles, where tiles[row][col] = tile at (row, col)
    Assume that the constructor receives an n-by-n array containing the n2 integers between 0 and n2 − 1,
    where 0 represents the blank square.
    Also assume that 2 ≤ n < 128.
    */
    public Board(int[][] tiles) {
    }

    /*
    String representation of this board
    * The toString() method returns a string composed of n + 1 lines.
    * The first line contains the board size n;
    * The remaining n lines contains the n-by-n grid of tiles in row-major order, using 0 to designate the blank square.
    */
    public String toString() {
    }

    // board dimension n
    public int dimension() {
    }

    // number of tiles out of place
    public int hamming() {
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
    }

    // is this board the goal board?
    public boolean isGoal() {
    }

    // does this board equal y?
    public boolean equals(Object y) {
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }
}
