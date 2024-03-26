package com.textr.filebuffer;

import com.textr.util.Point;
import com.textr.util.Validator;
import com.textr.util.Direction;

/**
 * Handles the movement of the cursor.
 * Currently only supports 4-directional movement by one unit.
 */
public final class CursorMover {

    private CursorMover(){
    }

    /**
     * Moves the cursor 1 unit in the given direction within the given text and updates the cursor's values appropriately.
     * @param cursor The cursor. Cannot be null.
     * @param direction The direction. Cannot be null.
     * @param text The text in which the cursor is moving. Cannot be null.
     */
    public static void move(Point cursor, Direction direction, Text text){
        Validator.notNull(cursor, "Cannot move a null cursor.");
        Validator.notNull(direction, "Cannot move the cursor in a null Direction.");
        Validator.notNull(text, "Cannot move the cursor over a null Text.");
        switch(direction){
            case UP -> moveUp(cursor, text);
            case RIGHT -> moveRight(cursor, text);
            case DOWN -> moveDown(cursor, text);
            case LEFT -> moveLeft(cursor, text);
        }
    }

    /**
     * Moves the cursor up one line, if not already on the first line.
     * After moving up it will update the cursor's X value if necessary.
     * More precisely, if the new line is shorter than the old line, it will move the cursor to the end of the new line.
     * @param cursor The cursor. Cannot be null.
     * @param text The text. Cannot be null.
     */
    private static void moveUp(Point cursor, Text text){
        boolean onFirstRow = cursor.getY() == 0;
        if(onFirstRow){
            return;
        }
        cursor.decrementY();
        int newLineLength = text.getLineLength(cursor.getY());
        if(cursor.getX() > newLineLength){
            cursor.setX(newLineLength);
        }
    }

    /**
     * Moves the cursor down one line, if not already on the last line.
     * After moving down it will update the cursor's X value if necessary.
     * More precisely, if the new line is shorter than the old line, it will move the cursor to the end of the new line.
     * @param cursor The cursor. Cannot be null.
     * @param text The text. Cannot be null.
     */
    private static void moveDown(Point cursor, Text text){
        boolean onLastRow = cursor.getY() == text.getAmountOfLines() - 1;
        if(onLastRow){
            return;
        }
        cursor.incrementY();
        int newLineLength = text.getLineLength(cursor.getY());
        if(cursor.getX() > newLineLength){
            cursor.setX(newLineLength);
        }
    }

    /**
     * Moves the cursor right one position, if not already at the end of the current line.
     * After moving right, it will update the cursor's Y value if necessary.
     * More precisely, if the cursor was at the end of the line, it will move to the start of the next line,
     * if it was not at the last line of the text.
     * @param cursor The cursor. Cannot be null.
     * @param text The text. Cannot be null.
     */
    private static void moveRight(Point cursor, Text text){
        boolean isAtEndOfLine = cursor.getX() == text.getLineLength(cursor.getY());
        if(!isAtEndOfLine){
            cursor.incrementX();
            return;
        }
        boolean isAtEndOfText = cursor.getY() == text.getAmountOfLines() - 1;
        if(!isAtEndOfText){
            cursor.setX(0);
            cursor.incrementY();
        }
    }

    /**
     * Moves the cursor left one position, if not already at the end of the current line.
     * After moving left, it will update the cursor's Y value if necessary.
     * More precisely, if the cursor was at the beginning of the line, it will move to the end of the previous line,
     * if it was not at the first line of the text.
     * @param cursor The cursor. Cannot be null.
     * @param text The text. Cannot be null.
     */
    private static void moveLeft(Point cursor, Text text){
        boolean isAtStartOfLine = cursor.getX() == 0;
        if(!isAtStartOfLine){
            cursor.decrementX();
            return;
        }
        boolean isAtStartOfText = cursor.getY() == 0;
        if(!isAtStartOfText){
            cursor.decrementY();
            cursor.setX(text.getLineLength(cursor.getY()));
        }
    }
}
