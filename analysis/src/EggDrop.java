/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     29 Mar, 2025
 **************************************************************************** */

// Suppose that you have an  n-story building (with floors 1 through n) and plenty of eggs.
// An egg breaks if it is dropped from floor T or higher and does not break otherwise.
// Your goal is to devise a strategy to determine the value of T
// given the following limitations on the number of eggs and tosses:

// Version 0: 1 egg, ≤ T tosses.
// Version 1: ∼ 1 log n eggs and ∼ 1 lgn tosses.
// Version 2: ∼ log T eggs and ∼ 2 log T tosses.
// Version 3: 2 eggs and ∼ 2 * (square root of n) tosses.
// Version 4: 2 eggs and ≤ c * (square root of T) tosses for some fixed constant c.

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class EggDrop {

    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        int version = Integer.parseInt(args[1]);
        int critical = StdRandom.uniformInt(1, size);
        switch (version) {
            case 0:
                versionZero(size, critical);
                break;
            case 1:
                versionOne(size, critical);
                break;
            case 2:
                versionTwo(size, critical);
                break;
            case 3:
                versionThree(size, critical);
                break;
            case 4:
                versionFour(size, critical);
                break;
        }
    }

    public static void versionZero(int size, int critical) {
        // 1 egg, ≤ T tosses
        int numberOfEggs = 1;
        int i = 1;
        int floor = 1;
        while (numberOfEggs != 0) {
            if (i >= critical) {
                numberOfEggs--;
                StdOut.printf("The egg broke at Floor %d!", i);
            }
            i++;
        }


    }

    public static void versionOne(int size, int critical) {
        //∼ 1 log n eggs and ∼ 1 lgn tosses.
        // we need to find the value of T at which the eggs start breaking
        // number of tosses is equal to number of eggs so no need to count eggs used
        int[] floors = new int[size + 1];
        int numberOfEggs = 0;
        for (int i = 1; i <= size; i++) {
            floors[i] = i;
        }
        int lo = 1;
        int hi = size - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (floors[mid] == critical) {
                numberOfEggs++;
                StdOut.printf("The number of eggs broke is %d\n", numberOfEggs);
                StdOut.printf("The eggs start breaking from Floor %d\n", floors[mid]);
                return;
            }
            else if (floors[mid] > critical) {
                hi = mid - 1;
                numberOfEggs++;
            }
            else lo = mid + 1;
        }
    }

    public static void versionTwo(int size, int critical) {
        //∼ log T eggs and ∼ 2 log T tosses
        // binary search takes logn but T is smaller than n
        // so we need to find range of T and perform binary search for tosses to not exceed log T

        int[] floors = new int[size + 1];
        for (int i = 1; i <= size; i++) {
            floors[i] = i;
        }
        int numberOfEggs = 1; // first egg breaks when level > critical
        int numberOfTosses = 0;
        int level = 1;

        // while floor is less than total floors and floor doesn't reach T
        while (level < size && floors[level] < critical) {
            level *= 2;
            numberOfTosses++;
        }
        // the level is in [2^(k-1) + 1, 2^k - 1], where 2^k is when egg breaks
        // so T is in the range [2^(k-1) + 1, 2^k - 1].
        // binary_search(arr, critical, level/2+1, level-1)
        int lo = level / 2 + 1;
        int hi = Math.min(level, size);

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            numberOfTosses++;

            if (floors[mid] == critical) {
                numberOfEggs++;
                StdOut.printf("The number of eggs broken is %d\n", numberOfEggs);
                StdOut.printf("The eggs start breaking from Floor %d\n", floors[mid]);
                return;
            }
            else if (floors[mid] > critical) {
                hi = mid - 1;
                numberOfEggs++;
            }
            else lo = mid + 1;
        }
    }

    public static void versionThree(int size, int critical) {
        // 2 eggs and ∼ 2 * (square root of n) tosses
        int numberOfTosses = 0;
        double level = Math.sqrt(size);
        while (level < size && level < critical) {
            level += Math.sqrt(size);
            numberOfTosses++;
        }
        int brokenEgg = 1; // first egg breaks when level > k.sqrt(n)
        // the range of T is: [(k-1)sqrt(n)+1, k*sqrt(n)-1
        int lo = (int) (level - Math.sqrt(size) - 1);
        int hi = (int) (level);
        int loc = hi;

        for (int floor = lo; floor <= hi; floor++) {
            numberOfTosses++;
            if (floor >= critical) {
                loc = floor;
                brokenEgg++;
                break;
            }
        }
        StdOut.printf("%d broke at Floor %d at %d tosses\n", brokenEgg, loc, numberOfTosses);
        StdOut.printf("The critical value was: %d\n", critical);
    }

    public static void versionFour(int size, int critical) {
        // 2 eggs and ≤ c * (square root of T) tosses for some fixed constant c
    }
}
