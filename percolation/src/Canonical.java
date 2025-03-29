// Union-Find with Specific Canonical Element
//
//         Add a method find() to the union-find data type so that find(i) returns the
//         largest element in the connected component containing i.
//
//         The operations union(), connected(), and find() should all take logarithmic
//         time or better.
//
//         For example, if one of the connected components is {1,2,6,9}, then the
//         find() method should return 9 for each of the four elements in the connected
//         component.

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Canonical {
    private int[] id;  // keeps track of roots
    private int[] size;  // keeps track of size of each connected items
    private int[] maximum; // keeps track of largest element in connected item
    private int maxim; // keeps track of largest element in connected item

    public Canonical(int n) {
        id = new int[n];
        size = new int[n];
        maximum = new int[n];
        maxim = 0;
        for (int i = 0; i < n; i++) {
            id[i] = i;
            size[i] = 1;
            maximum[i] = i;
        }
        StdOut.printf("The initial ids are: \n");
        for (int i = 0; i < n; i++) {
            StdOut.printf(id[i] + " ");
        }
        StdOut.printf("\nThe maximum for each ids are: \n");
        for (int i = 0; i < n; i++) {
            StdOut.printf(maximum[i] + " ");
        }
        StdOut.printf("\n");
    }

    public int find(int item) {
        maxim = maximum[item];
        while (item != id[item]) {
            if (id[item] > maxim) maxim = id[item];
            id[item] = id[id[item]];
            if (id[item] > maxim) maxim = id[item];
            item = id[item];
        }
        maximum[item] = maxim;
        return maximum[item];
    }

    public int root(int item) {
        while (item != id[item]) {
            id[item] = id[id[item]];
            item = id[item];
        }
        return item;
    }

    public boolean connected(int p, int q) {
        return (root(p) == root(q));
    }

    public void union(int p, int q) {
        int pid = root(p);
        StdOut.printf("The largest in connected set of %d is %d\n", p, find(p));
        int qid = root(q);
        StdOut.printf("The largest in connected set of %d is %d\n", q, find(q));

        if (pid == qid) return;

        if (size[pid] > size[qid]) {
            size[pid] += size[qid];
            id[qid] = pid;
        }
        else {
            size[qid] += size[pid];
            id[pid] = qid;
        }
        maximum[pid] = Math.max(maximum[p], maximum[q]);
        maximum[qid] = maximum[pid];
    }

    public void output(int n) {
        StdOut.printf("The final array is: \n");
        for (int i = 0; i < n; i++) {
            StdOut.print(id[i] + " ");
        }
        StdOut.printf("\n");
        StdOut.printf("The final maximum array is: \n");
        for (int i = 0; i < n; i++) {
            StdOut.print(maximum[i] + " ");
        }
        StdOut.printf("\n");
    }

    public static void main(String[] args) {
        System.out.println("This is the social connectivity problem.");
        int n = StdIn.readInt(); // number of objects
        StdOut.printf("\nThe numbers of items are: %d\n", n);
        int m = StdIn.readInt(); // number of timestamps
        StdOut.printf("The numbers of timestamps are: %d\n", m);

        Canonical uf = new Canonical(n);
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
