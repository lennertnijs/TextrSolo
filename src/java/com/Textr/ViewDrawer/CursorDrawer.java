package com.Textr.ViewDrawer;

import com.Textr.Util.Point;
import com.Textr.Terminal.TerminalService;
import com.Textr.Validator.Validator;

public final class CursorDrawer {

    private CursorDrawer(){
    }

    /**
     * Calculates the position for where to draw the cursor on the screen.
     * @param position The position of the view (0-based). Cannot be null.
     * @param anchor The anchor of the view (0-based). Cannot be null.
     * @param insertion The insertion point of the buffer (0-based). Cannot be null.
     *
     * @throws IllegalArgumentException If the position, anchor or insertion is null.
     */
    public static void draw(Point position, Point anchor, Point insertion){
        Validator.notNull(position, "Cannot draw the cursor because the position is null.");
        Validator.notNull(anchor, "Cannot draw the cursor because the anchor is null.");
        Validator.notNull(insertion, "Cannot draw the cursor because the insertion point is null.");
        int x = position.getX() + insertion.getX() - anchor.getX();
        int y = position.getY() + insertion.getY() - anchor.getY();
        TerminalService.moveCursor(x, y);
    }
}
