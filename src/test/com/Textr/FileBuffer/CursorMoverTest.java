package com.Textr.FileBuffer;

import com.Textr.Util.Direction;
import com.Textr.Util.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CursorMoverTest {

    @Test
    void move() {
        Text text = Text.create(new String[]{"Line 1", "Line 2 LONG", "Line 3"});
        Point cursor = Point.create(1, 1);

        /*
         * CursorMover should return IllegalArgumentException when a null argument is received
         */
        assertThrows(IllegalArgumentException.class, () -> CursorMover.move(null, Direction.UP, text));
        assertThrows(IllegalArgumentException.class, () -> CursorMover.move(cursor, null, text));
        assertThrows(IllegalArgumentException.class, () -> CursorMover.move(cursor, Direction.UP, null));

        /*
         * moving up:
         */
        cursor.setX(2);
        cursor.setY(2);
        CursorMover.move(cursor, Direction.UP, text); // Regular up
        assertEquals(1, cursor.getY(), "Cursor did not move up one line");
        assertEquals(2, cursor.getX(), "Cursor did not remain at correct X position (up)");

        cursor.setX(8);
        CursorMover.move(cursor, Direction.UP, text); // X > new line length
        assertEquals(0, cursor.getY(), "Cursor did not move up one line");
        assertEquals(text.getLineLength(0), cursor.getX(), "Cursor did not move to end of line correctly (up)");

        CursorMover.move(cursor, Direction.UP, text); // Already at top
        assertEquals(0, cursor.getY(), "Cursor did not remain at top of text moving up");
        assertEquals(text.getLineLength(0), cursor.getX(),
                "Cursor did not stay at position when moving up at top of text");

        /*
         * moving down:
         */
        cursor.setX(2);
        cursor.setY(0);
        CursorMover.move(cursor, Direction.DOWN, text); // Regular down
        assertEquals(1, cursor.getY(), "Cursor did not move down one line");
        assertEquals(2, cursor.getX(), "Cursor did not remain at correct X position (down)");

        cursor.setX(8);
        CursorMover.move(cursor, Direction.DOWN, text); // X > new line length
        assertEquals(2, cursor.getY(), "Cursor did not move down one line");
        assertEquals(text.getLineLength(2), cursor.getX(), "Cursor did not move to end of line correctly (down)");

        CursorMover.move(cursor, Direction.DOWN, text); // Already at bottom
        assertEquals(2, cursor.getY(), "Cursor did not remain at bottom of text moving up");
        assertEquals(text.getLineLength(2), cursor.getX(),
                "Cursor did not stay at position when moving down at bottom of text");

        /*
         * moving right:
         */
        cursor.setX(5);
        cursor.setY(0);
        CursorMover.move(cursor, Direction.RIGHT, text); // Regular right
        assertEquals(0, cursor.getY(), "Cursor did not remain at line on move to right");
        assertEquals(6, cursor.getX(), "Cursor did not move one character right within line");

        CursorMover.move(cursor, Direction.RIGHT, text); // End of line
        assertEquals(1, cursor.getY(), "Cursor did not move to new line when moving right at end of line");
        assertEquals(0, cursor.getX(), "Cursor did not move to start of new line when moving right at end of line");

        cursor.setX(6);
        cursor.setY(2);
        CursorMover.move(cursor, Direction.RIGHT, text); // End of text
        assertEquals(2, cursor.getY(), "Cursor did not remain at last line when moving right at end of text");
        assertEquals(6, cursor.getX(), "Cursor did not remain at end of line when moving right at end of text");

        /*
         * moving left:
         */
        cursor.setX(1);
        cursor.setY(1);
        CursorMover.move(cursor, Direction.LEFT, text); // Regular left
        assertEquals(1, cursor.getY(), "Cursor did not remain at line on move to left");
        assertEquals(0, cursor.getX(), "Cursor did not move one character left within line");

        CursorMover.move(cursor, Direction.LEFT, text); // Start of line
        assertEquals(0, cursor.getY(), "Cursor did not move to new line when moving left at start of line");
        assertEquals(text.getLineLength(0), cursor.getX(),
                "Cursor did not move to end of new line when moving left at start of line");

        cursor.setX(0);
        cursor.setY(0);
        CursorMover.move(cursor, Direction.LEFT, text); // End of text
        assertEquals(0, cursor.getY(), "Cursor did not remain at first line when moving left at start of text");
        assertEquals(0, cursor.getX(), "Cursor did not remain at start of line when moving left at start of text");
    }
}