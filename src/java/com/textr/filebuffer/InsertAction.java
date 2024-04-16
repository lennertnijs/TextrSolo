package com.textr.filebuffer;

public final class InsertAction implements Action{

    private final char character;
    private final int index;

    /**
     * Creates a new {@link InsertAction}.
     * Stores the 1D index and the inserted character.
     * @param index The index.
     * @param character The character.
     */
    public InsertAction(int index, char character){
        this.index = index;
        this.character = character;
    }

    /**
     * Executes this {@link InsertAction}.
     * @param text The text on which to execute the insertion.
     * @param cursor The cursor.
     */
    public void execute(IText text, ICursor cursor){
        text.insert(index, character);
        cursor.setInsertIndex(index + 1, text.getSkeleton());
    }

    /**
     * Undoes this {@link InsertAction}.
     * @param text The text on which to undo the insertion.
     * @param cursor The cursor.
     */
    public void undo(IText text, ICursor cursor){
        text.remove(index);
        cursor.setInsertIndex(index, text.getSkeleton());
    }
}
