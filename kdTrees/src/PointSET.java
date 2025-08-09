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

// A mutable data type to represent a set of points in the unit square
// (all points have x- and y-coordinates between 0 and 1)
// using a 2d-tree to support efficient range search (find all the points contained in a query rectangle)
// and nearest-neighbor search (find the closest point to a query point).

public class PointSET {

    // edu.princeton.cs.algs4.SET is used to implement Red-Black Tree
    private SET<Point2D> rbBST;

    // Constructs an empty set of points
    public PointSET() {
        this.rbBST = new SET<>();
    }

    // Returns true if the set is empty
    public boolean isEmpty() {
        return size() == 0;
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
        return null;
    }

    // Returns a nearest neighbor in the set to point p; null if the set is empty
    // Worst Case: time proportional to the number of points in the set
    public Point2D nearest(Point2D p) {
        return null;
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
    }
}

