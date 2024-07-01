package com.textr.filebuffer;

import java.util.Objects;

public final class DeleteAction implements Action {

    private final char character;
    private final int index;
    private final FileBuffer fileBuffer;

    /**
     * Creates a new {@link DeleteAction}.
     * @param index The index. Cannot be negative or bigger/equal than the length of the text.
     * @param character The character.
     * @throws IllegalArgumentException If the index is less than 0 or greater than or equal to the character length
     */
    public DeleteAction(int index, char character, FileBuffer fileBuffer){
        Objects.requireNonNull(fileBuffer, "Text is null.");
        if(index < 0 || index >= fileBuffer.getText().getCharAmount())
            throw new IndexOutOfBoundsException(String.format(
                    "Index %d out of bounds for length %d of text structure", index, fileBuffer.getText().getCharAmount()
            ));
        this.character = character;
        this.index = index;
        this.fileBuffer = fileBuffer;
    }

    /**
     * Executes this {@link DeleteAction}.
     */
    public int execute(){
        fileBuffer.delete(index);
        return index;
    }

    /**
     * Undoes this {@link DeleteAction}.
     */
    public void undo(){
        fileBuffer.insert(character, index);
    }
}