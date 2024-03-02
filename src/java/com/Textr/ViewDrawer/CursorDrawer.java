package com.Textr.ViewDrawer;

import com.Textr.Util.Point;
import com.Textr.Terminal.TerminalService;

public final class CursorDrawer {

    private CursorDrawer(){
    }

    public static void draw(Point position, Point anchor, Point insertion){
        int x = position.getX() + insertion.getX() - anchor.getX();
        int y = position.getY() + insertion.getY() - anchor.getY();
        TerminalService.moveCursor(x, y);
    }
}
