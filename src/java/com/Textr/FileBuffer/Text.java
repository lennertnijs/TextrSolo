package com.Textr.FileBuffer;

import com.Textr.Util.Validator;

import java.util.Arrays;

/**
 * Represents a text.
 */
public final class Text {

    private String[] lines;

    /**
     * Private constructor. Use the create() methods.
     * @param lines The text lines. Cannot be, or contain null.
     */
    private Text(String[] lines){
        this.lines = lines;
    }

    /**
     * Static factory method to create a {@link Text} with.
     * @param lines The text's lines
     *
     * @return The text
     * @throws IllegalArgumentException If the array is null, or contains a null element.
     */
    public static Text create(String[] lines){
        Validator.notNull(lines, "Cannot create a Text with null array of lines.");
        String[] linesCopy = new String[lines.length];
        for(int i = 0; i < lines.length; i++){
            Validator.notNull(lines[i], "Cannot create a Text because it contains a null line.");
            linesCopy[i] = lines[i];
        }
        return new Text(linesCopy);
    }

    /**
     * Static factory method tot create a {@link Text} with.
     * @param text The text
     *
     * @return The text
     * @throws IllegalArgumentException If the text is null.
     */
    public static Text create(String text){
        Validator.notNull(text, "Cannot create a Text with a null text.");
        return new Text(text.split(System.lineSeparator(), -1)); // -1 to keep trailing empty strings
    }

    /**
     * @return This text's lines.
     */
    public String[] getLines(){
        return Arrays.copyOf(lines, lines.length);
    }

    /**
     * @return This text's text.
     */
    public String getText(){
        return String.join(System.lineSeparator(), lines);
    }

    /**
     * Returns the length of this text's line at the given 0-based row index.
     * @param row The row index. Cannot be negative or bigger than the amount of lines - 1.
     *
     * @return The length of the line at index row.
     * @throws IllegalArgumentException If the row index is negative or bigger than the amount of lines - 1.
     */
    public int getLineLength(int row){
        Validator.withinRange(row, 0, lines.length - 1, "Invalid index to retrieve a text line length.");
        return lines[row].length();
    }

    /**
     * @return The amount of lines in this text.
     */
    public int getAmountOfLines(){
        return lines.length;
    }

    /**
     * @return The amount of characters in this text. Does not include newline characters.
     */
    public int getAmountOfChars(){
        int length = 0;
        for(String line : lines){
            length += line.length();
        }
        return length;
    }

    /**
     * Inserts the given character into this {@link Text} at the given 0-based row & column indices.
     * @param character The character
     * @param row The row index. Cannot be negative or bigger than the amount of lines in this text - 1.
     * @param col The column index. Cannot be negative or bigger than the length of the row.
     *
     * @throws IllegalArgumentException If the row or column index is invalid.
     */
    public void insertCharacter(char character, int row, int col){
        Validator.withinRange(row, 0, lines.length - 1, "Cannot insert a character at an invalid row index.");
        Validator.withinRange(col, 0, lines[row].length(), "Cannot insert a character at an invalid column index");
        StringBuilder builder = new StringBuilder(lines[row]);
        builder.insert(col, character);
        lines[row] = builder.toString();
    }

    /**
     * Removes the character from the text at the given 0-based row and column indices.
     * @param row The row. Cannot be negative or bigger than the amount of lines in this {@link Text} - 1.
     * @param col The column. Cannot be -2 or lower, nor be bigger than the length of the row.
     *            The reason -1 and the length line are allowed is because these operations are equal to removing the
     *            newline character at the start/end of the line, thus concatenating two lines.
     *
     * @throws IllegalArgumentException If the row or column index is invalid.
     */
    public void removeCharacter(int row, int col){
        Validator.withinRange(row, 0, lines.length - 1, "Cannot remove a character at an invalid row index.");
        Validator.withinRange(col, -1, lines[row].length(), "Cannot remove a character at an invalid column index");
        if(row == 0 && col == -1 || row == lines.length - 1 && col == lines[row].length()){
            return;
        }
        if(col == -1){
            concatenateRowAndNext(row - 1);
            return;
        }
        if(col == lines[row].length()){
            concatenateRowAndNext(row);
            return;
        }
        removeCharacterBase(row, col);
    }

    /**
     * Removes a character from this text that is not at the -1 column or last column.
     * @param row The row
     * @param col The column
     */
    private void removeCharacterBase(int row, int col){
        StringBuilder builder = new StringBuilder(lines[row]);
        builder.deleteCharAt(col);
        lines[row] = builder.toString();
    }

    /**
     * Removes a character from this {@link Text} that is at the -1 column.
     * This means concatenating this row and the next into 1 line.
     * @param row The row
     */
    private void concatenateRowAndNext(int row){
        String[] nwLines = new String[lines.length - 1];
        for(int i = 0; i < nwLines.length; i++){
            if(i < row){
                nwLines[i] = lines[i];
            }else if(i == row){
                nwLines[i] = lines[i] + lines[i + 1];
            }else{
                nwLines[i] = lines[i + 1];
            }
        }
        this.lines = nwLines;
    }

    /**
     * Splits the line at index row of this text into two new lines (breaks at index col).
     * @param row The row index. Cannot be negative, or bigger than the amount of lines in the text - 1.
     * @param col The colum index. Cannot be negative, or bigger than the length of the line at index row.
     *
     * @throws IllegalArgumentException If the row or column index is invalid.
     */
    public void splitLineAtColumn(int row, int col){
        Validator.withinRange(row, 0, lines.length - 1, "Cannot split a line at an invalid row index.");
        Validator.withinRange(col, 0, lines[row].length(), "Cannot split a line at an invalid column index");
        String[] newLines = new String[lines.length + 1];
        for(int i = 0; i < lines.length; i++){
            if(i < row){
                newLines[i] = lines[i];
            }else if(i == row){
                newLines[i] = lines[i].substring(0, Math.max(0, col));
                newLines[i+1] = lines[i].substring(col);
            }else{
                newLines[i+1] = lines[i];
            }
        }
        this.lines = newLines;
    }

    /**
     * Compares this text to the given object. Returns true if equal, false otherwise.
     * @param o The other object
     *
     * @return True if equal, false otherwise.
     */
    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!( o instanceof Text text)){
            return false;
        }
        return Arrays.equals(text.lines, this.lines);
    }

    /**
     * Generates and returns a hash code for this {@link Text}.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode(){
        return Arrays.hashCode(lines);
    }

    /**
     * Generates and returns a {@link String} representation of this text.
     *
     * @return The string representation.
     */
    @Override
    public String toString(){
        return String.format("Text[Lines: %s]", String.join(System.lineSeparator(), lines));
    }

    public Text copy(){
        return new Text(Arrays.copyOf(this.lines, this.lines.length));
    }
}
