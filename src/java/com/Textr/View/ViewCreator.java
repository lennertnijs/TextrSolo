package com.Textr.View;

import com.Textr.Point.Point;
import com.Textr.Validator.Validator;

/**
 * Class to create Views with.
 */
public final class ViewCreator {

    private ViewCreator(){
    }

    /**
     * Creates and returns a new {@link View} with the given parameters.
     * A new {@link View} means:
     * * Anchor point is set at (0,0)
     * @param fileBufferId The id of the {@link FileBuffer} of this view
     * @param position The global Terminal position of this view
     * @param dimensions The dimensions of this view
     *
     * @return The {@link View}
     * @throws IllegalArgumentException If a parameter is invalid.
     */
    public static View create(int fileBufferId, Point position, Dimension2D dimensions){
        Validator.notNegative(fileBufferId, "Cannot create a View with a negative FileBuffer id.");
        Validator.notNull(position, "Cannot create a View with a null position.");
        Validator.notNull(dimensions, "Cannot create a View with null dimensions.");
        Point anchor = Point.create(0,0);
        return View.builder().fileBufferId(fileBufferId).position(position).dimensions(dimensions).anchor(anchor).build();
    }
}
