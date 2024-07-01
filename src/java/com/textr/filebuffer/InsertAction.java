package com.textr.filebuffer;

import com.textr.filebufferV2.Action;

import java.util.Objects;

public final class InsertAction implements Action {

    private final char character;
    private final int index;
    private final FileBuffer buffer;

    /**
     * Creates a new {@link InsertAction}.
     * Stores the 1D index and the inserted character.
     * @param index The index. Cannot be outside the Text's range.
     * @param c The character.
     * @throws IllegalArgumentException If the index is less than 0 or greater than or equal to the character length
     */
    public InsertAction(char c, int index, FileBuffer buffer){
        Objects.requireNonNull(buffer, "Text is null.");
        if(index < 0 || index > buffer.getText().getCharAmount()) {
            throw new IndexOutOfBoundsException("Index out of buffer bounds.");
        }
        this.index = index;
        this.character = c;
        this.buffer = buffer;
    }

    /**
     * Executes this {@link InsertAction}.
     */
    public int execute(){
        buffer.insert(character, index);
        return index + 1;
    }

    /**
     * Undoes this {@link InsertAction}.
     */
    public int undo(){
        buffer.delete(index);
        return index;
    }
}
