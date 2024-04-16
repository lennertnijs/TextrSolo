package com.textr.filebuffer;

public final class DeleteAction implements Action {

    private final char character;
    private final int index;

    /**
     * Creates a new {@link DeleteAction}.
     * Stores the 1D index and the deleted character.
     * @param index The index.
     * @param character The character.
     */
    public DeleteAction(int index, char character){
        this.character = character;
        this.index = index;
    }

    /**
     * Executes this {@link DeleteAction}.
     * @param text The text on which to execute the deletion.
     * @param cursor The cursor.
     */
    public void execute(IText text, ICursor cursor){
        text.remove(index);
        cursor.setInsertIndex(index, text.getSkeleton());
    }

    /**
     * Undoes this {@link DeleteAction}.
     * @param text The text on which to undo the deletion.
     * @param cursor The cursor.
     */
    public void undo(IText text, ICursor cursor){
        text.insert(index, character);
        cursor.setInsertIndex(index + 1, text.getSkeleton());
    }
}