package com.Textr.View;

import com.Textr.Util.Point;
import com.Textr.Util.Validator;

/**
 * Class to create Views with.
 */
public final class ViewCreator {

    private ViewCreator(){
    }

    /**
     * Creates and returns a new view with the given parameters.
     * * The anchor point is set always initiated at (0,0)
     * @param fileBufferId The id of the file buffer of this view. Cannot be negative.
     * @param position The global Terminal position of this view. Cannot be null.
     * @param dimensions The dimensions of this view. Cannot be null.
     *
     * @return The view.
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
