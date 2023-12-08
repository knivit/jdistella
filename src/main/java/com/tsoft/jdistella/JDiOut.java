package com.tsoft.jdistella;

import java.util.Formatter;

public class JDiOut {

    public static final int stdout = 0;
    public static final int stderr = 1;

    public static void exit(int code) {
        System.exit(code);
    }

    public static String sprintf(String str, Object ... args) {
        return new Formatter().format(str, args).toString();
    }

    public static void fprintf(int channel, String str, Object ... args) {
        Formatter formatter = new Formatter().format(str, args);

        switch (channel) {
            case stderr -> System.err.print(formatter);
            default -> System.out.print(formatter);
        }
    }

    public static void print(String str, Object ... args) {
        Formatter formatter = new Formatter().format(str, args);
        System.out.print(formatter);
    }

    public static void printf(String str, Object ... args) {
        Formatter formatter = new Formatter().format(str, args);
        System.out.print(formatter);
    }
}
