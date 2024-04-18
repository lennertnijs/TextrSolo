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
     */
    public InsertAction(int index, char character, IText text){
        Objects.requireNonNull(text, "Text is null.");
        if(index < 0 || index > text.getCharAmount())
            throw new IllegalArgumentException("Index outside the text's legal values.");
        this.index = index;
        this.character = character;
        this.text = text;
    }

    /**
     * Executes this {@link InsertAction}.
     * @param cursor The cursor. Cannot be null.
     */
    public void execute(){
        text.insert(index, character);
    }

    /**
     * Undoes this {@link InsertAction}.
     * @param cursor The cursor. Cannot be null.
     */
    public void undo(){
        text.remove(index);
    }
}
