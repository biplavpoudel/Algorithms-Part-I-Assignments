/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     10 May, 2025
 **************************************************************************** */

import java.util.Arrays;

public class BruteCollinearPoints {

    public BruteCollinearPoints(Point[] points) {
        // Check for IllegalArgumentExceptions
        if (points == null)
            throw new IllegalArgumentException("The constructor argument is null");
        else if (points.length > 4)
            throw new IllegalArgumentException("Pass only 4 points at a time!");
        else {
            for (Point item : points) {
                if (item == null)
                    throw new IllegalArgumentException("A null Point is passed to the constructor");
            }
            Arrays.sort(points);
            if (sortedDuplicateChecker(points))
                throw new IllegalArgumentException("Duplicate arguments found!");
        }
        // finds all line segments containing 4 points
        
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
        return 0;
    }

    public LineSegment[] segments() {
        // the line segments
        return new LineSegment[2];
    }

    public static void main(String[] args) {

    }
}
