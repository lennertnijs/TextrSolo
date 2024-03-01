package com.Textr.View;

import com.Textr.Point.Point;
import com.Textr.Validator.Validator;

public final class ViewCreator {

    private ViewCreator(){

    }

    public static View create(int fileBufferId, Point position, Dimension2D dimensions){
        Validator.notNegative(fileBufferId, "Cannot create a View with a negative FileBuffer id.");
        Validator.notNull(position, "Cannot create a View with a null position.");
        Validator.notNull(dimensions, "Cannot create a View with null dimensions.");
        Point anchor = Point.create(0,0);
        return View.builder().fileBufferId(fileBufferId).position(position).dimensions(dimensions).anchor(anchor).build();
    }

}
