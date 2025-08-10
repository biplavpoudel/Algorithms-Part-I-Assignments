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

    // Data structure to represent a node in 2d-tree
    private static class Node {

        private Point2D pt;
        private final RectHV rect;
        private Node leftNode, rightNode;
        private Node parentNode;
        private boolean oddLevel;
        private int count;

        private Node(Point2D point, int count, boolean oddLevel, RectHV rect) {
            this.pt = point;
            this.count = count;
            this.oddLevel = oddLevel;
            this.rect = rect;
        }
    }

    // The root node of KdTree
    private Node root;

    // Constructs an empty set of points
    public KdTree() {
    }

    /**
     * @return {@code true} if kdTree is empty, {@code false} is otherwise.
     */
    public boolean isEmpty() {
        return (root == null);
    }

    /**
     * @return {@code int} number of nodes in the kdTree.
     */
    public int size() {
        return size(root);
    }

    /**
     * @param node specific node
     * @return {@code int} number of children of the node.
     */
    private int size(Node node) {
        if (node == null) return 0;
        return node.count;
    }

    /**
     * Adds the point to the set, if it is not already in the set.
     * Running time: {@code log N}
     *
     * @param p a point to be inserted in the tree
     */
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        // If tree is empty, add first node as root
        if (root == null) {
            root = new Node(p, 1, false, new RectHV(0.0, 0.0, 1.0, 1.0));
            return;
        }

        // Let's use recursive insertion to insert new nodes into the tree
        // No need to fret thinking root changes with each insertion,
        // as the put() returns the original root itself through recursion
        root = put(root, p, false, root.rect); // root is even level
    }

    private Node put(Node currNode, Point2D point, boolean oddLevel, RectHV rect) {
        if (currNode == null) return new Node(point, 1, oddLevel, rect);
        // for even levels, x-axis is compared
        if (!oddLevel) {
            int cmp = Double.compare(point.x(), currNode.pt.x());
            RectHV boundary;
            if (cmp >= 0) {
                if (currNode.rightNode == null) {
                    boundary = new RectHV(currNode.pt.x(), rect.ymin(), rect.xmax(),
                                          rect.ymax());
                }
                else {
                    boundary = currNode.rightNode.rect;
                }
                currNode.rightNode = put(currNode.rightNode, point, !currNode.oddLevel, boundary);
            }
            else {
                if (currNode.leftNode == null) {
                    boundary = new RectHV(rect.xmin(), rect.ymin(), currNode.pt.x(),
                                          rect.ymax());
                }
                else {
                    boundary = currNode.leftNode.rect;
                }
                currNode.leftNode = put(currNode.leftNode, point, !currNode.oddLevel, boundary);
            }
        }
        // for odd levels, we compare y-axis
        else {
            RectHV boundary;
            int cmp = Double.compare(point.y(), currNode.pt.y());
            if (cmp >= 0) {
                if (currNode.rightNode == null) {
                    boundary = new RectHV(rect.xmin(), currNode.pt.y(), rect.xmax(),
                                          rect.ymax());
                }
                else {
                    boundary = currNode.rightNode.rect;
                }
                currNode.rightNode = put(currNode.rightNode, point, !currNode.oddLevel, boundary);
            }
            else {
                if (currNode.leftNode == null) {
                    boundary = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), currNode.pt.y());
                }
                else {
                    boundary = currNode.leftNode.rect;
                }
                currNode.leftNode = put(currNode.leftNode, point, !currNode.oddLevel, boundary);
            }

        }
        currNode.count = 1 + size(currNode.leftNode) + size(currNode.rightNode);
        return currNode;
    }


    /**
     * Checks if the point is present in the kdTree or not.
     * Running time: {@code log N}
     *
     * @param p a point to be checked for pre-existence
     * @return {@code true} if point is already present,
     * {@code false} if point is yet to be inserted.
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        Node currNode = root;
        boolean oddLevel = false;
        while (currNode != null) {
            int cmp;
            if (oddLevel) cmp = Double.compare(p.y(), currNode.pt.y());
            else cmp = Double.compare(p.x(), currNode.pt.x());

            if (cmp == 0 && currNode.pt.equals(p)) {
                return true;
            }
            else if (cmp > 0) {
                currNode = currNode.rightNode;
            }
            else if (cmp < 0) {
                currNode = currNode.leftNode;
            }
            oddLevel = !oddLevel;
        }
        return false;
    }

    /**
     * Draws all points to standard draw
     */
    public void draw() {
        draw(root, false);
    }

    /**
     * Recursively draw points that divide our space.
     *
     * @param currNode current node that is being checked
     * @param oddLevel {@code true} y is key,
     *                 {@code false}  x is key
     */
    private void draw(Node currNode, boolean oddLevel) {

    }

    /**
     * Returns all points that are inside the rectangle (or on the boundary).
     *
     * @param rect given query rectangle
     * @return iterable list of points in the range of the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        return null;
    }

    /**
     * Returns a nearest neighbor in the set to point p
     *
     * @param p given query point.
     * @return the Point2D point closest to the query point; null if the kdTree is empty.
     */
    public Point2D nearest(Point2D p) {
        return null;
    }

    // Unit testing of the methods
    public static void main(String[] args) {
    }
}
