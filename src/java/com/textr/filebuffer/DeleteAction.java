package com.textr.filebuffer;

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
     */
    public DeleteAction(int index, char character, IText text){
        Objects.requireNonNull(text, "Text is null.");
        if(index < 0 || index >= text.getCharAmount())
            throw new IndexOutOfBoundsException("Index is invalid.");
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