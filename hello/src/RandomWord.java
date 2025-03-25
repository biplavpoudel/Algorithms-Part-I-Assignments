/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     3/13/25
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int count = 0;
        boolean rand;
        String champ = "";
        while (!StdIn.isEmpty()) {
            String words = StdIn.readString();
            count++;
            rand = StdRandom.bernoulli(1.0 / count);
            if (rand) {
                champ = words;
            }
        }
        StdOut.printf("%s\n", champ);
    }
}
