/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     23 Jul, 2025
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private SearchNode searchNode;
    private boolean isSolvable;

    // find a solution to the initial board (using the A* algorithm)
    // using Manhattan Priority function
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        // To find out if the initial board is solvable or not,
        // we take a twin and solve it side-by-side with the initial board
        // only one will be solvable.
        MinPQ<SearchNode> queue = new MinPQ<>();
        // if moves = 0, priority = manhattan + moves = manhattan
        queue.insert(new SearchNode(initial, null, 0));

        MinPQ<SearchNode> twinQueue = new MinPQ<>();
        twinQueue.insert(new SearchNode(initial.twin(), null, 0));

        // Removing the initial Node to add neighbors
        searchNode = queue.delMin();
        // StdOut.printf("The initial board is: %s", searchNode.currentBoard);

        SearchNode twinNode = twinQueue.delMin();

        // checking if the initialNode is the goal Node
        isSolvable = searchNode.currentBoard.isGoal();

        // Solving initial and twin to find out which one will lead to solution
        while (!isSolvable) {
            if (twinNode.currentBoard.isGoal())
                break; // if true, proves initial board is not solvable

            for (Board board : searchNode.currentBoard.neighbors()) {
                // critical optimization for disallowing already explored searchNodes
                if (searchNode.previousNode == null || !board.equals(
                        searchNode.previousNode.currentBoard)) {
                    queue.insert(new SearchNode(board, searchNode, searchNode.currentCost + 1));
                }
            }
            searchNode = queue.delMin();

            for (Board board : twinNode.currentBoard.neighbors()) {
                // critical optimization for disallowing already explored searchNodes
                if (twinNode.previousNode == null || !board.equals(
                        twinNode.previousNode.currentBoard)) {
                    twinQueue.insert(new SearchNode(board, twinNode, twinNode.currentCost + 1));
                }
            }
            twinNode = twinQueue.delMin();
            // Checking if the new searchNode (child) is the goal
            isSolvable = searchNode.currentBoard.isGoal();
        }
        // If not solvable, setting searchNode to be null
        if (!isSolvable) searchNode = null;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable) return -1;
        return searchNode.currentCost;
    }

    // sequence of boards in the shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable) return null;
        Stack<Board> solutionStack = new Stack<Board>();

        SearchNode node = searchNode;
        // adding the goal node first to the List
        // solutionList.add(node.currentBoard);

        while (node != null) {
            solutionStack.push(node.currentBoard);
            node = node.previousNode;
        }

        return solutionStack;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private final Board currentBoard;
        private final SearchNode previousNode;
        private final int currentCost;
        private final int manhattanPriority;

        private SearchNode(Board board, SearchNode prev, int cost) {
            this.currentBoard = board;
            this.previousNode = prev;
            this.currentCost = cost;
            this.manhattanPriority = this.currentBoard.manhattan()
                    + this.currentCost;  // sum of manhattan distance and currentCost
        }

        // this is necessary when deleting min from PQ and adding neighbors
        @Override
        public int compareTo(SearchNode that) {
            return Integer.compare(this.manhattanPriority, that.manhattanPriority);
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
