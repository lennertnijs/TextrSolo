package com.textr.filebuffer;

public final class DeleteAction implements Action {

    private final char character;
    private final int index;
    private final IText text;

    /**
     * Creates a new {@link DeleteAction}.
     * Stores the 1D index and the deleted character.
     * @param index The index.
     * @param character The character.
     */
    public DeleteAction(int index, char character, IText text){
        this.character = character;
        this.index = index;
        this.text = text;
    }

    /**
     * Executes this {@link DeleteAction}.
     */
    public void execute(){
        text.remove(index);
    }

    /**
     * Undoes this {@link DeleteAction}.
     */
    public void undo(){
        text.insert(index, character);
    }
}