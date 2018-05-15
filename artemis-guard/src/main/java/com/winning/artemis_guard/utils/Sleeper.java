package com.winning.artemis_guard.utils;

public class Sleeper {


    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ignored) {}
    }
}
