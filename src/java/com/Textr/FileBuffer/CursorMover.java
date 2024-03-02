package com.Textr.FileBuffer;

import com.Textr.Util.Point;
import com.Textr.View.Direction;

public final class CursorMover {

    private CursorMover(){
    }

    /**
     * Moves the cursor 1 unit in the given direction and updates the cursor's values appropriately.
     * @param cursor The cursor. Cannot be null.
     * @param direction
     * @param text
     */
    public static void move(Point cursor, Direction direction, Text text){
        switch(direction){
            case UP -> moveUp(cursor, text);
            case RIGHT -> moveRight(cursor, text);
            case DOWN -> moveDown(cursor, text);
            case LEFT -> moveLeft(cursor, text);
        }
    }

    /**
     * Moves the cursor up one line, if appropriate.
     * After moving up it will update the cursor's X value if necessary.
     * @param cursor The cursor
     * @param text The text
     */
    private static void moveUp(Point cursor, Text text){
        boolean onFirstRow = cursor.getY() == 0;
        if(onFirstRow){
            return;
        }
        cursor.decrementY();
        updateXAfterYChange(cursor, text.getLineLength(cursor.getY()));
    }

    /**
     * Moves the cursor down one line, if appropriate.
     * After moving down it will update the cursor's X value if necessary.
     * @param cursor The cursor
     * @param text The text
     */
    private static void moveDown(Point cursor, Text text){
        boolean onLastRow = cursor.getY() == text.getAmountOfLines() - 1;
        if(onLastRow){
            return;
        }
        cursor.incrementY();
        updateXAfterYChange(cursor, text.getLineLength(cursor.getY()));
    }

    /**
     * Moves the cursor right one position, if appropriate.
     * After moving right, it will update the cursor's Y value if necessary.
     * @param cursor The cursor
     * @param text The text
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
     * Moves the cursor left one position, if appropriate.
     * After moving left, it will update the cursor's Y value if necessary.
     * @param cursor The cursor
     * @param text The text
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

    /**
     * Updates the X value of the cursor if necessary.
     * @param cursor The cursor
     * @param lineLength The length of the new line
     */
    private static void updateXAfterYChange(Point cursor, int lineLength){
        if(cursor.getX() > lineLength){
            cursor.setX(lineLength);
        }
    }
}
