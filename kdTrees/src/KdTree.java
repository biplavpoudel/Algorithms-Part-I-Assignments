/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     07 Aug, 2025
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

/**
 * The {@code KdTree} represents a mutable data type to represent a set of points in
 * the unit square, same as {@code PointSET}.
 * <p>
 * Instead of <b>BST</b>, KdTree uses <b>2d-tree </b>to implement the same API.
 * The 2d-tree is a generalization of a BST to two-dimensional keys.
 * </p>
 * <p>
 * The idea is to build a BST with points in the nodes,
 * using the x- and y-coordinates of the points as keys in strictly alternating sequence.
 * </p>
 * The prime advantage of a 2d-tree over a BST is that it supports efficient implementation of range
 * search and nearest-neighbor search.
 * <ul>
 * <li>Each node corresponds to an axis-aligned rectangle in the
 * unit square, which encloses all the points in its subtree.
 * </li>
 * <li>
 * The root corresponds to the unit square;
 * the left and right children of the root corresponds to the two rectangles split by the
 * x-coordinate of the point at the root; and so forth.
 * </li>
 * </ul>
 */
public class KdTree {

    // Constructs an empty set of points
    public KdTree() {
    }

    // Returns true if the set is empty
    public boolean isEmpty() {
        return false;
    }

    // Returns the number of points in the set
    public int size() {
        return 0;
    }

    // Adds the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
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
