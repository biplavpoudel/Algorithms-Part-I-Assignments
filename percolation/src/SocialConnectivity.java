// Social Network Connectivity
//
//         Given a social network containing n members and a log file containing m timestamps
//         at which pairs of members formed friendships, design an algorithm to determine
//         the earliest time at which all members are connected
//         (i.e., every member is a friend of a friend of a friend ... of a friend).
//
//         Assume that the log file is sorted by timestamp and that friendship is an
//         equivalence relation.
//
//         The running time of your algorithm should be O(m log n) or better
//         and use extra space proportional to O(n).

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SocialConnectivity {
    private int[] id;
    private int[] size;

    public SocialConnectivity(int n) {
        id = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            size[i] = 1;
        }
        StdOut.printf("The initial ids are: \n");
        for (int i = 0; i < n; i++) {
            StdOut.printf(id[i] + " ");
        }
        StdOut.printf("\n");
    }

    public int root(int item) {
        while (item != id[item]) {
            id[item] = id[id[item]];  // parent set to grandparent
            item = id[item];  // node also set to grandparent; hence path compression
        }
        return item;
    }

    public boolean connected(int p, int q) {
        return (root(p) == root(q));
    }

    public void union(int p, int q) {
        int pid = root(p);
        int qid = root(q);

        if (pid == qid) return;

        if (size[pid] > size[qid]) {
            size[pid] += size[qid];
            id[qid] = pid;
        }
        else {
            size[qid] += size[pid];
            id[pid] = qid;
        }
    }

    public void output(int n) {
        StdOut.printf("The final array is: \n");
        for (int i = 0; i < n; i++) {
            StdOut.print(id[i] + " ");
        }
        StdOut.printf("\n");
    }

    public static void main(String[] args) {
        System.out.println("This is the social connectivity problem.");
        int n = StdIn.readInt(); // number of objects
        StdOut.printf("\nThe numbers of items are: %d\n", n);
        int m = StdIn.readInt(); // number of timestamps
        StdOut.printf("The numbers of timestamps are: %d\n", m);

        SocialConnectivity uf = new SocialConnectivity(n);
        StdOut.printf("The union operations are: \n");
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            boolean isConnected = uf.connected(p, q);
            if (!isConnected) {
                StdOut.printf("(%d, %d)\n", p, q);
                uf.union(p, q);
            }
        }
        uf.output(n);
    }
}
