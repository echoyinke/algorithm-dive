package com.keyin.algorithms.mathematics.timing;

import com.keyin.algorithms.mathematics.GreatestCommonDivisor;

import java.text.DecimalFormat;

public class GCDTiming {

    private static final DecimalFormat FORMAT = new DecimalFormat("#.######");

    public static void main(String[] args) {
        // Euclidean
        {
            System.out.println("Euclid's Greatest Common Divisor.");
            long x = 1989;
            long y = 867;
            long before = System.nanoTime();
            GreatestCommonDivisor.gcdUsingEuclides(x, y);
            long after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }
        // Recursion
        {
            System.out.println("Recursive's Greatest Common Divisor.");
            long x = 1989;
            long y = 867;
            long before = System.nanoTime();
            GreatestCommonDivisor.gcdUsingRecursion(x, y);
            long after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }
    }
}
