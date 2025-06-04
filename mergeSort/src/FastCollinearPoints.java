/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     10 May, 2025
 **************************************************************************** */

// FAST COLLINEAR POINTS
//
//   Given a point p, the following method determines whether p
//   participates in a set of 4 or more collinear points.
//     - Think of p as the origin
//     - For each other point q, determine the slope it makes with p
//     - Sort the points according to the slopes they make with p
//     - Check if any 3 (or more) adjacent points in the sorted order have equal
//       slopes with respect to p. If so, these points, together with p, are
//       collinear.
//
//   The method segments() should include each maximal line segment containing 4* (or more) points exactly once.
//   For example, if 5 points appear on a line
//   segment in the order p->q->r->s->t, then do not include the subsegments p->s
//   or q->t.
//
//  Performance requirements: the growth of the running time of your program
//   should be n^2 log n in the worst case, and it should use space proportional
//   to n plus the number of line segments returned. FastCollinearPoints should
//   work properly even if the input has 5 or more collinear points.
//

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class FastCollinearPoints {

    private final ArrayList<LineSegment> collinearLines;

    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
        collinearLines = new ArrayList<>();
        HashSet<String> duplicateSegments = new HashSet<>();
        int size = points.length;

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
                throw new IllegalArgumentException("Duplicate Points found!");
        }

        // For each point p in n, lets find slope of other points wrt to p

        for (int i = 0; i < size - 1; i++) {
            Point[] clonedPoints = points.clone();
            Point referencePoint = clonedPoints[i];
            ArrayList<Double> slopeArr = new ArrayList<>();

            // Points have been sorted according to the slope they make it reference point
            Arrays.sort(clonedPoints, referencePoint.slopeOrder());
            // The slopes of each sorted points is added to an array
            for (Point items : clonedPoints) {
                slopeArr.add(referencePoint.slopeTo(items));
            }

            // now to compare adjacent slopes for collinearity
            int count = 1;  // begin with count of 1
            int firstCol;  // first Collinear point in the set
            for (int j = 1; j < size; j++) {
                Double previousSlope = slopeArr.get(j - 1);
                Double currSlope = slopeArr.get(j);
                if (currSlope.compareTo(previousSlope) == 0) {
                    count++;
                }
                else {
                    if (count >= 3) {
                        firstCol = j - count;
                        collinearLines.add(
                                new LineSegment(clonedPoints[firstCol], clonedPoints[j - 1]));
                    }
                    count = 1;  // reset count if current slope not equal to previous
                }
            }
            // Since collinear line segments are only added if the next point has different slope,
            // the final collinear group doesn't get added when the loop ends.
            // So we need to add the final group at the end if count >= 3.
            if (count >= 3) {
                firstCol = size - count + 1;   // (size+1)-count to not mess index
                collinearLines.add(
                        new LineSegment(clonedPoints[firstCol], clonedPoints[size - 1]));
            }
        }

    }

    /**
     * Checks if the sorted array has duplicates by adjacent checking.
     *
     * @param items N-size array of type Point
     * @return True if duplicates present, else False
     */
    private static <T extends Comparable<T>> boolean sortedDuplicateChecker(T[] items) {
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
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

