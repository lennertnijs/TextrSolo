package com.Textr.View;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Used to generate unique view ids.
 */
public final class ViewIdGenerator {

    private static AtomicInteger id = new AtomicInteger();
    private ViewIdGenerator(){
    }

    /**
     * Returns a unique id for a view and automatically increments.
     *
     * @return The unique view id
     */
    public static int getId(){
        return id.getAndIncrement();
    }

    /**
     * Resets the view's id generator. This means they will start generating at 0 again.
     * USE THIS CAREFULLY. SHOULD ONLY BE USED FOR TEST PURPOSES.
     */
    public static void resetGenerator(){
        id = new AtomicInteger();
    }
}
