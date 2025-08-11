/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     07 Aug, 2025
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.List;

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
        // rect can be used to draw lines and range search for given rectangle
        private final RectHV rect;
        private Node leftNode, rightNode;
        private boolean oddLevel;
        private int count;

        private Node(Point2D point, int count, boolean oddLevel, RectHV rect) {
            this.pt = point;
            this.count = count;
            this.oddLevel = oddLevel;
            this.rect = rect;
        }
    }

    // root node of KdTree
    private Node root;

    // to count the number of nearest-neighbor calls
    private int nearestNeighborCalls;

    // Constructs an empty set of points
    public KdTree() {
        this.nearestNeighborCalls = 0;
    }

    public int getNearestCalls() {
        return nearestNeighborCalls;
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
     * Recursively draw points that divide our space using pre-order traversal.
     * The boundary of rect is used for vertical/horizontal line.
     *
     * @param currNode current node that is being checked
     * @param oddLevel {@code true} y is key,
     *                 {@code false}  x is key
     */
    private void draw(Node currNode, boolean oddLevel) {
        if (currNode == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.015);
        StdDraw.point(currNode.pt.x(), currNode.pt.y());
        if (oddLevel) {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(currNode.rect.xmin(), currNode.pt.y(), currNode.rect.xmax(), currNode.pt.
                    y());
        }
        else {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(currNode.pt.x(), currNode.rect.ymin(), currNode.pt.x(),
                         currNode.rect.ymax());
        }
        draw(currNode.leftNode, !oddLevel);
        draw(currNode.rightNode, !oddLevel);
    }

    /**
     * Returns all points that are inside the rectangle (or on the boundary).
     *
     * @param rect given query rectangle
     * @return iterable list of points in the range of the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> rangeQ = new Queue<>();
        enqueue(root, rect, rangeQ);
        return rangeQ;
    }

    /**
     * Recursively checks for nodes that are inside the rectangle (or on the boundary),
     * and stores the points that satisfy the condition.
     *
     * @param curr   current Node on kd-Tree
     * @param rect   given query rectangle
     * @param rangeQ queue that holds points that fall inside the rectangle
     */
    private void enqueue(Node curr, RectHV rect, Queue<Point2D> rangeQ) {
        if (curr == null || !curr.rect.intersects(rect)) return;
        enqueue(curr.leftNode, rect, rangeQ);
        if (rect.contains(curr.pt)) rangeQ.enqueue(curr.pt);
        enqueue(curr.rightNode, rect, rangeQ);
    }


    /**
     * Returns a nearest neighbor in the set to point p
     *
     * @param p given query point.
     * @return the Point2D point closest to the query point; null if the kdTree is empty.
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return (root == null || !root.rect.contains(p)) ? null : nearest(root, p, root.pt, false);
    }

    private Point2D nearest(Node curr, Point2D that, Point2D closest, boolean oddLevel) {
        if (curr == null) return closest;
        nearestNeighborCalls++;
        double closestDist = closest.distanceSquaredTo(that);
        double currDist = curr.pt.distanceSquaredTo(that);

        if (currDist < closestDist) {
            closest = curr.pt;
            closestDist = currDist;
        }

        int cmp = oddLevel ? Double.compare(that.y(), curr.pt.y()) :
                  Double.compare(that.x(), curr.pt.x());

        // since this node is in same side as the point, we check for closest with this, first.
        Node firstCheck = (cmp < 0) ? curr.leftNode : curr.rightNode;
        // we use this node as alternate because two points in same side doesn't mean they are the closest,
        // node in opposite side of the point could be closer.
        Node secondCheck = (cmp < 0) ? curr.rightNode : curr.leftNode;

        // this checks for closest in the side of the point
        closest = nearest(firstCheck, that, closest, !oddLevel);

        // this checks for closest in the other side of the point,
        // if the distance from rect (corresponding to this 2nd node) is smaller than the closest point

        if (secondCheck != null && secondCheck.rect.distanceSquaredTo(that) < closestDist) {
            closest = nearest(secondCheck, that, closest, !oddLevel);
        }

        return closest;
    }

    // Unit testing of the methods
    public static void main(String[] args) {
        List<Point2D> points = new ArrayList<>();
        // read all the inputs and add to an arraylist
        while (!StdIn.isEmpty()) {
            double xAxis = StdIn.readDouble();
            double yAxis = StdIn.readDouble();
            StdOut.printf("(%f, %f)\n", xAxis, yAxis);
            points.add(new Point2D(xAxis, yAxis));
        }
        KdTree kdtree = new KdTree();
        // start timer and build tree
        Stopwatch watch = new Stopwatch();
        for (Point2D pt : points) {
            kdtree.insert(pt);
        }
        // stop timer and check time to build tree, excluding reading inputs from file
        StdOut.printf("Elapsed time to build kdTree is: %.6f seconds\n ", watch.elapsedTime());
        StdOut.printf("Size of kdTree is: %d \n", kdtree.size());

        // using input1M.txt
        Point2D toFind = new Point2D(0.684711, 0.818767);
        StdOut.printf("Does it contain %s ? %b \n", toFind, kdtree.contains(toFind));

        // start timer to find number of operations per second
        long start = System.nanoTime();
        Point2D p = new Point2D(0.864, 0.565);
        // for input1M.txt, should be (0.864377, 0.564852)
        Point2D nearest = kdtree.nearest(p);
        long end = System.nanoTime();

        StdOut.printf("The number of nearest neighbor calls is: %d \n", kdtree.getNearestCalls());
        double elapsedSec = (end - start) / 1e9;
        double opsPerSecond = kdtree.getNearestCalls() / elapsedSec;
        StdOut.printf("%s is close to %s\n", nearest, p);
        StdOut.printf("The nearest-neighbor calls performed per second is: %f\n", opsPerSecond);
        // should be 1.6403299999994846E-7
        StdOut.printf("The squaredDistance from " + p + " to " + nearest + " is " +
                              nearest.distanceSquaredTo(p) + "\n");
    }
}
