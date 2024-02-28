package com.Textr.File;

import java.util.concurrent.atomic.AtomicInteger;

public class FileIdGenerator {
    private static AtomicInteger id = new AtomicInteger();

    public static int getId(){
        return id.getAndIncrement();
    }

    public static void resetGenerator(){
        id = new AtomicInteger();
    }
}
