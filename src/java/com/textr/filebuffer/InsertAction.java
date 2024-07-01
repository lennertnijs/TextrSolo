package com.textr.filebuffer;

import java.util.Objects;

public final class InsertAction implements Action{

    private final char character;
    private final int index;
    private final FileBuffer buffer;

    /**
     * Creates a new {@link InsertAction}.
     * Stores the 1D index and the inserted character.
     * @param index The index. Cannot be outside the Text's range.
     * @param character The character.
     * @throws IllegalArgumentException If the index is less than 0 or greater than or equal to the character length
     */
    public InsertAction(int index, char character, FileBuffer buffer){
        Objects.requireNonNull(buffer, "Text is null.");
        if(index < 0 || index > buffer.getText().getCharAmount())
            throw new IndexOutOfBoundsException(String.format(
                    "Index %d out of bounds for length %d of text structure", index,  buffer.getText().getCharAmount()
            ));
        this.index = index;
        this.character = character;
        this.buffer = buffer;
    }

    /**
     * Executes this {@link InsertAction}.
     */
    public int execute(){
        return buffer.insert(character, index);
    }

    /**
     * Undoes this {@link InsertAction}.
     */
    public void undo(){
        buffer.delete(index);
    }
}
