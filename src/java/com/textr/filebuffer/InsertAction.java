package com.textr.filebuffer;

import java.util.Objects;

public final class InsertAction implements Action{

    private final char character;
    private final int index;
    private final IText text;

    /**
     * Creates a new {@link InsertAction}.
     * Stores the 1D index and the inserted character.
     * @param index The index. Cannot be outside the Text's range.
     * @param character The character.
     * @param text The text. Cannot be null.
     * @throws IllegalArgumentException If the index is less than 0 or greater than or equal to the character length
     */
    public InsertAction(int index, char character, IText text){
        Objects.requireNonNull(text, "Text is null.");
        if(index < 0 || index > text.getCharAmount())
            throw new IndexOutOfBoundsException(String.format(
                    "Index %d out of bounds for length %d of text structure", index, text.getCharAmount()
            ));
        this.index = index;
        this.character = character;
        this.text = text;
    }

    /**
     * Executes this {@link InsertAction}.
     */
    public void execute(){
        text.insert(index, character);
    }

    /**
     * Undoes this {@link InsertAction}.
     */
    public void undo(){
        text.remove(index);
    }
}
