package com.textr.filebuffer;

import java.util.Objects;

public final class DeleteAction implements Action {

    private final char character;
    private final int index;
    private final IText text;
    private final Side side;

    /**
     * Creates a new {@link DeleteAction}.
     * @param index The index. Cannot be negative or bigger/equal than the length of the text.
     * @param character The character.
     * @param text The text. Cannot be null.
     * @param side The {@link Side} on which to remove. Cannot be null.
     *
     * @throws IllegalArgumentException If the index/side combination are before the first or after the last character.
     */
    public DeleteAction(int index, char character, IText text, Side side){
        Objects.requireNonNull(text, "Text is null.");
        if(index < 0 || index >= text.getCharAmount())
            throw new IllegalArgumentException("Index is invalid.");
        Objects.requireNonNull(side, "Side is null.");
        if(index == 0 && side == Side.BEFORE || index == text.getCharAmount() - 1 && side == Side.AFTER)
            throw new IllegalArgumentException("Cannot delete before the first, or after the last character.");
        this.character = character;
        this.index = index;
        this.text = text;
        this.side = side;
    }

    /**
     * Executes this {@link DeleteAction}.
     * @param cursor The cursor. Cannot be null.
     */
    public void execute(ICursor cursor){
        Objects.requireNonNull(cursor, "Cursor is null.");
        if(side == Side.BEFORE) {
            text.remove(index - 1);
            cursor.setInsertIndex(index - 1, text.getSkeleton());
            return;
        }
        text.remove(index);
        cursor.setInsertIndex(index, text.getSkeleton());
    }

    /**
     * Undoes this {@link DeleteAction}.
     * @param cursor The cursor. Cannot be null.
     */
    public void undo(ICursor cursor){
        Objects.requireNonNull(cursor, "Cursor is null.");
        if(side == Side.BEFORE){
            text.insert(index - 1, character);
            cursor.setInsertIndex(index, text.getSkeleton());
            return;
        }
        text.insert(index, character);
        cursor.setInsertIndex(index + 1, text.getSkeleton());
    }
}