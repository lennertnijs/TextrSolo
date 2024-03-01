package com.Textr.ViewDrawer;

import com.Textr.Point.Point;
import com.Textr.Terminal.TerminalService;
import com.Textr.Validator.Validator;

public class CursorDrawer {

    private CursorDrawer(){

    }

    public static void draw(Point position, Point anchor, Point insertion){
        int column = position.getX() + insertion.getX() - anchor.getX();
        int row = position.getY() + insertion.getY() - anchor.getY();
        TerminalService.moveCursor(row, column);
    }
}
