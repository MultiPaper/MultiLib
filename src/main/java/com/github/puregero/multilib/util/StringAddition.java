package com.github.puregero.multilib.util;

public class StringAddition {
    public static String add(String A, String B) {
        if (A == null) {
            return B;
        }

        try {
            long a = Long.parseLong(A);
            long b = Long.parseLong(B);
            return Long.toString(a + b);
        } catch (NumberFormatException ignored) {}

        try {
            double a = Double.parseDouble(A);
            double b = Double.parseDouble(B);
            return Double.toString(a + b);
        } catch (NumberFormatException ignored) {}

        return B;
    }
}
