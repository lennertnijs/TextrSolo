package com.textr.filebufferV2;

import com.textr.util.FixedPoint;
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
    }

    /**
     * @return The text's content as a string. Line breaks will be '\n'.
     */
    public String getContent(){
        return builder.toString();
    }

    /**
     * @return The text's lines as an array. Does not include any line breaks. (As they are split based on these \n)
     */
    public String[] getLines(){
        return builder.toString().split("\n", -1);
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

    public int getLineLength(int lineIndex){
        return builder.toString().split("\n", -1)[lineIndex].length();
    }

    /**
     * Converts the given index to a {@link Point} object based on this text skeleton. Newlines are considered
     * to be at the end of the line, and they count as one character on that line.
     * @param index the index to convert to a point object
     * @return The 2D Point representing the same location as the given index in the text skeleton
     */
    public Point convertToPoint(int index) {
        if (index < 0 || index > this.getCharAmount()) {
            throw new IndexOutOfBoundsException("Index is outside text bounds.");
        }
        int count = 0;
        int row = -1;
        for(int i = 0; i < getLineAmount(); i++){
            if(index < count + getLineLength(i)) {
                row = i;
                break;
            }
            count += getLineLength(i) + 1;
        }
        int col = index - count;
        return new Point(col, row);
    }

    /**
     * Converts the given point into an index representing the same location in the text skeleton. Points at the end of
     * a line (i.e. with X at the line length) are considered to be on the newline character.
     * @param point The 2D point to convert to an index
     * @return The 1D index in the text structure representing the same position as the given 2D point
     * @throws IllegalArgumentException if the given point is not a valid point in the text structure
     */
    public int convertToIndex(Point point) {
        if (this.getLineAmount() <= point.getY() || this.getLineLength(point.getY()) < point.getX())
            throw new IllegalArgumentException("Given point does not hold a valid location in this TextSkeleton");
        int count = 0;
        for(int i = 0; i < point.getY(); i++){
            count += this.getLineLength(i);
        }
        count += point.getX();
        return count;
    }

    /**
     * @return The string representation.
     */
    @Override
    public String toString(){
        return String.format("LineText[content=%s]", builder.toString());
    }
}
