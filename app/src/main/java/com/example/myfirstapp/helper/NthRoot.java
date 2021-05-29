package com.example.myfirstapp.helper;

public class NthRoot {

    /* Function to find nth root of a number */
  public static double root(double x, int n) {
        double lowerBound = 0;
        double upperBound = (1 > x) ? 1 : x;
        double approxRoot = (upperBound + lowerBound) / 2;
        while (approxRoot - lowerBound >= 0.001) {
            if (Math.pow(approxRoot, n) > x) upperBound = approxRoot;
            else if (Math.pow(approxRoot, n) < x) lowerBound = approxRoot;
            else break;
            approxRoot = (upperBound + lowerBound) / 2;
        }
        return Math.round(approxRoot * 1000.0) / 1000.0;
    }
}
