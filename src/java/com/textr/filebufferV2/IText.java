package com.textr.filebufferV2;

import com.textr.util.Point;

/**
 * An interface to represent a text.
 */
public interface IText {

    /**
     * @return The text's content as a single string. Line breaks may vary depending on implementation.
     */
    String getContent();

    /**
     * @return The text's content as an array of lines. Excludes line breaks.
     */
    String[] getLines();

    /**
     * @return The amount of lines in this text's content.
     */
    int getLineAmount();

    /**
     * @return The amount of characters in this text's content. Includes line breaks.
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
     * @param index The index. Cannot be negative or bigger than the length of the content.
     * @param character The character.
     *
     * @throws IndexOutOfBoundsException If the index is negative or bigger than the length of the text.
     */
    void insert(int index, char character);
    /**
     * Removes the character in the text at the given index.
     * @param index The index. Cannot be negative or equal/bigger than the length of the content.
     *
     * @throws IndexOutOfBoundsException If the index is negative or equal/bigger than the length of the text.
     */
    void remove(int index);

    Point convertToPoint(int index);
    int convertToIndex(Point point);
    int getLineLength(int lineIndex);
}
