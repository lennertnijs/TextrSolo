package com.Textr.View;

import com.Textr.Util.Dimension2D;
import com.Textr.Util.Point;
import com.Textr.Util.Validator;

/**
 * Updates the anchor point of a View.
 */
public final class AnchorUpdater {

    /**
     * Private constructor. Not for use.
     */
    private AnchorUpdater(){
    }

    /**
     * Updates the anchor point based on the cursor point and the terminal dimensions.
     * The anchor point represents the top-left point from which onwards a part of the buffer's text should be printed.
     * @param anchor The anchor. Cannot be null.
     * @param cursor The cursor. Cannot be null.
     * @param dimensions The dimensions of the terminal. Cannot be nul.
     *
     * @throws IllegalArgumentException If the anchor, cursor or dimensions are null.
     */
    public static void updateAnchor(Point anchor, Point cursor, Dimension2D dimensions){
        Validator.notNull(anchor, "Cannot update a null anchor.");
        Validator.notNull(cursor, "Cannot update the anchor because the cursor is null.");
        Validator.notNull(dimensions, "Cannot update the anchor because the dimensions of the terminal are null.");
        if(cursor.getX() < anchor.getX()){
            anchor.setX(cursor.getX());
        }
        if(cursor.getY() < anchor.getY()){
            anchor.setY(cursor.getY());
        }
        if(cursor.getX() > anchor.getX() + dimensions.getWidth() - 1){
            anchor.setX(cursor.getX() - dimensions.getWidth() + 1);
        }
        // -2 because the rows are have a status bar so -1
        if(cursor.getY() > anchor.getY() + dimensions.getHeight() - 2){
            anchor.setY(cursor.getY() - dimensions.getHeight() + 2);
        }
    }

}
