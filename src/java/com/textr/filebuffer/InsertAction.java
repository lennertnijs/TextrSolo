package com.textr.filebuffer;

import com.textr.util.Direction;

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
        if(index < 0 || index > text.getCharAmount())
            throw new IllegalArgumentException("Index outside the text's legal values.");
        Objects.requireNonNull(text, "Text is null.");
        this.index = index;
        this.character = character;
        this.text = text;
    }

    /**
     * Executes this {@link InsertAction}.
     * @param cursor The cursor. Cannot be null.
     */
    public void execute(ICursor cursor){
        Objects.requireNonNull(cursor, "Cursor is null.");
        text.insert(index, character);
        cursor.setInsertIndex(index + 1, text.getSkeleton());
    }

    /**
     * Undoes this {@link InsertAction}.
     * @param cursor The cursor. Cannot be null.
     */
    public void undo(ICursor cursor){
        Objects.requireNonNull(cursor, "Cursor is null.");
        text.remove(index);
        cursor.setInsertIndex(index, text.getSkeleton());
    }
}
