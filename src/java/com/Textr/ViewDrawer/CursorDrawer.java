package com.Textr.ViewDrawer;

import com.Textr.Util.Point;
import com.Textr.Terminal.TerminalService;
import com.Textr.Util.Validator;

/**
 * Draws the cursor.
 */
public final class CursorDrawer {

    private CursorDrawer(){
    }

    /**
     * Calculates the position for where to draw the cursor on the screen.
     * @param position The position of the view (0-based). Cannot be null.
     * @param anchor The anchor of the view (0-based). Cannot be null.
     * @param cursor The cursor of the buffer (0-based). Cannot be null.
     *
     * @throws IllegalArgumentException If the position, anchor or cursor is null.
     */
    public static void draw(Point position, Point anchor, Point cursor){
        Validator.notNull(position, "Cannot draw the cursor because the position is null.");
        Validator.notNull(anchor, "Cannot draw the cursor because the anchor is null.");
        Validator.notNull(cursor, "Cannot draw the cursor because the cursor position is null.");
        int x = position.getX() + cursor.getX() - anchor.getX();
        int y = position.getY() + cursor.getY() - anchor.getY();
        TerminalService.moveCursor(x, y);
    }
}
