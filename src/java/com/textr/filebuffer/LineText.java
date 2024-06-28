package com.textr.filebuffer;

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
    public void addListener(TextListener newListener) {
        if (!listeners.add(newListener))
            throw new IllegalStateException("Listener to add already present in Text's listeners");
    }

    @Override
    public void removeListener(TextListener oldListener) {
        if (!listeners.remove(oldListener))
            throw new NoSuchElementException("Listener to remove does not exist in Text's listeners");
    }

    @Override
    public int getListenerCount() {
        return listeners.size();
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
     * @throws IllegalArgumentException If the index is less than 0 or greater than or equal to the character length
     */
    public String getLine(int index){
        String[] lines = builder.toString().split("\n", -1);
        if (index < 0 || index >= lines.length)
            throw new IndexOutOfBoundsException(
                    String.format("Index %d out of bounds for length %d", index, lines.length));
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
     * @throws IllegalArgumentException If the index is less than 0 or greater than or equal to the character length
     */
    public char getCharacter(int index){
        if(index < 0 || index >= builder.length())
            throw new IllegalArgumentException(String.format(
                    "Index %d out of bounds for length %d of text", index, builder.length()
            ));
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
     * @throws IllegalArgumentException If the index is less than 0 or greater than or equal to the character length
     */
    public void insert(int index, char character){
        if(index < 0 || index > builder.length())
            throw new IllegalArgumentException(String.format(
                    "Index %d out of bounds for length %d of text", index, builder.length()
            ));
        builder.insert(index, character);

        // Notify listeners
        TextUpdateType type = TextUpdateType.CHAR_UPDATE;
        if (character == '\n')
            type = TextUpdateType.LINE_UPDATE;

        for (TextListener listener: listeners)
            listener.update(new TextUpdateReference(index, true, type), getSkeleton());
    }

    /**
     * Insert a line break in the text at the given index.
     * @param index The index. Cannot be negative or bigger than the length of the content.
     *
     * @throws IllegalArgumentException If the index is less than 0 or greater than or equal to the character length
     */
    public void insertLineBreak(int index){
        if(index < 0 || index > builder.length())
            throw new IllegalArgumentException(String.format(
                    "Index %d out of bounds for length %d of text", index, builder.length()
            ));
        builder.insert(index, "\n");

        // Notify listeners
        for (TextListener listener: listeners)
            listener.update(new TextUpdateReference(index, true, TextUpdateType.LINE_UPDATE), getSkeleton());
    }

    /**
     * Removes the character in the text at the given index.
     * @param index The index. Cannot be negative or equal/bigger than the length of the content.
     *
     * @throws IllegalArgumentException If the index is less than 0 or greater than or equal to the character length
     */
    public void remove(int index){
        if(index < 0 || index >= builder.length())
            throw new IllegalArgumentException(String.format(
                    "Index %d out of bounds for length %d of text", index, builder.length()
            ));
        char deletedChar = builder.charAt(index);
        builder.deleteCharAt(index);

        // Notify listeners
        TextUpdateType type;
        if (deletedChar == '\n')
            type = TextUpdateType.LINE_UPDATE;
        else
            type = TextUpdateType.CHAR_UPDATE;
        for (TextListener listener: listeners)
            listener.update(new TextUpdateReference(index, false, type), getSkeleton());
    }

    /**
     * @return The string representation of this {@link LineText}.
     */
    @Override
    public String toString(){
        return String.format("LineText[Text=%s]", builder.toString());
    }
}
