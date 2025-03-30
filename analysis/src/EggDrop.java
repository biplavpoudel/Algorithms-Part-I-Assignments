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
        int critical = StdRandom.uniformInt(0, size);
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
        for (int i = 0; i < size; i++) {
            if (i == critical) {
                numberOfEggs--;
                StdOut.printf("The egg broke at Floor %d!", i);
            }
        }


    }

    public static void versionOne(int size, int critical) {
        //∼ 1 log n eggs and ∼ 1 lgn tosses.

    }

    public static void versionTwo(int size, int critical) {
        //∼ log T eggs and ∼ 2 log T tosses
    }

    public static void versionThree(int size, int critical) {
        // 2 eggs and ∼ 2 * (square root of n) tosses
    }

    public static void versionFour(int size, int critical) {
        // 2 eggs and ≤ c * (square root of T) tosses for some fixed constant c
    }
}
