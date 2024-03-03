package com.Textr.FileBuffer;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Used to generate unique buffer ids.
 */
public final class FileBufferIdGenerator {

    private static AtomicInteger id = new AtomicInteger();

    /**
     * Returns a unique id for a file buffer and automatically increments.
     *
     * @return The unique file buffer id
     */
    public static int getId(){
        return id.getAndIncrement();
    }

    /**
     * Resets the file buffer's id generator. This means they will start generating at 0 again.
     * USE THIS CAREFULLY. SHOULD ONLY BE USED FOR TEST PURPOSES.
     */
    public static void resetGenerator(){
        id = new AtomicInteger();
    }
}
