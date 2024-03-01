package com.Textr.View;

import com.Textr.Point.Point;
import com.Textr.Validator.Validator;

public class AnchorUpdater {

    private AnchorUpdater(){
    }

    public static void updateAnchor(Point anchor, Point point, Dimension2D dimensions){
        Validator.notNull(anchor, "cd");
        Validator.notNull(point, "The insertion point cannot be null.");
        Validator.notNull(dimensions, "The dimensions cannot be null.");
        if(point.getX() < anchor.getX()){
            anchor.setX(point.getX());
        }
        if(point.getY() < anchor.getY()){
            anchor.setY(point.getY());
        }
        if(point.getX() > anchor.getX() + dimensions.getWidth() - 1){
            anchor.setX(point.getX() - dimensions.getWidth() + 1);
        }
        // -2 because the rows are have a status bar
        if(point.getY() > anchor.getY() + dimensions.getHeight() - 2){
            anchor.setY(point.getY() - dimensions.getHeight() + 2);
        }
    }

}
