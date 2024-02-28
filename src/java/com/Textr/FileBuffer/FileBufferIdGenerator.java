package com.Textr.FileBuffer;

import java.util.concurrent.atomic.AtomicInteger;

public class FileBufferIdGenerator {

    private static AtomicInteger id = new AtomicInteger();

    public static int getId(){
        return id.getAndIncrement();
    }

    public static void resetGenerator(){
        id = new AtomicInteger();
    }
}
