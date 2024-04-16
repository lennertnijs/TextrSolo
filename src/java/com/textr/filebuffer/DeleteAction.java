package com.textr.filebuffer;

import com.textr.util.Direction;

import java.util.Objects;

public final class DeleteAction implements Action {

    private final char character;
    private final int index;
    private final IText text;
    private final Side side;

    /**
     * Creates a new {@link DeleteAction}.
     * Stores the 1D index and the deleted character.
     * @param index The index.
     * @param character The character.
     */
    public DeleteAction(int index, char character, IText text, Side side){
        this.character = character;
        this.index = index;
        this.text = text;
        this.side = side;
    }

    /**
     * Executes this {@link DeleteAction}.
     */
    public void execute(ICursor cursor){
        Objects.requireNonNull(cursor, "Cursor is null.");
        text.remove(index);
        if(side == Side.BEFORE)
            cursor.move(Direction.LEFT, text.getSkeleton());
    }

    /**
     * Undoes this {@link DeleteAction}.
     */
    public void undo(ICursor cursor){
        Objects.requireNonNull(cursor, "Cursor is null.");
        text.insert(index, character);
        cursor.move(Direction.RIGHT, text.getSkeleton());
    }
}