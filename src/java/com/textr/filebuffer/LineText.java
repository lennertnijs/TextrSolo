package com.textr.filebuffer;

import com.textr.util.FixedPoint;
import com.textr.util.Point;

import java.util.*;

public final class LineText implements IText{

    private final StringBuilder builder;
    private final Set<TextListener> listeners = new HashSet<>();

    private static final char LINEBREAK = '\n';
    private LineText(StringBuilder builder){
        this.builder = builder;
    }

    public static LineText createFromString(String string){
        Objects.requireNonNull(string, "String is null.");
        String replacedLineBreaks = string.replace("\r\n", String.valueOf(LINEBREAK)).replace('\r', LINEBREAK);
        return new LineText(new StringBuilder(replacedLineBreaks));
    }

    @Override
    public boolean addListener(TextListener newListener) {
        return listeners.add(newListener);
    }

    @Override
    public boolean removeListener(TextListener oldListener) {
        return listeners.remove(oldListener);
    }

    private FixedPoint convertToPoint(int index) {
        ITextSkeleton structure = getSkeleton();
        int count = 0;
        int row = -1;
        for(int i = 0; i < structure.getLineAmount() ; i++){
            if(index < count + structure.getLineLength(i)) {
                row = i;
                break;
            }
            count += structure.getLineLength(i);
        }
        int col = index - count;
        return new FixedPoint(col, row);
    }

    private int convertToIndex(Point location) {
        ITextSkeleton structure = getSkeleton();
        if (structure.getLineAmount() >= location.getY() || structure.getLineLength(location.getY()) > location.getX())
            throw new IllegalArgumentException("Given location does not hold a valid location in this Text");
        int count = 0;
        for(int i = 0; i < location.getY(); i++){
            count += structure.getLineLength(i);
        }
        count += location.getX();
        return count;
    }

    /**
     * Returns the current content as a single String, with "\n" as line breaks.
     *
     * @return The String
     */
    public String getContent(){
        return builder.toString();
    }

    /**
     * Returns the lines of the current content as an array. Does not include line breaks.
     *
     * @return The String array.
     */
    public String[] getLines(){
        return builder.toString().split("\n", -1);
    }

    /**
     * Returns the line at the index.
     * @param index The index. Cannot be negative or equal/bigger than the amount of lines.
     *
     * @return The line
     * @throws IllegalArgumentException If the index is invalid.
     */
    public String getLine(int index){
        String[] lines = builder.toString().split("\n", -1);
        return lines[index];
    }

    /**
     * Returns the amount of lines in the current content. Supports empty lines.
     *
     * @return The line amount
     */
    public int getLineAmount(){
        return builder.toString().split("\n", -1).length;
    }

    /**
     * Returns the amount of characters in the current content. Counts line breaks as one character.
     *
     * @return The character amount
     */
    public int getCharAmount(){
        return builder.toString().length();
    }

    /**
     * Returns the character at the given index.
     * @param index The index. Cannot be negative, or equal/bigger than the length of the text.
     *
     * @return The character.
     * @throws IllegalArgumentException If the index is invalid.
     */
    public char getCharacter(int index){
        if(index < 0 || index >= builder.length())
            throw new IllegalArgumentException("Index is illegal.");
        return builder.charAt(index);
    }

    public ITextSkeleton getSkeleton(){
        List<Integer> lineLengths = new ArrayList<>();
        String[] lines = builder.toString().split("\n", -1);
        for(String line : lines){
            lineLengths.add(line.length() + 1);
        }
        int lastIndex = lineLengths.size() - 1;
        lineLengths.set(lastIndex, lineLengths.get(lastIndex));
        return new TextSkeleton(lineLengths);
    }

    /**
     * Inserts the given character in the text at the given index.
     * @param index The index. Cannot be negative or bigger than the length of the content.
     * @param character The character.
     *
     * @throws IllegalArgumentException If the index is illegal.
     */
    public void insert(int index, char character){
        if(index < 0 || index > builder.length())
            throw new IllegalArgumentException("Index is illegal.");
        builder.insert(index, character);

        // Notify listeners
        FixedPoint updateLocation = convertToPoint(index);
        for (TextListener listener: listeners)
            listener.update(updateLocation, true, TextUpdateType.CHAR_UPDATE, getSkeleton());
    }

    /**
     * Insert a line break in the text at the given index.
     * @param index The index. Cannot be negative or bigger than the length of the content.
     *
     * @throws IllegalArgumentException If the index is illegal.
     */
    public void insertLineBreak(int index){
        if(index < 0 || index > builder.length())
            throw new IllegalArgumentException("Index is illegal.");
        builder.insert(index, "\n");

        // Notify listeners
        FixedPoint updateLocation = convertToPoint(index);
        for (TextListener listener: listeners)
            listener.update(updateLocation, true, TextUpdateType.LINE_UPDATE, getSkeleton());
    }

    /**
     * Removes the character in the text at the given index.
     * @param index The index. Cannot be negative or equal/bigger than the length of the content.
     *
     * @throws IllegalArgumentException If the index is illegal.
     */
    public void remove(int index){
        if(index < 0 || index >= builder.length())
            throw new IllegalArgumentException("Index is illegal.");
        char deletedChar = builder.charAt(index);
        builder.deleteCharAt(index);

        // Notify listeners
        FixedPoint updateLocation = convertToPoint(index);
        TextUpdateType type;
        if (deletedChar == '\n')
            type = TextUpdateType.LINE_UPDATE;
        else
            type = TextUpdateType.CHAR_UPDATE;
        for (TextListener listener: listeners)
            listener.update(updateLocation, false, type, getSkeleton());
    }


    /**
     * Compares this {@link LineText} to the given object, and returns True if they're equal. False otherwise.
     *
     * @return True if equal, false otherwise.
     */
    @Override
    public boolean equals(Object other){
        if(!(other instanceof LineText text))
            return false;
        return builder.toString().contentEquals(text.builder);
    }

    /**
     * @return The hash code of this {@link LineText}.
     */
    @Override
    public int hashCode(){
        return builder.toString().hashCode();
    }

    /**
     * @return The string representation of this {@link LineText}.
     */
    @Override
    public String toString(){
        return String.format("LineText[Text=%s]", builder.toString());
    }
}
