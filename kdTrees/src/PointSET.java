/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     07 Aug, 2025
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

// A data type to represent a set of points in the unit square
// (all points have x- and y-coordinates between 0 and 1)
// using a 2d-tree to support efficient range search (find all the points contained in a query rectangle)
// and nearest-neighbor search (find the closest point to a query point).


public class PointSET {

    private SET<Point2D> points;

    // Constructs an empty set of points
    public PointSET() {
        this.points = new SET<>();
    }

    // Returns true if the set is empty
    public boolean isEmpty() {
        return size() == 0;
    }

    // Returns the number of points in the set
    public int size() {
        return this.points.size();
    }

    // Adds the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (this.points.contains(p)) return;
        this.points.add(p);
    }

    // Returns true if the set contains point p
    public boolean contains(Point2D p) {
        return false;
    }

    // Draws all points to standard draw
    public void draw() {
    }

    // Returns all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        return null;
    }

    // Returns a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        return null;
    }

    // Unit testing of the methods
    public static void main(String[] args) {

    }
}

