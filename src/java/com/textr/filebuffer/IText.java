package com.textr.filebuffer;

import com.textr.util.Direction;
import com.textr.util.Point;

/**
 * An interface to represent a text.
 */
public interface IText {


    /**
     * Generates and returns the 2-dimensional insert point of the 1-dimensional insert index in context of the text.
     * @param index The 1-dimensional insert index. Cannot be negative. Cannot be bigger than the length of the text.
     *
     * @return The 2-dimensional insert point of the text.
     */
    Point getInsertPoint(int index);

    /**
     * @return The text's content as a single string. Uses '\n' line break.
     */
    String getContent();

    /**
     * @return The amount of lines in this text's content.
     */
    int getLineAmount();

    /**
     * @return The text's content as an array of lines. EXCLUDES line breaks.
     */
    String[] getLines();

    /**
     * Fetches and returns the length of the line with the given row index. EXCLUDES line breaks.
     * @param rowIndex The row index. Cannot be negative. Cannot be equal or bigger than the amount of lines.
     *
     * @return The length of the line.
     */
    int getLineLength(int rowIndex);

    /**
     * @return The amount of characters in this text's content. INCLUDES LINEBREAKS.
     */
    int getCharAmount();

    /**
     * Fetches and returns the character at the given index.
     * @param index The index. Cannot be negative, or equal/bigger than the length of the text.
     *
     * @return The character.
     * @throws IndexOutOfBoundsException If the index is negative or equal/bigger than the length of the text.
     */
    char getCharacter(int index);

    /**
     * Inserts the given character in the text at the given index.
     * @param c The character.
     * @param index The index. Cannot be negative or bigger than the length of the content.
     *
     * @throws IndexOutOfBoundsException If the index is negative or bigger than the length of the text.
     */
    void insert(char c, int index);

    /**
     * Deletes whatever character is in the text at the given insert index.
     * Note that, for any insert index, the character at the same index is BEHIND that insert index.
     * Thus, all deletions are treated as DELETE AFTER.
     * @param index The index. Cannot be negative. Cannot be bigger than the length of the content.
     */
    void delete(int index);

    /**
     * Moves the insert index 1 unit in the given direction.
     * When moving right or left, the insert index will loop to the previous or next line when appropriate.
     * When moving up or down, the insert index will move left if the new line is shorter than the index's column.
     * When attempting to move outside the text, the method will do nothing:
     * - Moving right at the final index in the text.
     * - Moving left at the start of the text.
     * - Moving up in the first line of the text.
     * - Moving down in the last line of the text.
     * @param direction The direction. Cannot be null.
     */
    int move(Direction direction, int index);

    /**
     * @return A deep copy
     */
    IText copy();
}
