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

public class FastCol {

    private final ArrayList<LineSegment> collinearLines;
    // to add {min, max} array of collinear Points
    private final ArrayList<Pair> collinearLinesGroup;

    // finds all line segments containing 4 points
    public FastCol(Point[] points) {
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
            StdOut.printf("\n\n\ni == %d", i);
            Point[] clonedPoints = points.clone();
            Point referencePoint = clonedPoints[i];
            ArrayList<Double> slopeArr = new ArrayList<>();

            // Points have been sorted according to the slope they make it reference point
            Arrays.sort(clonedPoints, referencePoint.slopeOrder());
            StdOut.printf("\nThe sorted points based on %s are: \n", referencePoint);
            for (Point items : clonedPoints) {
                StdOut.printf("%s ", items);
            }


            // The slopes of each sorted points is added to an array
            StdOut.printf("\nThe slope of sorted points based on %s is: \n", referencePoint);
            for (Point items : clonedPoints) {
                StdOut.printf("%f ", referencePoint.slopeTo(items));
                slopeArr.add(referencePoint.slopeTo(items));
            }

            // now to compare adjacent slopes for collinearity
            int count = 2;  // begin with count of 2 where extra 1 is for reference point
            int firstCol;  // first Collinear point in the set
            for (int j = 2; j < size; j++) {  // first slope is always -ve infinity
                double previousSlope = slopeArr.get(j - 1);
                double currSlope = slopeArr.get(j);
                if (Double.compare(currSlope, previousSlope) == 0) {
                    count++;
                }
                else {
                    if (count >= 4) {
                        firstCol = j - count + 1;
                        addCollinearSegment(clonedPoints, firstCol, referencePoint, count);
                    }
                    count = 2;  // reset count if current slope not equal to previous
                }
            }
            // Since collinear line segments are only added if the next point has different slope,
            // the final collinear group doesn't get added when the loop ends.
            // So we need to add the final group at the end if count >= 3.
            if (count >= 4) {
                firstCol = size - count + 1;   // (size+1)-count to not mess index
                addCollinearSegment(clonedPoints, firstCol, referencePoint, count);
            }
        }
    }

    private void addCollinearSegment(Point[] Points, int firstCol, Point refPoint, int count) {
        Point[] colSegment = new Point[count];
        // first item is the reference point itself
        colSegment[0] = refPoint;

        for (int k = 0; k < (count - 1); k++) {
            colSegment[k + 1] = Points[firstCol + k];
        }

        // TESTING: this slope is the slope of the collinear points
        double groupSlope = colSegment[1].slopeTo(colSegment[2]);

        Arrays.sort(colSegment);
        Pair currOG = new Pair(colSegment, groupSlope);

        collinearChecker(currOG);
    }

    private void collinearChecker(Pair currOG) {
        Point keyMin = currOG.getGroup()[0];
        Point keyMax = currOG.getGroup()[currOG.getSize() - 1];
        double pairsSlope = currOG.getSlope();
        StdOut.printf("\nThe current group to be compared is: %s with slope: %f\n",
                      currOG.getString(), pairsSlope);

        ArrayList<Pair> toAdd = new ArrayList<>();
        ArrayList<Pair> toRemove = new ArrayList<>();
        boolean slopeEqual = false;
        boolean overLap = false;

        if (collinearLinesGroup.isEmpty()) {
            StdOut.printf(
                    "\nThe collinearLinesGroup is Empty. Adding to the collinearLinesGroup.\n");
            collinearLinesGroup.add(currOG);
        }
        else {
            for (Pair currItem : collinearLinesGroup) {
                StdOut.printf(
                        "DEBUG: The collinearLinesGroup item is %s with slope %f \n",
                        currItem.getString(),
                        currItem.getSlope()
                );

                Point currMax = currItem.getGroup()[currItem.getSize() - 1];
                Point currMin = currItem.getGroup()[0];
                double currSlope = currItem.getSlope();

                // if unequal slope, get next pairs for comparison
                if (Double.compare(pairsSlope, currSlope) != 0) {
                    StdOut.printf(
                            "Slopes are unequal. Comparing with another collinearLinesPairs if present.\n");
                    continue;
                }
                else {
                    StdOut.printf("Slopes are equal. Checking for duplicity.\n");
                    slopeEqual = true;
                    if (Double.compare(pairsSlope, currSlope) == 0
                            && keyMin.compareTo(currMin) == 0
                            && keyMax.compareTo(currMax) == 0) {
                        StdOut.printf(
                                "Duplicate found! Item not added to the collinearLinesGroup\n\n");
                        break;
                    }
                    else {
                        StdOut.printf("Not duplicate, same slope. Checking if segement overlaps.");
                        for (Point ogPoint : currOG.getGroup()) {
                            for (Point currPoint : currItem.getGroup()) {
                                if (ogPoint.compareTo(currPoint) == 0) {
                                    StdOut.printf(
                                            "Overlapping point found: %s. The lines are collinear.\n",
                                            currPoint);
                                    overLap = true;
                                    break;
                                }
                            }
                        }
                        StdOut.printf("The lines are coninciding.");
                        if (overLap) {
                            // let's find the maximal
                            Point[] maximal = { keyMin, keyMax, currMin, currMax };
                            Arrays.sort(maximal);
                            // new pair object is:
                            toAdd.add(new Pair(new Point[] { maximal[0], maximal[3] }, currSlope));
                            toRemove.add(currItem);
                        }
                    }
                }
            }
            if (slopeEqual) {
                for (Pair item : toAdd)
                    collinearLinesGroup.add(item);
            }
            if (!slopeEqual)
                collinearLinesGroup.add(currOG);
            if (overLap) {
                for (Pair item1 : toAdd)
                    collinearLinesGroup.add(item1);
                for (Pair item2 : toRemove)
                    collinearLinesGroup.remove(item2);
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
        return collinearLinesGroup.size();
    }

    public LineSegment[] segments() {
        // the line segments
        for (Pair items : collinearLinesGroup) {
            StdOut.printf("\n\n\nThe collinear line group is: %s\n", items.getString());
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
        FastCol collinear = new FastCol(points);
        StdOut.println("Number of segments: " + collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {

            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}


