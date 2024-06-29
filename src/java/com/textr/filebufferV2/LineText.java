package com.textr.filebufferV2;

import com.textr.util.Direction;
import com.textr.util.Point;

import java.util.*;

/**
 * A class representing a text using a 1-dimensional string builder.
 *
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
    private int insertIndex;

    /**
     * Creates a MUTABLE {@link LineText} with the given string.
     * Any \r\n and \r line breaks are replaced by the internal \n line break.
     * @param text The text string. Cannot be null.
     */
    public LineText(String text){
        Objects.requireNonNull(text, "Text string is null.");
        String replacedLineBreaks = text.replace("\r\n", String.valueOf(LINEBREAK))
                                        .replace('\r', LINEBREAK);
        this.builder = new StringBuilder(replacedLineBreaks);
        this.insertIndex = 0;
    }

    /**
     * {@inheritDoc}
     */
    public int getInsertIndex(){
        return insertIndex;
    }

    /**
     * {@inheritDoc}
     */
    public Point getInsertPoint(){
        return convertToPoint();
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
            throw new IllegalArgumentException("Row index is outside text bounds.");
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
    public void insert(int index, char character){
        if(index < 0 || index > builder.length()) {
            throw new IndexOutOfBoundsException("The insertion index is outside text bounds.");
        }
        builder.insert(index, character);
        moveRight();
    }

    /**
     * {@inheritDoc}
     */
    public void remove(int index){
        if(index < 0 || index >= builder.length()) {
            throw new IndexOutOfBoundsException("The removal index is outside text bounds.");
        }
        builder.deleteCharAt(index);
        if(index != insertIndex){
            moveLeft();
        }
    }

    /**
     * {@inheritDoc}
     */
    public void move(Direction direction){
        switch(direction){
            case RIGHT -> moveRight();
            case DOWN -> moveDown();
            case LEFT -> moveLeft();
            case UP -> moveUp();
        }
    }

    private void moveRight(){
        if(insertIndex >= builder.length()){
            return;
        }
        this.insertIndex++;
    }

    private void moveUp(){
        Point cursorAsPoint = getInsertPoint();
        if(cursorAsPoint.getY() == 0){
            return;
        }
        int y = cursorAsPoint.getY() - 1;
        int x = Math.min(cursorAsPoint.getX(), Math.max(0, getLineLength(y)));
        Point p = new Point(x, y);
        this.insertIndex = convertToIndex(p);
    }

    private void moveLeft(){
        if(insertIndex == 0){
            return;
        }
        this.insertIndex--;
    }

    private void moveDown(){
        Point cursorAsPoint = getInsertPoint();
        if(cursorAsPoint.getY() == getLineAmount() -1){
            return;
        }
        int y = cursorAsPoint.getY() + 1;
        int x = Math.min(cursorAsPoint.getX(), Math.max(0, getLineLength(y)));
        Point p = new Point(x, y);
        this.insertIndex = convertToIndex(p);
    }

    private Point convertToPoint() {
        int row = 0;
        int column = 0;
        for(int i = 0; i <= builder.length(); i++){
            if(i == insertIndex){
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
     * @return The string representation.
     */
    @Override
    public String toString(){
        return String.format("LineText[content=%s]", builder.toString());
    }
}
