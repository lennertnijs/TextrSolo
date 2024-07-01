package com.textr.filebuffer;

import com.textr.util.Direction;
import com.textr.util.Point;

import java.util.*;

/**
 * A class representing a text using a 1-dimensional string builder.
 */
public final class LineText implements IText {

    /**
     * The text's string builder. Characters are inserted and removed as necessary.
     */
    private final StringBuilder builder;
    /**
     * The internal line break used.
     */
    private static final char LINEBREAK = '\n';

    /**
     * Creates a MUTABLE {@link LineText} with the given string.
     * All newline characters are replaced by the internal \n line break.
     * @param text The text string. Cannot be null.
     */
    public LineText(String text){
        Objects.requireNonNull(text, "Text string is null.");
        String replacedLineBreaks = text.replace("\r\n", String.valueOf(LINEBREAK))
                                        .replace('\r', LINEBREAK);
        this.builder = new StringBuilder(replacedLineBreaks);
    }

    /**
     * {@inheritDoc}
     */
    public Point getInsertPoint(int index){
        if(index < 0 || index > builder.length()){
            throw new IndexOutOfBoundsException("Index outside text's bounds.");
        }
        return convertToPoint(index);
    }

    /**
     * {@inheritDoc}
     */
    public String getContent(){
        return builder.toString();
    }

    /**
     * {@inheritDoc}
     */
    public String[] getLines(){
        return builder.toString().split("\n", -1);
    }

    /**
     * {@inheritDoc}
     */
    public int getLineAmount(){
        return getLines().length;
    }

    /**
     * {@inheritDoc}
     */
    public int getLineLength(int rowIndex){
        if(rowIndex < 0 || rowIndex >= getLineAmount()){
            throw new IndexOutOfBoundsException("Line's row index is outside bounds.");
        }
        return getLines()[rowIndex].length();
    }

    /**
     * {@inheritDoc}
     */
    public int getCharAmount(){
        return builder.toString().length();
    }

    /**
     * {@inheritDoc}
     */
    public char getCharacter(int index){
        if(index < 0 || index >= builder.length()) {
            throw new IndexOutOfBoundsException("Index is outside text bounds.");
        }
        return builder.charAt(index);
    }

    /**
     * {@inheritDoc}
     */
    public void insert(char c, int index){
        if(index < 0 || index > builder.length()) {
            throw new IndexOutOfBoundsException("The insertion index is outside text bounds.");
        }
        builder.insert(index, c);
    }

    /**
     * {@inheritDoc}
     */
    public void delete(int index){
        if(index < 0 || index >= builder.length()) {
            throw new IndexOutOfBoundsException("The removal index is outside text bounds.");
        }
        builder.deleteCharAt(index);
    }

    /**
     * {@inheritDoc}
     */
    public int move(Direction direction, int index){
        Objects.requireNonNull(direction, "Direction is null.");
        if(index < 0 || index > builder.length()){
            throw new IndexOutOfBoundsException("Index is negative or bigger than the length of the content.");
        }
        switch(direction){
            case RIGHT -> { return moveRight(index);}
            case DOWN -> {return moveDown(index);}
            case LEFT -> {return moveLeft(index);}
            case UP -> {return moveUp(index);}
            default -> throw new RuntimeException();
        }
    }

    /**
     * Moves the insert index one to the right, if not already in the rightmost position.
     */
    private int moveRight(int index){
        if(index >= builder.length()){
            return index;
        }
        return index + 1;
    }

    /**
     * Moves the insert index one to the left, if not already in the leftmost position.
     */
    private int moveLeft(int index){
        if(index == 0){
            return index;
        }
        return index - 1;
    }

    /**
     * Moves the insert index one up, if not already in the first row.
     * Since moving up in a 1-dimensional context is hard to interpret and execute,
     * it transforms the insert index into a 2-dimensional insert point, with which it can
     * quickly move upwards, update appropriately and then convert back to the 1-dimensional insert index.
     */
    private int moveUp(int index){
        Point insertPoint = getInsertPoint(index);
        if(insertPoint.getY() == 0){
            return index;
        }
        int y = insertPoint.getY() - 1;
        int x = Math.min(insertPoint.getX(), getLineLength(y));
        Point p = new Point(x, y);
        return convertToIndex(p);
    }

    /**
     * Moves the insert index one down, if not already in the last row.
     * Since moving down in a 1-dimensional context is hard to interpret and execute,
     * it transforms the insert index into a 2-dimensional insert point, with which it can
     * quickly move down, update appropriately and then convert back to the 1-dimensional insert index.
     */
    private int moveDown(int index){
        Point cursorAsPoint = getInsertPoint(index);
        if(cursorAsPoint.getY() == getLineAmount() -1){
            return index;
        }
        int y = cursorAsPoint.getY() + 1;
        int x = Math.min(cursorAsPoint.getX(), getLineLength(y));
        Point p = new Point(x, y);
        return convertToIndex(p);
    }

    /**
     * Converts the internal insert index to a 2-dimensional insert point.
     * If the insert index is somehow in an illegal state (negative, or bigger than the text's contents),
     * behaviour will be undefined.
     * @return The point.
     */
    private Point convertToPoint(int index) {
        int row = 0;
        int column = 0;
        for(int i = 0; i <= builder.length(); i++){
            if(i == index){
                break;
            }
            if(builder.charAt(i) == '\n'){
                column = 0;
                row++;
            }else{
                column++;
            }
        }
        return new Point(column, row);
    }

    /**
     * Converts the given 2-dimensional insert point back to a 1-dimensional insert index, using the internal text's
     * contents.
     * If the insert point is somehow in an illegal state (null, or outside the text), behaviour will be undefined.
     * @param point The point. Cannot be null.
     *
     * @return The 1-dimensional insert index.
     */
    private int convertToIndex(Point point){
        int count = 0;
        for(int i = 0; i < getLineAmount(); i++){
            if(i < point.getY()){
                count += getLineLength(i) + 1;
            }else{
                break;
            }
        }
        return count + point.getX();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IText copy(){
        return new LineText(builder.toString());
    }

    /**
     * @return The string representation.
     */
    @Override
    public String toString(){
        return String.format("LineText[content={%s}]", builder.toString());
    }
}
