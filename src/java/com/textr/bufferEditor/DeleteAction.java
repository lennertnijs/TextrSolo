package com.textr.bufferEditor;

import com.textr.filebufferV2.BufferState;
import com.textr.filebufferV2.FileBuffer;

import java.util.Objects;

/**
 * Represents a delete action.
 */
public final class DeleteAction implements Action {

    /**
     * The character to delete
     */
    private final char character;
    /**
     * The index where to delete
     */
    private final int index;
    /**
     * The file buffer in which to delete.
     */
    private final FileBuffer fileBuffer;
    /**
     * Indicates whether this action execution made the buffer dirty.
     */
    private final boolean madeDirty;

    /**
     * Creates an IMMUTABLE {@link InsertAction}.
     * @param c The character to be inserted.
     * @param index The index at which to insert. Cannot be negative or bigger than the length of the buffer's text.
     * @param buffer The file buffer in which to insert. Cannot be null.
     */
    public DeleteAction(char c, int index, FileBuffer buffer){
        Objects.requireNonNull(buffer, "Text is null.");
        if(index < 0 || index >= buffer.getText().getCharAmount())
            throw new IndexOutOfBoundsException("Index is out of text bounds.");
        this.character = c;
        this.index = index;
        this.fileBuffer = buffer;
        this.madeDirty = fileBuffer.getState() == BufferState.CLEAN;
    }

    /**
     * {@inheritDoc}
     */
    public int execute(){
        fileBuffer.delete(index);
        if(madeDirty){
            fileBuffer.setState(BufferState.DIRTY);
        }
        return index;
    }

    /**
     * {@inheritDoc}
     */
    public int undo(){
        fileBuffer.insert(character, index);
        if(madeDirty){
            fileBuffer.setState(BufferState.CLEAN);
        }
        return index + 1;
    }
}