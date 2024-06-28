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
     * @return The text's content as a string. Line breaks will be '\n'.
     */
    public String getContent(){
        return builder.toString();
    }

    public int getInsertIndex(){
        return insertIndex;
    }

    /**
     * @return The text's lines as an array. Does not include any line breaks. (As they are split based on these \n)
     */
    public String[] getLines(){
        return builder.toString().split("\n", -1);
    }

    /**
     * Fetches and returns the length of the line at the given row in the text. Includes the line break.
     * @param lineIndex The line's row index. Cannot be negative. Cannot be equal/greater than the text's amount of rows.
     *
     * @return The length.
     */
    public int getLineLength(int lineIndex){
        if(lineIndex < 0 || lineIndex >= getLineAmount()){
            throw new IllegalArgumentException("Line index is outside text bounds.");
        }
        if(lineIndex == getLineAmount() - 1){
            return builder.toString().split("\n", -1)[lineIndex].length();
        }
        return builder.toString().split("\n", -1)[lineIndex].length() + 1;
    }

    /**
     * @return The amount of lines in this text. Empty lines will count.
     */
    public int getLineAmount(){
        return builder.toString().split("\n", -1).length;
    }

    /**
     * @return The amount of characters. Line breaks count as 1 character.
     */
    public int getCharAmount(){
        return builder.toString().length();
    }

    /**
     * Fetches and returns the character at the given index.
     * @param index The index. Cannot be negative, or equal/bigger than the length of the text.
     *
     * @return The character.
     */
    public char getCharacter(int index){
        if(index < 0 || index >= builder.length()) {
            throw new IndexOutOfBoundsException("Index is outside text bounds.");
        }
        return builder.charAt(index);
    }

    /**
     * Inserts the given character in the text at the given index.
     * @param index The index. Cannot be negative or bigger than the length of the content.
     * @param character The character.
     */
    public void insert(int index, char character){
        if(index < 0 || index > builder.length()) {
            throw new IndexOutOfBoundsException("The insertion index is outside text bounds.");
        }
        builder.insert(index, character);
    }

    /**
     * Removes the character in the text at the given index.
     * @param index The index. Cannot be negative or equal/bigger than the length of the content.
     */
    public void remove(int index){
        if(index < 0 || index >= builder.length()) {
            throw new IndexOutOfBoundsException("The removal index is outside text bounds.");
        }
        builder.deleteCharAt(index);
    }

    /**
     * Converts the given index to a {@link Point} based on this text's structure.
     * @param index The index to convert. Cannot be negative. Cannot be bigger than the text's length.
     *
     * @return The 2-dimensional {@link Point} representing the 1-dimensional index in context of this text.
     */
    public Point convertToPoint(int index) {
        if (index < 0 || index > builder.length()) {
            throw new IndexOutOfBoundsException("Index is outside text bounds.");
        }
        int row = 0;
        int column = 0;
        for(int i = 0; i <= builder.length(); i++){
            if(i == index){
                return new Point(column, row);
            }
            if(builder.charAt(i) == '\n'){
                column = 0;
                row++;
            }else{
                column++;
            }
        }
        throw new IllegalStateException("Should be unreachable.");
    }

    /**
     * @return The string representation.
     */
    @Override
    public String toString(){
        return String.format("LineText[content=%s]", builder.toString());
    }

    public void move(Direction direction){
        switch(direction){
            case RIGHT: moveRight();
            case DOWN: moveDown();
            case LEFT: moveLeft();
            case UP: moveUp();
        }
    }

    private void moveRight(){
        if(insertIndex == builder.length() + 1){
            return;
        }
        this.insertIndex++;
    }

    private void moveUp(){
        int count = 0;
        while(insertIndex != 0 || getCharacter(insertIndex) != '\n'){
            count++;
            insertIndex--;
        }
        if(insertIndex == 0){
            insertIndex = count;
            return;
        }
        insertIndex--;
        while(getCharacter(insertIndex) != '\n'){
            insertIndex--;
        }
        this.insertIndex += count;
    }

    private void moveLeft(){
        if(insertIndex == 0){
            return;
        }
        this.insertIndex--;
    }

    private void moveDown(){
        // do shit
    }
}
