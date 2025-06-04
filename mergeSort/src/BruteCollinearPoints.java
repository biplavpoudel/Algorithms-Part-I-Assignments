/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     10 May, 2025
 **************************************************************************** */

/*
 * Examines 4 points at a time and checks whether they all
 * lie on the same line segment, returning all such line segments.
 *
 * To check whether the 4 points p, q, r, and s are collinear,
 * check whether the three slopes between p and q, between p and r,
 * and between p and s are all equal.
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class BruteCollinearPoints {

    private final ArrayList<LineSegment> collinearLines;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        collinearLines = new ArrayList<>();
        HashSet<String> duplicateSegments = new HashSet<>();

        // Check for Corner cases
        if (points == null)
            throw new IllegalArgumentException("The constructor argument is null");
        else {
            for (Point item : points) {
                if (item == null)
                    throw new IllegalArgumentException("A null Point is passed to the constructor");
            }
            Arrays.sort(points);
            if (sortedDuplicateChecker(points))
                throw new IllegalArgumentException("Duplicate arguments found!");
        }
        int size = points.length;

        // brute-forcing all the possible set of 4 collinear points from the inputs
        for (int i = 0; i < size - 3; i++) {
            for (int j = i + 1; j < size - 2; j++) {
                for (int k = j + 1; k < size - 1; k++) {
                    for (int h = k + 1; h < size; h++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[h];

                        if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(q) == p.slopeTo(s)) {
                            Point[] linePoints = { p, q, r, s };
                            Arrays.sort(linePoints);
                            // to remove overlapping segments with HashSet
                            String key = linePoints[0].toString() + " -> "
                                    + linePoints[3].toString();
                            if (!duplicateSegments.contains(key)) {
                                duplicateSegments.add(key);
                                StdOut.printf(
                                        "Collinear points found! The points are: (%s,%s,%s,%s)\n",
                                        p.toString(), q.toString(),
                                        r.toString(), s.toString());
                                collinearLines.add(new LineSegment(p, s));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if the sorted array has duplicates by adjacent checking.
     *
     * @param items N-size array of type Point
     * @return True if duplicates present, else False
     */
    private static boolean sortedDuplicateChecker(Point[] items) {
        int size = items.length;
        for (int i = 1; i < size; i++) {
            if (items[i - 1].compareTo(items[i]) == 0)
                return true;
        }
        return false;
    }

    public int numberOfSegments() {
        // finds all line segments containing 4 points
        return collinearLines.size();
    }

    public LineSegment[] segments() {
        // the line segments
        return collinearLines.toArray(new LineSegment[numberOfSegments()]);
    }

    public static void main(String[] args) {
        // Point[] points = new Point[] {
        //         new Point(0, 1),
        //         new Point(1, 3),
        //         new Point(2, 5),
        //         new Point(3, 7),
        //         new Point(4, 9),
        //         new Point(2, 2),
        //         new Point(5, 1),
        //         new Point(0, 4),
        //         new Point(3, 1),
        //         new Point(4, 0),
        //         new Point(5, 5),
        //         new Point(1, 1)
        // };
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        BruteCollinearPoints coll = new BruteCollinearPoints(points);
        // StdOut.printf("The number of collinear line segments are: %d\n", coll.numberOfSegments());
        LineSegment[] response = coll.segments();
        for (LineSegment item : response) {
            StdOut.printf("The collinear segments are: %s\n", item.toString());
        }
    }
}
