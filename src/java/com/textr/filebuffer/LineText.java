package com.textr.filebuffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class LineText implements IText{

    private final StringBuilder builder;

    private static final char LINEBREAK = '\n';
    private LineText(StringBuilder builder){
        this.builder = builder;
    }

    public static LineText createFromString(String string){
        Objects.requireNonNull(string, "String is null.");
        String replacedLineBreaks = string.replace("\r\n", String.valueOf(LINEBREAK)).replace('\r', LINEBREAK);
        return new LineText(new StringBuilder(replacedLineBreaks));
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
        if(index < 0 || index >= lines.length)
            throw new IllegalArgumentException("Index is illegal.");
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
        lineLengths.set(lastIndex, lineLengths.get(lastIndex) - 1);
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
        builder.deleteCharAt(index);
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
