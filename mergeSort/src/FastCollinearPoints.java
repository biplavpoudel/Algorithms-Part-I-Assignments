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

public class FastCollinearPoints {

    private final ArrayList<LineSegment> collinearLines;
    // to add {min, max} array of collinear Points
    private final ArrayList<Pair> collinearLinesGroup;

    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
        collinearLines = new ArrayList<>();
        collinearLinesGroup = new ArrayList<>();

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

        int size = points.length;
        // For each point p in n, lets find slope of other points wrt to p
        for (int i = 0; i < size; i++) {
            // StdOut.printf("\n\n\ni == %d", i);
            Point referencePoint = points[i];
            ArrayList<Double> slopeArr = new ArrayList<>();

            // Points have been sorted according to the slope they make it reference point
            Point[] sortedBySlope = new Point[points.length - 1];
            int idx = 0;
            for (int j = 0; j < points.length; j++) {
                if (j != i) {
                    sortedBySlope[idx++] = points[j];
                }
            }
            Arrays.sort(sortedBySlope, referencePoint.slopeOrder());

            // StdOut.printf("\nThe sorted points based on %s are: \n", referencePoint);
            // for (Point items : sortedBySlope) {
            //     StdOut.printf("%s ", items);
            // }


            // The slopes of each sorted points is added to an array
            // StdOut.printf("\nThe slope of sorted points based on %s is: \n", referencePoint);
            for (Point items : sortedBySlope) {
                // StdOut.printf("%f ", referencePoint.slopeTo(items));
                slopeArr.add(referencePoint.slopeTo(items));
            }

            // now to compare adjacent slopes for collinearity
            double prevSlope = referencePoint.slopeTo(sortedBySlope[0]);
            int count = 1;
            int firstCol;  // first Collinear point in the set
            for (int j = 1; j < sortedBySlope.length; j++) {
                double currSlope = slopeArr.get(j);
                if (Double.compare(currSlope, prevSlope) == 0) {
                    count++;
                }
                else {
                    if (count >= 3) {
                        firstCol = j - count;
                        addCollinearSegment(sortedBySlope, firstCol, referencePoint, count);
                    }
                    count = 1;  // reset count if current slope not equal to previous
                    prevSlope = currSlope;
                }
            }
            // Since collinear line segments are only added if the next point has different slope,
            // the final collinear group doesn't get added when the loop ends.
            // So we need to add the final group at the end if count >= 3.
            if (count >= 3) {
                firstCol = sortedBySlope.length - count;
                addCollinearSegment(sortedBySlope, firstCol, referencePoint, count);
            }
        }
    }

    private void addCollinearSegment(Point[] Points, int firstCol, Point refPoint, int count) {
        Point[] colSegment = new Point[count + 1];
        // first item is the reference point itself
        colSegment[0] = refPoint;

        for (int k = 0; k < count; k++) {
            colSegment[k + 1] = Points[firstCol + k];
        }

        // TESTING: this slope is the slope of the collinear points
        double groupSlope = colSegment[1].slopeTo(colSegment[2]);

        Arrays.sort(colSegment);

        if (refPoint.compareTo(colSegment[0]) != 0) return;

        // referencePoint is the smallest; safe to check it.
        Pair currOG = new Pair(colSegment, groupSlope);
        collinearChecker(currOG);
    }

    private void collinearChecker(Pair currOG) {
        Point keyMin = currOG.getGroup()[0];
        Point keyMax = currOG.getGroup()[currOG.getSize() - 1];
        double pairsSlope = currOG.getSlope();

        // StdOut.printf("\nThe current group to be compared is: %s with slope: %f\n",
        //               currOG.getString(), pairsSlope);

        ArrayList<Pair> toAdd = new ArrayList<>();
        ArrayList<Pair> toRemove = new ArrayList<>();
        boolean duplicate = false;
        boolean overlapped = false;

        if (collinearLinesGroup.isEmpty()) {
            // StdOut.printf(
            //         "\nThe collinearLinesGroup is Empty. Adding to the collinearLinesGroup.\n");
            collinearLinesGroup.add(currOG);
            return;
        }
        for (Pair currItem : collinearLinesGroup) {
            // StdOut.printf(
            //         "DEBUG: The collinearLinesGroup item is %s with slope %f \n",
            //         currItem.getString(),
            //         currItem.getSlope()
            // );

            Point currMax = currItem.getGroup()[currItem.getSize() - 1];
            Point currMin = currItem.getGroup()[0];
            double currSlope = currItem.getSlope();

            // if unequal slope, get next pairs for comparison
            if (Double.compare(pairsSlope, currSlope) != 0) {
                // StdOut.printf(
                //         "Slopes are unequal. Comparing with another collinearLinesPairs if present.\n");
                continue;
            }

            // StdOut.printf("Slopes are equal. Checking for exact duplicity.\n");
            if (Double.compare(pairsSlope, currSlope) == 0
                    && keyMin.compareTo(currMin) == 0
                    && keyMax.compareTo(currMax) == 0) {
                // StdOut.printf(
                //         "Duplicate found! Item not added to the collinearLinesGroup\n\n");
                duplicate = true;
                break;
            }
            // StdOut.printf("Not duplicate, same slope. Checking if segement overlaps.");
            int shared = 0;
            for (Point p1 : currOG.getGroup()) {
                for (Point p2 : currItem.getGroup()) {
                    if (p1.compareTo(p2) == 0) {
                        // StdOut.printf(
                        //         "Overlapping point found: %s. The lines are collinear.\n",
                        //         p2);
                        shared++;
                        break;
                    }
                }
            }
            if (shared >= 2) {
                overlapped = true;
                // StdOut.printf("The lines are coninciding.");
                // let's find the maximal
                Point[] maximal = { keyMin, keyMax, currMin, currMax };
                Arrays.sort(maximal);
                // new pair object is:
                toAdd.add(new Pair(new Point[] { maximal[0], maximal[3] }, currSlope));
                toRemove.add(currItem);
            }
        }
        if (duplicate) return;

        if (overlapped) {
            collinearLinesGroup.addAll(toAdd);
            collinearLinesGroup.removeAll(toRemove);
        }
        else {
            // Neither overlap not duplicate
            collinearLinesGroup.add(currOG);
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
        return collinearLinesGroup.size();
    }

    public LineSegment[] segments() {
        // the line segments
        for (Pair items : collinearLinesGroup) {
            // StdOut.printf("\n\n\nThe collinear line group is: %s\n", items.getString());
            Point[] group = items.getGroup();
            Arrays.sort(group);
            Point min = group[0];
            Point max = group[items.getSize() - 1];
            collinearLines.add(new LineSegment(min, max));
        }
        return collinearLines.toArray(new LineSegment[numberOfSegments()]);
    }

    private class Pair {
        private Point[] group;
        private double slope;
        private ArrayList<Point> points;

        public Pair(Point[] group, double slope) {
            this.group = group;
            this.slope = slope;
        }

        public int getSize() {
            return group.length;
        }

        public Point[] getGroup() {
            return group;
        }

        public ArrayList<Point> getArray() {
            if (points == null)  // to avoid null-pointer error
                // to prevent duplication each time getArray() is called
                points = new ArrayList<>(Arrays.asList(group));
            return points;
        }

        public String getString() {
            return Arrays.toString(group);
        }

        public double getSlope() {
            return slope;
        }
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
        // StdOut.println("Number of segments: " + collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {

            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

