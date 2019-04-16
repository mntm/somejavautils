package com.github.mntm.somejavautils;


import com.sun.istack.internal.NotNull;

import java.util.Calendar;
import java.util.Random;


/**
 * The method's name are self explanatory
 */
public class RandomUtils {
    private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static int getInteger() {
        Random rand = new Random(Calendar.getInstance().getTime().hashCode());
        return rand.nextInt();
    }

    public static int getInteger(int bound) {
        Random rand = new Random(Calendar.getInstance().getTime().hashCode());
        return rand.nextInt(bound);
    }

    public static String getRandomString(int length, @NotNull final String charset) {
        StringBuilder sb = new StringBuilder();

        Random rand = new Random(Calendar.getInstance().getTime().hashCode());

        int count = length;
        while (count-- != 0) {
            int character = rand.nextInt(charset.length());
            sb.append(charset.charAt(character));
        }

        return sb.toString();
    }

    public static String getRandomString(int length) {
        return getRandomString(length, ALPHA_NUMERIC_STRING);
    }

    public static boolean probability(int multiplier, int divider) {
        if (multiplier <= 0) return false;

        int d = Math.abs(divider);

        if (multiplier > d) return true;

        int c = new Random().nextInt(d);

        return (c < multiplier);
    }

    public static boolean probability(int percent) {
        if (percent <= 0) return false;
        if (percent >= 100) return true;

        int seed = 100 / Math.abs(percent);

        Random r = new Random();

        return (r.nextInt(seed) == 0);
    }
}
