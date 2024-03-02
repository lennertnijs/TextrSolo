package com.Textr.View;

import com.Textr.Util.Point;
import com.Textr.Validator.Validator;

public class AnchorUpdater {

    /**
     * Private constructor. Not for use.
     */
    private AnchorUpdater(){
    }

    /**
     * Updates the anchor point based on the cursor point and the terminal dimensions.
     * @param anchor The anchor point. Cannot be null.
     * @param cursor The cursor point. Cannot be null.
     * @param dimensions The dimensions of the terminal. Cannot be nul.
     *
     * @throws IllegalArgumentException If the anchor point, cursor point or dimensions are null.
     */
    public static void updateAnchor(Point anchor, Point cursor, Dimension2D dimensions){
        Validator.notNull(anchor, "cd");
        Validator.notNull(cursor, "The insertion point cannot be null.");
        Validator.notNull(dimensions, "The dimensions cannot be null.");
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
