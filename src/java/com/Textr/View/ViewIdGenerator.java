package com.Textr.View;

import java.util.concurrent.atomic.AtomicInteger;

public class ViewIdGenerator {

    private static AtomicInteger id = new AtomicInteger();
    private ViewIdGenerator(){
    }

    public static int getId(){
        return id.getAndIncrement();
    }

    public static void resetGenerator(){
        id = new AtomicInteger();
    }
}
