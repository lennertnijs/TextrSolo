package com.textr.filebuffer;

import com.textr.filebufferV2.IText;

import java.util.Objects;

public final class DeleteAction implements Action {

    private final char character;
    private final int index;
    private final IText text;

    /**
     * Creates a new {@link DeleteAction}.
     * @param index The index. Cannot be negative or bigger/equal than the length of the text.
     * @param character The character.
     * @param text The text. Cannot be null.
     * @throws IllegalArgumentException If the index is less than 0 or greater than or equal to the character length
     */
    public DeleteAction(int index, char character, IText text){
        Objects.requireNonNull(text, "Text is null.");
        if(index < 0 || index >= text.getCharAmount())
            throw new IndexOutOfBoundsException(String.format(
                    "Index %d out of bounds for length %d of text structure", index, text.getCharAmount()
            ));
        this.character = character;
        this.index = index;
        this.text = text;
    }

    /**
     * Executes this {@link DeleteAction}.
     */
    public void execute(){
        text.remove(index);
    }

    /**
     * Undoes this {@link DeleteAction}.
     */
    public void undo(){
        text.insert(index, character);
    }
}