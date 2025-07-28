/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     23 Jul, 2025
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {


    // find a solution to the initial board (using the A* algorithm)
    // using Manhattan Priority function
    public Solver(Board initial) {
        MinPQ<SearchNode> queue = new MinPQ<>();
        // if moves = 0, priority = manhattan + moves = manhattan
        queue.insert(new SearchNode(initial, null, 0, initial.manhattan()));

        /*To find out if the initial board is solvable or not,
        we take a twin and solve it side-by-side with the initial board
        only one will be solvable.*/
        MinPQ<SearchNode> twinQueue = new MinPQ<>();
        Board twin = initial.twin();
        twinQueue.insert(new SearchNode(twin, null, 0, twin.manhattan()));
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return false;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return 0;
    }

    // sequence of boards in the shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return null;
    }

    private static class SearchNode implements Comparable<SearchNode> {
        private Board currentBoard;
        private SearchNode previousBoard;
        private int currentCost;
        private int manhattanPriority;

        private SearchNode(Board board, SearchNode prev, int cost, int priority) {
            this.currentBoard = board;
            this.previousBoard = prev;
            this.currentCost = cost;
            this.manhattanPriority = priority;  // sum of manhattan and currentCost
        }

        // this is necessary when deleting min from PQ and adding neighbors
        @Override
        public int compareTo(SearchNode o) {
            return Integer.compare(this.manhattanPriority, o.manhattanPriority);
        }
    }

    // test client
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
