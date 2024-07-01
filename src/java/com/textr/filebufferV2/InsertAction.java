package com.textr.filebufferV2;

import java.util.Objects;

/**
 * Represents an insert action.
 */
public final class InsertAction implements Action {

    /**
     * The character to insert
     */
    private final char character;
    /**
     * The index where to insert
     */
    private final int index;
    /**
     * The file buffer in which to insert.
     */
    private final FileBuffer buffer;
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
    public InsertAction(char c, int index, FileBuffer buffer){
        Objects.requireNonNull(buffer, "Text is null.");
        if(index < 0 || index > buffer.getText().getCharAmount()) {
            throw new IndexOutOfBoundsException("Index out of buffer bounds.");
        }
        this.character = c;
        this.index = index;
        this.buffer = buffer;
        this.madeDirty = buffer.getState() == BufferState.CLEAN;
    }

    /**
     * {@inheritDoc}
     */
    public int execute(){
        buffer.insert(character, index);
        if(madeDirty){
            buffer.setState(BufferState.DIRTY);
        }
        return index + 1;
    }

    /**
     * {@inheritDoc}
     */
    public int undo(){
        buffer.delete(index);
        if(madeDirty){
            buffer.setState(BufferState.CLEAN);
        }
        return index;
    }
}
