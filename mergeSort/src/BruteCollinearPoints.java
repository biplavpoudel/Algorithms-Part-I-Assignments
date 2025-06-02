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

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private ArrayList<LineSegment> collinear;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        collinear = new ArrayList<>();

        // Check for IllegalArgumentExceptions
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

        Point p = points[0];
        Point q = points[1];
        Point r = points[2];
        Point s = points[3];


        // the all four points lie in a same line segment, if their slope is same
        double pq = p.slopeTo(q);
        double pr = p.slopeTo(r);
        double ps = p.slopeTo(s);

        if (pq == pr && pq == ps) {
            StdOut.printf("All the four points %s, %s, %s, %s are collinear.\n", p.toString(),
                          q.toString(), r.toString(), s.toString());
            collinear.add(new LineSegment(p, s));
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
        return collinear.size();
    }

    public LineSegment[] segments() {
        // the line segments
        return collinear.toArray(new LineSegment[numberOfSegments()]);
    }

    public static void main(String[] args) {
        Point[] points = new Point[] {
                new Point(2, 3),
                new Point(2, 4),
                new Point(2, 5),
                new Point(2, 10),
                };
        BruteCollinearPoints coll = new BruteCollinearPoints(points);
    }
}
