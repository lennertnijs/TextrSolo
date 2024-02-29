package com.Textr.File;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class to generate unique {@link File} ids.
 */
public final class FileIdGenerator {
    private static AtomicInteger id = new AtomicInteger();

    /**
     * Generates a unique {@link File} id and returns it.
     * Automatically increments.
     *
     * @return A id.
     */
    public static int getId(){
        return id.getAndIncrement();
    }

    /**
     * Resets the {@link File} id generator back to 0.
     * USE WITH CAUTION
     */
    public static void resetGenerator(){
        id = new AtomicInteger();
    }
}
