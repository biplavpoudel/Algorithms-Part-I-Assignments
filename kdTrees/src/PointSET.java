/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     07 Aug, 2025
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code PointSET} represents a mutable data type to represent a set of points in
 * the unit square (all points have x- and y-coordinates between 0 and 1).<br><br>
 * PointSET, a brute-force implementation, uses a red-black BST for:
 * <ul>
 * <li>an eﬃcient range search (to find all the points contained in a query) rectangle.
 * <li>a nearest-neighbor search (to ﬁnd the closest point to a query point).
 * </ul>
 * <p>
 * This implementation uses a red-black BST using default library from algs4 package: {@code SET}.
 * </p>
 */
public class PointSET {

    // edu.princeton.cs.algs4.SET is used to implement Red-Black Tree
    private SET<Point2D> rbBST;

    // Constructs an empty set of points
    public PointSET() {
        this.rbBST = new SET<>();
    }

    // Returns true if the set is empty
    public boolean isEmpty() {
        return this.rbBST.isEmpty();
    }

    // Returns the number of points in the set
    public int size() {
        return this.rbBST.size();
    }

    // Adds the point to the set (if it is not already in the set)
    // Worst Case: time proportional to the logarithm of the number of points in the set
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Null point cannot be inserted!");
        this.rbBST.add(p);
    }

    // Returns true if the set contains point p
    // Worst Case: time proportional to the logarithm of the number of points in the set
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Null point cannot be checked!");
        return rbBST.contains(p);
    }

    // Draws all points to standard draw
    public void draw() {
        for (Point2D point : rbBST) {
            StdDraw.setPenRadius(0.015);
            StdDraw.point(point.x(), point.y());
        }
    }

    // Returns all points that are inside the rectangle (or on the boundary)
    // Worst Case: time proportional to the number of points in the set
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("The rectangle object is null!");
        List<Point2D> rangeBST = new ArrayList<>();
        for (Point2D point : rbBST) {
            if (rect.contains(point)) {
                rangeBST.add(point);
            }
        }
        return rangeBST;
    }

    // Returns a nearest neighbor in the set to point p; null if the set is empty
    // Worst Case: time proportional to the number of points in the set
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("The point object is null!");
        Point2D closestNeighbor = rbBST.iterator().next();
        // Squared distance is faster to compute as sqrt is not used
        double minDist = closestNeighbor.distanceSquaredTo(p);
        for (Point2D point : rbBST) {
            double dist = point.distanceSquaredTo(p);
            if (dist < minDist) {
                minDist = dist;
                closestNeighbor = point;
            }
        }
        return closestNeighbor;
    }

    // Unit testing of the methods
    public static void main(String[] args) {
        PointSET sets = new PointSET();
        while (!StdIn.isEmpty()) {
            double xAxis = StdIn.readDouble();
            double yAxis = StdIn.readDouble();
            StdOut.printf("(%f, %f)\n", xAxis, yAxis);
            sets.insert(new Point2D(xAxis, yAxis));
        }
        sets.draw();

        // For nearest neighbor
        Point2D test = new Point2D(0.5, 0.8);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.point(test.x(), test.y());

        Point2D nearest = sets.nearest(test);
        StdOut.printf("The closest point is: %s", sets.nearest(test));
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.point(nearest.x(), nearest.y());

        // For 2D range
        RectHV testRect = new RectHV(0.300, 0.700, 0.900, 0.850);
        StdDraw.setPenColor(StdDraw.GREEN);

        // Calculating center, half-width, and half-height
        double centerX = (testRect.xmin() + testRect.xmax()) / 2.0;
        double centerY = (testRect.ymin() + testRect.ymax()) / 2.0;
        double halfWidth = (testRect.xmax() - testRect.xmin()) / 2.0;
        double halfHeight = (testRect.ymax() - testRect.ymin()) / 2.0;
        StdDraw.rectangle(centerX, centerY, halfWidth, halfHeight);

        StdOut.printf("\n\nThe points in 2d range are:\n");
        for (Point2D points : sets.range(testRect)) {
            StdOut.print(points);
        }
    }
}

